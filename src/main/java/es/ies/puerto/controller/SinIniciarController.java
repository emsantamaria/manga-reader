package es.ies.puerto.controller;

import java.util.Properties;

import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.CsvOperations;
import es.ies.puerto.model.Mangas;
import javafx.fxml.FXML;
/**
 * @version 1.0
 * @author emsantamaria
 */
public class SinIniciarController extends AbstractController {
    Mangas mangas;
    CsvOperations csvOperations;

    /**
     * Initializes the controller by setting up manga and CSV operations.
     */
    public void initialize() {
        mangas = new Mangas();
        csvOperations = new CsvOperations();
    }
    
    /**
     * Sets up properties for the controller after initialization.
     */
    public void postInitialize() {
        Properties properties = getPropertiesIdioma();
    }

    /**
     * Navigates back to the previous screen.
     */
    @FXML
    protected void goBack() {
        
    }
}
