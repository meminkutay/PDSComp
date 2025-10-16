package com.pdsdataextractor;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class PDSAggregator {

    public static void main(String[] args) {
        String inputFolder = "/Users/kutay/Library/CloudStorage/OneDrive-MichiganStateUniversity/_Docs/_IdeaProjects/pdsdataextractor2Data/SDS-Data/PDS_OUT_JAVA";
        String inputFile = "SDS_2024_noBridge_PDS_JAVA.csv";

        if (args.length == 2) {
            inputFolder = args[0];
            inputFile = args[1];
        } else if (args.length != 0) {
            System.out.println("Usage: java PDSAggregator <inputFolder> <inputFile>");
            System.exit(1);
        }
        aggregateover01mile(inputFolder, inputFile);
    }

    public static CSVTable aggregateover01mile(String inputFolder, String inputFile) {

        try {
            String inputPath = inputFolder + "/" + inputFile;
            System.out.println("Reading file: " + inputPath);
            CSVTable table = new CSVTable(inputPath);
            System.out.println("✅ Done reading file: " + inputPath);

            List<String> requiredColumns = Arrays.asList(
                    "LocID", "PR", "CollectDirection", "Start_Mi", "End_Mi", "LENGTH", "SurveyDateTime",
                    "StaLatitude", "StaLongitude", "EndLatitude", "EndLongitude", "SurfaceType", "NoDataComment", "PDS"
            );
            for (String col : requiredColumns) {
                if (!table.data.containsKey(col)) {
                    throw new IllegalArgumentException("Missing required column in SDS data: " + col);
                }
            }


            // if NormStart_Mi and NormEnd_Mi doesn't exist
            if (!table.header.contains("NormStart_Mi") || !table.header.contains("NormEnd_Mi")) {
                table.data.put("NormStart_Mi", new ArrayList<>());
                table.data.put("NormEnd_Mi", new ArrayList<>());
                table.data.put("SegmentMid_Mi", new ArrayList<>());
                table.header.add("NormStart_Mi");
                table.header.add("NormEnd_Mi");
                table.header.add("SegmentMid_Mi");

                for (int i = 0; i < table.m; i++) {
                    double startMi = Double.parseDouble(table.data.get("Start_Mi").get(i));
                    double endMi = Double.parseDouble(table.data.get("End_Mi").get(i));
                    double normStart = Math.min(startMi, endMi);
                    double normEnd = Math.max(startMi, endMi);
                    double segmentMid = (normStart + normEnd) / 2.0;

                    table.data.get("NormStart_Mi").add(String.valueOf(normStart));
                    table.data.get("NormEnd_Mi").add(String.valueOf(normEnd));
                    table.data.get("SegmentMid_Mi").add(String.valueOf(segmentMid));
                }
            }

            List<Map<String, String>> aggregated = aggregateByPR(table);

            String outputFile = inputFolder + "/" + inputFile.replace(".csv", "_0.1mile_JAVA.csv");
            util_pds.writeAggregatedCSV(aggregated, outputFile);
            System.out.println("\n✅ Aggregated (0.1 mile) PDS saved to: " + outputFile);
            return table;

        } catch (Exception e) {
            System.err.println("❌ Error in aggregation: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private static List<Map<String, String>> aggregateByPR(CSVTable table) {
        List<Map<String, String>> results = new ArrayList<>();
        Set<String> prValues = new HashSet<>(table.data.get("PR"));
        int totalPR = prValues.size();
        int counter = 0;
        int progressBarWidth = 50;

        for (String pr : prValues) {
            counter++;
            System.out.printf("Aggregating rows: averaging over 0.1 mile segments...");
            util_pds.updateProgressBar(counter + 1, totalPR, progressBarWidth);

            // Filter rows with current PR
            List<Integer> indices = new ArrayList<>();
            for (int i = 0; i < table.m; i++) {
                if (table.data.get("PR").get(i).equals(pr)) {
                    indices.add(i);
                }
            }

            // Sort by NormStart_Mi
            indices.sort(Comparator.comparingDouble(i -> Double.parseDouble(table.data.get("NormStart_Mi").get(i))));

            double minMi = Double.parseDouble(table.data.get("NormStart_Mi").get(indices.get(0)));
            double maxMi = Double.parseDouble(table.data.get("NormEnd_Mi").get(indices.get(indices.size() - 1)));

            double current = minMi;
            double nextBin = Math.ceil(current * 10.0) / 10.0;
            nextBin = (nextBin == current) ? roundTo3(nextBin + 0.1) : roundTo3(nextBin);

            while (current < maxMi) {
                if (nextBin > maxMi) nextBin = maxMi;

                List<Integer> binRows = new ArrayList<>();
                for (int idx : indices) {
                    double mid = Double.parseDouble(table.data.get("SegmentMid_Mi").get(idx));
                    if (mid >= current && mid < nextBin) {
                        binRows.add(idx);
                    }
                }

                if (!binRows.isEmpty()) {
                    double totalLength = 0.0;
                    double weightedSum = 0.0;

                    for (int idx : binRows) {
                        double len = Double.parseDouble(table.data.get("LENGTH").get(idx));
                        double pds = Double.parseDouble(table.data.get("PDS").get(idx));
                        totalLength += len;
                        weightedSum += pds * len;
                    }

                    double avgPDS = weightedSum / totalLength;

                    int first = binRows.get(0);
                    int last = binRows.get(binRows.size() - 1);
                    double startMi = Double.parseDouble(table.data.get("Start_Mi").get(first));
                    double endMi = Double.parseDouble(table.data.get("End_Mi").get(first));

                    String StaLat = startMi < endMi ? table.data.get("StaLatitude").get(first) : table.data.get("EndLatitude").get(first);
                    String StaLong = startMi < endMi ? table.data.get("StaLongitude").get(first) : table.data.get("EndLongitude").get(first);
                    String EndLat = startMi < endMi ? table.data.get("EndLatitude").get(last) : table.data.get("StaLatitude").get(last);
                    String EndLong = startMi < endMi ? table.data.get("EndLongitude").get(last) : table.data.get("StaLongitude").get(last);

                    Map<String, String> row = new LinkedHashMap<>();
                    row.put("LocID", table.data.get("LocID").get(first));
                    row.put("PR", pr);
                    row.put("PR_BMP", String.format("%.5f", current));
                    row.put("PR_EMP", String.format("%.5f", nextBin));
                    row.put("LENGTH", String.format("%.5f", nextBin - current));
                    row.put("LENGTH_EXCLUDING_GAPS", String.format("%.5f", totalLength));
                    row.put("PDS", String.format("%.2f", avgPDS));
                    row.put("SurfaceType", table.data.get("SurfaceType").get(first));
                    row.put("CollectDirection", table.data.get("CollectDirection").get(first));
                    row.put("SurveyDateTime", table.data.get("SurveyDateTime").get(first));
                    row.put("StaLatitude", StaLat);
                    row.put("StaLongitude", StaLong);
                    row.put("EndLatitude", EndLat);
                    row.put("EndLongitude", EndLong);

                    results.add(row);
                }

                current = nextBin;
                nextBin = roundTo3(current + 0.1);
            }
        }

        return results;
    }

    private static double roundTo3(double val) {
        return Math.round(val * 1000.0) / 1000.0;
    }


}
