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
import es.ies.puerto.model.Mangas;
import es.ies.puerto.model.Usuario;
import es.ies.puerto.model.Usuarios;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
/**
 * @version 1.0
 * @author emsantamaria
 */
public class InitReadController extends AbstractController {
    Usuario usuario;
    Usuarios usuarios;
    Mangas mangas;
    List<Manga> gustados;
    List<Manga> generosGustados;
    List<ImageView> imageGenero;
    List<ImageView> imageManga;
    CsvOperations csvOperations;

    Manga mangaGenero1;
    Manga mangaGenero2;
    Manga mangaGenero3;
    Manga mangaGenero4;

    Manga mangaManga1;
    Manga mangaManga2;
    Manga mangaManga3;
    Manga mangaManga4;

    @FXML
    ImageView genero1;
    @FXML
    ImageView genero2;
    @FXML
    ImageView genero3;
    @FXML
    ImageView genero4;

    @FXML
    ImageView manga1;
    @FXML
    ImageView manga2;
    @FXML
    ImageView manga3;
    @FXML
    ImageView manga4;

    @FXML
    private Circle verPerfilButton;

    @FXML
    private Text mensajeText;

    @FXML
    private Button verGenerosButton;

    @FXML
    private Button verMangasGustadosButton;
    @FXML
    private Text verPerfil;
    @FXML
    private Button anterior;
    @FXML
    private Button anterior2;
    @FXML
    private Button siguiente;
    @FXML
    private Button siguiente1;
    @FXML
    private Button buscarMangaButton;
    Properties properties;

    /**
     * Initializes the controller by setting up user data and recommendations.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     */
    public InitReadController() throws ClassNotFoundException {
        csvOperations = new CsvOperations();
        usuarios = new Usuarios();
        usuario = csvOperations.readUsuario();
        mangas = new Mangas();
        generosGustados = mangas.recomendarByGeneros();
        gustados = mangas.recomendarByGustados();
    }

    /**
     * Sets up properties and UI elements for the controller.
     */
    public void initialize() {
        properties = readProperties();
        imageGenero = new ArrayList<>(Arrays.asList(genero1, genero2, genero3, genero4));
        imageManga = new ArrayList<>(Arrays.asList(manga1, manga2, manga3, manga4));
        verPerfil.setText(properties.getProperty("textPerfil"));
        mensajeText.setText(properties.getProperty("textBienvenido"));
        verGenerosButton.setText(properties.getProperty("textGeneros"));
        buscarMangaButton.setText(properties.getProperty("buttonSearch"));
        anterior.setText(properties.getProperty("buttonAnterior"));
        anterior2.setText(properties.getProperty("buttonAnterior"));
        siguiente.setText(properties.getProperty("buttonSiguiente"));
        siguiente1.setText(properties.getProperty("buttonSiguiente"));
        verMangasGustadosButton.setText(properties.getProperty("textMangas"));
    }

    /**
     * Sets up user-specific data after initialization.
     */
    public void postInitialize() {
        mensajeText.setText(mensajeText.getText() + " " + usuario.getNombre());
        rellenarMangas(generosGustados);
        rellenarMangas2(gustados);
        ponerGeneros(generosGustados);
        ponerMangas(gustados);
    }

    /**
     * Opens the user's profile screen.
     * @throws ClassNotFoundException if the Usuarios class cannot be loaded.
     */
    @FXML
    protected void verPerfil() throws ClassNotFoundException {
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("perfil.fxml"));
            Parent root = loader.load();

            PerfilController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());

            registroController.initialize();
            registroController.postInitialize();

            Stage stage = (Stage) verPerfilButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Displays recommended mangas based on genres.
     * @param mangas List of recommended mangas.
     */
    public void ponerGeneros(List<Manga> mangas) {
        for (int i = 0; i < imageGenero.size(); i++) {
            if (i < mangas.size()) {
                String url = "file:" + mangas.get(i).getDireccionImagen();
                Image image = new Image(url);
                imageGenero.get(i).setImage(image);
            }
            else{
                imageGenero.get(i).setVisible(false);
            }
        }
       
    }

    /**
     * Displays recommended mangas based on liked mangas.
     * @param mangas List of recommended mangas.
     */
    public void ponerMangas(List<Manga> mangas) {
        for (int i = 0; i < imageManga.size(); i++) {
            if (i < mangas.size()) {
                Image image = new Image("file:" + mangas.get(i).getDireccionImagen());
                imageManga.get(i).setImage(image);
            }
            else{
                imageGenero.get(i).setVisible(false);
            }
        }
       
    }

    /**
     * Opens the manga search screen.
     */
    @FXML
    protected void searchManga() {
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("search.fxml"));
            Parent root = loader.load();

            SearchController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.initialize();
            registroController.postInitialize();

            Stage stage = (Stage) buscarMangaButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Opens the details screen for a selected manga.
     * @param imageView The image view clicked.
     * @param manga The manga to display.
     * @throws IOException if the screen cannot be loaded.
     */
    @FXML
    protected void abrirManga(ActionEvent event) throws IOException {
        Button button=(Button)event.getSource();
        Manga manga=new Manga();
        manga=encontrar(button);
        if (!button.isVisible()) {
            return;
        }
        recordar();
        if (manga == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("manga.fxml"));
            Parent root = loader.load();
            MangaController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.initialize(manga);
            registroController.postInitialize();
            Stage stage = (Stage) verPerfilButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Opens the details screen for a selected manga.
     * @param imageView The image view clicked.
     * @param manga The manga to display.
     * @throws IOException if the screen cannot be loaded.
     */
    @FXML
    protected void abrirMangaa(ActionEvent event) throws IOException {
        Button button=(Button)event.getSource();
        Manga manga=new Manga();
        manga=encontrar(button);
        if (!button.isVisible()) {
            return;
        }
        recordar();
        if (manga == null) {
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(PrincipalApplication.class.getResource("manga.fxml"));
            Parent root = loader.load();
            MangaController registroController = loader.getController();
            registroController.setPropertiesIdioma(this.getPropertiesIdioma());
            registroController.initialize(manga);
            registroController.postInitialize();
            Stage stage = (Stage) verPerfilButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Manga encontrar(Button button){
        switch (button.getId()) {
            case "button1":
                return mangaGenero1;
            case "button2":
                return  mangaGenero2;    
            case "button3":
                return mangaGenero3;
            case "button4":
                return mangaGenero4;
            case "mangasButton1":
                return mangaManga1;
            case "mangasButton2":
                return mangaManga2;
            case "mangasButton3":
                return mangaManga3;
            default:
            return  mangaManga4;
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
            writer.write("initRead.fxml");
        }
    }

    /**
     * Displays the next set of recommended mangas based on genres.
     */
    @FXML
    protected void siguienteGenero() {
        List<Manga> mangasGenero = new ArrayList<>(Arrays.asList(mangaGenero1, mangaGenero2, mangaGenero3, mangaGenero4));
        Manga manga = new Manga();
        for (int i = 0; i < mangasGenero.size(); i++) {
            if (mangasGenero.get(i) != null) {
                manga = mangasGenero.get(i);
            }
        }

        int posicion = generosGustados.indexOf(manga);
        if (posicion == generosGustados.size() - 1) {
            return;
        }
        List<Manga> usariosList = generosGustados.subList(posicion + 1, generosGustados.size());
        if (posicion + 5 < generosGustados.size()) {
            usariosList = generosGustados.subList(posicion + 1, posicion + 5);
        }
        for (int i = 0; i < imageGenero.size(); i++) {
            if (i > usariosList.size()) {
                imageGenero.get(i).setVisible(false);
            }
        }
        rellenarMangas(usariosList);
        ponerGeneros(usariosList);
    }

    /**
     * Displays the next set of recommended mangas based on liked mangas.
     */
    @FXML
    protected void siguienteMangas() {
        List<Manga> mangasGenero = new ArrayList<>(Arrays.asList(mangaManga1, mangaManga2, mangaManga3, mangaManga4));
        Manga manga = new Manga();
        for (int i = 0; i < mangasGenero.size(); i++) {
            if (mangasGenero.get(i) != null) {
                manga = mangasGenero.get(i);
            }
        }

        int posicion = gustados.indexOf(manga);
        if (posicion == gustados.size() - 1) {
            return;
        }
        List<Manga> usariosList = gustados.subList(posicion + 1, gustados.size() - 1);
        if (posicion + 4 <= gustados.size() - 1) {
            usariosList = gustados.subList(posicion + 1, posicion + 5);
        }
        for (int i = 0; i < imageManga.size(); i++) {
            if (i > usariosList.size()) {
                imageManga.get(i).setVisible(false);
            }
        }
        rellenarMangas2(usariosList);
        ponerMangas(usariosList);
    }

    /**
     * Displays the previous set of recommended mangas based on genres.
     */
    @FXML
    protected void anteriorGenero() {
        for (ImageView manga : imageGenero) {
            manga.setVisible(true);
        }
        int pocision = generosGustados.indexOf(mangaGenero1);
        if (pocision < 1) {
            return;
        }
        int menos = 5;
        if (pocision - 5 < 0) {
            menos = pocision;
        }
        List<Manga> usariosList = generosGustados.subList(pocision - menos, pocision);
        rellenarMangas2(usariosList);
        ponerGeneros(usariosList);
    }

    /**
     * Displays the previous set of recommended mangas based on liked mangas.
     */
    @FXML
    protected void anteriorManga() {
        for (ImageView manga : imageManga) {
            manga.setVisible(true);
        }
        int pocision = gustados.indexOf(mangaManga1);
        if (pocision == 0) {
            return;
        }
        int menos = 5;
        if (pocision - 5 < 0) {
            menos = pocision;
        }
        List<Manga> usariosList = gustados.subList(pocision - menos, pocision+1);
        rellenarMangas2(usariosList);
        ponerMangas(usariosList);
    }

    public void rellenarMangas(List<Manga>mangas){
        switch (mangas.size()) {
            case 1:
            mangaGenero1=mangas.get(0);
            mangaGenero2=null;
            mangaGenero3=null;
            mangaGenero4=null;
                break;
            case 2:
                mangaGenero1=mangas.get(0);
                mangaGenero2=mangas.get(1);
                mangaGenero3=null;
                mangaGenero4=null;
                break;
            case 3:
                mangaGenero1=mangas.get(0);
                mangaGenero2=mangas.get(1);
                mangaGenero3=mangas.get(2);
                mangaGenero4=null;
                break;
            default:
            mangaGenero1=mangas.get(0);
            mangaGenero2=mangas.get(1);
            mangaGenero3=mangas.get(2);
            mangaGenero4=mangas.get(3);
                break;
        }
    }

    public void rellenarMangas2(List<Manga>mangas){
        switch (mangas.size()) {
            case 1:
            mangaManga1=mangas.get(0);
            mangaManga2=null;
            mangaManga3=null;
            mangaManga4=null;
                break;
            case 2:
                mangaManga1=mangas.get(0);
                mangaManga2=mangas.get(1);
                mangaManga3=null;
                mangaManga4=null;
                break;
            case 3:
                mangaManga1=mangas.get(0);
                mangaManga2=mangas.get(1);
                mangaManga3=mangas.get(2);
                mangaManga4=null;
                break;
            default:
            mangaManga1=mangas.get(0);
            mangaManga2=mangas.get(1);
            mangaManga3=mangas.get(2);
            mangaManga4=mangas.get(3);
                break;
        }
    }
}

