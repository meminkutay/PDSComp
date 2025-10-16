package com.pdsdataextractor;

import java.util.*;

public class tableProcessorHMA_copy {


    public tableProcessorHMA_copy() {
    }

    public static CSVTable process(String HMA_pvmtlist_path_S, String fpath_pmscsv) throws Exception {

        CSVTable PvmtListToAnalyze = new CSVTable(HMA_pvmtlist_path_S);

        PvmtListToAnalyze.header.add("Long_Center_Crack_L");
        PvmtListToAnalyze.data.put("Long_Center_Crack_L", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_Center_Crack_M");
        PvmtListToAnalyze.data.put("Long_Center_Crack_M", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_Center_Crack_H");
        PvmtListToAnalyze.data.put("Long_Center_Crack_H", new ArrayList<>());

        PvmtListToAnalyze.header.add("Long_Wheelpath_Crack_L");
        PvmtListToAnalyze.data.put("Long_Wheelpath_Crack_L", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_Wheelpath_Crack_M");
        PvmtListToAnalyze.data.put("Long_Wheelpath_Crack_M", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_Center_Crack_L");
        PvmtListToAnalyze.data.put("Long_Center_Crack_L", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_Center_Crack_M");
        PvmtListToAnalyze.data.put("Long_Center_Crack_M", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_Center_Crack_H");
        PvmtListToAnalyze.data.put("Long_Center_Crack_H", new ArrayList<>());

        PvmtListToAnalyze.header.add("Long_Wheelpath_Crack_L");
        PvmtListToAnalyze.data.put("Long_Wheelpath_Crack_L", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_Wheelpath_Crack_M");
        PvmtListToAnalyze.data.put("Long_Wheelpath_Crack_M", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_Wheelpath_Crack_H");
        PvmtListToAnalyze.data.put("Long_Wheelpath_Crack_H", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_Wheelpath_Crack_H");
        PvmtListToAnalyze.data.put("Long_Wheelpath_Crack_H", new ArrayList<>());

        PvmtListToAnalyze.header.add("EdgeCr_L");
        PvmtListToAnalyze.data.put("EdgeCr_L", new ArrayList<>());
        PvmtListToAnalyze.header.add("EdgeCr_M");
        PvmtListToAnalyze.data.put("EdgeCr_M", new ArrayList<>());
        PvmtListToAnalyze.header.add("EdgeCr_H");
        PvmtListToAnalyze.data.put("EdgeCr_H", new ArrayList<>());

        PvmtListToAnalyze.header.add("BlockCracking");
        PvmtListToAnalyze.data.put("BlockCracking", new ArrayList<>());

        PvmtListToAnalyze.header.add("Patching");
        PvmtListToAnalyze.data.put("Patching", new ArrayList<>());

        PvmtListToAnalyze.header.add("Trans_Crack_L");
        PvmtListToAnalyze.data.put("Trans_Crack_L", new ArrayList<>());
        PvmtListToAnalyze.header.add("Trans_Crack_M");
        PvmtListToAnalyze.data.put("Trans_Crack_M", new ArrayList<>());
        PvmtListToAnalyze.header.add("Trans_Crack_H");
        PvmtListToAnalyze.data.put("Trans_Crack_H", new ArrayList<>());

        PvmtListToAnalyze.header.add("Raveling");
        PvmtListToAnalyze.data.put("Raveling", new ArrayList<>());
        PvmtListToAnalyze.header.add("Bleeding");
        PvmtListToAnalyze.data.put("Bleeding", new ArrayList<>());

        System.out.println("Loading " + fpath_pmscsv);
        CSVTable PDDataTable = new CSVTable(fpath_pmscsv);
        System.out.println("Done loading");

        // Assumed that there is only one year in each csv
        String SURVEY_YEAR = PDDataTable.data.get("SURVEY_YEAR").get(0);

        long start = System.currentTimeMillis();

        for (int i = 0; i < PvmtListToAnalyze.m; i++) {
            String CS = PvmtListToAnalyze.data.get("CS").get(i);
            String BMP = PvmtListToAnalyze.data.get("BMP").get(i);
            String EMP = PvmtListToAnalyze.data.get("EMP").get(i);
            String DIR = PvmtListToAnalyze.data.get("DIR").get(i);

            String status_sout = String.format("-->Analyzing CS = %s, DIR = %s, BMP = %s, EMP = %s, YR = %s", CS, DIR, BMP, EMP, SURVEY_YEAR);
            System.out.println(status_sout);

            Map<String, Set<Integer>> idxALL = f_get_idx(PDDataTable, CS, SURVEY_YEAR, DIR, BMP, EMP);

            Map<String, String> TransverseCrack = processTransverseCrack(PDDataTable, CS, SURVEY_YEAR,
                    idxALL.get("TransverseCrack_low"), idxALL.get("TransverseCrack_medhigh"));
            Map<String, String> LongitudinalCenterCrack = processLongitudinalCenterCrack(PDDataTable, CS, SURVEY_YEAR,
                    idxALL.get("LongitudinalCenterCrack"));

            Map<String, String> LongitudinalWheelpathCrack = processLongitudinalWheelpathCrack(PDDataTable, CS, SURVEY_YEAR,
                    idxALL.get("LongitudinalWheelpathCrack"));

            // Add alligator cracking to the high severity longitudinal wheelpath cracking
            Map<String, String> AlligatorCrack = processAlligatorCrack(PDDataTable, CS, SURVEY_YEAR,
                    idxALL.get("AlligatorCrack"));

            double v1 = Double.parseDouble(AlligatorCrack.get("avg"));
            double v2 = Double.parseDouble(LongitudinalWheelpathCrack.get("high_avg"));
            LongitudinalWheelpathCrack.put("high_avg", String.valueOf(v1 + v2));
            LongitudinalWheelpathCrack.put("high_std", "NaN");


            Map<String, String> EdgeCrack = processEdgeCrack(PDDataTable, CS, SURVEY_YEAR,
                    idxALL.get("EdgeCrack"));

            Map<String, String> BlockCrack = processBlockCrack(PDDataTable, CS, SURVEY_YEAR,
                    idxALL.get("BlockCrack"));

            Map<String, String> Patching = processPatching(PDDataTable, CS, SURVEY_YEAR,
                    idxALL.get("Patching"));
            Map<String, String> Raveling = processRaveling(PDDataTable, CS, SURVEY_YEAR,
                    idxALL.get("Raveling"));
            Map<String, String> Bleeding = processBleeding(PDDataTable, CS, SURVEY_YEAR,
                    idxALL.get("Bleeding"));

            PvmtListToAnalyze.data.get("Trans_Crack_L").add(TransverseCrack.get("low_avg"));
            PvmtListToAnalyze.data.get("Trans_Crack_M").add(TransverseCrack.get("med_avg"));
            PvmtListToAnalyze.data.get("Trans_Crack_H").add(TransverseCrack.get("high_avg"));

            PvmtListToAnalyze.data.get("Long_Center_Crack_L").add(LongitudinalCenterCrack.get("low_avg"));
            PvmtListToAnalyze.data.get("Long_Center_Crack_M").add(LongitudinalCenterCrack.get("med_avg"));
            PvmtListToAnalyze.data.get("Long_Center_Crack_H").add(LongitudinalCenterCrack.get("high_avg"));

            PvmtListToAnalyze.data.get("Long_Wheelpath_Crack_L").add(LongitudinalWheelpathCrack.get("low_avg"));
            PvmtListToAnalyze.data.get("Long_Wheelpath_Crack_M").add(LongitudinalWheelpathCrack.get("med_avg"));
            PvmtListToAnalyze.data.get("Long_Wheelpath_Crack_H").add(LongitudinalWheelpathCrack.get("high_avg"));

            PvmtListToAnalyze.data.get("EdgeCr_L").add(EdgeCrack.get("low_avg"));
            PvmtListToAnalyze.data.get("EdgeCr_M").add(EdgeCrack.get("med_avg"));
            PvmtListToAnalyze.data.get("EdgeCr_H").add(EdgeCrack.get("high_avg"));
            PvmtListToAnalyze.data.get("BlockCracking").add(BlockCrack.get("avg"));
            PvmtListToAnalyze.data.get("Patching").add(Patching.get("avg"));

            PvmtListToAnalyze.data.get("Raveling").add(Raveling.get("avg"));
            PvmtListToAnalyze.data.get("Bleeding").add(Bleeding.get("avg"));

            long finish = System.currentTimeMillis();
            long timeElapsed = finish - start;
            System.out.println(" --> Elapsed time per section (sec): " + timeElapsed / 1000.0);
            start = System.currentTimeMillis();

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

        Set<Integer> pdcodes_LongitudinalCenterCrack = new HashSet<>(Arrays.asList(202, 218, 501));
        Set<Integer> pdcodes_LongitudinalWheelpathCrack = new HashSet<>(Arrays.asList(204, 205, 724, 725, 501));

        Set<Integer> pdcodes_AlligatorCrack = new HashSet<>(Arrays.asList(210, 220, 221, 222, 224, 234, 235, 730, 731, 501));

        Set<Integer> pdcodes_EdgeCrack = new HashSet<>(Arrays.asList(201, 203, 236, 237, 721, 723, 501));
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
        Set<Integer> idx_LongitudinalCenterCrack = new HashSet<>();
        Set<Integer> idx_LongitudinalWheelpathCrack = new HashSet<>();
        Set<Integer> idx_AlligatorCrack = new HashSet<>();
        Set<Integer> idx_EdgeCrack = new HashSet<>();
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

                if (pdcodes_LongitudinalCenterCrack.contains(pdcode_i)) {
                    idx_LongitudinalCenterCrack.add(i);
                }

                if (pdcodes_LongitudinalWheelpathCrack.contains(pdcode_i)) {
                    idx_LongitudinalWheelpathCrack.add(i);
                }

                if (pdcodes_AlligatorCrack.contains(pdcode_i)) {
                    idx_AlligatorCrack.add(i);
                }

                if (pdcodes_EdgeCrack.contains(pdcode_i)) {
                    idx_EdgeCrack.add(i);
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
        data.put("TransverseCrack_low", idx_TransverseCrack_low);
        data.put("TransverseCrack_medhigh", idx_TransverseCrack_medhigh);
        data.put("LongitudinalCenterCrack", idx_LongitudinalCenterCrack);
        data.put("LongitudinalWheelpathCrack", idx_LongitudinalWheelpathCrack);
        data.put("AlligatorCrack", idx_AlligatorCrack);
        data.put("EdgeCrack", idx_EdgeCrack);
        data.put("BlockCrack", idx_BlockCrack);
        data.put("Patching", idx_Patching);
        data.put("Raveling", idx_Raveling);
        data.put("Bleeding", idx_Bleeding);

        return data;
    }


    public static Map<String, String> processSeverityLongitudinalCenterlineCrack(CSVTable tabletoanalyze,
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
        output.put("avg", String.valueOf(avg));
        if (LongitudinalCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : LongitudinalCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / LongitudinalCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
        }
        return output;
    }

    public static Map<String, String> processSeverityLongitudinalWheelpathCrack(CSVTable tabletoanalyze,
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
            LongitudinalCrack_prcnt_severity.add(100 * LongitudinalCrack_length_severity / SecLen_mile_severity / 2); // 2 is for two wheelpaths.
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
        output.put("avg", String.valueOf(avg));
        if (LongitudinalCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : LongitudinalCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / LongitudinalCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
        }
        return output;
    }

    public static Map<String, String> processLongitudinalWheelpathCrack(CSVTable tabletoanalyze, String CS, String SURVEY_YEAR,
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
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if (col_i == 0) {
                if (rw_i >= 0 && rw_i <= 5) {
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
        Map<String, String> severity = processSeverityLongitudinalWheelpathCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
        output.put("low_avg", severity.get("avg"));
        output.put("low_std", severity.get("std"));
        severity = processSeverityLongitudinalWheelpathCrack(tabletoanalyze, idx_med, SURVEY_YEAR);
        output.put("med_avg", severity.get("avg"));
        output.put("med_std", severity.get("std"));
        severity = processSeverityLongitudinalWheelpathCrack(tabletoanalyze, idx_high, SURVEY_YEAR);
        output.put("high_avg", severity.get("avg"));
        output.put("high_std", severity.get("std"));

        return output;
    }

    public static Map<String, String> processLongitudinalCenterCrack(CSVTable tabletoanalyze, String CS, String SURVEY_YEAR,
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
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if (col_i == 0) {
                if (rw_i >= 0 && rw_i <= 5) {
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
        Map<String, String> severity = processSeverityLongitudinalCenterlineCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
        output.put("low_avg", severity.get("avg"));
        output.put("low_std", severity.get("std"));
        severity = processSeverityLongitudinalCenterlineCrack(tabletoanalyze, idx_med, SURVEY_YEAR);
        output.put("med_avg", severity.get("avg"));
        output.put("med_std", severity.get("std"));
        severity = processSeverityLongitudinalCenterlineCrack(tabletoanalyze, idx_high, SURVEY_YEAR);
        output.put("high_avg", severity.get("avg"));
        output.put("high_std", severity.get("std"));

        return output;
    }

    public static Map<String, String> processSeverityTransverseCrack(CSVTable tabletoanalyze,
                                                                     Set<Integer> idx,
                                                                     String SURVEY_YEAR) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");
        Integer yr = Integer.parseInt(SURVEY_YEAR);
        Set<Integer> pdcodes = new HashSet<>(Arrays.asList(101, 114, 701));
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
                if (pdcodes.contains(pdcode_i)) {
                    TransverseCrack_occurences_severity /= 4;
                }
            }
            double SecLen_mile_severity = emp_i - bmp_i;
            // According to Minnesota, the TC spacing is 10 feet
            // Max number of cracks for 100% cracking
            double Nmax = SecLen_mile_severity * 5280 / 10;
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
        output.put("avg", String.valueOf(avg));
        if (TransverseCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : TransverseCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / TransverseCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
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

        // Find the "low severity"
        Set<Integer> idx_low = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if ((col_i == 0 && rw_i == 0) || (col_i == 1 && rw_i == 1) || (col_i == 1 && rw_i == 2)
                    || (col_i == 1 && rw_i == 3) || (col_i == 1 && rw_i == 4) || (col_i == 2 && rw_i == 5)
                    || (col_i == 3 && rw_i == 5) || (col_i == 4 && rw_i == 5) || (col_i == 2 && rw_i == 6)
                    || (col_i == 3 && rw_i == 6)) {
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
            EdgeCrack_prcnt_severity.add(100 * EdgeCrack_length_severity / SecLen_mile_severity / 2); // 2 is for two edges
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
        output.put("avg", String.valueOf(avg));
        if (EdgeCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : EdgeCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / EdgeCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
        }
        return output;
    }

    public static Map<String, String> processEdgeCrack(CSVTable tabletoanalyze,
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
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            int pdcode_i = Integer.parseInt(tabletoanalyze.data.get("PDCODE").get(i));
            if (col_i == 0) {
                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 201 && rw_i == 1)
                        || (pdcode_i == 201 && rw_i == 2)
                        || (pdcode_i == 201 && rw_i == 3)
                        || (pdcode_i == 201 && rw_i == 4)
                        || (pdcode_i == 201 && rw_i == 5)
                        || (pdcode_i == 203 && rw_i == 1)
                        || (pdcode_i == 203 && rw_i == 2)
                        || (pdcode_i == 203 && rw_i == 3)
                        || (pdcode_i == 203 && rw_i == 4)
                        || (pdcode_i == 203 && rw_i == 5)
                        || (pdcode_i == 721 && rw_i == 1)
                        || (pdcode_i == 721 && rw_i == 2)
                        || (pdcode_i == 721 && rw_i == 3)
                        || (pdcode_i == 721 && rw_i == 4)
                        || (pdcode_i == 721 && rw_i == 5)
                        || (pdcode_i == 723 && rw_i == 1)
                        || (pdcode_i == 723 && rw_i == 2)
                        || (pdcode_i == 723 && rw_i == 3)
                        || (pdcode_i == 723 && rw_i == 4)
                        || (pdcode_i == 723 && rw_i == 5)
                        || (pdcode_i == 236 && rw_i == 1)
                        || (pdcode_i == 237 && rw_i == 1)) {
                    idx_low.add(i);
                }

                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 201 && rw_i == 6)
                        || (pdcode_i == 203 && rw_i == 6)
                        || (pdcode_i == 721 && rw_i == 6)
                        || (pdcode_i == 723 && rw_i == 6)
                        || (pdcode_i == 236 && rw_i == 2)
                        || (pdcode_i == 237 && rw_i == 2)) {
                    idx_med.add(i);
                }

                if ((pdcode_i == 501 && rw_i == 0)
                        || (pdcode_i == 201 && rw_i == 7)
                        || (pdcode_i == 203 && rw_i == 7)
                        || (pdcode_i == 721 && rw_i == 7)
                        || (pdcode_i == 723 && rw_i == 7)
                        || (pdcode_i == 236 && rw_i == 3)
                        || (pdcode_i == 237 && rw_i == 3)) {
                    idx_high.add(i);
                }

            }
        }
        Map<String, String> severity = processSeverityEdgeCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
        output.put("low_avg", severity.get("avg"));
        output.put("low_std", severity.get("std"));
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
        output.put("avg", String.valueOf(avg));
        if (AlligatorCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : AlligatorCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / AlligatorCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
        }
        return output;
    }

    public static Map<String, String> processAlligatorCrack(CSVTable tabletoanalyze,
                                                            String CS,
                                                            String SURVEY_YEAR,
                                                            Set<Integer> idx) {
        Map<String, String> output = new HashMap<>();
        output.put("avg", "NaN");
        output.put("std", "NaN");

        if (CS.equals("NaN")) {
            return output;
        }

        // Find the "all severities"
        Set<Integer> idx_low = new HashSet<>();
        for (int i : idx) {
            int rw_i = Integer.parseInt(tabletoanalyze.data.get("RW").get(i));
            int col_i = Integer.parseInt(tabletoanalyze.data.get("COL").get(i));
            if ((col_i == 0 && rw_i == 0) || (col_i == 0 && rw_i == 1) || (col_i == 0 && rw_i == 2)
                    || (col_i == 0 && rw_i == 3) || (col_i == 3 && rw_i == 1) || (col_i == 4 && rw_i == 1)
                    || (col_i == 5 && rw_i == 1) || (col_i == 3 && rw_i == 2) || (col_i == 4 && rw_i == 2)
                    || (col_i == 5 && rw_i == 2)) {
                idx_low.add(i);
            }
        }
        output = processSeverityAlligatorCrack(tabletoanalyze, idx_low, SURVEY_YEAR);
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
        output.put("avg", String.valueOf(avg));
        if (BlockCrack_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : BlockCrack_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / BlockCrack_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
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
        output.put("avg", String.valueOf(avg));
        if (Patching_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : Patching_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / Patching_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
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
        output.put("avg", String.valueOf(avg));
        if (Raveling_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : Raveling_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / Raveling_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
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
        output.put("avg", String.valueOf(avg));
        if (Bleeding_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : Bleeding_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / Bleeding_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
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
