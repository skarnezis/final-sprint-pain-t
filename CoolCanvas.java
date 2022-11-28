package com.example.pain_t;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;

import java.io.IOException;

/**
 * purpose: this deals with the majority of the canvas methods. An extension of DrawCanvas
 * @author stefanos karnezis
 *
 */
public class CoolCanvas extends DrawCanvas {
    boolean hashed = false;

    /**
     * purpose: Displays an image on thew canvas
     * notes: this is mainly used for File -> Open
     * @param location the file location
     */
    public void showImage(String location) {
        GraphicsContext gc = this.getGraphicsContext2D();
        Image myImage = new Image(location); // initialize image based on path
        this.setHeight(myImage.getHeight()); // resize canvas
        this.setWidth(myImage.getWidth());
        gc.drawImage(myImage, 0, 0); //put image in pain(t)
    }

    /**
     * changes the color of the graphics context stroke
     *
     * @param color
     */
    public void changeColor(Color color) {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setStroke(color);
    }

    /**
     * Sets the transform for the GraphicsContext to rotate around a pivot point.
     *
     * @param gc the graphics context the transform to applied to.
     * @param px the x pivot co-ordinate for the rotation (in canvas co-ordinates).
     * @param py the y pivot co-ordinate for the rotation (in canvas co-ordinates).
     */
    private void rotate(GraphicsContext gc, double px, double py) {
        Rotate r = new Rotate(90, px, py);
        gc.setTransform(r.getMxx(), r.getMyx(), r.getMxy(), r.getMyy(), r.getTx(), r.getTy());
    }

    /**
     * Draws an image on a graphics context.
     *
     * The image is drawn at (0, 0) rotated by angle pivoted around the point:
     *   (image.getWidth() / 2, image.getHeight() / 2)
     */
    public void rotateCanvas() {
        GraphicsContext gc = this.getGraphicsContext2D();
        WritableImage writableImage = new WritableImage((int) this.getWidth(), (int) this.getHeight());
        Image image = this.snapshot(null, writableImage);
        rotate(gc, image.getWidth() / 2, image.getHeight() / 2);
        gc.drawImage(image, 0, 0);
        gc.restore();
    }

    /**
     * purpose: this function and the ones below it change the thickness of the line being drawn
     */
    public void thickOneClick() throws IOException {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(1.0);
        if(hashed == true) {
            gc.setLineDashes(2);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: this function and the ones below it change the thickness of the line being drawn
     */
    public void thickTwoClick() throws IOException {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(2.0);
        if(hashed == true) {
            gc.setLineDashes(4);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: this function and the ones below it change the thickness of the line being drawn
     */
    public void thickFiveClick() throws IOException {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(5.0);
        if(hashed == true) {
            gc.setLineDashes(10);
        }
        else {
            gc.setLineDashes(0);
        }

    }

    /**
     * purpose: this function and the ones below it change the thickness of the line being drawn
     */
    public void thickEightClick() throws IOException {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(8.0);
        if(hashed == true) {
            gc.setLineDashes(16);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: this function and the ones below it change the thickness of the line being drawn
     */
    public void thickTenClick() throws IOException {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(10.0);
        if(hashed == true) {
            gc.setLineDashes(20);
        }
        else {
            gc.setLineDashes(0);
        }
    }

    /**
     * purpose: this function and the ones below it change the thickness of the line being drawn
     */
    public void thiQqClick() throws IOException {
        GraphicsContext gc = this.getGraphicsContext2D();
        gc.setLineWidth(20.0);
        if(hashed == true) {
            gc.setLineDashes(40);
        }
        else {
            gc.setLineDashes(0);
        }
    }


}
