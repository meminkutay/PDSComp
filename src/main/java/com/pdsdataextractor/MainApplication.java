package com.pdsdataextractor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main_gui_settings.fxml")); // Ensure correct path
        Scene scene = new Scene(fxmlLoader.load(), 800, 900);
        stage.setTitle("PDSComp V0.2");

        // Ensure the controller is properly initialized AFTER loading FXML
        MainController controller = fxmlLoader.getController();
        if (controller == null) {
            System.err.println("⚠️ Controller is still null! Ensure it's correctly defined in FXML.");
        } else {
            // Set the banner image
            Image bannerImage = new Image(getClass().getResource("/images/banner.png").toExternalForm());
            controller.setBannerImage(bannerImage);
        }

        // Set position of the stage (window) to upper-left corner
        stage.setX(10);
        stage.setY(30);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
