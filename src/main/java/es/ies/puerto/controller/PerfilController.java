package es.ies.puerto.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import es.ies.puerto.PrincipalApplication;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.CsvOperations;
import es.ies.puerto.model.Usuario;
import es.ies.puerto.model.Usuarios;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 * @version 1.0
 * @author emsantamaria
 */
public class PerfilController extends AbstractController {
    @FXML private TextField nombreTextFiled;
    @FXML private PasswordField passwordTextField;
    @FXML private TextField emailTextFiled;
    @FXML private TextField generosTextFlied;
    @FXML private Button saveChangesButton;
    @FXML private Button goBackButton;
    @FXML Label text;
    Usuario usuario;
    CsvOperations csvOperations;
    Usuarios usuarios;
    Properties properties;
    @FXML Button goBackButton1;

   @FXML Label welcomeText;
   @FXML Label welcomeText1;
   @FXML Label welcomeText111;
   @FXML Label welcomeText11;
   @FXML Button saveChangesButton1;

    public PerfilController(){

    }

    /**
     * Initializes the controller by setting up user data and properties.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     */
    public void initialize() throws ClassNotFoundException{
        properties=readProperties();
        csvOperations=new CsvOperations();
        usuarios=new Usuarios();
        usuario=csvOperations.readUsuario();
        usuario=usuarios.darUsuarioPorEmail(usuario.getEmail());
       
    }

    /**
     * Sets up properties for the controller after initialization.
     */
    public void postInitialize(){
        nombreTextFiled.setText(usuario.getNombre());
        passwordTextField.setText(usuario.getPassword());
        emailTextFiled.setText(usuario.getEmail());
        generosTextFlied.setText(usuario.getGeneros().toString());
        goBackButton1.setText(properties.getProperty("verTusMangas"));
        goBackButton.setText(properties.getProperty("volver"));
        welcomeText.setText(properties.getProperty("nombre"));
        welcomeText1.setText(properties.getProperty("textContrasenia"));
        welcomeText11.setText(properties.getProperty("generosFavoritos"));
        saveChangesButton1.setText(properties.getProperty("editGeneros"));
        saveChangesButton.setText(properties.getProperty("guardar"));
    }

    /**
     * Saves changes made to the user's profile.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     * @throws SQLException if a database access error occurs.
     */
    @FXML
    protected void saveChanges() throws ClassNotFoundException, SQLException{
    
        if(nombreTextFiled==null&&nombreTextFiled.getText().isBlank()){
            text.setText("nombre null");
            return;
        }
        if(passwordTextField==null&&passwordTextField.getText().isBlank()){
           text.setText("password null");
            return;
        }
        if(emailTextFiled==null&&emailTextFiled.getText().isBlank()){
            text.setText("email null");
           return;
        }
        usuario.setNombre(nombreTextFiled.getText());
        usuario.setPassword(passwordTextField.getText());
        usuario.setEmail(emailTextFiled.getText());
        csvOperations.usuarioIniciado(usuario);
        usuarios.update(usuario);
    }

    /**
     * Navigates back to the main reading screen.
     */
    @FXML 
    protected void goBackInit(){
        try {
        FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("initRead.fxml"));
        Parent root= loader.load();

        InitReadController registroController = loader.getController();
        registroController.setPropertiesIdioma(this.getPropertiesIdioma());
        
        registroController.postInitialize(); 

        Stage stage = (Stage) goBackButton.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show(); } catch (IOException ex) {
        }
    }

    /**
     * Opens the genre editing screen.
     */
    @FXML
    protected void editarGenero(){
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("generos.fxml"));
            Parent root = loader.load(); 
            GenerosController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.usuario(usuario);
            registroController.postInitialize();
            Stage stage = (Stage) text.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the user's liked mangas screen.
     */
    @FXML
    protected void verTusMangas(){
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("tusMangas.fxml"));
            Parent root = loader.load(); 
            TusMangasController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.initialize();
            Stage stage = (Stage) text.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
