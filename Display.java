package com.example.pain_t;


import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

/**
 * purpose: this deals with the majority of the display related methods
 * @author stefanos karnezis
 */
public class Display {

    /**
     * @param myTabPane the tabpane of the application
     * @return currentCanvas the current canvas selected
     * purpose: gets the current tab within the application
     * notes: used so that tabs can be implemented
     */
    public static CoolCanvas getCurrentCanvas(TabPane myTabPane)
    {
        CoolTab tempTab = (CoolTab) myTabPane.getSelectionModel().getSelectedItem(); //get tab of tabpane
        ScrollPane tempScrollPane = (ScrollPane) tempTab.getContent(); //get scrollpane off tab
        AnchorPane tempAnchorPane = (AnchorPane) tempScrollPane.getContent(); //get anchorpane off scrollpane
        StackPane tempStackPane = (StackPane) tempAnchorPane.getChildren().get(0); //get stackpane off anchorplane
        tempAnchorPane.setStyle("-fx-background-color: #ffffff"); //set to white
        return (CoolCanvas) tempStackPane.getChildren().get(0); //return that canvas
    }

}
