package es.ies.puerto.controller;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import es.ies.puerto.PrincipalApplication;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.CsvOperations;
import es.ies.puerto.model.Usuario;
import es.ies.puerto.model.Usuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * @version 1.0
 * @author emsantamaria
 */
public class GenerosController extends AbstractController {
    List<String> generos;
    Usuarios usuarios;
    CsvOperations csvOperations;
    @FXML 
    Text textMessage;
    @FXML
    TilePane tilePane;
    @FXML
    CheckBox romanceCheck;
    @FXML
    CheckBox shonenCheck;
    @FXML
    CheckBox seinenCheck;
    @FXML
    CheckBox accionCheck;
    @FXML
    CheckBox horrorCheck;
    @FXML
    CheckBox dramaCheck;
    @FXML
    CheckBox shoujoCheck;
    @FXML
    CheckBox deportesCheck;
    @FXML
    CheckBox historicaCheck;
    @FXML
    CheckBox combatesCheck;

    @FXML Button goBackButton1;
    @FXML Button goBackButton;
    @FXML Label welcomeText;
    @FXML Label welcomeText1;
    @FXML Label welcomeText111;
    @FXML Label welcomeText11;
    @FXML Button saveChangesButton1;
    @FXML Button saveChangesButton;
    @FXML Text texto;
    @FXML 
    Button continueButton;
    Usuario usuario;
    Properties properties;

    List<CheckBox> lista;

    public GenerosController() {
    }

    /**
     * Sets the user for whom genres are being managed.
     * @param usuario The user object.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     */
    public void usuario(Usuario usuario) throws ClassNotFoundException {
        this.usuario = usuario;
        usuarios = new Usuarios();
    }

    /**
     * Initializes the controller by setting up genre checkboxes and user data.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     */
    @FXML
    public void initialize() throws ClassNotFoundException {
        lista = new ArrayList<>(Arrays.asList(romanceCheck, shonenCheck, seinenCheck, accionCheck, horrorCheck));
        csvOperations = new CsvOperations();
        usuarios = new Usuarios();
        generos = new ArrayList<>();
    }

    /**
     * Sets up properties for the controller after initialization.
     */
    public void postInitialize() {
         properties=readProperties();
        if (usuario != null) {
            continueButton.setText("Guardar");
        }
        texto.setText(properties.getProperty("texto"));
    }

    /**
     * Adds or removes a genre from the user's selected genres based on checkbox interaction.
     * @param accionCheck The action event triggered by the checkbox.
     */
    @FXML
    protected void aniadirGeneros(ActionEvent accionCheck) {
        CheckBox checkBox = (CheckBox) accionCheck.getSource();
        if (generos.contains(checkBox.getText())) {
            generos.remove(checkBox.getText());
            return;
        }
        generos.add(checkBox.getText());
    }

    /**
     * Creates or updates the user's genre preferences.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     * @throws SQLException if a database access error occurs.
     */
    @FXML
    protected void crear() throws ClassNotFoundException, SQLException {
        if (generos.size() < 3) {
            textMessage.setText(properties.getProperty("textoErrorGeneros"));
            return;
        }
        if (usuario != null) {
            guardarGeneros(usuario);
            usuarios.update(usuario);
            goPerfil();
        }
        String nombre = "";
        String email = "";
        String password = "";
        try {
            nombre = loadPropertiesUsuario().get(0);
            email = loadPropertiesUsuario().get(1);
            password = loadPropertiesUsuario().get(2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Usuario usuario = new Usuario(generos, password, email, nombre);
        if (usuarios.aniadir(usuario)) {
            textMessage.setText("Todo ha salido perfe" + usuario);
            csvOperations.usuarioIniciado(usuario);
            continueInicio();
        }
    }

    /**
     * Saves the selected genres to the user object.
     * @param usuario The user object.
     */
    public void guardarGeneros(Usuario usuario) {
        usuario.setGeneros(generos);
    }

    /**
     * Navigates to the main reading screen after saving genres.
     */
    @FXML
    protected void continueInicio() {
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("initRead.fxml"));
            Parent root = loader.load();

            InitReadController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.postInitialize();

            Stage stage = (Stage) continueButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Navigates to the user's profile screen.
     */
    @FXML
    protected void goPerfil() {
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("perfil.fxml"));
            Parent root = loader.load();

            PerfilController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.initialize();
            registroController.postInitialize();

            Stage stage = (Stage) romanceCheck.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
