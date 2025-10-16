package com.pdsdataextractor;

import java.util.*;

public class pds_from_sds {

    public static void main(String[] args) {
        String inputFolder = "/Users/kutay/Library/CloudStorage/OneDrive-MichiganStateUniversity/_Docs/_IdeaProjects/pdsdataextractor2Data/SDS-Data/";
        String inputFile = "SDS_2023_noBridge_SurfaceType_Asphalt.csv";

        if (args.length == 2) {
            inputFolder = args[0];
            inputFile = args[1];
        } else if (args.length != 0) {
            System.out.println("\nUsage: java pds_from_sds <inputFolder> <inputFile>");
            System.exit(1);
        }
        String inputFilePath = inputFolder + "/" + inputFile;
        String inputFile_noext = inputFile.substring(0, inputFile.length() - 4);
        String outputFile = inputFolder + "/" + inputFile_noext + "_PDS_JAVA.csv";

        compute_pds_from_sds_data(inputFilePath, outputFile);
    }

    public static CSVTable compute_pds_from_sds_data(String inputFilePath, String outputFile) {

        System.out.println("Reading file: " + inputFilePath);

        try {
            CSVTable sdsData = new CSVTable(inputFilePath);
            System.out.println("✅ Done reading file: " + inputFilePath);
            List<String> requiredColumns = Arrays.asList(
                    "LocID", "PR", "CollectDirection", "Start_Mi", "End_Mi", "LENGTH", "SurveyDateTime",
                    "StaLatitude", "StaLongitude", "EndLatitude", "EndLongitude", "SurfaceType", "NoDataComment",
                    "LCUS_Len_Z1_Low", "LCUS_Len_Z1_Mod", "LCUS_Len_Z1_High", "LCUS_Len_Z2_Low", "LCUS_Len_Z2_Mod",
                    "LCUS_Len_Z2_High", "LCUS_Len_Z3_Low", "LCUS_Len_Z3_Mod", "LCUS_Len_Z3_High",
                    "LCUS_Len_Z4_Low", "LCUS_Len_Z4_Mod", "LCUS_Len_Z4_High", "LCUS_Len_Z5_Low",
                    "LCUS_Len_Z5_Mod", "LCUS_Len_Z5_High", "LCS_Len_Z1", "LCS_Len_Z2", "LCS_Len_Z3",
                    "LCS_Len_Z4", "LCS_Len_Z5", "TCUSFL_Len_Low", "TCUSFL_Len_Mod", "TCUSFL_Len_High",
                    "TCSFL_Len", "Patch_Area_Conc", "Patch_Area_Asphalt", "PH_Area", "PH_Cnt_Low",
                    "PH_Cnt_Mod", "PH_Cnt_High", "ALLIG_Area_Z1_Low", "ALLIG_Area_Z1_Mod", "ALLIG_Area_Z1_High",
                    "ALLIG_Area_Z2_Low", "ALLIG_Area_Z2_Mod", "ALLIG_Area_Z2_High", "ALLIG_Area_Z3_Low",
                    "ALLIG_Area_Z3_Mod", "ALLIG_Area_Z3_High", "ALLIG_Area_Z4_Low", "ALLIG_Area_Z4_Mod",
                    "ALLIG_Area_Z4_High", "ALLIG_Area_Z5_Low", "ALLIG_Area_Z5_Mod", "ALLIG_Area_Z5_High",
                    "ALLIG_Len_Z1_Low", "ALLIG_Len_Z1_Mod", "ALLIG_Len_Z1_High", "ALLIG_Len_Z2_Low",
                    "ALLIG_Len_Z2_Mod", "ALLIG_Len_Z2_High", "ALLIG_Len_Z3_Low", "ALLIG_Len_Z3_Mod",
                    "ALLIG_Len_Z3_High", "ALLIG_Len_Z4_Low", "ALLIG_Len_Z4_Mod", "ALLIG_Len_Z4_High",
                    "ALLIG_Len_Z5_Low", "ALLIG_Len_Z5_Mod", "ALLIG_Len_Z5_High", "Rav_Area", "Bleed_Area",
                    "TJ_Cnt_None", "TJ_Cnt_Low", "TJ_Cnt_Mod", "TJ_Cnt_High", "LJ_Len_Z1_Low", "LJ_Len_Z1_Mod",
                    "LJ_Len_Z1_High", "LJ_Len_Z5_Low", "LJ_Len_Z5_Mod", "LJ_Len_Z5_High", "TJS_Len_Pct",
                    "LJS_Len_Pct_Z1", "LJS_Len_Pct_Z5", "CB_Cnt_Low", "CB_Cnt_Mod", "CB_Cnt_High",
                    "Punch_Cnt", "Punch_Area"
            );
            for (String col : requiredColumns) {
                if (!sdsData.data.containsKey(col)) {
                    throw new IllegalArgumentException("Missing required column in SDS data: " + col);
                }
            }

            String columnNameToFilterOut = "NoDataComment";
            System.out.println("Eliminating the nonempty rows of NoDataComment");

            sdsData = filterByColumnValue(sdsData, columnNameToFilterOut);
            // sdsData.writeToFile(inputFolder + "/" + inputFile + "_dummy.csv");
            System.out.println("✅ Done eliminating ");
            //  sdsData.writeToConsole();

            List<String> baseColumns = Arrays.asList(
                    "LocID", "PR", "CollectDirection", "Start_Mi", "End_Mi", "LENGTH",
                    "SurveyDateTime", "StaLatitude", "StaLongitude", "EndLatitude",
                    "EndLongitude", "SurfaceType", "NoDataComment"
            );

            // For DEBUG
            //System.out.println("Available keys in sdsData:");
            //for (String key : sdsData.data.keySet()) {
            //    System.out.print("Key: [");
            //    for (char c : key.toCharArray()) {
            // System.out.print(c + "(" + Integer.toHexString(c) + ") ");
            //    }
            //    System.out.println("] -> raw: \"" + key + "\"");
            //}
            CSVTable PDS_out_data = new CSVTable();
            PDS_out_data.header = new ArrayList<>(baseColumns);

            for (String col : baseColumns) {
                if (!sdsData.data.containsKey(col)) {
                    throw new IllegalArgumentException("Missing required column in SDS data: " + col);
                }
                PDS_out_data.data.put(col, new ArrayList<>(sdsData.data.get(col)));
            }

            PDS_out_data.m = sdsData.m;

            // Add 'PDS' column with default value -1
            PDS_out_data.header.add("PDS");
            PDS_out_data.data.put("PDS", new ArrayList<>());
            for (int i = 0; i < sdsData.m; i++) {
                PDS_out_data.data.get("PDS").add("-1");
            }

            // Add 'NormStart_Mi' and 'NormEnd_Mi' columns
            sdsData.data.put("NormStart_Mi", new ArrayList<>());
            sdsData.data.put("NormEnd_Mi", new ArrayList<>());
            sdsData.header.add("NormStart_Mi");
            sdsData.header.add("NormEnd_Mi");

            for (int i = 0; i < sdsData.m; i++) {
                String startMi = sdsData.data.get("Start_Mi").get(i);
                String endMi = sdsData.data.get("End_Mi").get(i);

                double startValue = startMi.equals("NaN") ? Double.NaN : Double.parseDouble(startMi);
                double endValue = endMi.equals("NaN") ? Double.NaN : Double.parseDouble(endMi);

                double normStart = Math.min(startValue, endValue);
                double normEnd = Math.max(startValue, endValue);

                sdsData.data.get("NormStart_Mi").add(String.valueOf(normStart));
                sdsData.data.get("NormEnd_Mi").add(String.valueOf(normEnd));
            }

            //sdsData.writeToFile(inputFolder + "/" + sliced + "_dummy.csv");
            //PDS_out_data.writeToFile(inputFolder + "/" + sliced + "_PDS_out_data_dummy.csv");
            //System.out.println("PDS_out_data initialized");

            // Start the PDS calculations
            System.out.println("Calculating TWD and PDS ... \n");
            PDS_out_data = do_compute_twd_pds(sdsData, PDS_out_data);
            System.out.println("\n Done executing 'do_compute_twd_pds' function \n");

            // Write the output file
            System.out.println("Writing PDS to " + outputFile);
            PDS_out_data.writeToFile(outputFile);
            System.out.println("\n✅ PDS Results saved to: " + outputFile);
            return PDS_out_data;

        } catch (Exception e) {
            System.err.println("Error in compute_pds_from_sds_data function: " + e.getMessage());
            return null;
        }
    }

    private static CSVTable do_compute_twd_pds(CSVTable sdsData, CSVTable PDS_out_data) {


        List<String> pdsList = new ArrayList<>();

        // GET THE PDS COEFFICIENTS
        Map<String, double[]> df_coeffs = getCoeffs();

        // Iterate through each row in the SDS data with a progress bar
        int totalRows = sdsData.m;
        int progressBarWidth = 50;

        // Iterate through each row in the SDS data
        for (int i = 0; i < sdsData.m; i++) {
            // Update progress bar
            util_pds.updateProgressBar(i + 1, totalRows, progressBarWidth);

            // Call to calculate_twd_and_pds
            double[] result = eachrow_calculate_twd_and_pds(i, sdsData, df_coeffs);
            // double twd = result[0];
            double pds = result[1];
            pdsList.add(String.valueOf(pds));

        }

        // Assign PDS to PDS_out_data
        PDS_out_data.data.put("PDS", pdsList);

        // Copy NormStart_Mi and NormEnd_Mi to PDS_out_data
        PDS_out_data.header.add("NormStart_Mi");
        PDS_out_data.header.add("NormEnd_Mi");

        PDS_out_data.data.put("NormStart_Mi", new ArrayList<>(sdsData.data.get("NormStart_Mi")));
        PDS_out_data.data.put("NormEnd_Mi", new ArrayList<>(sdsData.data.get("NormEnd_Mi")));

        // Calculate SegmentMid_Mi and add to PDS_out_data
        PDS_out_data.header.add("SegmentMid_Mi");
        PDS_out_data.data.put("SegmentMid_Mi", new ArrayList<>());

        for (int i = 0; i < sdsData.m; i++) {
            String normStartStr = sdsData.data.get("NormStart_Mi").get(i);
            String normEndStr = sdsData.data.get("NormEnd_Mi").get(i);

            double normStart = normStartStr.equals("NaN") ? Double.NaN : Double.parseDouble(normStartStr);
            double normEnd = normEndStr.equals("NaN") ? Double.NaN : Double.parseDouble(normEndStr);

            double segmentMid = (normStart + normEnd) / 2;
            PDS_out_data.data.get("SegmentMid_Mi").add(String.valueOf(segmentMid));
        }
        return PDS_out_data;

    }



    private static double[] eachrow_calculate_twd_and_pds(int rowIndex, CSVTable sdsData, Map<String, double[]> coeffs) {
        double totalWeightedDistress = 0.0;
        Map<String, Double> valuePercentDict = new HashMap<>();

        double lengthSectFt = Double.parseDouble(sdsData.data.get("LENGTH").get(rowIndex)) * 5280;
        String surfaceType = sdsData.data.get("SurfaceType").get(rowIndex).toLowerCase();

        // Constants
        double laneWidthFt = 12.0;
        double tcSpacingFt = 10.0;
        double zoneWidthFt = laneWidthFt / 5.0;
        double jointSpacingFt = 15.0;

        for (String attribute : coeffs.keySet()) {
            if (sdsData.data.containsKey(attribute)) {
                String rawValueStr = sdsData.data.get(attribute).get(rowIndex);
                double rawValue = rawValueStr.equals("NaN") ? Double.NaN : Double.parseDouble(rawValueStr);

                if (!Double.isNaN(rawValue)) {
                    double valuePercent = 0.0;

                    try {
                        valuePercent = calculateValuePercent(attribute, rawValue, lengthSectFt, laneWidthFt, jointSpacingFt, tcSpacingFt, zoneWidthFt);
                        valuePercent = Math.round(valuePercent * 100.0) / 100.0; // Round to 2 decimal places

                        valuePercentDict.put(attribute + "_P", valuePercent);

                        double[] coefficients = coeffs.get(attribute);
                        // if asphalt, use the first coefficient, otherwise (i.e., concrete) use the second one.
                        double coefficient = surfaceType.equals("asphalt") ? coefficients[0] : coefficients[1];

                        totalWeightedDistress += valuePercent * coefficient;

                    } catch (Exception e) {
                        System.err.println("Error processing attribute: " + attribute + " - " + e.getMessage());
                    }
                }
            }
        }

        // Calculate PDS
        double pds = 25 * Math.exp(1.386294 - 0.045 * totalWeightedDistress);
        pds = Math.round(pds * 100.0) / 100.0;  // Round to 2 decimal places
        totalWeightedDistress = Math.round(totalWeightedDistress * 100.0) / 100.0;

        return new double[]{totalWeightedDistress, pds};
    }


    private static double calculateValuePercent(String attribute, double rawValue, double lengthSectFt, double laneWidthFt, double jointSpacingFt, double tcSpacingFt, double zoneWidthFt) {
        switch (attribute) {
            case "CB_Cnt_Low":
            case "CB_Cnt_Mod":
            case "CB_Cnt_High":
                return 0.25 * 100.0 * rawValue / (lengthSectFt / jointSpacingFt);

            case "TCUSFL_Len_Low":
            case "TCUSFL_Len_Mod":
            case "TCUSFL_Len_High":
            case "TCSFL_Len":
                return 100.0 * rawValue / (laneWidthFt * (lengthSectFt / tcSpacingFt));

            case "Patch_Area_Conc":
            case "Patch_Area_Asphalt":
            case "PH_Area":
            case "Rav_Area":
            case "Bleed_Area":
            case "Punch_Area":
                return 100.0 * rawValue / (laneWidthFt * lengthSectFt);

            case "TJ_Cnt_Low":
            case "TJ_Cnt_Mod":
            case "TJ_Cnt_High":
                return 100.0 * rawValue / (lengthSectFt / jointSpacingFt);

            case "ALLIG_Area_Z1_Low":
            case "ALLIG_Area_Z1_Mod":
            case "ALLIG_Area_Z1_High":
            case "ALLIG_Area_Z2_Low":
            case "ALLIG_Area_Z2_Mod":
            case "ALLIG_Area_Z2_High":
            case "ALLIG_Area_Z3_Low":
            case "ALLIG_Area_Z3_Mod":
            case "ALLIG_Area_Z3_High":
            case "ALLIG_Area_Z4_Low":
            case "ALLIG_Area_Z4_Mod":
            case "ALLIG_Area_Z4_High":
            case "ALLIG_Area_Z5_Low":
            case "ALLIG_Area_Z5_Mod":
            case "ALLIG_Area_Z5_High":
                return 100.0 * rawValue / (zoneWidthFt * lengthSectFt);

            case "LCUS_Len_Z1_Low":
            case "LCUS_Len_Z1_Mod":
            case "LCUS_Len_Z1_High":
            case "LCUS_Len_Z2_Low":
            case "LCUS_Len_Z2_Mod":
            case "LCUS_Len_Z2_High":
            case "LCUS_Len_Z3_Low":
            case "LCUS_Len_Z3_Mod":
            case "LCUS_Len_Z3_High":
            case "LCUS_Len_Z4_Low":
            case "LCUS_Len_Z4_Mod":
            case "LCUS_Len_Z4_High":
            case "LCUS_Len_Z5_Low":
            case "LCUS_Len_Z5_Mod":
            case "LCUS_Len_Z5_High":
            case "LCS_Len_Z1":
            case "LCS_Len_Z2":
            case "LCS_Len_Z3":
            case "LCS_Len_Z4":
            case "LCS_Len_Z5":
            case "LJ_Len_Z1_Low":
            case "LJ_Len_Z1_Mod":
            case "LJ_Len_Z1_High":
            case "LJ_Len_Z5_Low":
            case "LJ_Len_Z5_Mod":
            case "LJ_Len_Z5_High":
                return 100.0 * rawValue / lengthSectFt;

            default:
                return 0.0;
        }
    }

    public static Map<String, double[]> getCoeffs() {
        Map<String, double[]> coeffDict = new HashMap<>();

        coeffDict.put("LCUS_Len_Z1_Low", new double[]{0.0055, 0});
        coeffDict.put("LCUS_Len_Z1_Mod", new double[]{0.0155, 0});
        coeffDict.put("LCUS_Len_Z1_High", new double[]{0.042, 0});
        coeffDict.put("LCUS_Len_Z2_Low", new double[]{0.0055, 0.0225});
        coeffDict.put("LCUS_Len_Z2_Mod", new double[]{0.109, 0.03375});
        coeffDict.put("LCUS_Len_Z2_High", new double[]{0.4155, 0.045});
        coeffDict.put("LCUS_Len_Z3_Low", new double[]{0.0088, 0.036});
        coeffDict.put("LCUS_Len_Z3_Mod", new double[]{0.1744, 0.054});
        coeffDict.put("LCUS_Len_Z3_High", new double[]{0.6648, 0.072});
        coeffDict.put("LCUS_Len_Z4_Low", new double[]{0.0055, 0.0225});
        coeffDict.put("LCUS_Len_Z4_Mod", new double[]{0.109, 0.03375});
        coeffDict.put("LCUS_Len_Z4_High", new double[]{0.4155, 0.045});
        coeffDict.put("LCUS_Len_Z5_Low", new double[]{0.0055, 0});
        coeffDict.put("LCUS_Len_Z5_Mod", new double[]{0.0155, 0});
        coeffDict.put("LCUS_Len_Z5_High", new double[]{0.042, 0});

        coeffDict.put("LCS_Len_Z1", new double[]{0.00275, 0});
        coeffDict.put("LCS_Len_Z2", new double[]{0.00275, 0.01125});
        coeffDict.put("LCS_Len_Z3", new double[]{0.0044, 0.018});
        coeffDict.put("LCS_Len_Z4", new double[]{0.00275, 0.01125});
        coeffDict.put("LCS_Len_Z5", new double[]{0.00275, 0});

        coeffDict.put("TCUSFL_Len_Low", new double[]{0.017, 0.0225});
        coeffDict.put("TCUSFL_Len_Mod", new double[]{0.028, 0.225});
        coeffDict.put("TCUSFL_Len_High", new double[]{0.884, 0.45});
        coeffDict.put("TCSFL_Len", new double[]{0.0085, 0.01125});

        coeffDict.put("Patch_Area_Conc", new double[]{0, 0.315});
        coeffDict.put("Patch_Area_Asphalt", new double[]{0, 0.315});
        coeffDict.put("PH_Area", new double[]{0.4903, 0.6});

        coeffDict.put("ALLIG_Area_Z1_Low", new double[]{0.00825, 0});
        coeffDict.put("ALLIG_Area_Z1_Mod", new double[]{0.02325, 0});
        coeffDict.put("ALLIG_Area_Z1_High", new double[]{0.063, 0});
        coeffDict.put("ALLIG_Area_Z2_Low", new double[]{0.00825, 0});
        coeffDict.put("ALLIG_Area_Z2_Mod", new double[]{0.1635, 0});
        coeffDict.put("ALLIG_Area_Z2_High", new double[]{0.62325, 0});
        coeffDict.put("ALLIG_Area_Z3_Low", new double[]{0.0132, 0});
        coeffDict.put("ALLIG_Area_Z3_Mod", new double[]{0.2616, 0});
        coeffDict.put("ALLIG_Area_Z3_High", new double[]{0.9972, 0});
        coeffDict.put("ALLIG_Area_Z4_Low", new double[]{0.00825, 0});
        coeffDict.put("ALLIG_Area_Z4_Mod", new double[]{0.1635, 0});
        coeffDict.put("ALLIG_Area_Z4_High", new double[]{0.62325, 0});
        coeffDict.put("ALLIG_Area_Z5_Low", new double[]{0.00825, 0});
        coeffDict.put("ALLIG_Area_Z5_Mod", new double[]{0.02325, 0});
        coeffDict.put("ALLIG_Area_Z5_High", new double[]{0.063, 0});

        coeffDict.put("Rav_Area", new double[]{0.4903, 0});
        coeffDict.put("Bleed_Area", new double[]{0.012, 0});

        coeffDict.put("TJ_Cnt_None", new double[]{0, 0});
        coeffDict.put("TJ_Cnt_Low", new double[]{0, 0.0225});
        coeffDict.put("TJ_Cnt_Mod", new double[]{0, 0.225});
        coeffDict.put("TJ_Cnt_High", new double[]{0, 0.45});

        coeffDict.put("LJ_Len_Z1_Low", new double[]{0, 0.1125});
        coeffDict.put("LJ_Len_Z1_Mod", new double[]{0, 0.16875});
        coeffDict.put("LJ_Len_Z1_High", new double[]{0, 0.225});
        coeffDict.put("LJ_Len_Z5_Low", new double[]{0, 0.1125});
        coeffDict.put("LJ_Len_Z5_Mod", new double[]{0, 0.16875});
        coeffDict.put("LJ_Len_Z5_High", new double[]{0, 0.225});

        coeffDict.put("TJS_Len_Pct", new double[]{0, 0});
        coeffDict.put("LJS_Len_Pct_Z1", new double[]{0, 0});
        coeffDict.put("LJS_Len_Pct_Z5", new double[]{0, 0});

        coeffDict.put("CB_Cnt_Low", new double[]{0, 0.15});
        coeffDict.put("CB_Cnt_Mod", new double[]{0, 0.4});
        coeffDict.put("CB_Cnt_High", new double[]{0, 0.6});

        coeffDict.put("Punch_Cnt", new double[]{0, 0});
        coeffDict.put("Punch_Area", new double[]{0, 0.6});

        return coeffDict;
    }

    public static CSVTable filterByColumnValue(CSVTable sdsData, String columnNameToFilterOut) {
        if (!sdsData.header.contains(columnNameToFilterOut)) {
            throw new IllegalArgumentException("Column not found: " + columnNameToFilterOut);
        }

        CSVTable filteredTable = new CSVTable();
        filteredTable.header = new ArrayList<>(sdsData.header);
        for (String col : sdsData.header) {
            filteredTable.data.put(col, new ArrayList<>());
        }

        // Iterate through each row in the SDS data with a progress bar
        int totalRows = sdsData.m;
        int progressBarWidth = 50;

        for (int i = 0; i < sdsData.m; i++) {
            // Update progress bar
            util_pds.updateProgressBar(i + 1, totalRows, progressBarWidth);

            String value = sdsData.data.get(columnNameToFilterOut).get(i);
            if (value.equals("NaN")) {
                for (String col : sdsData.header) {
                    filteredTable.data.get(col).add(sdsData.data.get(col).get(i));
                }
                filteredTable.m++;
            }
        }

        return filteredTable;
    }
}
