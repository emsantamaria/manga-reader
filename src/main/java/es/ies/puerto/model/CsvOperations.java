package es.ies.puerto.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @version 1.0
 * @author emsantamaria
 */
public class CsvOperations {
   private File file;
   static String pathMangas="src/main/resources/es/ies/puerto/mangas.txt";
   static String pathUsuarios="src/main/resources/es/ies/puerto/usuario.txt";

   public CsvOperations(){
       file=new File(pathMangas);
       if(!file.exists()){
           try {
               file.createNewFile();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
       file=new File(pathUsuarios);
       if(!file.exists()){
           try {
               file.createNewFile();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }
   
   /**
    * Reads mangas from the CSV file.
    * @return A list of mangas.
    */
   public List<Manga> read(){
       file=new File(pathMangas);
       List<Manga>mangas=new ArrayList<>();
       try {
           BufferedReader reader=new BufferedReader(new FileReader(file));
           String line="";
           String frase="";
           while ((line=reader.readLine())!=null) {
               frase+=line+",";
           }
           eliminarCorchetes(frase);
           mangas=mangas(frase);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return mangas;
   }

   /**
    * Writes a list of mangas to the CSV file.
    * @param mangas The list of mangas to write.
    */
   public void writeMangas(List<Manga>mangas){
       file=new File(pathMangas);
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
           for (Manga manga : mangas) {
               writer.write(manga.toString());
               writer.newLine();
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   /**
    * Reads users from the CSV file.
    * @return A list of users.
    */
   public List<Usuario>readUsuarios(){
       file=new File(pathUsuarios);
       List<Usuario>usuarios=new ArrayList<>();
       try {
           BufferedReader reader=new BufferedReader(new FileReader(file));
           String line;
           while ((line=reader.readLine())!=null) {
               List<Manga> mangas=new ArrayList<>();
               String[]array=line.split("]/");
               List<String>generos=new ArrayList<>();
               String[]arrayPropiedades=array[1].split(",");
               for (int i = 0; i < arrayPropiedades.length-3; i++) {
                   char[]arrayChar=arrayPropiedades[i].toCharArray();
                   String letra="";
                   for (int j = 0; j < arrayChar.length; j++) {
                       if(arrayChar[j]=='['){
                           arrayChar[j]=' ';
                       }
                       if(arrayChar[j]==']'){
                           arrayChar[j]=' ';
                       }
                       letra+=arrayChar[j]+"";
                   }
                   arrayPropiedades[i]=letra.trim();
                   generos.add(arrayPropiedades[i]);
               }
               String arrayManga=array[0];
               mangas= mangas(arrayManga);
               if(mangas==null){
                   mangas=new ArrayList<>();
               }
               Usuario usuario=new Usuario(mangas, generos, arrayPropiedades[arrayPropiedades.length-2], arrayPropiedades[arrayPropiedades.length-2], arrayPropiedades[arrayPropiedades.length-1]);
               usuarios.add(usuario);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return usuarios;
   }

   /**
    * Adds a manga to the list and updates the CSV file.
    * @param mangas The list of mangas.
    * @param manga The manga to add.
    * @return True if the manga was added, otherwise false.
    */
   public boolean add(List<Manga> mangas,Manga manga){
       if(!mangas.contains(manga)){
           mangas.add(manga);
           writeMangas(mangas);
           return true;
       }
       return false;
   }

   /**
    * Removes a manga from the list.
    * @param mangas The list of mangas.
    * @param manga The manga to remove.
    * @return True if the manga was removed, otherwise false.
    */
   public boolean remove(List<Manga> mangas,Manga manga){
       return mangas.remove(manga);
   }

   /**
    * Adds a user to the list and updates the CSV file.
    * @param usuario The user to add.
    * @param usuarios The list of users.
    * @return True if the user was added, otherwise false.
    */
   public boolean addUsuario(Usuario usuario,List<Usuario>usuarios){
       if(!usuarios.contains(usuario)){
           usuarios.add(usuario);
           file=new File(pathUsuarios);
           file.delete();
           try {
               file.createNewFile();
               crear(usuarios);
           } catch (IOException e) {
               e.printStackTrace();
           }
           return true;
       }
       return false;
   }

   /**
    * Writes the list of users to the CSV file.
    * @param usuarios The list of users.
    * @return True if the operation was successful, otherwise false.
    */
   public boolean crear(List<Usuario>usuarios){
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
           for (Usuario usuarioWrite : usuarios) {
               writer.write(usuarioWrite.toString());
               writer.newLine();
           }
           return true;
       }catch (IOException e) {
           e.printStackTrace();
           return false;
       }
   }

   /**
    * Saves the currently logged-in user's information to a file.
    * @param usuario The logged-in user.
    */
   public void usuarioIniciado(Usuario usuario){
       String path="src/main/resources/es/ies/puerto/usuarioIniciado.txt";
       String pathMangasGustados="src/main/resources/es/ies/puerto/usuarioMangas.txt";
       file=new File(path);
       File fileManga=new File(pathMangasGustados);
       if(file.exists()){
           file.delete();
       }
       if(fileManga.exists()){
           fileManga.delete();
       }
       try {
           file.createNewFile();
           fileManga.createNewFile();
       } catch (IOException e) {
           e.printStackTrace();
       }
       try (BufferedWriter writer=new BufferedWriter(new FileWriter(file))){
           String generos="";
           for (int i = 0; i < usuario.getGeneros().size(); i++) {
               generos+=usuario.getGeneros().get(i);
               if(i!=usuario.getGeneros().size()-1){
                   generos+=",";
               }
           }
           writer.write(generos+","+usuario.getNombre()+","+usuario.getEmail()+","+usuario.getPassword());
           writeUsuarioManga(usuario.getMangasGustados());
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   /**
    * Writes the liked mangas of the logged-in user to a file.
    * @param mangas The list of liked mangas.
    */
   public void writeUsuarioManga(List<Manga>mangas){
       File fileWrite=new File("src/main/resources/es/ies/puerto/usuarioMangas.txt");
       try (BufferedWriter writer=new BufferedWriter(new FileWriter(fileWrite))){
           for (Manga manga : mangas) {
               writer.write(manga.toString());
               writer.newLine();
           }
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   /**
    * Reads the currently logged-in user's information from a file.
    * @return The logged-in user.
    */
   public Usuario readUsuario() {
       String path="src/main/resources/es/ies/puerto/usuarioIniciado.txt";
       file=new File(path);
       Usuario usuario=new Usuario();
       if(!file.exists()){
           return usuario;
       }
       try {
           BufferedReader reader=new BufferedReader(new FileReader(file));
           String line;
           while ((line=reader.readLine())!=null) {
               String[]array=line.split(",");
               List<String> generos=new ArrayList<>();
               for (int i = 0; i < array.length-3; i++) {
                   generos.add(array[i]);
               }
               String nombre=array[array.length-3];
               String email=array[array.length-2];
               String password=array[array.length-1];
               List<Manga>mangas=readManga("src/main/resources/es/ies/puerto/usuarioMangas.txt");
               if(mangas==null){
                   mangas=new ArrayList<>();
               }
               usuario=new Usuario(mangas, generos, password, email, nombre);
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       return usuario;
   }

   /**
    * Reads mangas from a specified file.
    * @param pathRead The path of the file to read.
    * @return A list of mangas.
    */
   public List<Manga>readManga(String pathRead){
       file=new File(pathRead);
       List<Manga>mangas=new ArrayList<>();
       try {
           String frase="";
           BufferedReader reader=new BufferedReader(new FileReader(file));
           String line="";
           while ((line=reader.readLine())!=null) {
               frase+=line+",";
           }
           frase=eliminarCorchetes(frase);
           mangas=mangas(frase);
       } catch (Exception e) {
           e.printStackTrace();
       }
       return mangas;
   }

   /**
    * Converts a string into a list of mangas.
    * @param line The string containing manga data.
    * @return A list of mangas.
    */
   public List<Manga>mangas(String line){
       line=eliminarCorchetes(line);
       List<Manga>mangas=new ArrayList<>();
       String[]array=line.split(",");
       int numMangas=array.length/10;
       int pocision=0;
       for (int i = 0; i < numMangas;i++) { 
           String propiedades="";
           for (int j = pocision; j < pocision+10; j++) {
               propiedades+=array[j].trim();
               if(j<pocision+9){
                   propiedades+=",";
               }
           }
           String[]arrayPropiedades=propiedades.split(",");
           List<String>generos=new ArrayList<>(Arrays.asList(arrayPropiedades[0],arrayPropiedades[1],arrayPropiedades[2]));
           Manga manga=new Manga(arrayPropiedades[3], generos, arrayPropiedades[4], Integer.parseInt(arrayPropiedades[5]), arrayPropiedades[6], arrayPropiedades[7], arrayPropiedades[arrayPropiedades.length-2], arrayPropiedades[arrayPropiedades.length-1]);
           mangas.add(manga);
           pocision=pocision+10;
       }
       return mangas;
   }

   /**
    * Removes brackets from a string.
    * @param line The string to process.
    * @return The processed string.
    */
   public String eliminarCorchetes(String line){
       char[]arrayChar=line.toCharArray();
       String frase="";
       for (int j = 0; j < arrayChar.length; j++) {
           if(arrayChar[j]=='['||arrayChar[j]==']'){
               arrayChar[j]=' ';
           }
           frase+=arrayChar[j]+"";
       }
       line=frase.trim();
       return line;
   }
}
