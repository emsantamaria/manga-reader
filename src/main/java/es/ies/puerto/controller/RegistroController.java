package es.ies.puerto.controller;

import java.util.Properties;

import es.ies.puerto.PrincipalApplication;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.Usuarios;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * @version 1.0
 * @author emsantamaria
 */
public class RegistroController extends AbstractController {
    Properties properties;
    
    @FXML TextField textFiledUsuario;

    @FXML Text textMensaje;

    @FXML Button buttonRegistrar;

    @FXML PasswordField textFieldPassword;

    @FXML PasswordField textFieldPasswordRepit;

    @FXML TextField textFieldEmail;

    @FXML
    private Text textUsuario;

    @FXML
    private Text textContrasenia;

    @FXML Text textMensaje2;

    Usuarios usuarios;

    /**
     * Initializes the controller by setting up the Usuarios instance.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     */
    public void initialize() throws ClassNotFoundException {
        usuarios = new Usuarios();
    }

    /**
     * Sets up properties for the controller after initialization.
     */
    public void postInitialize() {
        properties = readProperties();
        if (properties != null) {
            textUsuario.setText(properties.getProperty("textUsuario"));
            textContrasenia.setText(properties.getProperty("textContrasenia"));
            textMensaje2.setText(properties.getProperty("textRepit"));
        } else {
            textUsuario.setText("Usuario");
            textContrasenia.setText("Contraseña");
        }
    }

    /**
     * Handles the registration button click event. Validates input and registers the user.
     */
    @FXML
    protected void onClickRegistar() {
        if (textFieldPassword == null || textFieldPassword.getText().isEmpty()
                || textFieldPasswordRepit == null || textFieldPasswordRepit.getText().isEmpty()) {
            textMensaje.setText(properties.getProperty("mensajeErrorPasword"));
            return;
        }
        
        if (!textFieldPassword.getText().equals(textFieldPasswordRepit.getText())) {
            textMensaje.setText(properties.getProperty("mensajeErrorNotEquals"));
            return;
        }
        if (usuarios.iniciarSesion(textFiledUsuario.getText(), textFieldPassword.getText())) {
            textMensaje.setText(properties.getProperty("mensajeErrorUsuarioExistente"));
            return;
        }
        if (usuarios.findEmail(textFieldEmail.getText())) {
            textMensaje.setText(properties.getProperty("mensajeErrorEmailExistente"));
            return;
        }
        
        savePropertiesUsuario(textFiledUsuario.getText(), textFieldPassword.getText(), textFieldEmail.getText());
        abrirSeleccionDeGeneros();
    }

    /**
     * Opens the genre selection screen after successful registration.
     */
    @FXML
    protected void abrirSeleccionDeGeneros() {
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("generos.fxml"));
            Parent root = loader.load(); 
            GenerosController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            
            registroController.postInitialize();
            Stage stage = (Stage) textFiledUsuario.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
