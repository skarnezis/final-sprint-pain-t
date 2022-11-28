package com.example.pain_t;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * purpose: this is the application code with takes care of the actual application window
 * @author stefanos karnezis
 *
 */
public class Pain_tApplication extends Application {
    /**
     * @param stage the 'stage,' which is essentially the application window
     * @throws IOException for opening,closing,etc.
     * purpose: this is meant to initialize the application window
     * notes: none
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Pain_tApplication.class.getResource("pain_t-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("pain(t)");
        stage.setScene(scene);
        stage.show();
        Pain_tController controller = fxmlLoader.getController();
        try {
            controller.start();
        } catch (Exception e) {
            System.out.println("WHOOPSIE FRICKIN DAISY IT DIDN'T WORK");
        }
        stage.setOnCloseRequest(evt -> {
            // prevent window from closing
            evt.consume();
            // execute own shutdown procedure
            shutdown(stage);
        });
    }

    /**
     * @param args part of that line we all memorize
     * purpose: every java person knows this one
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * @param stage the application window
     * purpose: used in order to prompt the user with 'are you sure?'
     */
    private void shutdown (Stage stage) {
        // you could also use your logout window / whatever here instead
        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to exit? You might have unsaved changes to your work", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES){
            // you may need to close other windows or replace this with Platform.exit();
            stage.close();
        }
    }
}