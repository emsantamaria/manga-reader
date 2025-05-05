package es.ies.puerto.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import es.ies.puerto.PrincipalApplication;
import es.ies.puerto.controller.abstractas.AbstractController;
import es.ies.puerto.model.CsvOperations;
import es.ies.puerto.model.Manga;
import es.ies.puerto.model.Usuario;
import es.ies.puerto.model.Usuarios;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * @version 1.0
 * @author emsantamaria
 */
public class MangaController extends AbstractController {
    Manga manga;
    Usuario usuario;
    CsvOperations csvOperations;
    @FXML Text nombre;
    @FXML Text categoria;
    @FXML Text dibujante;
    @FXML Text autor;
    @FXML Text generos;
    @FXML Text capitulos;
    @FXML Text textNombre;
    @FXML Text textCategoria;
    @FXML Text textDibujante;
    @FXML Text textautor;
    @FXML Text textGeneros;
    @FXML Text textCapitulos;
    @FXML ImageView imageFill;
    @FXML Button goBackBt;
    String xml;
    @FXML ImageView imagen;
    @FXML Hyperlink link;
    HostServices hostServices;

    public MangaController() throws ClassNotFoundException {
        Usuarios usuarios = new Usuarios();
        csvOperations = new CsvOperations();
        usuario = csvOperations.readUsuario();
        usuario = usuarios.darUsuarioPorEmail(usuario.getEmail());
        hostServices();
    }

    /**
     * Initializes the controller with manga details and properties.
     * @param manga The manga to display.
     * @throws IOException if an error occurs while loading properties.
     */
    public void initialize(Manga manga) throws IOException {
        Properties properties = readProperties();
        textNombre.setText(properties.getProperty("nombre"));
        textCapitulos.setText(properties.getProperty("capitulos"));
        textCategoria.setText(properties.getProperty("categoria"));
        textDibujante.setText(properties.getProperty("dibujante"));
        textautor.setText(properties.getProperty("autor"));
        textGeneros.setText(properties.getProperty("generos"));
        link.setText(properties.getProperty("leer"));
        goBackBt.setText(properties.getProperty("volver"));
        restaurar();
        this.manga = manga;
    }

    /**
     * Sets up host services for opening external links.
     */
    public void hostServices() {
        this.hostServices = getHostServices();
    }

    /**
     * Sets up the manga details after initialization.
     */
    public void postInitialize() {
        autor.setText(manga.getAutor());
        dibujante.setText(manga.getDibujante());
        capitulos.setText(manga.getNumCapitulos() + "");
        Image image = new Image("file:" + manga.getDireccionImagen());
        imageFill.setImage(image);
        String generosString = "";
        for (int i = 0; i < manga.getGeneros().size(); i++) {
            if (i == manga.getGeneros().size() - 1) {
                generosString += manga.getGeneros().get(i);
            } else {
                generosString += manga.getGeneros().get(i) + "/";
            }
        }
        generos.setText(generosString);
        categoria.setText(manga.getCategoria());
        nombre.setText(manga.getNombre());
        if (usuario != null && usuario.getMangasGustados() != null && usuario.getMangasGustados().contains(manga)) {
            Image imageHeart = new Image("file:src/main/resources/images/liked.jpg");
            imagen.setImage(imageHeart);
        }
    }

    /**
     * Navigates back to the previous screen.
     * @throws IOException if the screen cannot be loaded.
     */
    @FXML
    protected void goBack() throws IOException {
        restaurar();
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource(xml));
            Parent root = loader.load();
            switch (xml) {
                case "initRead.fxml":
                    InitReadController registroController = loader.getController();
                    registroController.setPropertiesIdioma(this.getPropertiesIdioma());
                    registroController.postInitialize();
                    break;

                case "tusMangas.fxml":
                    TusMangasController tusMangasController = loader.getController();
                    tusMangasController.setPropertiesIdioma(this.getPropertiesIdioma());
                    break;
                default:
                    SearchController searchController = loader.getController();
                    searchController.setPropertiesIdioma(this.getPropertiesIdioma());
                    searchController.postInitialize();
                    break;
            }

            Stage stage = (Stage) imageFill.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Restores the previous screen state from a file.
     * @throws IOException if the file cannot be read.
     */
    public void restaurar() throws IOException {
        File file = new File("src/main/resources/es/ies/puerto/recordar.txt");
        if (!file.exists()) {
            return;
        }
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = "";
        while ((line = reader.readLine()) != null) {
            this.xml = line;
        }
    }

    /**
     * Adds or removes the manga from the user's favorites.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     * @throws SQLException if a database access error occurs.
     */
    @FXML
    protected void addFavorite() throws ClassNotFoundException, SQLException {
        Usuarios usuarios = new Usuarios();
        if (usuario.getMangasGustados().contains(manga)) {
            usuario.removeMangaGustado(manga);
            Image imageHeart = new Image("file:src/main/resources/images/unliked.png");
            imagen.setImage(imageHeart);
            usuarios.update(usuario);
            csvOperations.usuarioIniciado(usuario);
            return;
        }
        usuario.addMangaGustado(manga);
        Image imageHeart = new Image("file:src/main/resources/images/liked.jpg");
        imagen.setImage(imageHeart);
        usuarios.update(usuario);
        csvOperations.usuarioIniciado(usuario);
    }

    /**
     * Opens the manga's reading link in a browser.
     */
    @FXML
    protected void cambiar() {
        String url = manga.getEnlace();

        try {
            java.net.URI uri = new java.net.URI(url);
            hostServices.showDocument(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
