package com.pdsdataextractor;

import java.io.*;
import java.util.*;

public class CSVTable {
    private static final String COMMA_DELIMITER = ",";

    Map<String, List<String>> data;
    List<String> header;

    int n;
    int m; // rown count

    // Add this constructor inside CSVTable.java
    public CSVTable() {
        this.data = new HashMap<>();
        this.header = new ArrayList<>();
        this.m = 0;
    }

    private static int countRows(String fileName) throws IOException {
        int rowCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            // Skip the header
            br.readLine();
            while (br.readLine() != null) {
                rowCount++;
            }
        }
        return rowCount;
    }

    public CSVTable(String fileName) throws Exception {
        int rowCount = countRows(fileName);
        System.out.println("Row count: " + rowCount);

        data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            if (line == null) {
                throw new Exception("File is empty");
            }
            header = new ArrayList<>();
            String[] rawHeaders = line.split(COMMA_DELIMITER, -1);
            for (String rawCol : rawHeaders) {
                String cleanedCol = rawCol.replace("\uFEFF", "").trim(); // Remove BOM and whitespace
                header.add(cleanedCol);
                data.put(cleanedCol, new ArrayList<>());
            }

            // Iterate through each row in the SDS data with a progress bar
            int progressBarWidth = 50;
            m = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER, -1);
                n = 0;
                for (String col : header) {
                    String value = (n < values.length && !values[n].trim().isEmpty()) ? values[n] : "NaN";
                    data.get(col).add(value);
                    n++;
                }
                util_pds.updateProgressBar(m + 1, rowCount, progressBarWidth);

                m++;
            }

            int expectedSize = -1;
            for (String col : data.keySet()) {
                if (expectedSize == -1) {
                    expectedSize = data.get(col).size();
                } else if (data.get(col).size() != expectedSize) {
                    throw new RuntimeException("Inconsistent row sizes detected! Column: " + col + " has " + data.get(col).size() + " rows, expected: " + expectedSize);
                }
            }

            System.out.println("✅ All columns have the same row count: " + expectedSize);
        }
    }

    public static CSVTable concat(CSVTable a, CSVTable b) {
        if (!a.header.equals(b.header)) {
            throw new IllegalArgumentException("Headers do not match. Cannot concatenate tables.");
        }

        CSVTable merged = new CSVTable();
        merged.header.addAll(a.header);

        // Initialize empty lists for each column
        for (String col : merged.header) {
            merged.data.put(col, new ArrayList<>());
        }

        // Add data from table a
        for (int i = 0; i < a.m; i++) {
            for (String col : merged.header) {
                merged.data.get(col).add(a.data.get(col).get(i));
            }
        }

        // Add data from table b
        for (int i = 0; i < b.m; i++) {
            for (String col : merged.header) {
                merged.data.get(col).add(b.data.get(col).get(i));
            }
        }

        merged.m = merged.data.get(merged.header.get(0)).size();  // Set row count
        return merged;
    }

    public void writeToFile(String fileName) throws IOException {
        if (!fileName.endsWith(".csv")) {
            fileName = fileName + ".csv";
        }

        FileWriter fileWriter = new FileWriter(fileName);
        PrintWriter printWriter = new PrintWriter(fileWriter);
        List<String> row = new ArrayList<>();
        for (String col : header) {
            row.add(col);
        }
        printWriter.println(String.join(",", row));
        int i = 0;
        while (i < m) {
            row.clear();
            for (String col : header) {
                row.add(data.get(col).get(i));
            }
            printWriter.println(String.join(",", row));
            i++;
        }
        printWriter.close();
    }

    public void writeToConsole() {
        List<String> row = new ArrayList<>();
        for (String col : header) {
            row.add(col);
        }
        System.out.println(String.join(",", row));
        int i = 0;
        while (i < m) {
            row.clear();
            for (String col : header) {
                row.add(data.get(col).get(i));
            }
            System.out.println(String.join(",", row));
            i++;
        }
    }
}
