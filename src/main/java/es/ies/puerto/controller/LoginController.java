package es.ies.puerto.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @version 1.0
 * @author emsantamaria
 */
public class LoginController extends AbstractController {
    

    Usuarios usuarios;

    @FXML
    private TextField textFieldUsuario;
    
    @FXML
    private PasswordField textFieldPassword;

    @FXML
    private Text textFieldMensaje;

    @FXML
    private Button openRegistrarButton;

    @FXML
    private Text textUsuario;
    @FXML Button aceptarButton;
    @FXML
    private Text textContrasenia;

    @FXML
    private ComboBox comboIdioma;

    @FXML
    private Button readMangaButton;
    File file=new File("src/main/resources/es/ies/puerto/usuarioIniciado.txt");

    /**
     * Initializes the controller by setting up the language options and default language.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     */
    @FXML
    public void initialize() throws ClassNotFoundException {
        List<String> idiomas = new ArrayList<>();
        idiomas.add("es");
        idiomas.add("en");
        idiomas.add("fr");
        if(comboIdioma!=null){
             comboIdioma.getItems().addAll(idiomas);
        } 
        usuarios=new Usuarios();
        writeIdioma("es");
    }

    /**
     * Deletes the file used to store the logged-in user's information.
     */
    @FXML
    public void postInitialize(){
        
        file.delete();
    }

    /**
     * Changes the application's language based on the selected value in the combo box.
     */
    @FXML
    protected void cambiarIdioma() {
        setPropertiesIdioma(loadIdioma("idioma", comboIdioma.getValue().toString()));
        writeIdioma(comboIdioma.getValue().toString());
        textUsuario.setText(getPropertiesIdioma().getProperty("textUsuario"));
        textContrasenia.setText(getPropertiesIdioma().getProperty("textContrasenia"));
        openRegistrarButton.setText(getPropertiesIdioma().getProperty("registrar"));
        aceptarButton.setText(getPropertiesIdioma().getProperty("registrar"));
    }

    /**
     * Handles the login button click event. Validates user credentials and navigates to the next screen if successful.
     */
    @FXML
    protected void onLoginButtonClick() {
        file.delete();
        if (textFieldUsuario== null || textFieldUsuario.getText().isEmpty() || 
            textFieldPassword == null || textFieldPassword.getText().isEmpty() ) {
                textFieldMensaje.setText("Credenciales null o vacias");
                return;
        }
        if (usuarios.iniciarSesion(textFieldUsuario.getText(), textFieldPassword.getText())) {
            textFieldMensaje.setText("Usuario validado correctamente");
            CsvOperations csvOperations=new CsvOperations();
            Usuario usuario=new Usuario(textFieldPassword.getText(), textFieldUsuario.getText());
            usuario=usuarios.recibirUsurio(textFieldUsuario.getText(), textFieldPassword.getText());
            csvOperations.usuarioIniciado(usuario);
            continueRead();
            return;
        } 

        textFieldMensaje.setText("Credenciales invalidas");
    }

    /**
     * Opens the registration screen when the "Register" button is clicked.
     */
    @FXML
    protected void openRegistrarClick() {

        try {

            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("registro.fxml"));
            Parent root = loader.load();
    
            RegistroController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            
            registroController.postInitialize(); 
    
            Stage stage = (Stage) textFieldUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the manga reading screen if the user is logged in.
     */
    @FXML
    protected void continueRead(){
        try {

            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("initRead.fxml"));
            Parent root = loader.load();
    
            InitReadController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.initialize();
            registroController.postInitialize();
    
            Stage stage = (Stage) readMangaButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the manga search screen.
     */
    @FXML
    protected void searchManga(){
        try {
            file.delete();
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("search.fxml"));
            Parent root = loader.load();
    
            SearchController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            
            registroController.postInitialize();
    
            Stage stage = (Stage) readMangaButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the password recovery screen.
     */
    @FXML
    protected void recoverPassoword(){
        file.delete();
        try {

            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("recover.fxml"));
            Parent root = loader.load();
    
            RecoverPasswordController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            
            registroController.postInitialize();
    
            Stage stage = (Stage) readMangaButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

