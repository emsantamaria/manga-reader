package es.ies.puerto.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author emsantamaria
 */
public class Mangas {
    private List<Manga>mangas;
    private CsvOperations csvOperations;
    Usuario usuario;

    public Mangas(){
        csvOperations=new CsvOperations();
        mangas=csvOperations.read();
        usuario=csvOperations.readUsuario();
    }

    /**
     * Recommends mangas based on the user's favorite genres.
     * @return List of recommended mangas.
     */
    public List<Manga> recomendarByGeneros(){
        List<Manga>recomendados=new ArrayList<>();
        for (int i = 0; i < usuario.getGeneros().size(); i++) {
            String generoUsuario= usuario.getGeneros().get(i);
            for (Manga manga : mangas) {
                if(manga.getGeneros().contains(generoUsuario)&&!recomendados.contains(manga)&&!usuario.getMangasGustados().contains(manga)){
                    recomendados.add(manga);
                }
            }
        }
        return recomendados;
    }

    /**
     * Recommends mangas based on the user's liked mangas.
     * @return List of recommended mangas.
     */
    public List<Manga> recomendarByGustados(){

        List<Manga>recomendados=new ArrayList<>();
        for (int i = 0; i < usuario.getMangasGustados().size(); i++) {
           for (Manga manga : mangas) {
            for (String genero : usuario.getMangasGustados().get(i).getGeneros()) {
                if(manga.getGeneros().contains(genero)&&!usuario.getMangasGustados().contains(manga)&&!recomendados.contains(manga)){
                    recomendados.add(manga);
                }
            }
           } 
        }
        return recomendados;
    }

    /**
     * Returns a sublist of mangas containing up to 5 mangas.
     * @param recomendar List of mangas to be filtered.
     * @return List of up to 5 mangas.
     */
    public List<Manga> mangasDe5En5(List<Manga>recomendar){
        List<Manga>mangas5=new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                if(recomendar.size()>i){
                    mangas5.add(recomendar.get(i));
                }
            }
        return mangas5;
    }
    
    /**
     * Searches for a manga by its name.
     * @param nombre The name of the manga to search for.
     * @return The manga if found, otherwise null.
     */
    public Manga searchMangas(String nombre){

        for (Manga mangaBuscar : mangas) {      
           if(mangaBuscar.getNombre().equals(nombre)||mangaBuscar.getNombre().equals(nombre.toLowerCase())){
            return mangaBuscar;
           }
        }
        return null;
    }
    

}
