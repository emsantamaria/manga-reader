package es.ies.puerto.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import es.ies.puerto.PrincipalApplication;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.CsvOperations;
import es.ies.puerto.model.Manga;
import es.ies.puerto.model.Mangas;
import es.ies.puerto.model.Usuario;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @version 1.0
 * @author emsantamaria
 */
public class SearchController extends AbstractController {
    @FXML Text text;
    @FXML TextField nameTextFiled;
    @FXML Button goBackButton;
    @FXML Button searchButton;
    @FXML ImageView imageFill;
    Usuario usuario;
    Mangas mangas;
    Manga manga;
    @FXML Image imagen;
    CsvOperations csvOperations;
    Properties properties;
    @FXML Label welcomeText;

    /**
     * Initializes the controller by setting up CSV operations, mangas, and properties.
     */
    public void initialize() {
        csvOperations = new CsvOperations();
        mangas = new Mangas();
        properties = readProperties();
    }

    /**
     * Sets up properties for the controller after initialization.
     */
    public void postInitialize() {
        goBackButton.setText(properties.getProperty("volver"));
        welcomeText.setText(properties.getProperty("textBuscar"));
        searchButton.setText(properties.getProperty("search"));
    }

    /**
     * Searches for a manga by its name and displays its details if found.
     * @throws IOException if an error occurs while processing the search.
     */
    @FXML 
    protected void searchMangas() throws IOException {
        recordar();
        System.out.println(1);
        if (nameTextFiled == null || nameTextFiled.getText().isBlank()) {
            text.setText("No se ha encontrado ningun manga");
            System.out.println(2);
            return;
        }
        if ((manga = mangas.searchMangas(nameTextFiled.getText())) != null) {
            System.out.println(3);
            String url = "file:" + manga.getDireccionImagen();
            
            Image image = new Image(url);
            imageFill.setImage(image);
            return;
        }
        System.out.println(4);
        text.setText("No se ha encontrado ningun manga");
    }

    /**
     * Navigates back to the previous screen based on the user's login state.
     */
    @FXML
    protected void goBack() {
        File file = new File("src/main/resources/es/ies/puerto/usuarioIniciado.txt");
        String xml = "login.fxml";
        boolean respuesta = file.exists();
        if (respuesta) {
            xml = "initRead.fxml";
        }
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource(xml));
            Parent root = loader.load(); 
            if (respuesta) {
                InitReadController initReadController = loader.getController();
                initReadController.setPropertiesIdioma(this.getPropertiesIdioma());
                initReadController.initialize();
                initReadController.postInitialize();
            } else {
                LoginController registroController = loader.getController();
                registroController.setPropertiesIdioma(this.getPropertiesIdioma());
                registroController.postInitialize();
            }
               
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the selected manga's details screen.
     */
    @FXML
    protected void openManga() {
        File file = new File("src/main/resources/es/ies/puerto/usuarioIniciado.txt");
        String xml = "mangaSinIniciar.fxml";
        if (file.exists()) {
            xml = "manga.fxml";
        }
        if (manga == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource(xml));
            Parent root = loader.load(); 
            MangaController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.initialize(manga);
            registroController.postInitialize();
            Stage stage = (Stage) goBackButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the current screen state to a file for later restoration.
     * @throws IOException if the file cannot be written.
     */
    public void recordar() throws IOException {
        File file = new File("src/main/resources/es/ies/puerto/recordar.txt");
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("search.fxml");
        }
    }
}

