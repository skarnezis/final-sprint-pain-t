package com.example.pain_t;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Stack;
import java.util.Timer;
import java.util.TimerTask;

/**
 * purpose: this deals with the majority of the tab related methods. An extension of the Tab class
 * @author stefanos karnezis
 */
public class CoolTab extends Tab {
    boolean savedOnce = false;
    Timer tracker = new Timer(true);

    String fileLocation = null;

    Stack<Image> prevActions = new Stack<>();
    Stack<Image> redoBackup = new Stack<>();

    /**
     * this function operates the timer for autosave
     *
     * @param tracker the timer for the application
     */
    public void runTimer(Timer tracker, TabPane myTabPane) {
        if(savedOnce) {
            tracker.scheduleAtFixedRate(new TimerTask() //schedule the timer at a fixed interval
            {
                public void run() //create a new timer task 'run'
                {
                    Platform.runLater(() -> {
                        if(fileLocation != null) { //ensure (again) that the file location has been filled
                            try {
                                // save your work
                                saveClick();
                            }
                            catch (IOException e) {throw new RuntimeException();}
                        }
                    });
                }
            }, 0, 3000);
            //System.out.println("timer " + tracker); used for error checking
            System.out.println("3 seconds has passed");
        }
    }

    /**
     * gives me timer hehe
     * @return tracker, the timer
     */
    public Timer getTimer() {
        return tracker;
    }

    /**
     * this returns the current tab that is selected based off the tabpane
     * @param myTabPane
     * @return
     */
    public static CoolTab getCurrentTab(TabPane myTabPane) {
        return (CoolTab) myTabPane.getSelectionModel().getSelectedItem();
    }

    /**
     * take a snapshot of what just happened
     *
     */
    public void getRecentAction() {
        ScrollPane tempScrollPane = (ScrollPane) this.getContent();
        AnchorPane tempAnchorPane = (AnchorPane) tempScrollPane.getContent();
        StackPane tempStackPane = (StackPane) tempAnchorPane.getChildren().get(0);
        CoolCanvas tempCanvas = (CoolCanvas) tempStackPane.getChildren().get(0);
        WritableImage writableImage = new WritableImage((int)tempCanvas.getWidth(), (int)tempCanvas.getHeight());
        Image tempImage = tempStackPane.snapshot(null, writableImage);
        addAction(tempImage);
        System.out.println("getRecentAction");
    }

    /**
     * keeps track of what's been done
     *
     * @param current
     */
    public void addAction(Image current) {
        prevActions.push(current);
    }

    /**
     * used to undo an action
     *
     * @return an object of type image
     */
    public Image getPrevAction() {
        Image backup = null;
        if(prevActions.size() != 1) {
            //getRecentAction();
            backup = prevActions.pop();
            redoBackup.push(backup);
            System.out.println("backup added to redo stack. Size of stack is now " + redoBackup.size());
        }
        System.out.println("addAction");
        return backup;
    }

    /**
     * used to redo an action
     *
     * @return an object of type Image
     */
    public Image redoAction() {
        Image image = null;
        System.out.println("redoAction");
        if(redoBackup.size() >= 1) {
            image = redoBackup.pop();
            System.out.println("image popped. size of stack is now " + redoBackup.size());
        }
        else {
            System.out.println("size of stack is too small. nothing happened");
        }
        return image;
    }

    /**
     * @param widthTB the textbox labeled 'width'
     * purpose: this resizes the width of the stackpane
     * notes: used for the textbox in the top toolbar
     */
    public void resizeWidth(TextField widthTB) {
        String widthStr = widthTB.getText();
        double width = Double.valueOf(widthStr);
        ScrollPane tempScrollPane = (ScrollPane) this.getContent();
        AnchorPane tempAnchorPane = (AnchorPane) tempScrollPane.getContent();
        StackPane myStackPane = (StackPane) tempAnchorPane.getChildren().get(0);
        myStackPane.setMinWidth(width);
        myStackPane.setMaxWidth(width);
    }
    /**
     * @param heightTB the textbox labeled 'height'
     * notes: used for the textbox in the top toolbar
     * purpose: resizes the height of the stackpane
     */
    public void resizeHeight(TextField heightTB) {
        String heightStr = heightTB.getText();
        double height = Double.valueOf(heightStr);
        ScrollPane tempScrollPane = (ScrollPane) this.getContent();
        AnchorPane tempAnchorPane = (AnchorPane) tempScrollPane.getContent();
        StackPane myStackPane = (StackPane) tempAnchorPane.getChildren().get(0);
        myStackPane.setMinHeight(height);
        myStackPane.setMaxHeight(height);
    }



    /**
     * purpose: this function is meant to save the current pain(t) canvas
     * notes: similar to the actual save feature, this program will prompt the user
     *        to name the file the first time they save it, but then will save the file
     *        with the same location, name, etc. as the first time they named it
     * @throws IOException
     */
    public String saveClick() throws IOException {
        FileChooser fileChooser = new FileChooser(); // create a FileChooser
        ScrollPane tempScrollPane = (ScrollPane) this.getContent();
        AnchorPane tempAnchorPane = (AnchorPane) tempScrollPane.getContent();
        StackPane myStackPane = (StackPane) tempAnchorPane.getChildren().get(0);
        CoolCanvas myCanvas = (CoolCanvas) myStackPane.getChildren().get(0);
        try {
            if(fileLocation == null) { // check to see if the image was already saved somewhere

                WritableImage writableImage = new WritableImage((int) myCanvas.getWidth(), (int) myCanvas.getHeight());
                myStackPane.snapshot(null, writableImage);
                fileChooser.setTitle("Save Resource File"); // Title of window
                fileChooser.getExtensionFilters().addAll( // different types of filters for files
                        //new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"),
                        new FileChooser.ExtensionFilter("PNG", "*.png"));
                File selectedFile = fileChooser.showSaveDialog(myCanvas.getScene().getWindow()); // grab the file
                fileLocation = selectedFile.getPath();
                setFileLocation(fileLocation);
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", selectedFile);
                System.out.println("File saved to " + fileLocation);
            }
            else
            { // if the file was already saved, save it the same way again
                WritableImage writableImage = new WritableImage((int) myCanvas.getWidth(), (int) myCanvas.getHeight());
                myStackPane.snapshot(null, writableImage);
                File file = new File(fileLocation);
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
                System.out.println("File saved to " + fileLocation);
            }
        }
        catch (Exception e)
        {
            System.out.println("error saving/save cancelled"); // just in case something goes wrong
        }
        return  fileLocation;
    }

    /**
     * purpose: this function is meant to replicate the 'save as' feature in pain(t)
     * notes: none
     * @return String, the file location/address
     */
    public String saveAsClick() throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("! WARNING !");
        alert.setContentText("Saving between different file types may result " +
                "in some data being lost or a lower quality image...");
        alert.showAndWait();
        FileChooser fileChooser = new FileChooser(); // create a FileChooser
        ScrollPane tempScrollPane = (ScrollPane) this.getContent();
        AnchorPane tempAnchorPane = (AnchorPane) tempScrollPane.getContent();
        StackPane myStackPane = (StackPane) tempAnchorPane.getChildren().get(0);
        CoolCanvas myCanvas = (CoolCanvas) myStackPane.getChildren().get(0);
        try {
            WritableImage writableImage = new WritableImage((int)myCanvas.getWidth(), (int)myCanvas.getHeight());
            myStackPane.snapshot(null, writableImage); // take a snapshot of the current canvas
            BufferedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
            fileChooser.setTitle("Save Resource File"); // Title of window
            fileChooser.getExtensionFilters().addAll( // different types of filters for files
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
            File selectedFile = fileChooser.showSaveDialog(myCanvas.getScene().getWindow()); // grab the file
            fileLocation = selectedFile.getPath(); // save the file location
            if(fileLocation.endsWith(".jpg")) { //if saving as a jpg
                BufferedImage newImage = new BufferedImage((int)myCanvas.getWidth(), (int)myCanvas.getHeight(),
                        BufferedImage.TYPE_3BYTE_BGR);
                int[] px = new int[(int) (myCanvas.getWidth() * myCanvas.getHeight())];
                renderedImage.getRGB(0,0,(int) myCanvas.getWidth(), (int) myCanvas.getHeight(), px,0,
                        (int) myCanvas.getWidth());
                newImage.setRGB(0,0,(int) myCanvas.getWidth(), (int) myCanvas.getHeight(), px,0,
                        (int) myCanvas.getWidth());
                ImageIO.write(newImage, "jpg", new File(selectedFile.getPath()));
            }
            else { //otherwise save as a png
                ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", selectedFile); // save the file
                System.out.println("File saved to " + fileLocation);
            }
        }
        catch (Exception e)
        {
            System.out.println("error saving/save cancelled"); // just in case something goes wrong
        }
        return fileLocation;
    }
    /**
     * purpose: this function is meant to allow the user to open an image and load into the canvas of pain(t)
     *
     * notes: none
     * @return String, the file location/address
     */
    public String openClick() throws IOException {
        Stage fileStage = new Stage();
        try
        {
            FileChooser fileChooser = new FileChooser(); // create a FileChooser

            fileChooser.setTitle("Open Resource File"); // Title of window
            fileChooser.getExtensionFilters().addAll( // different types of filters for files
                    //new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"),
                    new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                    new FileChooser.ExtensionFilter("GIF", "*.gif"),
                    new FileChooser.ExtensionFilter("GIMP", "*gimp"));

            File selectedFile = fileChooser.showOpenDialog(fileStage); // grab the file
            String location = selectedFile.getPath(); // grab the file path
            return location;
        }
        catch (Exception e)
        {
            System.out.println("open cancelled"); // just in case something goes wrong
            return null;
        }
    }

    /**
     * sets the savedOnce value to true
     *
     */
    public void setSavedOnce() {
        savedOnce = true;
    }


    /**
     * sets the file location of that specific tab to where it is saved, accessed, etc.
     *
     * @param address
     */
    public void setFileLocation(String address) {
        fileLocation = address;
    }
}
