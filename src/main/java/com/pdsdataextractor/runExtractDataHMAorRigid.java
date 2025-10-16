package com.pdsdataextractor;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class runExtractDataHMAorRigid {
    public runExtractDataHMAorRigid() {
    }


    public static void main(String[] args) throws Exception {

        String HMA_or_RIGID_pvmtlist_path_S = "data/HMA_pvmtlist.csv";
//        String HMA_or_RIGID_pvmtlist_path_S = "data/HMA_pvmtlist_5.csv";
//        String HMA_or_RIGID_pvmtlist_path_S = "data/Rigid_pvmtlist_5.csv";

        String t_file_path = "data/csvs/";
        File dir = new File(t_file_path);

        File[] fileList = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".csv"));
        // Sort fileList by name
        Arrays.sort(fileList, new Comparator() {
            @Override
            public int compare(Object f1, Object f2) {
                return ((File) f1).getName().compareTo(((File) f2).getName());
            }
        });

        //Prints the fileList in file name ascending order
        for (File file : fileList) {
            System.out.println(file.getName());
        }

        run(HMA_or_RIGID_pvmtlist_path_S, fileList);

    }

    public static void run(String HMA_pvmtlist_path_S, File[] files) throws Exception {
        // Read data
        File HMA_pvmtlist_path_txt = new File(HMA_pvmtlist_path_S);
        String fld_out = String.valueOf(HMA_pvmtlist_path_txt.getParentFile());
        String pvmnt_list_fname = HMA_pvmtlist_path_txt.getName();
        int end = pvmnt_list_fname.indexOf(".");

        String pvmnt_list_fname_noext = pvmnt_list_fname.substring(0, end);


        CSVTable PvmtListToAnalyzeinit = new CSVTable(HMA_pvmtlist_path_S);

        // Ensure all rows have the same pavement surface type
        Set<String> uniqueSurfaces = new HashSet<>(PvmtListToAnalyzeinit.data.get("SURFACE"));

        if (uniqueSurfaces.size() > 1) {
            throw new Exception("Error: Inconsistent pavement surface types found in the dataset! " +
                    "Expected a single type, but found: " + uniqueSurfaces);
        }

        // Now it's safe to retrieve the surface type
        String pvmntsurface = PvmtListToAnalyzeinit.data.get("SURFACE").get(0);
        System.out.println("STARTING ...");

        // Create a combined CSVTable to store all results
        CSVTable combinedResults = null;

        // Process data
        for (File f : files) {
            CSVTable result = f_process_one_Tfile(f, HMA_pvmtlist_path_S, pvmntsurface);

            // Initialize combinedResults if it's the first file
            if (combinedResults == null) {
                combinedResults = result;
            } else {
                combinedResults = CSVTable.concat(combinedResults, result);
            }
        }

        // Save the combined results to a single file
        if (combinedResults != null) {
            String savepath = fld_out + "/" + pvmnt_list_fname_noext + "_JAVA_COMBINED.csv";
            System.out.println("Saving combined results... " + savepath);
            combinedResults.writeToFile(savepath);
            System.out.println("Done! Combined results saved.");
        }
    }

    public static CSVTable f_process_one_Tfile(File f, String PvmtlistPathS, String pvmntsurface) throws Exception {
        long start = System.currentTimeMillis();
        String fpath_pmscsv = f.getPath();
        System.out.println("\t@thread: [" + Thread.currentThread().threadId() + "] starting --> " + fpath_pmscsv);

        CSVTable PvmtListToAnalyze;  // Declare the variable before the if-else block

        if (pvmntsurface.equals("Flexible")) {
            System.out.println("\t Surface = " + pvmntsurface);
            PvmtListToAnalyze = tableProcessorHMA.process(PvmtlistPathS, fpath_pmscsv);
        } else if (pvmntsurface.equals("Rigid")) {
            System.out.println("\t Surface = " + pvmntsurface);
            PvmtListToAnalyze = tableProcessorRIGID.process(PvmtlistPathS, fpath_pmscsv);
            //            PvmtListToAnalyze = tableProcessorRIGID_copy.process(PvmtlistPathS, fpath_pmscsv);
        } else {
            throw new Exception("Pavement surface type error. Only Flexible and Rigid are allowed");
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(" --> Elapsed time (sec): " + timeElapsed / 1000.0);

        return PvmtListToAnalyze;  // Now PvmtListToAnalyze is accessible
    }


}
