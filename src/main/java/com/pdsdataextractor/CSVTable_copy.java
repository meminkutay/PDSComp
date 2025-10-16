package com.pdsdataextractor;

import java.io.*;
import java.util.*;

public class CSVTable_copy {
    private static final String COMMA_DELIMITER = ",";

    Map<String, List<String>> data;
    List<String> header;

    int n;
    int m; // rown count

    // Add this constructor inside CSVTable.java
    public CSVTable_copy() {
        this.data = new HashMap<>();
        this.header = new ArrayList<>();
        this.m = 0;
    }

    public CSVTable_copy(String fileName) throws Exception {
        data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line = br.readLine();
            if (line == null) {
                throw new Exception();
            }
            header = new ArrayList<>();
            header.addAll(Arrays.asList(line.split(COMMA_DELIMITER)));
            for (String col : header) {
                data.put(col, new ArrayList<>());
            }
            m = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                n = 0;
                if (values.length > header.size()) {
                    throw new Exception("🚨 Row " + (m + 1) + " has more columns (" + values.length + ") than the header (" + header.size() + ")");
                }

                for (String value : values) {
                    String colname = header.get(n++);
                    if ((colname.equals("CS") || colname.equals("BMP") || colname.equals("EMP") || colname.equals("DIR")) && value.equals("NaN")) {
                        throw new Exception("!!!! There is NaN in column: " + colname + ". Check the data in the sheet below!!!\n"
                                + fileName + "\n All the NaNs need to be removed from the sheet before continuing...\n\n");
                    } else {
                        data.get(colname).add(value);
                    }

                }
                m++;
            }

            // Determine expected row size from the first column
            int expectedSize = -1;
            for (String col : data.keySet()) {
                if (expectedSize == -1) {
                    expectedSize = data.get(col).size(); // Set initial expected size
                } else if (data.get(col).size() != expectedSize) {
                    throw new RuntimeException("🚨 Inconsistent row sizes detected! \n File:" + fileName + " Column: " + col +
                            " has " + data.get(col).size() + " rows, expected: " + expectedSize);
                }
            }

            //            // Print column sizes (for debugging)
            //            for (String col : data.keySet()) {
            //                System.out.println("Column: " + col + " -> Size: " + data.get(col).size());
            //            }

            System.out.println("✅ All columns have the same row count: " + expectedSize);


        }
    }
    public static CSVTable_copy concat(CSVTable_copy a, CSVTable_copy b) {
        if (!a.header.equals(b.header)) {
            throw new IllegalArgumentException("Headers do not match. Cannot concatenate tables.");
        }

        CSVTable_copy merged = new CSVTable_copy();
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
