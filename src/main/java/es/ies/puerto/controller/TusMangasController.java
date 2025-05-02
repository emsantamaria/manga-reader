package es.ies.puerto.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import es.ies.puerto.PrincipalApplication;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.CsvOperations;
import es.ies.puerto.model.Manga;
import es.ies.puerto.model.Usuario;
import es.ies.puerto.model.Usuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
/**
 * @version 1.0
 * @author emsantamaria
 */
public class TusMangasController extends AbstractController {
    CsvOperations csvOperations;
    Usuario usuario;
    Usuarios usuarios;
    @FXML Button manga1;
    @FXML Button manga2;
    @FXML Button manga3;
    @FXML Button manga4;
    @FXML Button manga5;
    @FXML Button manga31;
    @FXML Button manga111;
    @FXML Button manga11;
    List<Button> botones;

    public TusMangasController() throws ClassNotFoundException {
        csvOperations = new CsvOperations();
        usuarios = new Usuarios();
        usuario = csvOperations.readUsuario();
        usuario = usuarios.darUsuarioPorEmail(usuario.getEmail());
    }

    /**
     * Initializes the controller by setting up user data and button labels.
     */
    public void initialize() {
        Properties properties = readProperties();
        botones = new ArrayList<>(Arrays.asList(manga1, manga2, manga3, manga4, manga5));
        ponerNombre(usuario.getMangasGustados());
        manga31.setText(properties.getProperty("volver"));
        manga11.setText(properties.getProperty("buttonSiguiente"));
        manga111.setText(properties.getProperty("buttonAnterior"));
    }

    /**
     * Sets the names of the mangas on the buttons.
     * @param mangas List of mangas to display.
     */
    public void ponerNombre(List<Manga> mangas) {
        for (int i = 0; i < botones.size(); i++) {
            botones.get(i).setText(" ");
            if (i < mangas.size()) {
                botones.get(i).setText(mangas.get(i).getNombre());
            }
        }
    }

    /**
     * Opens the selected manga when a button is clicked.
     * @param actionEvent The event triggered by the button click.
     * @throws IOException if the manga cannot be opened.
     */
    @FXML
    protected void abrirManga(ActionEvent actionEvent) throws IOException {
        Button button = (Button) actionEvent.getSource();
        Manga manga = findManga(button.getText());
        openMangas1(manga);
    }

    /**
     * Finds a manga by its name in the user's liked mangas.
     * @param nombre The name of the manga.
     * @return The manga if found, otherwise null.
     */
    public Manga findManga(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return null;
        }
        Manga manga = new Manga(nombre);
        int pocision = usuario.getMangasGustados().indexOf(manga);
        manga = usuario.getMangasGustados().get(pocision);
        return manga;
    }

    /**
     * Opens the manga details screen for the selected manga.
     * @param manga The manga to open.
     * @throws IOException if the screen cannot be loaded.
     */
    protected void openMangas1(Manga manga) throws IOException {
        if (manga == null) {
            return;
        }
        recordar();
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("manga.fxml"));
            Parent root = loader.load();
            MangaController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.initialize(manga);
            registroController.postInitialize();
            Stage stage = (Stage) manga1.getScene().getWindow();
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
            writer.write("tusMangas.fxml");
        }
    }

    /**
     * Navigates back to the profile screen.
     */
    @FXML
    protected void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("perfil.fxml"));
            Parent root = loader.load();
            PerfilController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.initialize();
            registroController.postInitialize();
            Stage stage = (Stage) manga1.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays the next set of mangas in the user's liked mangas list.
     */
    @FXML
    protected void siguiente() {
        Manga nombre = new Manga();
        for (int i = 0; i < botones.size(); i++) {
            if (!botones.get(i).getText().isBlank()) {
                nombre = findManga(botones.get(i).getText());
            }
        }

        int pocision = usuario.getMangasGustados().indexOf(nombre);
        List<Manga> mangas = usuario.getMangasGustados().subList(pocision + 1, usuario.getMangasGustados().size());
        if (pocision == usuario.getMangasGustados().size() - 1) {
            return;
        }

        if (pocision + 6 < usuario.getMangasGustados().size() - 1) {
            mangas = usuario.getMangasGustados().subList(pocision + 1, pocision + 6);
        }
        ponerNombre(mangas);
    }

    /**
     * Displays the previous set of mangas in the user's liked mangas list.
     */
    @FXML
    protected void anterior() {
        Manga nombre = findManga(manga1.getText());

        int pocision = usuario.getMangasGustados().indexOf(nombre);
        if (pocision < 1) {
            return;
        }
        List<Manga> mangas = usuario.getMangasGustados().subList(0, pocision + 1);
        if (pocision - 5 > 0) {
            mangas = usuario.getMangasGustados().subList(pocision - 5, pocision + 1);
        }
        ponerNombre(mangas);
    }
}
