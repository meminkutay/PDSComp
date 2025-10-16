package com.pdsdataextractor;

import javafx.scene.control.Alert;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class util_pds {
    public static void updateProgressBar(int current, int total, int barWidth) {
        int completed = (int) ((double) current / total * barWidth);
        StringBuilder progressBar = new StringBuilder("[");

        for (int j = 0; j < barWidth; j++) {
            if (j < completed) {
                progressBar.append("=");
            } else {
                progressBar.append(" ");
            }
        }

        progressBar.append("] ").append((current * 100) / total).append("%");
        System.out.print("\r" + progressBar.toString());
    }

    public static void writeAggregatedCSV(List<Map<String, String>> rows, String filePath) throws Exception {
        if (rows.isEmpty()) return;

        try (PrintWriter writer = new PrintWriter(new FileWriter(new File(filePath)))) {
            List<String> headers = new ArrayList<>(rows.get(0).keySet());
            writer.println(String.join(",", headers));

            for (Map<String, String> row : rows) {
                List<String> values = new ArrayList<>();
                for (String key : headers) {
                    values.add(row.getOrDefault(key, ""));
                }
                writer.println(String.join(",", values));
            }
        }
    }
    public static String validateRequiredColumns(CSVTable table, List<String> requiredCols) {
        for (String col : requiredCols) {
            if (!table.data.containsKey(col)) {
                String errortxt = "🚨 Missing required column: '" + col + "'.\n\n" +
                        "Please choose a CSV file that includes all of the following columns:\n" +
                        requiredCols.stream().map(c -> " - " + c).reduce("", (a, b) -> a + "\n" + b);

                // Show popup alert
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Required Columns");
                alert.setHeaderText("Invalid CSV File");
                alert.setContentText(errortxt);
                alert.showAndWait();

                return errortxt;
            }
        }
        return "";
    }

}
