package com.example.lab4;

import com.example.lab4.services.CoordinateGridManager;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * Controller for handling the logic of the coordinate system application.
 * This includes managing user input, rendering the coordinate grid,
 * and determining the position of points on the grid.
 */
public class HelloController {

    @FXML
    private TextField xField;

    @FXML
    private TextField yField;

    @FXML
    private Button calculateButton;

    @FXML
    private Label resultLabel;

    @FXML
    private Canvas canvas;

    private CoordinateGridManager coordinateGridManager;

    /**
     * Initializes the controller and sets up the coordinate grid.
     * This method is invoked automatically after the FXML file is loaded.
     */
    @FXML
    public void initialize() {
        coordinateGridManager = new CoordinateGridManager(canvas);
        coordinateGridManager.renderCoordinateSystem();
        calculateButton.setOnAction(event -> handleCalculateAction());
    }

    /**
     * Handles the calculation of the quadrant for the user-specified point.
     * Parses input from the text fields, determines the quadrant,
     * and updates the label and canvas with the result.
     */
    private void handleCalculateAction() {
        try {
            double x = Double.parseDouble(xField.getText());
            double y = Double.parseDouble(yField.getText());
            String quadrant = CoordinateGridManager.findPointPosition(x, y);
            resultLabel.setText("The point is in " + quadrant);
            coordinateGridManager.drawPointOnGrid(x, y);
        } catch (NumberFormatException ex) {
            new Alert(Alert.AlertType.ERROR, "The input should be numeric!").showAndWait();
        }
    }
}
