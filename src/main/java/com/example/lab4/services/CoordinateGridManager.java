package com.example.lab4.services;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * A utility class for managing and drawing a Cartesian coordinate system
 * on a JavaFX Canvas. This includes rendering the grid, placing points,
 * scaling the grid dynamically, and identifying the quadrant of a point.
 */
public class CoordinateGridManager {

    private final Canvas canvas;
    private final double centerX;
    private final double centerY;
    private final double canvasWidth;
    private final double canvasHeight;

    /**
     * Initializes the manager with a given canvas and calculates the center and size.
     *
     * @param canvas The Canvas where the coordinate system will be drawn.
     */
    public CoordinateGridManager(Canvas canvas) {
        this.canvas = canvas;
        this.canvasWidth = canvas.getWidth();
        this.canvasHeight = canvas.getHeight();
        this.centerX = canvasWidth / 2;
        this.centerY = canvasHeight / 2;
    }

    /**
     * Identifies the position of a point in the Cartesian plane.
     *
     * @param x The X-coordinate of the point.
     * @param y The Y-coordinate of the point.
     * @return A string describing the location of the point.
     */
    public static String findPointPosition(double x, double y) {
        if (x > 0 && y > 0) return "First quadrant";
        if (x < 0 && y > 0) return "Second quadrant";
        if (x < 0 && y < 0) return "Third quadrant";
        if (x > 0 && y < 0) return "Fourth quadrant";
        if (x == 0 && y != 0) return "On the Y-axis";
        if (y == 0 && x != 0) return "On the X-axis";
        return "At the origin";
    }

    /**
     * Determines the largest absolute value of the given coordinates.
     *
     * @param x The X-coordinate.
     * @param y The Y-coordinate.
     * @return The maximum absolute value.
     */
    private static double calculateMaxAbsoluteValue(double x, double y) {
        return Math.max(Math.abs(x), Math.abs(y));
    }

    /**
     * Calculates the appropriate scale for the grid based on the point coordinates.
     * If the point exceeds the default grid size, it adjusts dynamically.
     *
     * @param x The X-coordinate of the point.
     * @param y The Y-coordinate of the point.
     * @return A scaling factor for the grid.
     */
    private static double calculateScale(double x, double y) {
        double maxValue = calculateMaxAbsoluteValue(x, y);
        double defaultGridSize = 10;

        while (defaultGridSize < maxValue) {
            defaultGridSize *= 2;
        }
        return defaultGridSize;
    }

    /**
     * Draws the coordinate system grid along with X and Y axes.
     */
    public void renderCoordinateSystem() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasWidth, canvasHeight);

        gc.setStroke(Color.BLACK);
        gc.strokeLine(0, centerY, canvasWidth, centerY);
        gc.strokeLine(centerX, 0, centerX, canvasHeight);

        gc.setStroke(Color.LIGHTGRAY);

        int divisions = 10;
        double step = centerX / divisions;

        for (int i = 1; i <= divisions; i++) {
            double offset = i * step;

            gc.strokeLine(centerX + offset, 0, centerX + offset, canvasHeight);
            gc.strokeLine(centerX - offset, 0, centerX - offset, canvasHeight);

            gc.strokeLine(0, centerY + offset, canvasWidth, centerY + offset);
            gc.strokeLine(0, centerY - offset, canvasWidth, centerY - offset);
        }
    }

    /**
     * Draws a point on the coordinate system at the specified coordinates.
     *
     * @param x The X-coordinate of the point.
     * @param y The Y-coordinate of the point.
     */
    public void drawPointOnGrid(double x, double y) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        renderCoordinateSystem();
        addGridLabels(x, y);

        double scaleX = centerX / calculateScale(x, y);
        double scaleY = centerY / calculateScale(x, y);

        gc.setFill(Color.RED);
        double pointX = centerX + x * scaleX;
        double pointY = centerY - y * scaleY;
        gc.fillOval(pointX - 5, pointY - 5, 10, 10);
    }

    /**
     * Displays numeric labels along the grid axes based on the scale.
     *
     * @param x The X-coordinate for scaling.
     * @param y The Y-coordinate for scaling.
     */
    public void addGridLabels(double x, double y) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.DARKGRAY);

        int divisions = 10;
        double step = calculateScale(x, y) / divisions;

        for (int i = 1; i <= divisions; i++) {
            double offset = i * centerX / divisions;

            gc.fillText(String.format("%.1f", i * step), centerX + offset, centerY - 5);

            gc.fillText(String.format("%.1f", -i * step), centerX - offset, centerY - 5);

            gc.fillText(String.format("%.1f", i * step), centerX + 5, centerY - offset);

            gc.fillText(String.format("%.1f", -i * step), centerX + 5, centerY + offset);
        }
    }
}
