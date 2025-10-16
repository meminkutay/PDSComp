package com.pdsdataextractor;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.concurrent.Task;
import javafx.scene.control.ListView;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static com.pdsdataextractor.pds_from_sds.compute_pds_from_sds_data;

public class MainController {
    @FXML
    public Label statustext;

    @FXML
    public CSVTable PvmtListToAnalyzePost2020;
    public String inputFilePavementListPost2020;
    @FXML
    private ListView csvlist;

    @FXML
    private ListView csvlistSDS;

    @FXML
    private List<File> selectedPDSfiles;
    @FXML
    private Label SDS_output_folder;

    @FXML
    private Button btnSelectSDSCSV;
    @FXML
    private Button btnComputePDSfromSDSAllRows;
    @FXML
    private Button btnAveragePDSfromSDS;

    @FXML
    private Button btnSelectPavementlistpre2019;
    @FXML
    private Button btnRunButtonClickPre2019;

    @FXML
    private Button btnSelectPavementlistpost2020;
    @FXML
    private Button btnRunButtonClickPost2020;


    @FXML
    private Label HMA_pvmtlist_path;

    @FXML
    private Label HMA_pvmtlist_surftype;

    private List<File> selectedTfiles;

    @FXML
    private ImageView logoImageView; // Ensure this matches the fx:id in FXML

    public void setBannerImage(Image image) {
        logoImageView.setImage(image);
    }

    public MainController() {
        System.out.println("✅ MainController Initialized");
    }

    @FXML
    protected void onRunButtonClick() {

        String HMA_pvmtlist_path_S = HMA_pvmtlist_path.getText();
        File HMA_pvmtlist_path_txt = new File(HMA_pvmtlist_path_S);
        String fld_out = String.valueOf(HMA_pvmtlist_path_txt.getParentFile());

        // ✅ This is fine since it's called from the JavaFX Application Thread
        statustext.setText("Running, please wait... \nThis can take several minutes per Tfile!!!\n" +
                "Periodically check the folder: " + fld_out + " for the csv outputs.");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                CSVTable combinedResults = null;
                long lastSaveTime = System.currentTimeMillis();

                try {
                    long start = System.currentTimeMillis();

                    CSVTable PvmtListToAnalyzeinit = new CSVTable(HMA_pvmtlist_path_S);
                    Set<String> uniqueSurfaces = new HashSet<>(PvmtListToAnalyzeinit.data.get("SURFACE"));

                    if (uniqueSurfaces.size() > 1) {
                        throw new Exception("Inconsistent pavement surface types! Found: " + uniqueSurfaces);
                    }

                    String pvmntsurface = uniqueSurfaces.iterator().next();

                    Platform.runLater(() -> statustext.setText(
                            "Running, please wait... \nThis can take several minutes per Tfile!!!\n" +
                                    "Periodically check the folder: " + fld_out + " for the csv outputs.\n" +
                                    "--> Surface Type: " + pvmntsurface));

                    String pvmnt_list_fname = HMA_pvmtlist_path_txt.getName();
                    int end = pvmnt_list_fname.indexOf(".");
                    String pvmnt_list_fname_noext = pvmnt_list_fname.substring(0, end);
                    String finalOutputPath = fld_out + "/" + pvmnt_list_fname_noext + "_PDS-vals_COMBINED.csv";
                    String tempOutputPath = fld_out + "/" + pvmnt_list_fname_noext + "_PDS-vals_TEMP.csv";

                    System.out.println("STARTING ...");

                    for (File f : selectedTfiles) {
                        CSVTable result = f_process_one_Tfile(f, HMA_pvmtlist_path_S, pvmntsurface);

                        String baseName = f.getName();
                        int dotIndex = baseName.lastIndexOf(".");
                        if (dotIndex != -1) {
                            baseName = baseName.substring(0, dotIndex);  // Remove file extension
                        }
                        String currentyearOutputPath = fld_out + "/" + pvmnt_list_fname_noext + "--" + baseName + "_PDS-vals.csv";
                        result.writeToFile(currentyearOutputPath);
                        System.out.println("✅ Saved individual Tfile result to: " + currentyearOutputPath);


                        if (combinedResults == null) {
                            combinedResults = result;
                        } else {
                            combinedResults = CSVTable.concat(combinedResults, result);
                        }

                    }

                    if (combinedResults != null) {
                        combinedResults.writeToFile(finalOutputPath);
                        System.out.println("✅ Final results saved to: " + finalOutputPath);

                        Platform.runLater(() -> statustext.setText("✅ Finished! Output saved to:\n" + finalOutputPath));
                    }

                    long finish = System.currentTimeMillis();
                    long timeElapsed = finish - start;
                    String finaltxt = "All done --> Elapsed time: " + timeElapsed / 1000.0 + "seconds.";
                    System.out.println(finaltxt);
                    Platform.runLater(() -> statustext.setText(finaltxt));


                } catch (Exception e) {

                    String errMsg = "❌ Error: " + e.getMessage();
                    Platform.runLater(() -> statustext.setText(errMsg));
                    e.printStackTrace();
                }

                return null;
            }
        };


        // ✅ Start the task
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();
    }

    @FXML
    protected void onRunButtonClickMulticore() {

        String HMA_pvmtlist_path_S = HMA_pvmtlist_path.getText();
        File HMA_pvmtlist_path_txt = new File(HMA_pvmtlist_path_S);
        String fld_out = String.valueOf(HMA_pvmtlist_path_txt.getParentFile());

        // ✅ This is fine since it's called from the JavaFX Application Thread
        statustext.setText("Running, please wait... \nThis can take several minutes per Tfile!!!\n" +
                "Periodically check the folder: " + fld_out + " for the csv outputs.");

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                List<Thread> threads = new ArrayList<>();
                List<CSVTable> resultsList = Collections.synchronizedList(new ArrayList<>());

                try {
                    long start = System.currentTimeMillis();

                    CSVTable PvmtListToAnalyzeinit = new CSVTable(HMA_pvmtlist_path_S);
                    Set<String> uniqueSurfaces = new HashSet<>(PvmtListToAnalyzeinit.data.get("SURFACE"));

                    if (uniqueSurfaces.size() > 1) {
                        throw new Exception("Inconsistent pavement surface types! Found: " + uniqueSurfaces);
                    }

                    String pvmntsurface = uniqueSurfaces.iterator().next();

                    Platform.runLater(() -> statustext.setText(
                            "Running, please wait... \nThis can take several minutes per Tfile!!!\n" +
                                    "Periodically check the folder: " + fld_out + " for the csv outputs.\n" +
                                    "--> Surface Type: " + pvmntsurface));

                    String pvmnt_list_fname = HMA_pvmtlist_path_txt.getName();
                    int end = pvmnt_list_fname.indexOf(".");
                    String pvmnt_list_fname_noext = pvmnt_list_fname.substring(0, end);
                    String finalOutputPath = fld_out + "/" + pvmnt_list_fname_noext + "_PDS-vals_COMBINED_multicore.csv";

                    System.out.println("STARTING via Parallel Tasks...");

                    for (File f : selectedTfiles) {
                        Thread thread = new Thread(() -> {
                            try {
                                CSVTable result = f_process_one_Tfile(f, HMA_pvmtlist_path_S, pvmntsurface);
                                synchronized (resultsList) {
                                    resultsList.add(result);
                                }
                                String baseName = f.getName();
                                int dotIndex = baseName.lastIndexOf(".");
                                if (dotIndex != -1) {
                                    baseName = baseName.substring(0, dotIndex);  // Remove file extension
                                }
                                String currentyearOutputPath = fld_out + "/" + pvmnt_list_fname_noext + "--" + baseName + "_PDS-vals.csv";
                                result.writeToFile(currentyearOutputPath);
                                System.out.println("✅ Saved individual Tfile result to: " + currentyearOutputPath);
                                System.out.println("✅ Finished: " + f.getName());
                            } catch (Exception fileErr) {
                                System.err.println("⚠️ Error processing file " + f.getName() + ": " + fileErr.getMessage());
                            }
                        });
                        threads.add(thread);
                        thread.start();
                    }

                    for (Thread t : threads) {
                        t.join();
                    }

                    CSVTable combinedResults = null;
                    for (CSVTable result : resultsList) {
                        if (combinedResults == null) {
                            combinedResults = result;
                        } else {
                            combinedResults = CSVTable.concat(combinedResults, result);
                        }
                    }

                    if (combinedResults != null) {
                        combinedResults.writeToFile(finalOutputPath);
                        System.out.println("✅ Final results saved to: " + finalOutputPath);

                        Platform.runLater(() -> statustext.setText("✅ Finished! Output saved to:\n" + finalOutputPath));
                    }

                    long finish = System.currentTimeMillis();
                    long timeElapsed = finish - start;
                    System.out.println(" --> TOTAL Elapsed time (sec): " + timeElapsed / 1000.0);

                } catch (Exception e) {
                    Platform.runLater(() -> statustext.setText("❌ Error: " + e.getMessage()));
                    e.printStackTrace();
                }

                return null;
            }
        };


        // ✅ Start the task
        Thread th = new Thread(task);
        th.setDaemon(true);
        th.start();

    }

    @FXML
    protected void onRunButtonClickAverageoverpointonemile() {

        statustext.setText("Averaging over 0.1 mile segments \n Starting...\n");

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV file that includes the 0.01 mile PDS");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV file", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);
        String inputFolder = selectedFile.getParent();
        String inputFile = selectedFile.getName();
        //String inputFolder = "/Users/kutay/Library/CloudStorage/OneDrive-MichiganStateUniversity/_Docs/_IdeaProjects/pdsdataextractor2Data/SDS-Data/PDS_OUT_JAVA";
        //String inputFile = "SDS_2024_noBridge_SurfaceType_Concrete_PDS_JAVA.csv";

        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    long start = System.currentTimeMillis();
                    CSVTable resultsaggregation = PDSAggregator.aggregateover01mile(inputFolder, inputFile);
                    if (resultsaggregation == null) {
                        throw new IllegalArgumentException("❌ Error in averaging");
                    }
                    long finish = System.currentTimeMillis();
                    long timeElapsed = finish - start;
                    String finaltxtt = "Aggregation done --> Elapsed time: " + timeElapsed / 1000.0 + "seconds.";
                    Platform.runLater(() -> statustext.setText(finaltxtt));

                } catch (Exception e) {
                    // Handle the exception by updating the UI with an error message
                    Platform.runLater(() -> statustext.setText("Error: " + e.getMessage()));
                    System.err.println("Exception caught in Task: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };


        Thread th = new Thread(task);
        th.setDaemon(true);
        System.out.println("--> Starting " + th);
        th.start();

    }

    @FXML
    protected void onRunButtonClickSDSallrows() {


        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Output Folder");
        File selectedFolder = directoryChooser.showDialog(null);

        if (selectedFolder != null) {
            SDS_output_folder.setText(selectedFolder.getAbsolutePath());
            System.out.println("Selected Folder: " + selectedFolder.getAbsolutePath());
        } else {
            String errortxt = "No folder selected.";
            System.out.println(errortxt);
            // Show popup alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No folder selected");
            alert.setHeaderText("No folder selected");
            alert.setContentText(errortxt);
            alert.showAndWait();

            return;
        }

        String finalFld_out = SDS_output_folder.getText();
        statustext.setText("Running, please wait... \nThis can take several minutes per SDS!!!\n" +
                "Periodically check the folder: " + finalFld_out + " for the csv outputs.");
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() {
                try {
                    long start = System.currentTimeMillis();
                    System.out.println("STARTING SDS calculations...");

                    // Create a combined CSVTable to store all results
                    CSVTable combinedResults = null;

                    // Process data
                    for (File f : selectedPDSfiles) {
                        CSVTable result = f_process_one_SDSfile(f);

                        // Initialize combinedResults if it's the first file
                        if (combinedResults == null) {
                            combinedResults = result;
                        } else {
                            combinedResults = CSVTable.concat(combinedResults, result);
                        }
                    }

                    // Save the combined results to a single file
                    if (combinedResults != null) {
                        String savepath = finalFld_out + "/SDS_results_JAVA_COMBINED.csv";
                        System.out.println("Saving combined results... " + savepath);
                        combinedResults.writeToFile(savepath);
                        System.out.println("Done! Combined results saved.");
                    }

                    long finish = System.currentTimeMillis();
                    long timeElapsed = finish - start;
                    String finaltxt = "All done --> Elapsed time: " + timeElapsed / 1000.0 + "seconds.";
                    System.out.println(finaltxt);
                    btnAveragePDSfromSDS.setDisable(false); // Enable CSV selection
                    Platform.runLater(() -> statustext.setText(finaltxt));


                } catch (Exception e) {
                    // Handle the exception by updating the UI with an error message
                    Platform.runLater(() -> statustext.setText("Error: " + e.getMessage()));
                    System.err.println("Exception caught in Task: " + e.getMessage());
                    e.printStackTrace();
                }
                return null;
            }
        };


        Thread th = new Thread(task);
        th.setDaemon(true);
        System.out.println("--> Starting " + th);
        th.start();


    }
//


    @FXML
    protected void onQuitButtonClick() {
        Platform.exit();
        statustext.setText("Stopped");
    }

    @FXML
    protected void onChooseClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select PD/AD (Pre-2019 data) CSV files");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV file", "*.csv"));
        selectedTfiles = fileChooser.showOpenMultipleDialog(null);
        System.out.println(selectedTfiles);

        csvlist.getItems().clear();
        for (File f : selectedTfiles) {
            csvlist.getItems().add(f.getAbsolutePath());
        }
        btnSelectPavementlistpre2019.setDisable(false);
    }

    @FXML
    protected void onChooseClickSDS() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select SDS (Post-2020 data) CSV files");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV file", "*.csv"));
        selectedPDSfiles = fileChooser.showOpenMultipleDialog(null);
        System.out.println(selectedPDSfiles);

        csvlistSDS.getItems().clear();
        for (File f : selectedPDSfiles) {
            csvlistSDS.getItems().add(f.getAbsolutePath());
        }
        btnComputePDSfromSDSAllRows.setDisable(false); // Enable CSV selection

    }

//
//    @FXML
//    protected void onSelectPavementsClick() throws Exception {
//
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select CSV files");
//        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV file", "*.csv"));
//        File selectedFile = fileChooser.showOpenDialog(null);
//
//        HMA_pvmtlist_path.setText(String.valueOf(selectedFile));
//        File HMA_pvmtlist_path_txt = new File(HMA_pvmtlist_path.getText());
//        String fld = String.valueOf(HMA_pvmtlist_path_txt.getParentFile());
//        String fname = HMA_pvmtlist_path_txt.getName();
//        int end = fname.indexOf(".");
//
//        System.out.println("PARENT FOLDER: " + fld);
//
//        String HMA_pvmtlist_path_S = HMA_pvmtlist_path.getText();
//        CSVTable PvmtListToAnalyzeinit = new CSVTable(HMA_pvmtlist_path_S);
//        String pvmntsurface = PvmtListToAnalyzeinit.data.get("SURFACE").get(0);
//
//        HMA_pvmtlist_surftype.setText(pvmntsurface);
//
//        //        statustext.setText("Results will be saved to \n " + fld + "/" + fname.substring(0, end) + "_JAVA_OUTPUT.csv");
//
//    }

    @FXML
    protected void onSelectPavementsClick() throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV file");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV file", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);
        HMA_pvmtlist_surftype.setText("Surface type: ");

        if (selectedFile != null) {
            HMA_pvmtlist_path.setText(selectedFile.getAbsolutePath());

            String fld = selectedFile.getParent();
            String fname = selectedFile.getName();
            int end = fname.indexOf(".");
            System.out.println("PARENT FOLDER: " + fld);

            // Load data
            CSVTable PvmtListToAnalyzeinit = new CSVTable(selectedFile.getAbsolutePath());

            // ✅ Required columns to check
            List<String> requiredCols = Arrays.asList("SURFACE", "CS", "DIR", "BMP", "EMP");

            for (String col : requiredCols) {
                if (!PvmtListToAnalyzeinit.data.containsKey(col)) {
                    String errortxt = "🚨 Error: Missing required column '" + col + "' in the CSV file.";
                    HMA_pvmtlist_path.setText(errortxt);
                    HMA_pvmtlist_surftype.setText(errortxt);

                    throw new Exception(errortxt);
                }
            }

            // Ensure all rows have the same SURFACE type
            Set<String> uniqueSurfaces = new HashSet<>(PvmtListToAnalyzeinit.data.get("SURFACE"));
            System.out.println("uniqueSurfaces: " + uniqueSurfaces);

            if (uniqueSurfaces.size() > 1) {
                throw new Exception("Error: Inconsistent pavement surface types found in the dataset! " +
                        "Expected a single type, but found: " + uniqueSurfaces);
            }

            // Set surface type
            String pvmntsurface = uniqueSurfaces.iterator().next();
            HMA_pvmtlist_surftype.setText("Surface type: " + pvmntsurface + ", number of rows: " + PvmtListToAnalyzeinit.m);
            btnRunButtonClickPre2019.setDisable(false);

        } else {
            System.out.println("No file selected.");
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
        } else {
            throw new Exception("Pavement surface type error. Only Flexible and Rigid are allowed");
        }

        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(" --> Elapsed time (sec): " + timeElapsed / 1000.0);

        return PvmtListToAnalyze;  // Now PvmtListToAnalyze is accessible
    }

    private CSVTable f_process_one_SDSfile(File f) {
        String inputFilePath = f.getPath();
        System.out.println("\t@thread: [" + Thread.currentThread().threadId() + "] starting --> " + inputFilePath);

        String inputFileName = f.getName();
        String inputFileName_noext = inputFileName.substring(0, inputFileName.length() - 4);
        String outputFile = SDS_output_folder.getText() + "/" + inputFileName_noext + "_PDS_JAVA.csv";

        CSVTable PDS_out_data = compute_pds_from_sds_data(inputFilePath, outputFile);

        return PDS_out_data;
    }

    @FXML
    private void onSelectPavementsPost2020(ActionEvent actionEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV file with pavement list: PR, PR_BMP and PR_EMP");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV file", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);
        String inputFolder = selectedFile.getParent();
        inputFilePavementListPost2020 = selectedFile.getName();
        String HMA_pvmtlist_path_S = inputFolder + "/" + inputFilePavementListPost2020;
        statustext.setText("inputFolder = " + inputFolder + "\n" +
                "inputFilePavementListPost2020 = " + inputFilePavementListPost2020);

        PvmtListToAnalyzePost2020 = new CSVTable(HMA_pvmtlist_path_S);

        // ✅ Required columns to check
        List<String> requiredCols = Arrays.asList("PR", "PR_BMP", "PR_EMP");

        String errortxt = util_pds.validateRequiredColumns(PvmtListToAnalyzePost2020, requiredCols);
        if (errortxt.isEmpty()) {
            statustext.setText("Pavement list: " + inputFilePavementListPost2020);
            btnRunButtonClickPost2020.setDisable(false);
        } else {
            statustext.setText(errortxt);
        }


    }

    @FXML
    public void onComputePDSPavementList2020(ActionEvent actionEvent) throws Exception {

        statustext.setText("Number of rows in the pavement list:" + PvmtListToAnalyzePost2020.m);
        System.out.println(PvmtListToAnalyzePost2020.header);

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV file with PDS data and PR, PR_BMP and PR_EMP");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV file", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(null);
        String inputFolder = selectedFile.getParent();
        String inputFile_sds_data_table = selectedFile.getName();
        String path_to_pds_data_table = inputFolder + "/" + inputFile_sds_data_table;
        statustext.setText("inputFolder = " + inputFolder + "\n" +
                "inputFile_sds_data_table = " + inputFile_sds_data_table);

        CSVTable tabletoanalyze = new CSVTable(path_to_pds_data_table);

        // ✅ Required columns to check
        List<String> requiredCols = Arrays.asList("SurfaceType", "PR", "NormStart_Mi", "NormEnd_Mi",
                "LENGTH", "PDS", "SurveyDateTime", "StaLatitude", "StaLongitude", "EndLatitude", "EndLongitude");

        String errortxt = util_pds.validateRequiredColumns(tabletoanalyze, requiredCols);
        if (errortxt.isEmpty()) {
            statustext.setText("Loaded:" + path_to_pds_data_table);
        } else {
            statustext.setText(errortxt);
            return;  // Stop execution here
        }

        List<Map<String, String>> results = new ArrayList<>();

        for (int j = 0; j < PvmtListToAnalyzePost2020.m; j++) {

            int pr = Integer.parseInt(PvmtListToAnalyzePost2020.data.get("PR").get(j));
            double bmp = Double.parseDouble(PvmtListToAnalyzePost2020.data.get("PR_BMP").get(j));
            double emp = Double.parseDouble(PvmtListToAnalyzePost2020.data.get("PR_EMP").get(j));
            String outtext = "\rComputing the average PDS values for pr:" + pr + " bmp:" + bmp + " emp:" + emp;

            //            System.out.println(outtext);
            //            statustext.setText(outtext);

            util_pds.updateProgressBar(j + 1, PvmtListToAnalyzePost2020.m, 50);


            double weightedSum = 0;
            double totalLength = 0;

            int n = 0;
            String SurveyDateTime = "";
            String StaLatitude = "";
            String StaLongitude = "";
            String EndLatitude = "";
            String EndLongitude = "";
            String surfacetype = "";

            for (int i = 0; i < tabletoanalyze.m; i++) {
                int pr_i = Integer.parseInt(tabletoanalyze.data.get("PR").get(i));
                double bmp_i = Double.parseDouble(tabletoanalyze.data.get("NormStart_Mi").get(i));
                double emp_i = Double.parseDouble(tabletoanalyze.data.get("NormEnd_Mi").get(i));
                if (pr_i == pr && bmp_i >= bmp && emp_i <= emp) {
                    // logic to compute average PDS in this range
                    double pds_i = Double.parseDouble(tabletoanalyze.data.get("PDS").get(i));
                    double length_i = Double.parseDouble(tabletoanalyze.data.get("LENGTH").get(i));
                    weightedSum += pds_i * length_i;
                    totalLength += length_i;
                    if (n == 0) {
                        SurveyDateTime = tabletoanalyze.data.get("SurveyDateTime").get(i);
                        StaLatitude = tabletoanalyze.data.get("StaLatitude").get(i);
                        StaLongitude = tabletoanalyze.data.get("StaLongitude").get(i);
                        surfacetype = tabletoanalyze.data.get("SurfaceType").get(i);
                    }
                    n += 1;
                    EndLatitude = tabletoanalyze.data.get("EndLatitude").get(i);
                    EndLongitude = tabletoanalyze.data.get("EndLongitude").get(i);
                }
            }

            if (n > 0) {
                double avgPDS = (totalLength > 0) ? (weightedSum / totalLength) : 0;
                Map<String, String> row = new LinkedHashMap<>();
                row.put("PR", String.format("%d", pr));  // Integer format
                row.put("PR_BMP", String.format("%.5f", bmp));
                row.put("PR_EMP", String.format("%.5f", emp));
                row.put("LENGTH", String.format("%.5f", emp - bmp));
                row.put("LENGTH_EXCLUDING_GAPS", String.format("%.5f", totalLength));
                row.put("PDS", String.format("%.2f", avgPDS));
                row.put("SurfaceType", surfacetype);
                row.put("SurveyDateTime", SurveyDateTime);
                row.put("StaLatitude", StaLatitude);
                row.put("StaLongitude", StaLongitude);
                row.put("EndLatitude", EndLatitude);
                row.put("EndLongitude", EndLongitude);

                results.add(row);
            }
        }

        if (results.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No Matches Found");
            alert.setHeaderText(null);
            alert.setContentText("No matching segments were found in the selected PDS file.");
            alert.showAndWait();
            statustext.setText("");

            return; // optionally stop further processing
        }

        String outputFile = inputFolder + "/" + inputFile_sds_data_table.replace(".csv", "_pavementlist_JAVA.csv");
        util_pds.writeAggregatedCSV(results, outputFile);
        String outtext = "Pavement list averaging is done." +
                "\n Pavement List: " + inputFilePavementListPost2020 +
                "\n SDS data table: " + inputFile_sds_data_table +
                "\n Output: " + outputFile;

        System.out.println(outtext);
        statustext.setText(outtext);

    }
}
