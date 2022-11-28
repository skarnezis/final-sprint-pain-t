/**********************************************************************
 * Program: CS-250 Microsoft Pain(t)
 * ********************************************************************
 * @author stefanos karnezis
 * date created: 9/8/2022
 * last updated: 11/27/2022
 * *******************************************************************
 * purpose: this program is meant to be the beginning of programming
 *          Microsoft paint using JavaFX
 * *******************************************************************
 * notes: see below
 *
 * To create a new feature...
 *    1. Have an FXML function in the controller class associated with the FXML file
 *    2. create a method in the appropriate class for this action
 *    3. in the method of the appropriate class, code what the action should do
 *    4. pass the method to the FXML method in the controller class
 *
 * current bonus features will be listed in release notes
 *********************************************************************/

package com.example.pain_t;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * This is the highest level in the hierarchy of the code. All methods across all classes work together in this class in order to make paint operate with the input from the user
 *
 */
public class Pain_tController {
    public Pain_tController() throws IOException {
    }

    // determines switch statement for mouse actions

    /**
     * Related to the switch statement for the mouse actions.
     *
     */
    public enum Mode {
        Cursor, Draw, Rectangle, Square,
        Circle, Ellipse, StraightLn, Eyedrop, FreePoly,
        RoundRect, Triangle, Select, Copy, Copy2, Move
    }
    public Mode selector = Mode.Cursor;
    double x1 = -1000;
    double x2 = -1000;
    double y1 = -1000;
    double y2 = -1000;
    int untitledCounter = 1;
    boolean hashed = false;
    int numSides = 0;
    double[] xPoints = new double[100];
    double[] yPoints = new double[100];
    File file2 = new File("C:\\CS-250\\Paint2.0\\Pain_t\\logger.txt");
    BufferedWriter logger = new BufferedWriter(new FileWriter(file2));
    Clipboard clipboard;
    ClipboardContent content;
    Image test;
    @FXML
    public ColorPicker colorSelector;
    @FXML
    public TabPane myTabPane;
    @FXML
    TextField widthTB;
    @FXML
    TextField heightTB;
    @FXML
    ToolBar defaultToolbar;
    @FXML
    private StackPane myStackPane;

    Timer timer = new Timer();

    /**
     * starts up the application, creating a new tab and beginning the timer for the logger
     *
     * @throws Exception
     */
    public void start() throws Exception {
        newTab();
        CoolTab.getCurrentTab(myTabPane).getRecentAction();
        runTimer(timer);
        logger.write("Application started; " + java.time.LocalDateTime.now() + "\n");
    }

    /**
     * method to run the timer
     *
     * @param tracker the timer
     */
    public void runTimer(Timer tracker) {
            tracker.scheduleAtFixedRate(new TimerTask() //schedule the timer at a fixed interval
            {
                public void run() //create a new timer task 'run'
                {
                    Platform.runLater(() -> {
                            try {
                                logger.flush();
                            }
                            catch (IOException e) {System.out.println("Error");}
                    });
                }
            }, 0, 3000);
            //System.out.println("timer " + tracker); used for error checking
    }

    //MenuBar
    @FXML
    void openClick(ActionEvent event) throws Exception {
        String location = CoolTab.getCurrentTab(myTabPane).openClick();
        CoolTab.getCurrentTab(myTabPane).setFileLocation(location);
        Display.getCurrentCanvas(myTabPane).showImage(location);
        CoolTab.getCurrentTab(myTabPane).getRecentAction();
        logger.write("Open Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    @FXML
    void saveAsClick(ActionEvent event) throws Exception {
        CoolTab.getCurrentTab(myTabPane).saveAsClick();
        CoolTab.getCurrentTab(myTabPane).setSavedOnce();
        CoolTab.getCurrentTab(myTabPane).runTimer(CoolTab.getCurrentTab(myTabPane).getTimer(), myTabPane);
        logger.write("Save As Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    @FXML
    void saveClick() throws IOException {
        CoolTab.getCurrentTab(myTabPane).saveClick();
        CoolTab.getCurrentTab(myTabPane).setSavedOnce();
        CoolTab.getCurrentTab(myTabPane).runTimer(CoolTab.getCurrentTab(myTabPane).getTimer(), myTabPane);
        logger.write("Save Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    @FXML
    void aboutClick(ActionEvent event) throws Exception {
        MenuBar.aboutClick();
        logger.write("About Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    @FXML
    void clearCanvas(ActionEvent event) throws IOException{
        MenuBar.clearCanvas(Display.getCurrentCanvas(myTabPane));
        CoolTab.getCurrentTab(myTabPane).getRecentAction();
        logger.write("Clear Canvas Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    @FXML
    void pasteButton(ActionEvent event) throws IOException {
        MenuBar.pasteButton(Display.getCurrentCanvas(myTabPane));
        CoolTab.getCurrentTab(myTabPane).getRecentAction();
        logger.write("Paste Button Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    @FXML
    void undoClick() throws IOException{
        GraphicsContext gc = Display.getCurrentCanvas(myTabPane).getGraphicsContext2D();
        gc.drawImage(CoolTab.getCurrentTab(myTabPane).getPrevAction(), 0,0);
        logger.write("Undo Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    @FXML
    void redoClick() throws IOException{
        GraphicsContext gc = Display.getCurrentCanvas(myTabPane).getGraphicsContext2D();
        gc.drawImage(CoolTab.getCurrentTab(myTabPane).redoAction(),0,0);
        logger.write("Redo Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }

    // Display
    @FXML
    void rotateCanvas() throws IOException{
        Display.getCurrentCanvas(myTabPane).rotateCanvas();
        logger.write("Rotate Canvas Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    @FXML
    void resizeWidth() throws IOException{
        CoolTab.getCurrentTab(myTabPane).resizeWidth(widthTB);
        logger.write("Resize Width Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    @FXML
    void resizeHeight() throws IOException{
        CoolTab.getCurrentTab(myTabPane).resizeHeight(heightTB);
        logger.write("Resize Height Clicked; " + java.time.LocalDateTime.now() + "\n");
        //logger.flush();
    }
    /**
     * purpose: creates a new mf TAB.
     *
     * notes: can be called bt Ctrl+T.
     * @throws IOException
     */
    @FXML
    public void newTab() throws IOException {

        // new Tab
        CoolTab newTab = new CoolTab();
        myTabPane.getTabs().add(newTab);
        newTab.setText("Untitled Tab " + untitledCounter);
        untitledCounter++;

        // new ScrollPane for Tab
        ScrollPane newScrollPane = new ScrollPane();
        newScrollPane.setPrefSize(10,10);
        newTab.setContent(newScrollPane);

        // new AnchorPane for AnchorPane
        AnchorPane newAnchorPane = new AnchorPane();
        newAnchorPane.setPrefSize(20,20);
        newAnchorPane.setStyle("-fx-background-color: #ffffff");
        newScrollPane.setContent(newAnchorPane);

        // new StackPane
        StackPane newStackPane = new StackPane();
        newStackPane.setPrefSize(20,20);
        newAnchorPane.getChildren().add(newStackPane);

        // new Canvas for ScrollPane
        CoolCanvas newCanvas = new CoolCanvas();
        newCanvas.setWidth(newScrollPane.getScene().getWindow().getWidth());
        newCanvas.setHeight(newScrollPane.getScene().getWindow().getHeight());
        newStackPane.getChildren().add(newCanvas);

        newTab.getRecentAction();
        myTabPane.getSelectionModel().select(newTab);

        newCanvas.setOnMousePressed((MouseEvent event) -> {
            mousePress(event);
        });
        newCanvas.setOnMouseDragged((MouseEvent event) -> {
            mouseDragged(event);
        });
        newCanvas.setOnMouseReleased((MouseEvent event) -> {
            mouseReleased(event);
        });
        logger.write("New Tab Created; " + java.time.LocalDateTime.now() + "\n");
    }
    // ToolBarOne
    @FXML
    void colorSelectorClick(ActionEvent event) throws Exception {
        Display.getCurrentCanvas(myTabPane).changeColor(colorSelector.getValue());
        logger.write("Color Selector Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void cursorClick(ActionEvent event) throws Exception {
        selector = Mode.Cursor;
        Display.getCurrentCanvas(myTabPane).cursorClick();
        logger.write("Cursor Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void drawLineClick(ActionEvent event) throws Exception {
        selector = Mode.Draw;
        Display.getCurrentCanvas(myTabPane).drawLineClick(colorSelector);
        logger.write("Draw Line Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void eraserAction(ActionEvent event) throws Exception {
        selector = Mode.Draw;
        Display.getCurrentCanvas(myTabPane).eraserAction();
        logger.write("Eraser Clicked; " + java.time.LocalDateTime.now() + "\n");
        logger.flush();
    }
    @FXML
    void drawSquare() throws IOException {
        selector = Mode.Square;
        Display.getCurrentCanvas(myTabPane).drawSquare();
        logger.write("Draw Square Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void drawRect() throws IOException{
        selector = Mode.Rectangle;
        Display.getCurrentCanvas(myTabPane).drawRect();
        logger.write("Draw Rectangle Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void drawCircle() throws IOException{
        selector = Mode.Circle;
        Display.getCurrentCanvas(myTabPane).drawCircle();
        logger.write("Draw Circle Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void drawEllipse() throws IOException{
        selector = Mode.Ellipse;
        Display.getCurrentCanvas(myTabPane).drawEllipse();
        logger.write("Draw Ellipse Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void drawPoly() throws IOException {
        selector = Mode.FreePoly;
        Display.getCurrentCanvas(myTabPane).drawPoly();
        logger.write("Draw free shape Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void drawTriangle() throws IOException{
        selector = Mode.Triangle;
        Display.getCurrentCanvas(myTabPane).drawTriangle();
        logger.write("Draw Triangle Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void freeHandDashed () throws IOException {
        selector = Mode.Draw;
        Display.getCurrentCanvas(myTabPane).freeHandDashed();
        logger.write("Draw freehand dashed Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void freeHandDraw() throws IOException{
        selector = Mode.Draw;
        Display.getCurrentCanvas(myTabPane).freeHandDraw();
        logger.write("Draw freehand Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void drawRoundRect() throws IOException{
        selector = Mode.RoundRect;
        Display.getCurrentCanvas(myTabPane).drawRoundRect();
        logger.write("Draw Round Rectangle Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void drawStrLine() throws IOException{
        selector = Mode.StraightLn;
        Display.getCurrentCanvas(myTabPane).drawStrLine();
        logger.write("Draw Straight Line Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void drawStrLineDashed() throws IOException{
        selector = Mode.StraightLn;
        Display.getCurrentCanvas(myTabPane).drawStrLineDashed();
        logger.write("Draw Straight Dashed Line Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void eyedropTool() throws IOException{
        selector = Mode.Eyedrop;
        logger.write("Eyedrop Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void copyMove() throws IOException{
        selector = Mode.Select;
        logger.write("Copy/Move Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void selectMove() throws IOException{
        selector = Mode.Move;
        logger.write("Select/Move Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void thickOneClick(ActionEvent event) throws Exception {
        Display.getCurrentCanvas(myTabPane).thickOneClick();
        logger.write("Thickness 1.0 Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void thickTwoClick(ActionEvent event) throws Exception {
        Display.getCurrentCanvas(myTabPane).thickTwoClick();
        logger.write("Thickness 2.0 Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void thickFiveClick(ActionEvent event) throws Exception {
        Display.getCurrentCanvas(myTabPane).thickFiveClick();
        logger.write("Thickness 5.0 Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void thickEightClick(ActionEvent event) throws Exception {
        Display.getCurrentCanvas(myTabPane).thickEightClick();
        logger.write("Thickness 8.0 Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void thickTenClick(ActionEvent event) throws Exception {
        Display.getCurrentCanvas(myTabPane).thickTenClick();
        logger.write("Thickness 10.0 Clicked; " + java.time.LocalDateTime.now() + "\n");
    }
    @FXML
    void thiQqClick(ActionEvent event) throws Exception {
        Display.getCurrentCanvas(myTabPane).thiQqClick();
        logger.write("THIQQ Clicked; " + java.time.LocalDateTime.now() + "\n");
    }

    /**
     * takes care of all cases of whenever the mouse is pressed
     *
     * @param event a mouse event
     */
    @FXML
    void mousePress(MouseEvent event) {
        CoolTab.getCurrentTab(myTabPane).getRecentAction();
        GraphicsContext gc = Display.getCurrentCanvas(myTabPane).getGraphicsContext2D();
        gc.getStroke();
        gc.getLineWidth();
        switch(selector) {
            case Cursor:
                break;

            case Draw:
            case StraightLn:
                gc.beginPath();
                gc.moveTo(event.getX(), event.getY());
                gc.stroke();
                break;

            case Square:
            case Rectangle:
            case Circle:
            case Ellipse:
            case RoundRect:
            case Triangle:
            case Select:
            case Move:
                x1 = event.getX();
                y1 = event.getY();
                break;

            case Eyedrop:
                WritableImage image = gc.getCanvas().snapshot(null, null); //grab a snapshot...
                Color aRGB = image.getPixelReader().getColor((int) event.getX(), (int) event.getY()); //...of the cursor location
                colorSelector.setValue(aRGB); //set the colorPicker to the RGB value of that pixel
                break;

            case FreePoly:
                if(numSides == 0) { // if no shape has been created yet
                    x1 = event.getX(); // x1, y1 is the first dot
                    y1 = event.getY();
                    gc.beginPath();
                    gc.moveTo(event.getX(), event.getY());
                    gc.stroke(); // begin a straight line draw to initialize the first side
                    System.out.println(numSides);
                }
                else {
                    // otherwise do nothing
                }
                break;

            case Copy:
                // paste the clipboard shit here
                gc.drawImage(test, event.getX(), event.getY()); //put the selected image here
                gc.closePath();
                selector = Mode.Cursor; //go back to the cursor

            case Copy2:
                gc.drawImage(test, event.getX(), event.getY()); //put the selected image here
                gc.closePath();
                gc.setFill(Color.WHITE);
                gc.fillRect(x1,y1,x2-x1,y2-y1);
                selector = Mode.Cursor; //go back to the cursor
        }
    }

    /**
     * takes care of all cases whenever the mouse is dragged
     *
     * @param event
     */
    @FXML
    void mouseDragged(MouseEvent event) {
        //ToolbarOne.mouseDragged(Display.getCurrentCanvas(myTabPane), event);
        GraphicsContext gc = Display.getCurrentCanvas(myTabPane).getGraphicsContext2D();
        switch(selector) {
            case Cursor:
            case StraightLn:
            case Eyedrop:
            case Rectangle:
            case Ellipse:
            case Square:
            case Circle:
            case RoundRect:
            case FreePoly:
            case Select:
            case Move:
                break;

            case Draw:
                gc.lineTo(event.getX(), event.getY());
                gc.stroke();
                gc.closePath();
                gc.beginPath();
                gc.moveTo(event.getX(), event.getY());
                break;
        }
    }

    /**
     * takes care of all cases whenever the mouse is released
     *
     * @param event
     */
    @FXML
    void mouseReleased(MouseEvent event){
        //ToolbarOne.mouseReleased(Display.getCurrentCanvas(myTabPane), event);
        GraphicsContext gc = Display.getCurrentCanvas(myTabPane).getGraphicsContext2D();
        switch(selector) {
            case Cursor:
            case Eyedrop:
                break;

            case Draw:
            case StraightLn:
                gc.lineTo(event.getX(), event.getY());
                gc.stroke();
                gc.closePath();
                break;

            case Square:
                x2 = event.getX();
                y2 = event.getY();
                gc.strokeRect(x1, y1, x2-x1, x2-x1);
                gc.closePath();
                break;

            case Rectangle:
                x2 = event.getX();
                y2 = event.getY();
                gc.strokeRect(x1, y1, x2-x1, y2-y1);
                gc.closePath();
                break;

            case Circle:
                x2 = event.getX();
                y2 = event.getY();
                gc.strokeOval(x1, y1, x2-x1, x2-x1);
                gc.closePath();
                break;

            case Ellipse:
                x2 = event.getX();
                y2 = event.getY();
                gc.strokeOval(x1, y1, x2-x1, y2-y1);
                gc.closePath();
                break;

            case RoundRect:
                x2 = event.getX();
                y2 = event.getY();
                gc.strokeRoundRect(x1, y1, x2-x1, y2-y1, 10, 10);
                break;

            case FreePoly:
                if(numSides == 0) { //if no side has been initialized yet
                    xPoints[numSides] = event.getX();
                    yPoints[numSides] = event.getY();
                    gc.lineTo(event.getX(), event.getY());
                    gc.stroke();
                    gc.closePath();
                    numSides++;
                    xPoints[numSides] = event.getX();
                    yPoints[numSides] = event.getY();
                    System.out.println(numSides);
                }
                else {
                    xPoints[numSides] = event.getX(); // this is the current dot
                    yPoints[numSides] = event.getY(); // numSides-1 is the prev dot
                    if((Math.abs(xPoints[numSides] - x1) < 5) && (Math.abs(yPoints[numSides] - y1) < 5)) {
                        gc.strokeLine(x1, y1, xPoints[numSides-1], yPoints[numSides-1]);
                        System.out.println(numSides); // if connecting to last dot...
                        numSides = 0;                 // ...close off shape
                    }
                    else {
                        gc.strokeLine(xPoints[numSides-1],yPoints[numSides-1],xPoints[numSides],yPoints[numSides]);
                        numSides++; // otherwise, continue making sides
                        System.out.println(numSides);
                    }
                }
                break;

            case Triangle:
                x2 = event.getX();
                y2 = event.getY();
                double length = Math.abs(x1-x2);
                double[] xPoints = {x1, (length/2)+x1, x2};
                double[] yPoints = new double[3];
                yPoints[1] = y1 - Math.sqrt(3)*0.5*length; //do some math to determine where the points should be
                yPoints[0] = y1;
                yPoints[2] = y1;
                gc.strokePolygon(xPoints, yPoints, 3);
                break;

            case Select:
                x2 = event.getX();
                y2 = event.getY();
                gc.closePath();
                WritableImage writableImage = new WritableImage((int)(Math.abs(x2-x1)), (int)(Math.abs(y2-y1)));
                myStackPane = (StackPane) Display.getCurrentCanvas(myTabPane).getParent();
                test = myStackPane.snapshot(null, writableImage); //grab the image based off the cursor location
                clipboard = Clipboard.getSystemClipboard();
                content = new ClipboardContent();
                content.putImage(writableImage);
                clipboard.setContent(content);
                selector = Mode.Copy;
                //Display.newCanvas(myCanvas, (StackPane) myCanvas.getParent());
                break;

            case Move:
                x2 = event.getX();
                y2 = event.getY();
                WritableImage otherImage = new WritableImage((int)(Math.abs(x2-x1)), (int)(Math.abs(y2-y1)));
                myStackPane = (StackPane) Display.getCurrentCanvas(myTabPane).getParent();
                test = myStackPane.snapshot(null, otherImage); //grab the image based off the cursor location
                clipboard = Clipboard.getSystemClipboard();
                content = new ClipboardContent();
                content.putImage(otherImage);
                clipboard.setContent(content);
                //Display.newCanvas(myCanvas, (StackPane) myCanvas.getParent());
                selector = Mode.Copy2;
                break;
        }
    }
}
