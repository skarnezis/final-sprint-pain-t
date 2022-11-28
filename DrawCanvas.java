package com.example.pain_t;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

/**
 * purpose: this deals with the majority of the canvas related methods. An extension of the Canvas class
 * @author stefanos karnezis
 */
public class DrawCanvas extends Canvas {
    boolean hashed = false; // used to determine if a dashed line is drawn or not

    /**
     * purpose: for when the user wants to freehand draw
     * notes: defaults to a black line with a thickness of 1.0
     * @param colorSelector the color picker of the current canvas
     */
    public void drawLineClick(ColorPicker colorSelector) {

        //selector = Mode.Draw;
        GraphicsContext gc = this.getGraphicsContext2D();
        //selector = Mode.Draw;
        gc.setStroke(colorSelector.getValue());
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: for when the user does not want to do anything in their canvas
     * notes: none
     */
    public void cursorClick() {
        GraphicsContext gc = this.getGraphicsContext2D();
        System.out.println("mode is 1");
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: eraser
     * notes: freehand eraser, also erases parts of images
     */
    public void eraserAction() {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setStroke(Color.WHITE);
        gc.setLineDashes(0);
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: draws a mf square
     * notes: none
     */
    public void drawSquare() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: draws a mf rectangle
     * notes: none
     */
    public void drawRect() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: draws a mf circle
     * notes: none
     */
    public void drawCircle() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: draws a mf ellipse
     * notes: none
     */
    public void drawEllipse() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: draws a mf semicircle
     * notes: none
     */
    public void drawRoundRect() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: draws a mf polygon with as many mf sides as you want
     * notes: none
     */
    public void drawPoly() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: draws a mf Triangle
     * notes: none
     */
    public void drawTriangle() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: freehand draw a dashed line
     * notes: dash spacing changes depending on line thickness
     */
    public void freeHandDashed() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = true;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: freehand draw
     * notes: same function as pencil icon
     */
    public void freeHandDraw() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: draw a straight line
     * notes: none
     */
    public void drawStrLine() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = false;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: draw a straight dashed line
     * notes: none
     */
    public void drawStrLineDashed() {
        GraphicsContext gc = this.getGraphicsContext2D();
        hashed = true;
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }
}
