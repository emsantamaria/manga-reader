package es.ies.puerto.model;

import java.util.List;
import java.util.Objects;

/**
 * @version 1.0
 * @author emsantamaria
 */
public class Manga {
    private String nombre;
    private List<String>generos;
    private String categoria;
    private int numCapitulos;
    private String autor;
    private String dibujante;
    private String enlace;
    private String direccionImagen;
    private int id;

    public Manga() {
    }
    public Manga(String nombre) {
        this.nombre=nombre;
    }

    public Manga(String nombre, List<String> generos, String categoria, int numCapitulos, String autor, String dibujante, String enlace) {
        this.nombre = nombre;
        this.generos = generos;
        this.categoria = categoria;
        this.numCapitulos = numCapitulos;
        this.autor = autor;
        this.dibujante = dibujante;
        this.enlace = enlace;
    }

    public Manga(String nombre, List<String> generos, String categoria, int numCapitulos, String autor, String dibujante, String enlace,String direccionImagen) {
        this.nombre = nombre;
        this.generos = generos;
        this.categoria = categoria;
        this.numCapitulos = numCapitulos;
        this.autor = autor;
        this.dibujante = dibujante;
        this.enlace = enlace;
        this.direccionImagen=direccionImagen;
    }
    public Manga(int id,String nombre, List<String> generos, String categoria, int numCapitulos, String autor, String dibujante, String enlace,String direccionImagen) {
        this.nombre = nombre;
        this.generos = generos;
        this.categoria = categoria;
        this.numCapitulos = numCapitulos;
        this.autor = autor;
        this.dibujante = dibujante;
        this.enlace = enlace;
        this.direccionImagen=direccionImagen;
        this.id=id;
    }

    /**
     * Gets the manga's ID.
     * @return The manga's ID.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Sets the manga's ID.
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the image URL of the manga.
     * @return The image URL.
     */
    public String getDireccionImagen() {
        return this.direccionImagen;
    }

    /**
     * Sets the image URL of the manga.
     * @param direccionImagen The image URL to set.
     */
    public void setDireccionImagen(String direccionImagen) {
        this.direccionImagen = direccionImagen;
    }

    public Manga direccionImagen(String direccionImagen) {
        setDireccionImagen(direccionImagen);
        return this;
    }

    /**
     * Gets the manga's name.
     * @return The manga's name.
     */
    public String getNombre() {
        return this.nombre;
    }

    /**
     * Sets the manga's name.
     * @param nombre The name to set.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Gets the genres of the manga.
     * @return A list of genres.
     */
    public List<String> getGeneros() {
        return this.generos;
    }

    /**
     * Sets the genres of the manga.
     * @param generos The list of genres to set.
     */
    public void setGeneros(List<String> generos) {
        this.generos = generos;
    }

    /**
     * Gets the category of the manga.
     * @return The category.
     */
    public String getCategoria() {
        return this.categoria;
    }

    /**
     * Sets the category of the manga.
     * @param categoria The category to set.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Gets the number of chapters in the manga.
     * @return The number of chapters.
     */
    public int getNumCapitulos() {
        return this.numCapitulos;
    }

    /**
     * Sets the number of chapters in the manga.
     * @param numCapitulos The number of chapters to set.
     */
    public void setNumCapitulos(int numCapitulos) {
        this.numCapitulos = numCapitulos;
    }

    /**
     * Gets the author of the manga.
     * @return The author's name.
     */
    public String getAutor() {
        return this.autor;
    }

    /**
     * Sets the author of the manga.
     * @param autor The author's name to set.
     */
    public void setAutor(String autor) {
        this.autor = autor;
    }

    /**
     * Gets the artist of the manga.
     * @return The artist's name.
     */
    public String getDibujante() {
        return this.dibujante;
    }

    /**
     * Sets the artist of the manga.
     * @param dibujante The artist's name to set.
     */
    public void setDibujante(String dibujante) {
        this.dibujante = dibujante;
    }

    /**
     * Gets the link to read the manga.
     * @return The link.
     */
    public String getEnlace() {
        return this.enlace;
    }

    /**
     * Sets the link to read the manga.
     * @param enlace The link to set.
     */
    public void setEnlace(String enlace) {
        this.enlace = enlace;
    }

    public Manga nombre(String nombre) {
        setNombre(nombre);
        return this;
    }

    public Manga generos(List<String> generos) {
        setGeneros(generos);
        return this;
    }

    public Manga categoria(String categoria) {
        setCategoria(categoria);
        return this;
    }

    public Manga numCapitulos(int numCapitulos) {
        setNumCapitulos(numCapitulos);
        return this;
    }

    public Manga autor(String autor) {
        setAutor(autor);
        return this;
    }

    public Manga dibujante(String dibujante) {
        setDibujante(dibujante);
        return this;
    }

    public Manga enlace(String enlace) {
        setEnlace(enlace);
        return this;
    }

    /**
     * Converts the manga object to a string representation.
     * @return A string representation of the manga.
     */
    @Override
    public String toString() {
        return  getGeneros()+ "" +
            "," +  getNombre()+ "" +
            "," + getCategoria() + "" +
            "," + getNumCapitulos() +
            "," + getAutor() +
            "," + getDibujante() +
            "," + getEnlace()+
            ","+getDireccionImagen();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Manga)) {
            return false;
        }
        Manga manga = (Manga) o;
        return Objects.equals(nombre, manga.nombre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre);
    }
}