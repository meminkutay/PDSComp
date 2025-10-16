package com.pdsdataextractor;

import java.util.*;

public class tableProcessorRIGID {


    public tableProcessorRIGID() {
    }

    public static CSVTable process(String RIGID_pvmtlist_path_S, String fpath_pmscsv) throws Exception {

        CSVTable PvmtListToAnalyze = new CSVTable(RIGID_pvmtlist_path_S);

        System.out.println("Loading " + fpath_pmscsv);
        CSVTable PDDataTable = new CSVTable(fpath_pmscsv);
        System.out.println("Done loading");

        // Assumed that there is only one year in each csv
        String SURVEY_YEAR = PDDataTable.data.get("SURVEY_YEAR").get(0);

        PvmtListToAnalyze.header.add("SURVEY_YEAR");
        PvmtListToAnalyze.data.put("SURVEY_YEAR", new ArrayList<>());

        PvmtListToAnalyze.header.add("PDS");
        PvmtListToAnalyze.data.put("PDS", new ArrayList<>());

        PvmtListToAnalyze.header.add("Total_Weighted_Distress");
        PvmtListToAnalyze.data.put("Total_Weighted_Distress", new ArrayList<>());

        // Transverse Cracking
        PvmtListToAnalyze.header.add("TCSFL_Perc");// sealed
        PvmtListToAnalyze.data.put("TCSFL_Perc", new ArrayList<>());// sealed
        PvmtListToAnalyze.header.add("TCUSFL_Perc_Low");
        PvmtListToAnalyze.data.put("TCUSFL_Perc_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("TCUSFL_Perc_Mod");
        PvmtListToAnalyze.data.put("TCUSFL_Perc_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("TCUSFL_Perc_High");
        PvmtListToAnalyze.data.put("TCUSFL_Perc_High", new ArrayList<>());

        // Longitudinal cracking - Centerline
        PvmtListToAnalyze.header.add("LCS_Perc_Z3"); // sealed
        PvmtListToAnalyze.data.put("LCS_Perc_Z3", new ArrayList<>());// sealed
        PvmtListToAnalyze.header.add("LCUS_Perc_Z3_Low");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z3_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z3_Mod");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z3_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z3_High");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z3_High", new ArrayList<>());

        // Longitudinal cracking - Zones 2 and 4 -- Wheelpaths
        PvmtListToAnalyze.header.add("LCS_Perc_Z2");// sealed
        PvmtListToAnalyze.data.put("LCS_Perc_Z2", new ArrayList<>());// sealed
        PvmtListToAnalyze.header.add("LCUS_Perc_Z2_Low");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z2_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z2_Mod");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z2_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z2_High");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z2_High", new ArrayList<>());

        PvmtListToAnalyze.header.add("LCS_Perc_Z4");// sealed
        PvmtListToAnalyze.data.put("LCS_Perc_Z4", new ArrayList<>());// sealed
        PvmtListToAnalyze.header.add("LCUS_Perc_Z4_Low");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z4_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z4_Mod");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z4_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z4_High");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z4_High", new ArrayList<>());

        // Longitudinal joints - Zones 1 and 5 - Edges
        PvmtListToAnalyze.header.add("LJ_Perc_Z1_Low");
        PvmtListToAnalyze.data.put("LJ_Perc_Z1_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("LJ_Perc_Z1_Mod");
        PvmtListToAnalyze.data.put("LJ_Perc_Z1_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("LJ_Perc_Z1_High");
        PvmtListToAnalyze.data.put("LJ_Perc_Z1_High", new ArrayList<>());

        PvmtListToAnalyze.header.add("LJ_Perc_Z5_Low");
        PvmtListToAnalyze.data.put("LJ_Perc_Z5_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("LJ_Perc_Z5_Mod");
        PvmtListToAnalyze.data.put("LJ_Perc_Z5_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("LJ_Perc_Z5_High");
        PvmtListToAnalyze.data.put("LJ_Perc_Z5_High", new ArrayList<>());

        PvmtListToAnalyze.header.add("Delamination_avg");
        PvmtListToAnalyze.data.put("Delamination_avg", new ArrayList<>());

        PvmtListToAnalyze.header.add("TJ_Perc_Low");
        PvmtListToAnalyze.data.put("TJ_Perc_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("TJ_Perc_Mod");
        PvmtListToAnalyze.data.put("TJ_Perc_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("TJ_Perc_High");
        PvmtListToAnalyze.data.put("TJ_Perc_High", new ArrayList<>());

        PvmtListToAnalyze.header.add("Patch_Perc_Asphalt");
        PvmtListToAnalyze.data.put("Patch_Perc_Asphalt", new ArrayList<>());

        PvmtListToAnalyze.header.add("Patch_Perc_Conc");
        PvmtListToAnalyze.data.put("Patch_Perc_Conc", new ArrayList<>());

        // Following is up to date 4/24/25
        Map<String, Double> distressCoefficients = new HashMap<>();
        distressCoefficients.put("LCUS_Perc_Z2_Low", 0.0225);
        distressCoefficients.put("LCUS_Perc_Z2_Mod", 0.03375);
        distressCoefficients.put("LCUS_Perc_Z2_High", 0.045);
        distressCoefficients.put("LCUS_Perc_Z3_Low", 0.036);
        distressCoefficients.put("LCUS_Perc_Z3_Mod", 0.054);
        distressCoefficients.put("LCUS_Perc_Z3_High", 0.072);
        distressCoefficients.put("LCUS_Perc_Z4_Low", 0.0225);
        distressCoefficients.put("LCUS_Perc_Z4_Mod", 0.03375);
        distressCoefficients.put("LCUS_Perc_Z4_High", 0.045);
        distressCoefficients.put("LCS_Perc_Z2", 0.01125);
        distressCoefficients.put("LCS_Perc_Z3", 0.018);
        distressCoefficients.put("LCS_Perc_Z4", 0.01125);
        distressCoefficients.put("TCUSFL_Perc_Low", 0.0225);
        distressCoefficients.put("TCUSFL_Perc_Mod", 0.225);
        distressCoefficients.put("TCUSFL_Perc_High", 0.45);
        distressCoefficients.put("TCSFL_Perc", 0.01125);
        distressCoefficients.put("Patch_Perc_Conc", 0.315);
        distressCoefficients.put("Patch_Perc_Asphalt", 0.315);
        distressCoefficients.put("TJ_Perc_Low", 0.0225);
        distressCoefficients.put("TJ_Perc_Mod", 0.225);
        distressCoefficients.put("TJ_Perc_High", 0.45);
        distressCoefficients.put("LJ_Perc_Z1_Low", 0.1125);
        distressCoefficients.put("LJ_Perc_Z1_Mod", 0.16875);
        distressCoefficients.put("LJ_Perc_Z1_High", 0.225);
        distressCoefficients.put("LJ_Perc_Z5_Low", 0.1125);
        distressCoefficients.put("LJ_Perc_Z5_Mod", 0.16875);
        distressCoefficients.put("LJ_Perc_Z5_High", 0.225);

        // Define the metadata columns that should NOT be cleared to "NaN"
        //        Set<String> metadataColumns = new HashSet<>(Arrays.asList(
        //                "Pavement Type", "Type", "ID", "REGION", "Route", "Location",
        //                "CS", "JN", "BMP", "EMP", "DIR", "OPENED", "SURFACE", "SURVEY_YEAR"
        //        ));

        Set<String> metadataColumns = new HashSet<>(Arrays.asList(
                "ID", "SURFACE", "PARENT_FIX", "ROUTE", "DIR-WAY", "REGION", "TIER",
                "YEAR_OPEN", "YEAR_END", "LIFE", "PR", "PR_BMP", "PR_EMP", "CS", "DIR",
                "BMP", "EMP", "LENGTH", "N_LANES", "LANE_MILES", "JOB_NO",
                "CONTRACT_ID", "LANE_MILE_COST", "F_NF", "SURVEY_YEAR"
        ));


        long start = System.currentTimeMillis();

        for (int i = 0; i < PvmtListToAnalyze.m; i++) {
            String CS = PvmtListToAnalyze.data.get("CS").get(i);
            String BMP = PvmtListToAnalyze.data.get("BMP").get(i);
            String EMP = PvmtListToAnalyze.data.get("EMP").get(i);
            String DIR = PvmtListToAnalyze.data.get("DIR").get(i);

            PvmtListToAnalyze.data.get("SURVEY_YEAR").add(SURVEY_YEAR);

            String status_sout = String.format("-->Analyzing CS = %s, DIR = %s, BMP = %s, EMP = %s, YR = %s", CS, DIR, BMP, EMP, SURVEY_YEAR);
            System.out.println(status_sout);

            Map<String, Set<Integer>> idxALL = f_get_idx(PDDataTable, CS, SURVEY_YEAR, DIR, BMP, EMP);
            // Check if all indices are empty
            boolean allEmpty = idxALL.values().stream().allMatch(Set::isEmpty);

            if (allEmpty) {
                status_sout = String.format("!!!No distress data found for " +
                        "CS = %s, DIR = %s, BMP = %s, EMP = %s, YR = %s, " +
                        "setting all distress-related values to NaN.", CS, DIR, BMP, EMP, SURVEY_YEAR);
                System.out.println(status_sout);

                // Fill only distress-related columns with "NaN"
                // System.out.println("**ID** :" + PvmtListToAnalyze.data.get("ID"));

                for (String key : PvmtListToAnalyze.data.keySet()) {
                    if (!metadataColumns.contains(key)) { // Exclude metadata columns
                        PvmtListToAnalyze.data.get(key).add("NaN");
                    }
                    // System.out.println(key + ": " + PvmtListToAnalyze.data.get(key));
                }

            } else {
                // Transverse cracking
                Map<String, String> TransverseCrack = processTransverseCrack(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_TransverseCrack"));
                PvmtListToAnalyze.data.get("TCSFL_Perc").add(TransverseCrack.get("sealed_avg"));
                PvmtListToAnalyze.data.get("TCUSFL_Perc_Low").add(TransverseCrack.get("low_avg"));
                PvmtListToAnalyze.data.get("TCUSFL_Perc_Mod").add(TransverseCrack.get("med_avg"));
                PvmtListToAnalyze.data.get("TCUSFL_Perc_High").add(TransverseCrack.get("high_avg"));

                // Centerline cracking
                Map<String, String> LongitudinalZone3Crack = processLongitudinalCrack_Zones234(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_LongitudinalZone3Crack"));
                PvmtListToAnalyze.data.get("LCS_Perc_Z3").add(LongitudinalZone3Crack.get("sealed_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z3_Low").add(LongitudinalZone3Crack.get("low_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z3_Mod").add(LongitudinalZone3Crack.get("med_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z3_High").add(LongitudinalZone3Crack.get("high_avg"));

                // Zones 2 and 4 -- Wheelpaths
                Map<String, String> LongitudinalZone2Crack = processLongitudinalCrack_Zones234(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_LongitudinalZone2Crack"));
                PvmtListToAnalyze.data.get("LCS_Perc_Z2").add(LongitudinalZone2Crack.get("sealed_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z2_Low").add(LongitudinalZone2Crack.get("low_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z2_Mod").add(LongitudinalZone2Crack.get("med_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z2_High").add(LongitudinalZone2Crack.get("high_avg"));

                Map<String, String> LongitudinalZone4Crack = processLongitudinalCrack_Zones234(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_LongitudinalZone4Crack"));
                PvmtListToAnalyze.data.get("LCS_Perc_Z4").add(LongitudinalZone4Crack.get("sealed_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z4_Low").add(LongitudinalZone4Crack.get("low_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z4_Mod").add(LongitudinalZone4Crack.get("med_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z4_High").add(LongitudinalZone4Crack.get("high_avg"));

                // Transverse Joint Spalling
                Map<String, String> TransverseJointSpall = processTransverseJointSpall(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_TransverseJointSpall"));
                // Transverse Joint Spalling
                PvmtListToAnalyze.data.get("TJ_Perc_Low").add(TransverseJointSpall.get("low_avg"));
                PvmtListToAnalyze.data.get("TJ_Perc_Mod").add(TransverseJointSpall.get("med_avg"));
                PvmtListToAnalyze.data.get("TJ_Perc_High").add(TransverseJointSpall.get("high_avg"));

                //Longitudinal joint spall on edges
                Map<String, String> LongitudinalJointSpalling_Zone1 = processLongitudinalJointSpalling(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_LongitudinalJointSpalling_Zone1"));
                PvmtListToAnalyze.data.get("LJ_Perc_Z1_Low").add(LongitudinalJointSpalling_Zone1.get("low_avg"));
                PvmtListToAnalyze.data.get("LJ_Perc_Z1_Mod").add(LongitudinalJointSpalling_Zone1.get("med_avg"));
                PvmtListToAnalyze.data.get("LJ_Perc_Z1_High").add(LongitudinalJointSpalling_Zone1.get("high_avg"));


                Map<String, String> LongitudinalJointSpalling_Zone5 = processLongitudinalJointSpalling(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_LongitudinalJointSpalling_Zone5"));
                PvmtListToAnalyze.data.get("LJ_Perc_Z5_Low").add(LongitudinalJointSpalling_Zone5.get("low_avg"));
                PvmtListToAnalyze.data.get("LJ_Perc_Z5_Mod").add(LongitudinalJointSpalling_Zone5.get("med_avg"));
                PvmtListToAnalyze.data.get("LJ_Perc_Z5_High").add(LongitudinalJointSpalling_Zone5.get("high_avg"));

                // Delamination
                Map<String, String> DelaminatedArea = processDelaminatedArea(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_DelaminatedArea"));
                PvmtListToAnalyze.data.get("Delamination_avg").add(DelaminatedArea.get("avg"));

                Map<String, String> Patching_Asphalt = processPatching(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_Patching_Asphalt"));
                PvmtListToAnalyze.data.get("Patch_Perc_Asphalt").add(Patching_Asphalt.get("avg"));

                Map<String, String> Patching_Concrete = processPatching(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_Patching_Concrete"));
                PvmtListToAnalyze.data.get("Patch_Perc_Conc").add(Patching_Concrete.get("avg"));

                // Compute the Total Weighted Distress
                double totalWeightedDistress = 0.0;
                for (Map.Entry<String, Double> entry : distressCoefficients.entrySet()) {
                    String key = entry.getKey();
                    double coefficient = entry.getValue();

                    // Retrieve the distress value from PvmtListToAnalyze
                    // System.out.println(" key: " + key );
                    String distressValueStr = PvmtListToAnalyze.data.get(key).get(PvmtListToAnalyze.data.get(key).size() - 1);

                    if (distressValueStr != null && !distressValueStr.equals("NaN")) {
                        try {
                            double distressValue = Double.parseDouble(distressValueStr);
                            totalWeightedDistress += distressValue * coefficient;
                        } catch (NumberFormatException e) {
                            System.err.println("Warning: Could not parse value for " + key);
                        }
                    }
                }


                // Compute PDS using the formula: PDS = 25 * exp(1.386294 - 0.045 * total_weighted_distress)
                double pds = 25 * Math.exp(1.386294 - 0.045 * totalWeightedDistress);

                // Add the computed Total Weighted Distress to the dataset
                PvmtListToAnalyze.data.get("Total_Weighted_Distress").add(String.valueOf(roundToDecimals(totalWeightedDistress, 2)));

                // Add PDS to the dataset
                PvmtListToAnalyze.data.get("PDS").add(String.valueOf(roundToDecimals(pds, 2)));


                long finish = System.currentTimeMillis();
                long timeElapsed = finish - start;
                System.out.println(" --> Elapsed time per section (sec): " + timeElapsed / 1000.0);
                start = System.currentTimeMillis();

            }
        }

        return PvmtListToAnalyze;
    }

    public static Map<String, String> processPatching(CSVTable tabletoanalyze,
                                                      String CS,
                                                      String SURVEY_YEAR,
                                                      Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }

        // Find the " severity"
        Set<Integer> idx_low = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 1 && rw_i == 1) ||
                    (col_i == 1 && rw_i == 2) ||
                    (col_i == 1 && rw_i == 3) ||
                    (col_i == 1 && rw_i == 4) ||
                    (col_i == 1 && rw_i == 5) ||
                    (col_i == 2 && rw_i == 1) ||
                    (col_i == 2 && rw_i == 2) ||
                    (col_i == 2 && rw_i == 3) ||
                    (col_i == 2 && rw_i == 4) ||
                    (col_i == 2 && rw_i == 5) ||
                    (col_i == 3 && rw_i == 1) ||
                    (col_i == 3 && rw_i == 2) ||
                    (col_i == 3 && rw_i == 3) ||
                    (col_i == 3 && rw_i == 4) ||
                    (col_i == 3 && rw_i == 5)) {
                idx_low.add(i);
            }
        }
        output = processSeverityPatching(tabletoanalyze, idx_low, SURVEY_YEAR);
        return output;
    }

    public static Map<String, String> processSeverityPatching(CSVTable tabletoanalyze,
                                                              Set<Integer> idx,
                                                              String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> Patching_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double Patching_length_severity = 0;
            if (pdcode_i != 501) {
                Patching_length_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            Patching_prcnt_severity.add(100 * Patching_length_severity / SecLen_mile_severity);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> Patching_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double Patching_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    Patching_prcnt_segment_severity_sum += Patching_prcnt_severity.get(j);
                }
                j++;
            }
            if (yr >= 2012 && yr <= 2017) {
                Patching_prcnt_segment_severity_sum /= 0.2941;
            }
            if (Patching_prcnt_segment_severity_sum > 100) {
                Patching_prcnt_segment_severity_sum = 100;
            }
            Patching_prcnt_segment_severity.add(Patching_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : Patching_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / Patching_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (Patching_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : Patching_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / Patching_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }


    public static Map<String, Set<Integer>> f_get_idx(CSVTable tabletoanalyze,
                                                      String CS,
                                                      String SURVEY_YEAR,
                                                      String DIR,
                                                      String BMP,
                                                      String EMP) {


        Set<Integer> pdcodes_TransverseCrack = new HashSet<>(Arrays.asList(102, 105, 107, 112, 113, 712, 713, 501));
        Set<Integer> pdcodes_LongitudinalZone3Crack = new HashSet<>(Arrays.asList(219, 228, 231, 738, 741, 501));

//        Set<Integer> pdcodes_LongitudinalZone2Crack = new HashSet<>(Arrays.asList(206, 212, 214, 227, 230, 737, 501));
//        Set<Integer> pdcodes_LongitudinalZone4Crack = new HashSet<>(Arrays.asList(207, 213, 215, 229, 232, 740, 742, 501));
//
        // Corrected per Dan Sokolnicki's comments on October 15, 2025
        Set<Integer> pdcodes_LongitudinalZone2Crack = new HashSet<>(Arrays.asList(206, 212, 213, 227, 230, 737, 740, 501));
        Set<Integer> pdcodes_LongitudinalZone4Crack = new HashSet<>(Arrays.asList(207, 214, 215, 229, 232, 739, 742, 501));

        Set<Integer> pdcodes_TransverseJointSpall = new HashSet<>(Arrays.asList(106, 706, 501));

        Set<Integer> pdcodes_LongitudinalJointSpalling_Zone1 = new HashSet<>(Arrays.asList(208, 501));
        Set<Integer> pdcodes_LongitudinalJointSpalling_Zone5 = new HashSet<>(Arrays.asList(209, 501));
        Set<Integer> pdcodes_DelaminatedArea = new HashSet<>(Arrays.asList(301, 341, 751, 501));
        Set<Integer> pdcodes_Patching_Asphalt = new HashSet<>(Arrays.asList(326, 501));
        Set<Integer> pdcodes_Patching_Concrete = new HashSet<>(Arrays.asList(327, 501));


        if (CS.contains(" (")) {
            CS = CS.substring(0, CS.indexOf(" ("));
        }

        int cs = Integer.parseInt(CS);
        int yr = Integer.parseInt(SURVEY_YEAR);
        double bmp = Double.parseDouble(BMP);
        double emp = Double.parseDouble(EMP);
        Set<Integer> idx_TransverseCrack = new HashSet<>();
        Set<Integer> idx_LongitudinalZone3Crack = new HashSet<>();
        Set<Integer> idx_LongitudinalZone2Crack = new HashSet<>();
        Set<Integer> idx_LongitudinalZone4Crack = new HashSet<>();

        Set<Integer> idx_TransverseJointSpall = new HashSet<>();
        Set<Integer> idx_LongitudinalJointSpalling_Zone1 = new HashSet<>();
        Set<Integer> idx_LongitudinalJointSpalling_Zone5 = new HashSet<>();

        Set<Integer> idx_DelaminatedArea = new HashSet<>();
        Set<Integer> idx_Patching_Asphalt = new HashSet<>();
        Set<Integer> idx_Patching_Concrete = new HashSet<>();

        // get the filtered index
        for (int i = 0; i < tabletoanalyze.m; i++) {
            int cs_i = Integer.parseInt(tabletoanalyze.data.get("CSNUM").get(i));
            int yr_i = Integer.parseInt(tabletoanalyze.data.get("SURVEY_YEAR").get(i));
            double bmp_i = Double.parseDouble(tabletoanalyze.data.get("BMP").get(i));
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            String dir_i = tabletoanalyze.data.get("DIR").get(i);
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            if (yr_i == yr && cs_i == cs && bmp_i >= bmp && emp_i <= emp && dir_i.equals(DIR)) {

                if (pdcodes_TransverseCrack.contains(pdcode_i)) {
                    idx_TransverseCrack.add(i);
                }

                if (pdcodes_LongitudinalZone3Crack.contains(pdcode_i)) {
                    idx_LongitudinalZone3Crack.add(i);
                }

                if (pdcodes_LongitudinalZone2Crack.contains(pdcode_i)) {
                    idx_LongitudinalZone2Crack.add(i);
                }

                if (pdcodes_LongitudinalZone4Crack.contains(pdcode_i)) {
                    idx_LongitudinalZone4Crack.add(i);
                }

                if (pdcodes_TransverseJointSpall.contains(pdcode_i)) {
                    idx_TransverseJointSpall.add(i);
                }

                if (pdcodes_LongitudinalJointSpalling_Zone1.contains(pdcode_i)) {
                    idx_LongitudinalJointSpalling_Zone1.add(i);
                }

                if (pdcodes_LongitudinalJointSpalling_Zone5.contains(pdcode_i)) {
                    idx_LongitudinalJointSpalling_Zone5.add(i);
                }

                if (pdcodes_DelaminatedArea.contains(pdcode_i)) {
                    idx_DelaminatedArea.add(i);
                }

                if (pdcodes_Patching_Asphalt.contains(pdcode_i)) {
                    idx_Patching_Asphalt.add(i);
                }

                if (pdcodes_Patching_Concrete.contains(pdcode_i)) {
                    idx_Patching_Concrete.add(i);
                }


            }
        }

        Map<String, Set<Integer>> data = new HashMap<>();
        data.put("idx_TransverseCrack", idx_TransverseCrack);
        data.put("idx_LongitudinalZone3Crack", idx_LongitudinalZone3Crack);
        data.put("idx_LongitudinalZone2Crack", idx_LongitudinalZone2Crack);
        data.put("idx_LongitudinalZone4Crack", idx_LongitudinalZone4Crack);
        data.put("idx_TransverseJointSpall", idx_TransverseJointSpall);
        data.put("idx_LongitudinalJointSpalling_Zone1", idx_LongitudinalJointSpalling_Zone1);
        data.put("idx_LongitudinalJointSpalling_Zone5", idx_LongitudinalJointSpalling_Zone5);
        data.put("idx_DelaminatedArea", idx_DelaminatedArea);
        data.put("idx_Patching_Asphalt", idx_Patching_Asphalt);
        data.put("idx_Patching_Concrete", idx_Patching_Concrete);

        return data;
    }

    public static Map<String, String> processSeverityLongZone234Crack(CSVTable tabletoanalyze,
                                                                      Set<Integer> idx,
                                                                      String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> LongitudinalCrack_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double LongitudinalCrack_length_severity = 0;
            if (pdcode_i != 501) {
                LongitudinalCrack_length_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            LongitudinalCrack_prcnt_severity.add(100 * LongitudinalCrack_length_severity / SecLen_mile_severity);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> LongitudinalCrack_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double LongitudinalCrack_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    LongitudinalCrack_prcnt_segment_severity_sum += LongitudinalCrack_prcnt_severity.get(j);
                }
                j++;
            }
            if (yr >= 2012 && yr <= 2017) {
                LongitudinalCrack_prcnt_segment_severity_sum /= 0.2941;
            }

            // 100% cap
            if (LongitudinalCrack_prcnt_segment_severity_sum > 100) {
                LongitudinalCrack_prcnt_segment_severity_sum = 100;
            }
            LongitudinalCrack_prcnt_segment_severity.add(LongitudinalCrack_prcnt_segment_severity_sum);
        }

        double avg = 0;
        for (double d : LongitudinalCrack_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / LongitudinalCrack_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (LongitudinalCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : LongitudinalCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / LongitudinalCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processLongitudinalCrack_Zones234(CSVTable tabletoanalyze, String CS, String SURVEY_YEAR,
                                                                        Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        output.put("sealed_avg", "NaN");
        output.put("sealed_std", "NaN");
        output.put("low_avg", "NaN");
        output.put("low_std", "NaN");
        output.put("med_avg", "NaN");
        output.put("med_std", "NaN");
        output.put("high_avg", "NaN");
        output.put("high_std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }

        // Find the "low severity"
        Set<Integer> idx_low = new HashSet<>();
        Set<Integer> idx_sealed = new HashSet<>();
        Set<Integer> idx_med = new HashSet<>();
        Set<Integer> idx_high = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if (col_i == 0) {
                if (rw_i >= 0 && rw_i <= 4) {
                    idx_sealed.add(i);
                }

                if (rw_i == 0 || rw_i == 5) {
                    idx_low.add(i);
                }
                if (rw_i == 0 || rw_i == 6) {
                    idx_med.add(i);
                }
                if (rw_i == 0 || rw_i == 7) {
                    idx_high.add(i);
                }
            }
        }
        Map<String, String> severity = processSeverityLongZone234Crack(tabletoanalyze, idx_low, SURVEY_YEAR);
        output.put("low_avg", severity.get("avg"));
        output.put("low_std", severity.get("std"));

        severity = processSeverityLongZone234Crack(tabletoanalyze, idx_sealed, SURVEY_YEAR);
        output.put("sealed_avg", severity.get("avg"));
        output.put("sealed_std", severity.get("std"));

        severity = processSeverityLongZone234Crack(tabletoanalyze, idx_med, SURVEY_YEAR);
        output.put("med_avg", severity.get("avg"));
        output.put("med_std", severity.get("std"));
        severity = processSeverityLongZone234Crack(tabletoanalyze, idx_high, SURVEY_YEAR);
        output.put("high_avg", severity.get("avg"));
        output.put("high_std", severity.get("std"));

        return output;
    }

    public static Map<String, String> processSeverityTransverseJoint(CSVTable tabletoanalyze,
                                                                     Set<Integer> idx,
                                                                     String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> TransverseJoint_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double TransverseJoint_occurences_severity = 0;
            if (pdcode_i != 501) {
                TransverseJoint_occurences_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));

            }
            double SecLen_mile_severity = emp_i - bmp_i;
            // TC spacing is assumed to be 15 feet
            double Joint_spacing_ft = 15; // ft

            // Max number of cracks for 100% cracking
            double Nmax = SecLen_mile_severity * 5280 / Joint_spacing_ft;
            TransverseJoint_prcnt_severity.add(100 * TransverseJoint_occurences_severity / Nmax);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> TransverseJoint_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double TransverseJoint_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    TransverseJoint_prcnt_segment_severity_sum += TransverseJoint_prcnt_severity.get(j);
                }
                j++;
            }

            // MDOT personnel explained the distress calls made for the 2012 - 2017 data were only at the sampled locations
            // (about 29.41% of any 0.1 mile segment of each Control Section).
            // Therefore, it was suggested to consider a 0.2941 division factor to expand distress quantities out to
            // any total mileage of interest for those years of measured PMS data.

            if (yr >= 2012 && yr <= 2017) {
                TransverseJoint_prcnt_segment_severity_sum /= 0.2941;
            }

            // Commented below --> No 100% cap
            if (TransverseJoint_prcnt_segment_severity_sum > 100) {
                TransverseJoint_prcnt_segment_severity_sum = 100;
            }
            TransverseJoint_prcnt_segment_severity.add(TransverseJoint_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : TransverseJoint_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / TransverseJoint_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (TransverseJoint_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : TransverseJoint_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / TransverseJoint_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processSeverityTransverseCrack(CSVTable tabletoanalyze,
                                                                     Set<Integer> idx,
                                                                     String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> TransverseCrack_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double TransverseCrack_occurences_severity = 0;
            if (pdcode_i != 501) {
                TransverseCrack_occurences_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));

            }
            double SecLen_mile_severity = emp_i - bmp_i;
            // TC spacing is assumed to be 15 feet
            double Joint_spacing_ft = 15; // ft

            // Max number of cracks for 100% cracking
            double Nmax = SecLen_mile_severity * 5280 / Joint_spacing_ft;
            TransverseCrack_prcnt_severity.add(100 * TransverseCrack_occurences_severity / Nmax);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> TransverseCrack_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double TransverseCrack_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    TransverseCrack_prcnt_segment_severity_sum += TransverseCrack_prcnt_severity.get(j);
                }
                j++;
            }

            // MDOT personnel explained the distress calls made for the 2012 - 2017 data were only at the sampled locations
            // (about 29.41% of any 0.1 mile segment of each Control Section).
            // Therefore, it was suggested to consider a 0.2941 division factor to expand distress quantities out to
            // any total mileage of interest for those years of measured PMS data.

            if (yr >= 2012 && yr <= 2017) {
                TransverseCrack_prcnt_segment_severity_sum /= 0.2941;
            }

            // 100% cap
            if (TransverseCrack_prcnt_segment_severity_sum > 100) {
                TransverseCrack_prcnt_segment_severity_sum = 100;
            }
            TransverseCrack_prcnt_segment_severity.add(TransverseCrack_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : TransverseCrack_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / TransverseCrack_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (TransverseCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : TransverseCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / TransverseCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processTransverseCrackLow_sealed(CSVTable tabletoanalyze,
                                                                       String CS,
                                                                       String SURVEY_YEAR,
                                                                       Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }

        // Find the "sealed low severity"
        Set<Integer> idx_low_sealed = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 1 && rw_i == 1) ||
                    (col_i == 1 && rw_i == 2) ||
                    (col_i == 1 && rw_i == 3) ||
                    (col_i == 1 && rw_i == 4)) {
                idx_low_sealed.add(i);
            }
        }
        output = processSeverityTransverseCrack(tabletoanalyze, idx_low_sealed, SURVEY_YEAR);
        return output;
    }

    public static Map<String, String> processTransverseCrackLow(CSVTable tabletoanalyze,
                                                                String CS,
                                                                String SURVEY_YEAR,
                                                                Set<Integer> idx) {

        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        if (CS.equals("NaN")) {
            return output;
        }

        // Find the "low severity"
        Set<Integer> idx_low = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 2 && rw_i == 5) ||
                    (col_i == 3 && rw_i == 5) ||
                    (col_i == 4 && rw_i == 5) ||
                    (col_i == 5 && rw_i == 5) ||
                    (col_i == 2 && rw_i == 6) ||
                    (col_i == 2 && rw_i == 7) ||
                    (col_i == 2 && rw_i == 8)) {
                idx_low.add(i);
            }
        }
        output = processSeverityTransverseCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
        return output;
    }

    public static Map<String, String> processTransverseCrackMedHigh(CSVTable tabletoanalyze,
                                                                    String CS,
                                                                    String SURVEY_YEAR,
                                                                    Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        output.put("med_avg", "NaN");
        output.put("med_std", "NaN");
        output.put("high_avg", "NaN");
        output.put("high_std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }

        if (idx.isEmpty()) {
            //System.out.println(String.format("-->No Longitudinal Cracking for CS = %s, DIR = %s, BMP = %s, EMP = %s, YR = %s", CS, DIR, BMP, EMP, SURVEY_YEAR));
            return output;
        }

        // Find the "medium and high severity"
        Set<Integer> idx_med = new HashSet<>();
        Set<Integer> idx_high = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 3 && rw_i == 6) ||
                    (col_i == 4 && rw_i == 6) ||
                    (col_i == 5 && rw_i == 6) ||
                    (col_i == 3 && rw_i == 7) ||
                    (col_i == 3 && rw_i == 8)) {
                idx_med.add(i);
            }
            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 4 && rw_i == 7) ||
                    (col_i == 5 && rw_i == 7) ||
                    (col_i == 4 && rw_i == 8) ||
                    (col_i == 5 && rw_i == 8)) {
                idx_high.add(i);
            }
        }
        Map<String, String> severity = processSeverityTransverseCrack(tabletoanalyze, idx_med, SURVEY_YEAR);
        output.put("med_avg", severity.get("avg"));
        output.put("med_std", severity.get("std"));
        severity = processSeverityTransverseCrack(tabletoanalyze, idx_high, SURVEY_YEAR);
        output.put("high_avg", severity.get("avg"));
        output.put("high_std", severity.get("std"));

        return output;
    }

    public static Map<String, String> processTransverseJointSpall(CSVTable tabletoanalyze,
                                                                  String CS,
                                                                  String SURVEY_YEAR,
                                                                  Set<Integer> idx) {

        Map<String, String> output = new HashMap<>();
        output.put("low_avg", "NaN");
        output.put("low_std", "NaN");
        output.put("med_avg", "NaN");
        output.put("med_std", "NaN");
        output.put("high_avg", "NaN");
        output.put("high_std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }

        // Find the "medium and high severity"
        Set<Integer> idx_low = new HashSet<>();
        Set<Integer> idx_med = new HashSet<>();
        Set<Integer> idx_high = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));

            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 2 && rw_i == 2) ||
                    (col_i == 3 && rw_i == 2) ||
                    (col_i == 4 && rw_i == 2) ||
                    (col_i == 5 && rw_i == 2)) {
                idx_low.add(i);
            }

            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 2 && rw_i == 3) ||
                    (col_i == 3 && rw_i == 3) ||
                    (col_i == 4 && rw_i == 3) ||
                    (col_i == 2 && rw_i == 4) ||
                    (col_i == 2 && rw_i == 5)) {
                idx_med.add(i);
            }
            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 5 && rw_i == 3) ||
                    (col_i == 3 && rw_i == 4) ||
                    (col_i == 4 && rw_i == 4) ||
                    (col_i == 5 && rw_i == 4) ||
                    (col_i == 3 && rw_i == 5) ||
                    (col_i == 4 && rw_i == 5) ||
                    (col_i == 5 && rw_i == 5)) {
                idx_high.add(i);
            }
        }
        Map<String, String> severity = processSeverityTransverseJoint(tabletoanalyze, idx_low, SURVEY_YEAR);
        output.put("low_avg", severity.get("avg"));
        output.put("low_std", severity.get("std"));
        severity = processSeverityTransverseJoint(tabletoanalyze, idx_med, SURVEY_YEAR);
        output.put("med_avg", severity.get("avg"));
        output.put("med_std", severity.get("std"));
        severity = processSeverityTransverseJoint(tabletoanalyze, idx_high, SURVEY_YEAR);
        output.put("high_avg", severity.get("avg"));
        output.put("high_std", severity.get("std"));

        return output;

    }

    public static Map<String, String> processTransverseCrack(CSVTable tabletoanalyze,
                                                             String CS,
                                                             String SURVEY_YEAR,
                                                             Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        Map<String, String> TCLow = processTransverseCrackLow(tabletoanalyze, CS, SURVEY_YEAR, idx);
        output.put("low_avg", TCLow.get("avg"));
        output.put("low_std", TCLow.get("std"));

        Map<String, String> TCLow_sealed = processTransverseCrackLow_sealed(tabletoanalyze, CS, SURVEY_YEAR, idx);
        output.put("sealed_avg", TCLow_sealed.get("avg"));
        output.put("sealed_std", TCLow_sealed.get("std"));

        Map<String, String> TCMedHigh = processTransverseCrackMedHigh(tabletoanalyze, CS, SURVEY_YEAR, idx);
        output.put("med_avg", TCMedHigh.get("med_avg"));
        output.put("med_std", TCMedHigh.get("med_std"));
        output.put("high_avg", TCMedHigh.get("high_avg"));
        output.put("high_std", TCMedHigh.get("high_std"));
        return output;
    }

    public static Map<String, String> procesLongJoint(CSVTable tabletoanalyze,
                                                      Set<Integer> idx,
                                                      String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> LongJoint_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double LongJoint_length_severity = 0;
            if (pdcode_i != 501) {
                LongJoint_length_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            LongJoint_prcnt_severity.add(100 * LongJoint_length_severity / SecLen_mile_severity);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> LongJoint_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double EdgeCrack_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    EdgeCrack_prcnt_segment_severity_sum += LongJoint_prcnt_severity.get(j);
                }
                j++;
            }
            if (yr >= 2012 && yr <= 2017) {
                EdgeCrack_prcnt_segment_severity_sum /= 0.2941;
            }

            if (EdgeCrack_prcnt_segment_severity_sum > 100) {
                EdgeCrack_prcnt_segment_severity_sum = 100;
            }
            LongJoint_prcnt_segment_severity.add(EdgeCrack_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : LongJoint_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / LongJoint_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (LongJoint_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : LongJoint_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / LongJoint_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processLongitudinalJointSpalling(CSVTable tabletoanalyze,
                                                                       String CS,
                                                                       String SURVEY_YEAR,
                                                                       Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        output.put("low_avg", "NaN");
        output.put("low_std", "NaN");
        output.put("med_avg", "NaN");
        output.put("med_std", "NaN");
        output.put("high_avg", "NaN");
        output.put("high_std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }

        // Find the "low severity"
        Set<Integer> idx_low = new HashSet<>();
        Set<Integer> idx_med = new HashSet<>();
        Set<Integer> idx_high = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));

            if (rw_i == 0 || rw_i == 2) {
                idx_low.add(i);
            }

            if (rw_i == 0 || rw_i == 3) {
                idx_med.add(i);
            }

            if (rw_i == 0 || rw_i == 4) {
                idx_high.add(i);
            }
        }
        Map<String, String> severity = procesLongJoint(tabletoanalyze, idx_low, SURVEY_YEAR);
        output.put("low_avg", severity.get("avg"));
        output.put("low_std", severity.get("std"));
        severity = procesLongJoint(tabletoanalyze, idx_med, SURVEY_YEAR);
        output.put("med_avg", severity.get("avg"));
        output.put("med_std", severity.get("std"));
        severity = procesLongJoint(tabletoanalyze, idx_high, SURVEY_YEAR);
        output.put("high_avg", severity.get("avg"));
        output.put("high_std", severity.get("std"));

        return output;
    }


    public static Map<String, String> processSeverityDelamination(CSVTable tabletoanalyze,
                                                                  Set<Integer> idx,
                                                                  String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> Delamination_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double Delamination_length_severity = 0;
            if (pdcode_i != 501) {
                Delamination_length_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            Delamination_prcnt_severity.add(100 * Delamination_length_severity / SecLen_mile_severity);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> Delamination_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double Delamination_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    Delamination_prcnt_segment_severity_sum += Delamination_prcnt_severity.get(j);
                }
                j++;
            }
            if (yr >= 2012 && yr <= 2017) {
                Delamination_prcnt_segment_severity_sum /= 0.2941;
            }

            if (Delamination_prcnt_segment_severity_sum > 100) {
                Delamination_prcnt_segment_severity_sum = 100;
            }

            Delamination_prcnt_segment_severity.add(Delamination_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : Delamination_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / Delamination_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (Delamination_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : Delamination_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / Delamination_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processDelaminatedArea(CSVTable tabletoanalyze,
                                                             String CS,
                                                             String SURVEY_YEAR,
                                                             Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }

        // Find the "low severity"
        Set<Integer> idx_all = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if (col_i >= 0 && rw_i >= 0) {
                idx_all.add(i);
            }
        }
        output = processSeverityDelamination(tabletoanalyze, idx_all, SURVEY_YEAR);
        return output;
    }

    public static double roundToDecimals(double value, int decimals) {
        double scale = Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
    }


}
