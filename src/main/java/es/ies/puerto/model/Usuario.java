package es.ies.puerto.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
/**
 * @version 1.0
 * @author emsantamaria
 */
public class Usuario {
    private List<Manga> mangasGustados;
    private String nombre;
    private String password;
    private String email;
    private List<String> generos;
    CsvOperations csvOperations;
    int id;
    
    public Usuario() {
    }

    public Usuario( String password,String nombre,String email,int id ) {
        this.nombre = nombre;
        this.password = password;
        this.id=id;
        this.email=email;
        csvOperations=new CsvOperations();
    }

    public Usuario( String password,String nombre ) {
        this.nombre = nombre;
        this.password = password;
        csvOperations=new CsvOperations();
    }

    public Usuario(List<Manga> mangasGustados,List<String> generos , String password, String email,String nombre ) {
        this.mangasGustados = mangasGustados;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.generos = generos;
    }
    public Usuario(int id,List<Manga> mangasGustados,List<String> generos , String password, String email,String nombre ) {
        this.mangasGustados = mangasGustados;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.id=id;
        this.generos = generos;
    }

    public Usuario(List<String> generos, String password, String email, String nombre) {
        mangasGustados=new ArrayList<>();
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.generos = generos;
    }

    /**
     * Gets the list of mangas liked by the user.
     * @return A list of liked mangas.
     */
    public List<Manga> getMangasGustados() {
        return this.mangasGustados;
    }

    /**
     * Sets the list of mangas liked by the user.
     * @param mangasGustados The list of mangas to set.
     */
    public void setMangasGustados(List<Manga> mangasGustados) {
        this.mangasGustados = mangasGustados;
    }

    /**
     * Gets the user's name.
     * @return The user's name.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Sets the user's name.
     * @param nombre The name to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the user's password.
     * @return The user's password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the user's password.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the user's email.
     * @return The user's email.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the user's email.
     * @param email The email to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the user's favorite genres.
     * @return A list of favorite genres.
     */
    public List<String> getGeneros() {
        return this.generos;
    }

    public Usuario(List<Manga> mangasGustados, String nombre, String password, String email, List<String> generos, CsvOperations csvOperations, int id) {
        this.mangasGustados = mangasGustados;
        this.nombre = nombre;
        this.password = password;
        this.email = email;
        this.generos = generos;
        this.csvOperations = csvOperations;
        this.id = id;
    }

    /**
     * Gets the CSV operations instance.
     * @return The CSV operations instance.
     */
    public CsvOperations getCsvOperations() {
        return this.csvOperations;
    }

    /**
     * Sets the CSV operations instance.
     * @param csvOperations The CSV operations instance to set.
     */
    public void setCsvOperations(CsvOperations csvOperations) {
        this.csvOperations = csvOperations;
    }

    /**
     * Gets the user's ID.
     * @return The user's ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the user's ID.
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    public Usuario csvOperations(CsvOperations csvOperations) {
        setCsvOperations(csvOperations);
        return this;
    }

    public Usuario id(int id) {
        setId(id);
        return this;
    }

    /**
     * Sets the user's favorite genres.
     * @param generos The list of genres to set.
     */
    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    public Usuario mangasGustados(List<Manga> mangasGustados) {
        setMangasGustados(mangasGustados);
        return this;
    }

    public Usuario nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public Usuario password(String password) {
        setPassword(password);
        return this;
    }

    public Usuario email(String email) {
        setEmail(email);
        return this;
    }

    public Usuario generos(List<String> generos) {
        setGeneros(generos);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Usuario)) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(nombre, usuario.nombre) && Objects.equals(password, usuario.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash( nombre, password);
    }

    @Override
    public String toString() {
        return getMangasGustados() + 
            "/" + getGeneros() + 
            "," + getNombre() + 
            "," + getPassword() + 
            "," + getEmail();
    }
    
    /**
     * Adds a manga to the user's list of liked mangas.
     * @param manga The manga to add.
     * @return True if the manga was added, otherwise false.
     */
    public boolean addMangaGustado(Manga manga){
        return mangasGustados.add(manga);
    }

    /**
     * Removes a manga from the user's list of liked mangas.
     * @param manga The manga to remove.
     * @return True if the manga was removed, otherwise false.
     */
    public boolean removeMangaGustado(Manga manga){
        return mangasGustados.remove(manga);
    }

    /**
     * Adds a genre to the user's list of favorite genres.
     * @param genero The genre to add.
     */
    public void addGenero(String genero){
        generos.add(genero);
    }

}
