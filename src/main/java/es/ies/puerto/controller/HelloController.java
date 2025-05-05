package es.ies.puerto.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

/**
 * @version 1.0
 * @author emsantamaria
 */
public class HelloController {
    
    @FXML
    private Label welcomeText;

    /**
     * Handles the button click event to display a welcome message.
     */
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("¡Bienvenidos al mundo de la programación!");
    }
}