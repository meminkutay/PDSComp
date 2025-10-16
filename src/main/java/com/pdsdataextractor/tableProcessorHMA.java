package com.pdsdataextractor;

import java.util.*;

public class tableProcessorHMA {


    public tableProcessorHMA() {
    }

    public static CSVTable process(String HMA_pvmtlist_path_S, String fpath_pmscsv) throws Exception {

        CSVTable PvmtListToAnalyze = new CSVTable(HMA_pvmtlist_path_S);

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

        // Longitudinal cracking - Zones 1 and 5 - Edges
        PvmtListToAnalyze.header.add("LCS_Perc_Z1");// sealed
        PvmtListToAnalyze.data.put("LCS_Perc_Z1", new ArrayList<>());// sealed
        PvmtListToAnalyze.header.add("LCUS_Perc_Z1_Low");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z1_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z1_Mod");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z1_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z1_High");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z1_High", new ArrayList<>());

        PvmtListToAnalyze.header.add("LCS_Perc_Z5");// sealed
        PvmtListToAnalyze.data.put("LCS_Perc_Z5", new ArrayList<>());// sealed
        PvmtListToAnalyze.header.add("LCUS_Perc_Z5_Low");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z5_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z5_Mod");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z5_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("LCUS_Perc_Z5_High");
        PvmtListToAnalyze.data.put("LCUS_Perc_Z5_High", new ArrayList<>());

        // Alligator cracking - Zones 2 and 4 -- Wheelpaths
        PvmtListToAnalyze.header.add("ALLIG_Perc_Z2_Low");
        PvmtListToAnalyze.data.put("ALLIG_Perc_Z2_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("ALLIG_Perc_Z2_Mod");
        PvmtListToAnalyze.data.put("ALLIG_Perc_Z2_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("ALLIG_Perc_Z2_High");
        PvmtListToAnalyze.data.put("ALLIG_Perc_Z2_High", new ArrayList<>());

        PvmtListToAnalyze.header.add("ALLIG_Perc_Z4_Low");
        PvmtListToAnalyze.data.put("ALLIG_Perc_Z4_Low", new ArrayList<>());
        PvmtListToAnalyze.header.add("ALLIG_Perc_Z4_Mod");
        PvmtListToAnalyze.data.put("ALLIG_Perc_Z4_Mod", new ArrayList<>());
        PvmtListToAnalyze.header.add("ALLIG_Perc_Z4_High");
        PvmtListToAnalyze.data.put("ALLIG_Perc_Z4_High", new ArrayList<>());

        // Block cracking
        PvmtListToAnalyze.header.add("BlockCracking_perc");
        PvmtListToAnalyze.data.put("BlockCracking_perc", new ArrayList<>());

        PvmtListToAnalyze.header.add("Patch_Perc_Asphalt");
        PvmtListToAnalyze.data.put("Patch_Perc_Asphalt", new ArrayList<>());


        PvmtListToAnalyze.header.add("Rav_Perc");
        PvmtListToAnalyze.data.put("Rav_Perc", new ArrayList<>());
        PvmtListToAnalyze.header.add("Bleed_Perc");
        PvmtListToAnalyze.data.put("Bleed_Perc", new ArrayList<>());

        // Define the updated distress coefficients for asphalt 4/24/25
        Map<String, Double> distressCoefficients = new HashMap<>();
        distressCoefficients.put("LCUS_Perc_Z1_Low", 0.0055);
        distressCoefficients.put("LCUS_Perc_Z1_Mod", 0.0155);
        distressCoefficients.put("LCUS_Perc_Z1_High", 0.042);
        distressCoefficients.put("LCUS_Perc_Z2_Low", 0.0055);
        distressCoefficients.put("LCUS_Perc_Z2_Mod", 0.109);
        distressCoefficients.put("LCUS_Perc_Z2_High", 0.4155);
        distressCoefficients.put("LCUS_Perc_Z3_Low", 0.0088);
        distressCoefficients.put("LCUS_Perc_Z3_Mod", 0.1744);
        distressCoefficients.put("LCUS_Perc_Z3_High", 0.6648);
        distressCoefficients.put("LCUS_Perc_Z4_Low", 0.0055);
        distressCoefficients.put("LCUS_Perc_Z4_Mod", 0.109);
        distressCoefficients.put("LCUS_Perc_Z4_High", 0.4155);
        distressCoefficients.put("LCUS_Perc_Z5_Low", 0.0055);
        distressCoefficients.put("LCUS_Perc_Z5_Mod", 0.0155);
        distressCoefficients.put("LCUS_Perc_Z5_High", 0.042);
        distressCoefficients.put("LCS_Perc_Z1", 0.00275);
        distressCoefficients.put("LCS_Perc_Z2", 0.00275);
        distressCoefficients.put("LCS_Perc_Z3", 0.0044);
        distressCoefficients.put("LCS_Perc_Z4", 0.00275);
        distressCoefficients.put("LCS_Perc_Z5", 0.00275);
        distressCoefficients.put("TCUSFL_Perc_Low", 0.017);
        distressCoefficients.put("TCUSFL_Perc_Mod", 0.028);
        distressCoefficients.put("TCUSFL_Perc_High", 0.884);
        distressCoefficients.put("TCSFL_Perc", 0.0085);
        distressCoefficients.put("Patch_Perc_Asphalt", 0.0);
        //        distressCoefficients.put("PH_Perc", 0.4903);
        //        distressCoefficients.put("ALLIG_Perc_Z1_Low", 0.00825);
        //        distressCoefficients.put("ALLIG_Perc_Z1_Mod", 0.02325);
        //        distressCoefficients.put("ALLIG_Perc_Z1_High", 0.063);
        distressCoefficients.put("ALLIG_Perc_Z2_Low", 0.00825);
        distressCoefficients.put("ALLIG_Perc_Z2_Mod", 0.1635);
        distressCoefficients.put("ALLIG_Perc_Z2_High", 0.62325);
        //        distressCoefficients.put("ALLIG_Perc_Z3_Low", 0.0132);
        //        distressCoefficients.put("ALLIG_Perc_Z3_Mod", 0.2616);
        //        distressCoefficients.put("ALLIG_Perc_Z3_High", 0.9972);
        distressCoefficients.put("ALLIG_Perc_Z4_Low", 0.00825);
        distressCoefficients.put("ALLIG_Perc_Z4_Mod", 0.1635);
        distressCoefficients.put("ALLIG_Perc_Z4_High", 0.62325);
        //        distressCoefficients.put("ALLIG_Perc_Z5_Low", 0.00825);
        //        distressCoefficients.put("ALLIG_Perc_Z5_Mod", 0.02325);
        //        distressCoefficients.put("ALLIG_Perc_Z5_High", 0.063);
        distressCoefficients.put("Rav_Perc", 0.4903);
        distressCoefficients.put("Bleed_Perc", 0.012);
        // Block cracking coefficient left as is (can be adjusted if needed)
        distressCoefficients.put("BlockCracking_perc", 0.01);

        //        // Define the distress coefficients - old ones below
        //        Map<String, Double> distressCoefficients = new HashMap<>();
        //        distressCoefficients.put("LCUS_Perc_Z1_Low", 0.0055);
        //        distressCoefficients.put("LCUS_Perc_Z1_Mod", 0.0155);
        //        distressCoefficients.put("LCUS_Perc_Z1_High", 0.042);
        //        distressCoefficients.put("LCUS_Perc_Z2_Low", 0.0055);
        //        distressCoefficients.put("LCUS_Perc_Z2_Mod", 0.109);
        //        distressCoefficients.put("LCUS_Perc_Z2_High", 0.4155);
        //        distressCoefficients.put("LCUS_Perc_Z3_Low", 0.0088);
        //        distressCoefficients.put("LCUS_Perc_Z3_Mod", 0.1744);
        //        distressCoefficients.put("LCUS_Perc_Z3_High", 0.6648);
        //        distressCoefficients.put("LCUS_Perc_Z4_Low", 0.0055);
        //        distressCoefficients.put("LCUS_Perc_Z4_Mod", 0.109);
        //        distressCoefficients.put("LCUS_Perc_Z4_High", 0.4155);
        //        distressCoefficients.put("LCUS_Perc_Z5_Low", 0.0055);
        //        distressCoefficients.put("LCUS_Perc_Z5_Mod", 0.0155);
        //        distressCoefficients.put("LCUS_Perc_Z5_High", 0.042);
        //        distressCoefficients.put("LCS_Perc_Z1", 0.00275);
        //        distressCoefficients.put("LCS_Perc_Z2", 0.00275);
        //        distressCoefficients.put("LCS_Perc_Z3", 0.0044);
        //        distressCoefficients.put("LCS_Perc_Z4", 0.00275);
        //        distressCoefficients.put("LCS_Perc_Z5", 0.00275);
        //        distressCoefficients.put("TCUSFL_Perc_Low", 0.017);
        //        distressCoefficients.put("TCUSFL_Perc_Mod", 0.028);
        //        distressCoefficients.put("TCUSFL_Perc_High", 0.884);
        //        distressCoefficients.put("TCSFL_Perc", 0.0085);
        //        distressCoefficients.put("Patch_Perc_Asphalt", 0.0);
        //        distressCoefficients.put("ALLIG_Perc_Z2_Low", 0.0055);
        //        distressCoefficients.put("ALLIG_Perc_Z2_Mod", 0.109);
        //        distressCoefficients.put("ALLIG_Perc_Z2_High", 0.4155);
        //        distressCoefficients.put("ALLIG_Perc_Z4_Low", 0.0055);
        //        distressCoefficients.put("ALLIG_Perc_Z4_Mod", 0.109);
        //        distressCoefficients.put("ALLIG_Perc_Z4_High", 0.4155);
        //        distressCoefficients.put("Rav_Perc", 0.4903);
        //        distressCoefficients.put("Bleed_Perc", 0.012);
        ////        distressCoefficients.put("BlockCracking_perc", 0.9434);
        //         distressCoefficients.put("BlockCracking_perc", 0.01);

        // Define the metadata columns that should NOT be cleared to "NaN"
        //        Set<String> metadataColumns = new HashSet<>(Arrays.asList(
        //                "Pavement Type", "ID", "REGION", "CS", "JN", "BMP", "EMP", "DIR",
        //                "OPENED", "SURFACE", "SURVEY_YEAR"
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

            // Get indices of various PD codes within the PDDataTable
            Map<String, Set<Integer>> idxALL = f_get_idx(PDDataTable, CS, SURVEY_YEAR, DIR, BMP, EMP);

            // Check if all indices are empty
            boolean allEmpty = idxALL.values().stream().allMatch(Set::isEmpty);

            if (allEmpty) {
                status_sout = String.format("!!!No distress data found for " +
                        "CS = %s, DIR = %s, BMP = %s, EMP = %s, YR = %s, " +
                        "setting all distress-related values to NaN.", CS, DIR, BMP, EMP, SURVEY_YEAR);
                System.out.println(status_sout);

                // Fill only distress-related columns with "NaN"
                for (String key : PvmtListToAnalyze.data.keySet()) {
                    if (!metadataColumns.contains(key)) { // Exclude metadata columns
                        PvmtListToAnalyze.data.get(key).add("NaN");
                    }
                }

            } else {
                // Normal processing continues as before...
                // Transverse cracking
                Map<String, String> TransverseCrack = processTransverseCrack(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_TransverseCrack_low"), idxALL.get("idx_TransverseCrack_medhigh"));
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

                // Zones 1 and 5 --- EDGES
                Map<String, String> LongitudinalZone1Crack = processLongitudinalCrack_Zone1(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_LongitudinalZone1Crack"));
                PvmtListToAnalyze.data.get("LCS_Perc_Z1").add(LongitudinalZone1Crack.get("sealed_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z1_Low").add(LongitudinalZone1Crack.get("low_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z1_Mod").add(LongitudinalZone1Crack.get("med_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z1_High").add(LongitudinalZone1Crack.get("high_avg"));

                Map<String, String> LongitudinalZone5Crack = processLongitudinalCrack_Zone5(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_LongitudinalZone5Crack"));
                PvmtListToAnalyze.data.get("LCS_Perc_Z5").add(LongitudinalZone5Crack.get("sealed_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z5_Low").add(LongitudinalZone5Crack.get("low_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z5_Mod").add(LongitudinalZone5Crack.get("med_avg"));
                PvmtListToAnalyze.data.get("LCUS_Perc_Z5_High").add(LongitudinalZone5Crack.get("high_avg"));

                // Alligator crack
                Map<String, String> AlligatorCrackZone2 = processAlligatorCrack(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_AlligatorCrackZone2"));
                PvmtListToAnalyze.data.get("ALLIG_Perc_Z2_Low").add(AlligatorCrackZone2.get("low_avg"));
                PvmtListToAnalyze.data.get("ALLIG_Perc_Z2_Mod").add(AlligatorCrackZone2.get("med_avg"));
                PvmtListToAnalyze.data.get("ALLIG_Perc_Z2_High").add(AlligatorCrackZone2.get("high_avg"));

                Map<String, String> AlligatorCrackZone4 = processAlligatorCrack(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("idx_AlligatorCrackZone4"));
                PvmtListToAnalyze.data.get("ALLIG_Perc_Z4_Low").add(AlligatorCrackZone4.get("low_avg"));
                PvmtListToAnalyze.data.get("ALLIG_Perc_Z4_Mod").add(AlligatorCrackZone4.get("med_avg"));
                PvmtListToAnalyze.data.get("ALLIG_Perc_Z4_High").add(AlligatorCrackZone4.get("high_avg"));


                // Raveling
                Map<String, String> Raveling = processRaveling(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("Raveling"));
                PvmtListToAnalyze.data.get("Rav_Perc").add(Raveling.get("avg"));

                // Bleeding
                Map<String, String> Bleeding = processBleeding(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("Bleeding"));
                PvmtListToAnalyze.data.get("Bleed_Perc").add(Bleeding.get("avg"));


                // Patching
                Map<String, String> Patching = processPatching(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("Patching"));
                PvmtListToAnalyze.data.get("Patch_Perc_Asphalt").add(Patching.get("avg"));

                // Block cracking
                Map<String, String> BlockCrack = processBlockCrack(PDDataTable, CS, SURVEY_YEAR,
                        idxALL.get("BlockCrack"));
                PvmtListToAnalyze.data.get("BlockCracking_perc").add(BlockCrack.get("avg"));


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


    public static Map<String, Set<Integer>> f_get_idx(CSVTable tabletoanalyze,
                                                      String CS,
                                                      String SURVEY_YEAR,
                                                      String DIR,
                                                      String BMP,
                                                      String EMP) {


        Set<Integer> pdcodes_TransverseCrack_low = new HashSet<>(Arrays.asList(101, 103, 104, 110, 114, 701, 703, 704, 501));
        // FOR MEDIUM AND HIGH, ELIMINATED THE TRANSVERSE TEAR
        Set<Integer> pdcodes_TransverseCrack_medhigh = new HashSet<>(Arrays.asList(103, 104, 110, 703, 704, 501));

        Set<Integer> pdcodes_LongitudinalZone3Crack = new HashSet<>(Arrays.asList(202, 218, 722, 501));

        Set<Integer> pdcodes_LongitudinalZone2Crack = new HashSet<>(Arrays.asList(204, 724, 501));
        Set<Integer> pdcodes_LongitudinalZone4Crack = new HashSet<>(Arrays.asList(205, 725, 501));

        Set<Integer> pdcodes_AlligatorCrackZone2 = new HashSet<>(Arrays.asList(210, 221, 224, 235, 731, 501));
        Set<Integer> pdcodes_AlligatorCrackZone4 = new HashSet<>(Arrays.asList(220, 222, 234, 730, 501));

        Set<Integer> pdcodes_Zone1Crack = new HashSet<>(Arrays.asList(201, 236, 721, 501));
        Set<Integer> pdcodes_Zone5Crack = new HashSet<>(Arrays.asList(203, 237, 723, 501));

        Set<Integer> pdcodes_BlockCrack = new HashSet<>(Arrays.asList(310, 345, 760, 501));
        Set<Integer> pdcodes_Patching = new HashSet<>(Arrays.asList(326, 327, 501));
        Set<Integer> pdcodes_Raveling = new HashSet<>(Arrays.asList(405, 501));
        Set<Integer> pdcodes_Bleeding = new HashSet<>(Arrays.asList(406, 501));


        if (CS.contains(" (")) {
            CS = CS.substring(0, CS.indexOf(" ("));
        }

        int cs = Integer.parseInt(CS);
        int yr = Integer.parseInt(SURVEY_YEAR);
        double bmp = Double.parseDouble(BMP);
        double emp = Double.parseDouble(EMP);
        Set<Integer> idx_TransverseCrack_low = new HashSet<>();
        Set<Integer> idx_TransverseCrack_medhigh = new HashSet<>();
        Set<Integer> idx_LongitudinalZone3Crack = new HashSet<>();
        Set<Integer> idx_LongitudinalZone2Crack = new HashSet<>();
        Set<Integer> idx_LongitudinalZone4Crack = new HashSet<>();
        Set<Integer> idx_AlligatorCrackZone2 = new HashSet<>();
        Set<Integer> idx_AlligatorCrackZone4 = new HashSet<>();
        Set<Integer> idx_LongitudinalZone1Crack = new HashSet<>();
        Set<Integer> idx_LongitudinalZone5Crack = new HashSet<>();
        Set<Integer> idx_BlockCrack = new HashSet<>();
        Set<Integer> idx_Patching = new HashSet<>();
        Set<Integer> idx_Raveling = new HashSet<>();
        Set<Integer> idx_Bleeding = new HashSet<>();

        // get the filtered index
        for (int i = 0; i < tabletoanalyze.m; i++) {
            int cs_i = Integer.parseInt(tabletoanalyze.data.get("CSNUM").get(i));
            int yr_i = Integer.parseInt(tabletoanalyze.data.get("SURVEY_YEAR").get(i));
            double bmp_i = Double.parseDouble(tabletoanalyze.data.get("BMP").get(i));
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            String dir_i = tabletoanalyze.data.get("DIR").get(i);
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            if (yr_i == yr && cs_i == cs && bmp_i >= bmp && emp_i <= emp && dir_i.equals(DIR)) {

                if (pdcodes_TransverseCrack_low.contains(pdcode_i)) {
                    idx_TransverseCrack_low.add(i);
                }

                if (pdcodes_TransverseCrack_medhigh.contains(pdcode_i)) {
                    idx_TransverseCrack_medhigh.add(i);
                }

                if (pdcodes_LongitudinalZone3Crack.contains(pdcode_i)) {
                    idx_LongitudinalZone3Crack.add(i); // Centerline
                }

                if (pdcodes_LongitudinalZone2Crack.contains(pdcode_i)) {
                    idx_LongitudinalZone2Crack.add(i);  // Left Wheelpath
                }

                if (pdcodes_LongitudinalZone4Crack.contains(pdcode_i)) {
                    idx_LongitudinalZone4Crack.add(i); // Right Wheelpath
                }

                if (pdcodes_AlligatorCrackZone2.contains(pdcode_i)) {
                    idx_AlligatorCrackZone2.add(i);
                }

                if (pdcodes_AlligatorCrackZone4.contains(pdcode_i)) {
                    idx_AlligatorCrackZone4.add(i);
                }

                if (pdcodes_Zone1Crack.contains(pdcode_i)) {
                    idx_LongitudinalZone1Crack.add(i); //Left edge
                }

                if (pdcodes_Zone5Crack.contains(pdcode_i)) {
                    idx_LongitudinalZone5Crack.add(i); // Right edge
                }

                if (pdcodes_BlockCrack.contains(pdcode_i)) {
                    idx_BlockCrack.add(i);
                }
                if (pdcodes_Patching.contains(pdcode_i)) {
                    idx_Patching.add(i);
                }
                if (pdcodes_Raveling.contains(pdcode_i)) {
                    idx_Raveling.add(i);
                }
                if (pdcodes_Bleeding.contains(pdcode_i)) {
                    idx_Bleeding.add(i);
                }


            }
        }

        Map<String, Set<Integer>> data = new HashMap<>();
        data.put("idx_TransverseCrack_low", idx_TransverseCrack_low);
        data.put("idx_TransverseCrack_medhigh", idx_TransverseCrack_medhigh);
        data.put("idx_LongitudinalZone3Crack", idx_LongitudinalZone3Crack); // Centerline

        data.put("idx_LongitudinalZone2Crack", idx_LongitudinalZone2Crack); // Left Wheelpath
        data.put("idx_LongitudinalZone4Crack", idx_LongitudinalZone4Crack); // Right wheelpath

        data.put("idx_AlligatorCrackZone2", idx_AlligatorCrackZone2);
        data.put("idx_AlligatorCrackZone4", idx_AlligatorCrackZone4);

        data.put("idx_LongitudinalZone1Crack", idx_LongitudinalZone1Crack); // Left edge
        data.put("idx_LongitudinalZone5Crack", idx_LongitudinalZone5Crack); // Right edge

        data.put("BlockCrack", idx_BlockCrack);
        data.put("Patching", idx_Patching);
        data.put("Raveling", idx_Raveling);
        data.put("Bleeding", idx_Bleeding);

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

    public static Map<String, String>   processLongitudinalCrack_Zones234(CSVTable tabletoanalyze, String CS, String SURVEY_YEAR,
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

    public static double roundToDecimals(double value, int decimals) {
        double scale = Math.pow(10, decimals);
        return Math.round(value * scale) / scale;
    }

    public static Map<String, String> processSeverityTransverseCrack(CSVTable tabletoanalyze,
                                                                     Set<Integer> idx,
                                                                     String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);

        Set<Integer> pdcodes_transverse_tear = new HashSet<>(Arrays.asList(101, 114, 701));

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
                // Assume four transverse tears are equal to one transverse crack
                // Assumed tear = ~ 3', TC = ~ 12'
                TransverseCrack_occurences_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
                if (pdcodes_transverse_tear.contains(pdcode_i)) {
                    TransverseCrack_occurences_severity /= 4;
                }
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            // According to Minnesota, the TC spacing is 10 feet
            // Max number of cracks for 100% cracking
            double Nmax = SecLen_mile_severity * 5280 / 10;
            //            TransverseCrack_prcnt_severity.add(100 * TransverseCrack_occurences_severity / Nmax);
            TransverseCrack_prcnt_severity.add(roundToDecimals(100 * TransverseCrack_occurences_severity / Nmax, 2));

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

        // Find the "low severity unsealed"
        Set<Integer> idx_low = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if ((col_i == 2 && rw_i == 5) ||
                    (col_i == 3 && rw_i == 5) ||
                    (col_i == 4 && rw_i == 5) ||
                    (col_i == 2 && rw_i == 6) ||
                    (col_i == 3 && rw_i == 6)) {
                idx_low.add(i);
            }
        }
        output = processSeverityTransverseCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
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

        // Find the "medium and high severity"
        Set<Integer> idx_med = new HashSet<>();
        Set<Integer> idx_high = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if ((col_i == 0 && rw_i == 0) || (col_i == 4 && rw_i == 6) || (col_i == 2 && rw_i == 7)
                    || (col_i == 3 && rw_i == 7) || (col_i == 4 && rw_i == 7)) {
                idx_med.add(i);
            }
            if ((col_i == 0 && rw_i == 0) || (col_i == 2 && rw_i == 8) || (col_i == 3 && rw_i == 8)
                    || (col_i == 4 && rw_i == 8)) {
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

    public static Map<String, String> processTransverseCrack(CSVTable tabletoanalyze,
                                                             String CS,
                                                             String SURVEY_YEAR,
                                                             Set<Integer> idx_low,
                                                             Set<Integer> idx_medhigh) {
        Map<String, String> output = new HashMap<>();
        Map<String, String> TCLow = processTransverseCrackLow(tabletoanalyze, CS, SURVEY_YEAR, idx_low);
        output.put("low_avg", TCLow.get("avg"));
        output.put("low_std", TCLow.get("std"));

        Map<String, String> TCLow_sealed = processTransverseCrackLow_sealed(tabletoanalyze, CS, SURVEY_YEAR, idx_low);
        output.put("sealed_avg", TCLow_sealed.get("avg"));
        output.put("sealed_std", TCLow_sealed.get("std"));

        Map<String, String> TCMedHigh = processTransverseCrackMedHigh(tabletoanalyze, CS, SURVEY_YEAR, idx_medhigh);
        output.put("med_avg", TCMedHigh.get("med_avg"));
        output.put("med_std", TCMedHigh.get("med_std"));
        output.put("high_avg", TCMedHigh.get("high_avg"));
        output.put("high_std", TCMedHigh.get("high_std"));
        return output;
    }

    public static Map<String, String> processSeverityEdgeCrack(CSVTable tabletoanalyze,
                                                               Set<Integer> idx,
                                                               String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> EdgeCrack_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double EdgeCrack_length_severity = 0;
            if (pdcode_i != 501) {
                EdgeCrack_length_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            EdgeCrack_prcnt_severity.add(100 * EdgeCrack_length_severity / SecLen_mile_severity);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> EdgeCrack_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double EdgeCrack_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    EdgeCrack_prcnt_segment_severity_sum += EdgeCrack_prcnt_severity.get(j);
                }
                j++;
            }
            if (yr >= 2012 && yr <= 2017) {
                EdgeCrack_prcnt_segment_severity_sum /= 0.2941;
            }
            if (EdgeCrack_prcnt_segment_severity_sum > 100) {
                EdgeCrack_prcnt_segment_severity_sum = 100;
            }
            EdgeCrack_prcnt_segment_severity.add(EdgeCrack_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : EdgeCrack_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / EdgeCrack_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (EdgeCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : EdgeCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / EdgeCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processLongitudinalCrack_Zone1(CSVTable tabletoanalyze,
                                                                     String CS,
                                                                     String SURVEY_YEAR,
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
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            if (col_i == 0) {
                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 201 && rw_i == 1)
                        || (pdcode_i == 201 && rw_i == 2)
                        || (pdcode_i == 201 && rw_i == 3)
                        || (pdcode_i == 201 && rw_i == 4)
                        || (pdcode_i == 721 && rw_i == 1)
                        || (pdcode_i == 721 && rw_i == 2)
                        || (pdcode_i == 721 && rw_i == 3)
                        || (pdcode_i == 721 && rw_i == 4)) {
                    idx_sealed.add(i);
                }

                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 201 && rw_i == 5)
                        || (pdcode_i == 721 && rw_i == 5)
                        || (pdcode_i == 236 && rw_i == 1)) {
                    idx_low.add(i);
                }

                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 201 && rw_i == 6)
                        || (pdcode_i == 721 && rw_i == 6)
                        || (pdcode_i == 236 && rw_i == 2)) {
                    idx_med.add(i);
                }

                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 201 && rw_i == 7)
                        || (pdcode_i == 721 && rw_i == 7)
                        || (pdcode_i == 236 && rw_i == 3)) {
                    idx_high.add(i);
                }

            }
        }
        Map<String, String> severity = processSeverityEdgeCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
        output.put("low_avg", severity.get("avg"));
        output.put("low_std", severity.get("std"));

        severity = processSeverityEdgeCrack(tabletoanalyze, idx_sealed, SURVEY_YEAR);
        output.put("sealed_avg", severity.get("avg"));
        output.put("sealed_std", severity.get("std"));

        severity = processSeverityEdgeCrack(tabletoanalyze, idx_med, SURVEY_YEAR);
        output.put("med_avg", severity.get("avg"));
        output.put("med_std", severity.get("std"));

        severity = processSeverityEdgeCrack(tabletoanalyze, idx_high, SURVEY_YEAR);
        output.put("high_avg", severity.get("avg"));
        output.put("high_std", severity.get("std"));

        return output;
    }

    public static Map<String, String> processLongitudinalCrack_Zone5(CSVTable tabletoanalyze,
                                                                     String CS,
                                                                     String SURVEY_YEAR,
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
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            if (col_i == 0) {
                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 203 && rw_i == 1)
                        || (pdcode_i == 203 && rw_i == 2)
                        || (pdcode_i == 203 && rw_i == 3)
                        || (pdcode_i == 203 && rw_i == 4)
                        || (pdcode_i == 723 && rw_i == 1)
                        || (pdcode_i == 723 && rw_i == 2)
                        || (pdcode_i == 723 && rw_i == 3)
                        || (pdcode_i == 723 && rw_i == 4)) {
                    idx_sealed.add(i);
                }

                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 203 && rw_i == 5)
                        || (pdcode_i == 723 && rw_i == 5)
                        || (pdcode_i == 237 && rw_i == 1)) {
                    idx_low.add(i);
                }

                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 203 && rw_i == 6)
                        || (pdcode_i == 723 && rw_i == 6)
                        || (pdcode_i == 237 && rw_i == 2)) {
                    idx_med.add(i);
                }

                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 203 && rw_i == 7)
                        || (pdcode_i == 723 && rw_i == 7)
                        || (pdcode_i == 237 && rw_i == 3)) {
                    idx_high.add(i);
                }

            }
        }
        Map<String, String> severity = processSeverityEdgeCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
        output.put("low_avg", severity.get("avg"));
        output.put("low_std", severity.get("std"));

        severity = processSeverityEdgeCrack(tabletoanalyze, idx_sealed, SURVEY_YEAR);
        output.put("sealed_avg", severity.get("avg"));
        output.put("sealed_std", severity.get("std"));

        severity = processSeverityEdgeCrack(tabletoanalyze, idx_med, SURVEY_YEAR);
        output.put("med_avg", severity.get("avg"));
        output.put("med_std", severity.get("std"));

        severity = processSeverityEdgeCrack(tabletoanalyze, idx_high, SURVEY_YEAR);
        output.put("high_avg", severity.get("avg"));
        output.put("high_std", severity.get("std"));

        return output;
    }


    public static Map<String, String> processSeverityAlligatorCrack(CSVTable tabletoanalyze,
                                                                    Set<Integer> idx,
                                                                    String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> AlligatorCrack_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double AlligatorCrack_length_severity = 0;
            if (pdcode_i != 501) {
                AlligatorCrack_length_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            AlligatorCrack_prcnt_severity.add(100 * AlligatorCrack_length_severity / SecLen_mile_severity);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> AlligatorCrack_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double AlligatorCrack_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    AlligatorCrack_prcnt_segment_severity_sum += AlligatorCrack_prcnt_severity.get(j);
                }
                j++;
            }
            if (yr >= 2012 && yr <= 2017) {
                AlligatorCrack_prcnt_segment_severity_sum /= 0.2941;
            }
            if (AlligatorCrack_prcnt_segment_severity_sum > 100) {
                AlligatorCrack_prcnt_segment_severity_sum = 100;
            }
            AlligatorCrack_prcnt_segment_severity.add(AlligatorCrack_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : AlligatorCrack_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / AlligatorCrack_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (AlligatorCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : AlligatorCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / AlligatorCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processAlligatorCrack(CSVTable tabletoanalyze,
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

        // Find the "all severities"
        Set<Integer> idx_low = new HashSet<>();
        Set<Integer> idx_med = new HashSet<>();
        Set<Integer> idx_high = new HashSet<>();

        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 0 && rw_i == 1) ||
                    (col_i == 2 && rw_i == 1) ||
                    (col_i == 2 && rw_i == 2)) {
                idx_low.add(i);
            }

            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 0 && rw_i == 2) ||
                    (col_i == 3 && rw_i == 1) ||
                    (col_i == 4 && rw_i == 1) ||
                    (col_i == 3 && rw_i == 2)) {
                idx_med.add(i);
            }

            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 0 && rw_i == 3) ||
                    (col_i == 5 && rw_i == 1) ||
                    (col_i == 4 && rw_i == 2) ||
                    (col_i == 5 && rw_i == 2)) {
                idx_high.add(i);
            }
        }
        Map<String, String> severity = processSeverityAlligatorCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
        output.put("low_avg", severity.get("avg"));
        output.put("low_std", severity.get("std"));

        severity = processSeverityAlligatorCrack(tabletoanalyze, idx_med, SURVEY_YEAR);
        output.put("med_avg", severity.get("avg"));
        output.put("med_std", severity.get("std"));

        severity = processSeverityAlligatorCrack(tabletoanalyze, idx_high, SURVEY_YEAR);
        output.put("high_avg", severity.get("avg"));
        output.put("high_std", severity.get("std"));

        return output;
    }

    public static Map<String, String> processSeverityBlockCrack(CSVTable tabletoanalyze,
                                                                Set<Integer> idx,
                                                                String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> BlockCrack_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double BlockCrack_length_severity = 0;
            if (pdcode_i != 501) {
                BlockCrack_length_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            BlockCrack_prcnt_severity.add(100 * BlockCrack_length_severity / SecLen_mile_severity);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> BlockCrack_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double BlockCrack_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    BlockCrack_prcnt_segment_severity_sum += BlockCrack_prcnt_severity.get(j);
                }
                j++;
            }
            if (yr >= 2012 && yr <= 2017) {
                BlockCrack_prcnt_segment_severity_sum /= 0.2941;
            }
            if (BlockCrack_prcnt_segment_severity_sum > 100) {
                BlockCrack_prcnt_segment_severity_sum = 100;
            }
            BlockCrack_prcnt_segment_severity.add(BlockCrack_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : BlockCrack_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / BlockCrack_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (BlockCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : BlockCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / BlockCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processBlockCrack(CSVTable tabletoanalyze,
                                                        String CS,
                                                        String SURVEY_YEAR,
                                                        Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }

        // Find the "all severity"
        Set<Integer> idx_low = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if (col_i == 0 && rw_i >= 0 && rw_i <= 10) {
                idx_low.add(i);
            }
        }
        output = processSeverityBlockCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
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

    public static Map<String, String> processSeverityRaveling(CSVTable tabletoanalyze,
                                                              Set<Integer> idx,
                                                              String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> Raveling_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double Raveling_length_severity = 0;
            if (pdcode_i != 501) {
                Raveling_length_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            Raveling_prcnt_severity.add(100 * Raveling_length_severity / SecLen_mile_severity);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> Raveling_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double Raveling_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    Raveling_prcnt_segment_severity_sum += Raveling_prcnt_severity.get(j);
                }
                j++;
            }
            if (yr >= 2012 && yr <= 2017) {
                Raveling_prcnt_segment_severity_sum /= 0.2941;
            }
            if (Raveling_prcnt_segment_severity_sum > 100) {
                Raveling_prcnt_segment_severity_sum = 100;
            }
            Raveling_prcnt_segment_severity.add(Raveling_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : Raveling_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / Raveling_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (Raveling_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : Raveling_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / Raveling_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processRaveling(CSVTable tabletoanalyze,
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
            if (col_i == 0 && rw_i == 0) {
                idx_low.add(i);
            }
        }
        output = processSeverityRaveling(tabletoanalyze, idx_low, SURVEY_YEAR);
        return output;
    }

    public static Map<String, String> processSeverityBleeding(CSVTable tabletoanalyze,
                                                              Set<Integer> idx,
                                                              String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        List<Double> Bleeding_prcnt_severity = new ArrayList<>();
        List<String> BMPs = new ArrayList<>();
        Set<String> CS_BMP_U = new HashSet<>();
        for (int i : idx) {
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            String bmpstr_i = tabletoanalyze.data.get("BMP").get(i);
            double bmp_i = Double.parseDouble(bmpstr_i);
            double emp_i = Double.parseDouble(tabletoanalyze.data.get("EMP").get(i));
            double Bleeding_length_severity = 0;
            if (pdcode_i != 501) {
                Bleeding_length_severity = Double.parseDouble(tabletoanalyze.data.get("LENGTHORCOUNT").get(i));
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            Bleeding_prcnt_severity.add(100 * Bleeding_length_severity / SecLen_mile_severity);
            // Since there are multiple PDs per 0.1 mile segment, they need to be added for each 0.1 mile segment.
            CS_BMP_U.add(bmpstr_i);
            BMPs.add(bmpstr_i);
        }

        List<Double> Bleeding_prcnt_segment_severity = new ArrayList<>();
        for (String CS_BMP_U_i : CS_BMP_U) {
            double Bleeding_prcnt_segment_severity_sum = 0;
            int j = 0;
            for (String bmp_i : BMPs) {
                if (bmp_i.equals(CS_BMP_U_i)) {
                    Bleeding_prcnt_segment_severity_sum += Bleeding_prcnt_severity.get(j);
                }
                j++;
            }
            if (yr >= 2012 && yr <= 2017) {
                Bleeding_prcnt_segment_severity_sum /= 0.2941;
            }
            if (Bleeding_prcnt_segment_severity_sum > 100) {
                Bleeding_prcnt_segment_severity_sum = 100;
            }
            Bleeding_prcnt_segment_severity.add(Bleeding_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : Bleeding_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / Bleeding_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(roundToDecimals(avg, 2)));
        if (Bleeding_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : Bleeding_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / Bleeding_prcnt_segment_severity.size());
            output.put("std", String.valueOf(roundToDecimals(std, 2)));
        }
        return output;
    }

    public static Map<String, String> processBleeding(CSVTable tabletoanalyze,
                                                      String CS,
                                                      String SURVEY_YEAR,
                                                      Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }
        if (CS.contains(" (")) {
            CS = CS.substring(0, CS.indexOf(" ("));
        }

        // Find the "low severity"
        Set<Integer> idx_low = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if (col_i == 0 && rw_i == 0) {
                idx_low.add(i);
            }
        }
        output = processSeverityBleeding(tabletoanalyze, idx_low, SURVEY_YEAR);
        return output;
    }
}
