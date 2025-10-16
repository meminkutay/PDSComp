package com.pdsdataextractor;

import java.util.*;

public class tableProcessorRIGID_copy {


    public tableProcessorRIGID_copy() {
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

        PvmtListToAnalyze.header.add("Delamination_avg");
        PvmtListToAnalyze.data.put("Delamination_avg", new ArrayList<>());

        PvmtListToAnalyze.header.add("Long_joint_spalling_avg_L");
        PvmtListToAnalyze.data.put("Long_joint_spalling_avg_L", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_joint_spalling_avg_M");
        PvmtListToAnalyze.data.put("Long_joint_spalling_avg_M", new ArrayList<>());
        PvmtListToAnalyze.header.add("Long_joint_spalling_avg_H");
        PvmtListToAnalyze.data.put("Long_joint_spalling_avg_H", new ArrayList<>());

        PvmtListToAnalyze.header.add("Transverse_joint_spalling_avg_L");
        PvmtListToAnalyze.data.put("Transverse_joint_spalling_avg_L", new ArrayList<>());
        PvmtListToAnalyze.header.add("Transverse_joint_spalling_avg_M");
        PvmtListToAnalyze.data.put("Transverse_joint_spalling_avg_M", new ArrayList<>());
        PvmtListToAnalyze.header.add("Transverse_joint_spalling_avg_H");
        PvmtListToAnalyze.data.put("Transverse_joint_spalling_avg_H", new ArrayList<>());

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

        PvmtListToAnalyze.header.add("Trans_Crack_L");
        PvmtListToAnalyze.data.put("Trans_Crack_L", new ArrayList<>());
        PvmtListToAnalyze.header.add("Trans_Crack_M");
        PvmtListToAnalyze.data.put("Trans_Crack_M", new ArrayList<>());
        PvmtListToAnalyze.header.add("Trans_Crack_H");
        PvmtListToAnalyze.data.put("Trans_Crack_H", new ArrayList<>());

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

            Map<String, String> TransverseCrack = processTransverseCrack(PDDataTable, CS, SURVEY_YEAR, idxALL.get("TransverseCrack"));
            Map<String, String> LongitudinalCenterCrack = processLongitudinalCenterCrack(PDDataTable, CS, SURVEY_YEAR, idxALL.get("LongitudinalCenterCrack"));
            Map<String, String> LongitudinalWheelpathCrack = processLongitudinalWheelpathCrack(PDDataTable, CS, SURVEY_YEAR, idxALL.get("LongitudinalWheelpathCrack"));

            Map<String, String> TransverseJointSpall = processTransverseJointSpall(PDDataTable, CS, SURVEY_YEAR, idxALL.get("TransverseJointSpall"));
            Map<String, String> LongitudinalJointSpall = processLongitudinalJointSpalling(PDDataTable, CS, SURVEY_YEAR, idxALL.get("LongitudinalJointSpalling"));

            Map<String, String> DelaminatedArea = processDelaminatedArea(PDDataTable, CS, SURVEY_YEAR, idxALL.get("DelaminatedArea"));

            // Add alligator cracking to the high severity longitudinal wheelpath cracking

            PvmtListToAnalyze.data.get("Trans_Crack_L").add(TransverseCrack.get("low_avg"));
            PvmtListToAnalyze.data.get("Trans_Crack_M").add(TransverseCrack.get("med_avg"));
            PvmtListToAnalyze.data.get("Trans_Crack_H").add(TransverseCrack.get("high_avg"));

            PvmtListToAnalyze.data.get("Transverse_joint_spalling_avg_L").add(TransverseJointSpall.get("low_avg"));
            PvmtListToAnalyze.data.get("Transverse_joint_spalling_avg_M").add(TransverseJointSpall.get("med_avg"));
            PvmtListToAnalyze.data.get("Transverse_joint_spalling_avg_H").add(TransverseJointSpall.get("high_avg"));

            PvmtListToAnalyze.data.get("Long_joint_spalling_avg_L").add(LongitudinalJointSpall.get("low_avg"));
            PvmtListToAnalyze.data.get("Long_joint_spalling_avg_M").add(LongitudinalJointSpall.get("med_avg"));
            PvmtListToAnalyze.data.get("Long_joint_spalling_avg_H").add(LongitudinalJointSpall.get("high_avg"));

            PvmtListToAnalyze.data.get("Long_Center_Crack_L").add(LongitudinalCenterCrack.get("low_avg"));
            PvmtListToAnalyze.data.get("Long_Center_Crack_M").add(LongitudinalCenterCrack.get("med_avg"));
            PvmtListToAnalyze.data.get("Long_Center_Crack_H").add(LongitudinalCenterCrack.get("high_avg"));

            PvmtListToAnalyze.data.get("Long_Wheelpath_Crack_L").add(LongitudinalWheelpathCrack.get("low_avg"));
            PvmtListToAnalyze.data.get("Long_Wheelpath_Crack_M").add(LongitudinalWheelpathCrack.get("med_avg"));
            PvmtListToAnalyze.data.get("Long_Wheelpath_Crack_H").add(LongitudinalWheelpathCrack.get("high_avg"));

            PvmtListToAnalyze.data.get("Delamination_avg").add(DelaminatedArea.get("avg"));


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


        Set<Integer> pdcodes_TransverseCrack = new HashSet<>(Arrays.asList(102, 105, 107, 112, 113, 712, 713, 501));
        Set<Integer> pdcodes_LongitudinalCenterCrack = new HashSet<>(Arrays.asList(219, 228, 231, 738, 741, 501));
        Set<Integer> pdcodes_LongitudinalWheelpathCrack = new HashSet<>(Arrays.asList(206, 207, 212, 213, 214, 215, 227, 229, 230, 232, 737, 740, 742, 501));
        Set<Integer> pdcodes_TransverseJointSpall = new HashSet<>(Arrays.asList(106, 706, 501));
        Set<Integer> pdcodes_LongitudinalJointSpalling = new HashSet<>(Arrays.asList(208, 209, 501));
        Set<Integer> pdcodes_DelaminatedArea = new HashSet<>(Arrays.asList(301, 341, 751, 501));


        if (CS.contains(" (")) {
            CS = CS.substring(0, CS.indexOf(" ("));
        }

        int cs = Integer.parseInt(CS);
        int yr = Integer.parseInt(SURVEY_YEAR);
        double bmp = Double.parseDouble(BMP);
        double emp = Double.parseDouble(EMP);
        Set<Integer> idx_TransverseCrack = new HashSet<>();
        Set<Integer> idx_LongitudinalCenterCrack = new HashSet<>();
        Set<Integer> idx_LongitudinalWheelpathCrack = new HashSet<>();
        Set<Integer> idx_TransverseJointSpall = new HashSet<>();
        Set<Integer> idx_LongitudinalJointSpalling = new HashSet<>();
        Set<Integer> idx_DelaminatedArea = new HashSet<>();

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

                if (pdcodes_LongitudinalCenterCrack.contains(pdcode_i)) {
                    idx_LongitudinalCenterCrack.add(i);
                }

                if (pdcodes_LongitudinalWheelpathCrack.contains(pdcode_i)) {
                    idx_LongitudinalWheelpathCrack.add(i);
                }

                if (pdcodes_TransverseJointSpall.contains(pdcode_i)) {
                    idx_TransverseJointSpall.add(i);
                }

                if (pdcodes_LongitudinalJointSpalling.contains(pdcode_i)) {
                    idx_LongitudinalJointSpalling.add(i);
                }

                if (pdcodes_DelaminatedArea.contains(pdcode_i)) {
                    idx_DelaminatedArea.add(i);
                }


            }
        }

        Map<String, Set<Integer>> data = new HashMap<>();
        data.put("TransverseCrack", idx_TransverseCrack);
        data.put("LongitudinalCenterCrack", idx_LongitudinalCenterCrack);
        data.put("LongitudinalWheelpathCrack", idx_LongitudinalWheelpathCrack);
        data.put("TransverseJointSpall", idx_TransverseJointSpall);
        data.put("LongitudinalJointSpalling", idx_LongitudinalJointSpalling);
        data.put("DelaminatedArea", idx_DelaminatedArea);

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

            //            if (LongitudinalCrack_prcnt_segment_severity_sum > 100) {
            //                LongitudinalCrack_prcnt_segment_severity_sum = 100;
            //            }
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
            //            if (LongitudinalCrack_prcnt_segment_severity_sum > 100) {
            //                LongitudinalCrack_prcnt_segment_severity_sum = 100;
            //            }
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
            //            if (TransverseJoint_prcnt_segment_severity_sum > 100) {
            //                TransverseJoint_prcnt_segment_severity_sum = 100;
            //            }
            TransverseJoint_prcnt_segment_severity.add(TransverseJoint_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : TransverseJoint_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / TransverseJoint_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(avg));
        if (TransverseJoint_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : TransverseJoint_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / TransverseJoint_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
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

            // Commented below --> No 100% cap
            //            if (TransverseCrack_prcnt_segment_severity_sum > 100) {
            //                TransverseCrack_prcnt_segment_severity_sum = 100;
            //            }
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
            if ((col_i == 0 && rw_i == 0) ||
                    (col_i == 1 && rw_i == 1) ||
                    (col_i == 1 && rw_i == 2) ||
                    (col_i == 1 && rw_i == 3) ||
                    (col_i == 1 && rw_i == 4) ||
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
            LongJoint_prcnt_severity.add(100 * LongJoint_length_severity / SecLen_mile_severity / 2); // 2 is for two edges
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
            //            if (EdgeCrack_prcnt_segment_severity_sum > 100) {
            //                EdgeCrack_prcnt_segment_severity_sum = 100;
            //            }
            LongJoint_prcnt_segment_severity.add(EdgeCrack_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : LongJoint_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / LongJoint_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(avg));
        if (LongJoint_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : LongJoint_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / LongJoint_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
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
            //            if (Delamination_prcnt_segment_severity_sum > 100) {
            //                Delamination_prcnt_segment_severity_sum = 100;
            //            }

            Delamination_prcnt_segment_severity.add(Delamination_prcnt_segment_severity_sum);
        }
        double avg = 0;
        for (double d : Delamination_prcnt_segment_severity) {
            avg += d;
        }
        avg = avg / Delamination_prcnt_segment_severity.size();
        output.put("avg", String.valueOf(avg));
        if (Delamination_prcnt_segment_severity.size() > 1) {
            double std = 0;
            for (double d : Delamination_prcnt_segment_severity) {
                std += (d - avg) * (d - avg);
            }
            std = Math.sqrt(std / Delamination_prcnt_segment_severity.size());
            output.put("std", String.valueOf(std));
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

}
