package com.example.pain_t;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

import java.io.IOException;

/**
 * purpose: this is meant to handle all actions that take place within the menu bar of the application
 * @author stefanos karnezis
 */
public class MenuBar {

    /**
     * purpose: this function serves as the Help -> about info popup
     *
     * notes: none
     */
    public static void aboutClick() throws IOException {

        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setContentText("I have neither given or received, nor have I tolerated\n" +
                    "others' use of unauthorized aid.\n\n" +
                    "also... here's where I got some pics from:\n https://icons8.com/");
            Font font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12);
            alert.showAndWait();
        }
        catch (Exception e)
        {
            System.out.println("Error clicking about");
        }
    }
    /**
     * @param myCanvas the current canvas that is selected
     * purpose: this function clears the current canvas
     * notes: prompts user to confirm they want canvas cleared
     */
    public static void clearCanvas(Canvas myCanvas) {

        Alert alert = new Alert(Alert.AlertType.NONE, "Are you sure you want to clear this canvas?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            GraphicsContext gc = myCanvas.getGraphicsContext2D();
            gc.clearRect(0, 0, myCanvas.getWidth(), myCanvas.getHeight());
            myCanvas.setWidth(1000);
            myCanvas.setHeight(1000);
        }
    }
    /**
     * @param myCanvas the current canvas that is selected
     * purpose: pastes an image from clipboard
     * notes: none
     */
    public static void pasteButton(Canvas myCanvas) {

        Image image = Clipboard.getSystemClipboard().getImage();
        GraphicsContext gc = myCanvas.getGraphicsContext2D();
        gc.drawImage(image, 0, 0);
    }
}
