package es.ies.puerto.controller.abstractas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javafx.application.Application;
import javafx.stage.Stage;
/**
 * @version 1.0
 * @author emsantamaria
 */
public abstract class AbstractController extends Application {
   
    private Properties propertiesIdioma;

    /**
     * Sets the language properties for the application.
     * @param properties The language properties to set.
     */
    public void setPropertiesIdioma(Properties properties) {
        propertiesIdioma = properties;
    }

    /**
     * Gets the current language properties of the application.
     * @return The language properties.
     */
    public Properties getPropertiesIdioma() {
        return propertiesIdioma;
    }

    /**
     * Writes the selected language to a file.
     * @param idioma The language code to write.
     */
    public void writeIdioma(String idioma){
        File file=new File("src/main/resources/idioma.txt");
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(idioma);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the language properties from a file.
     * @return The language properties.
     */
    public Properties readProperties(){
        File file=new File("src/main/resources/idioma.txt");
        Properties properties=new Properties();
        if(!file.exists()){
            return null;
        } 
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String idioma="es";
            String line = reader.readLine(); 
            if (line != null) {
                idioma = line; 
            }
                String path="src/main/resources/idioma-"+idioma+".properties";
                FileInputStream input=new FileInputStream(path);
                InputStreamReader stream=new InputStreamReader(input, "UTF-8");
                properties.load(stream);
            }
         catch (Exception e) {
            e.printStackTrace();
        }
        return properties;
    }

    /**
     * Loads language-specific properties from a file.
     * @param nombreFichero The base name of the properties file.
     * @param idioma The language code.
     * @return The loaded properties.
     */
    public Properties loadIdioma(String nombreFichero, String idioma) {
        Properties properties = new Properties();
        
        if (nombreFichero == null || idioma == null) {
            return properties;
        }
        
        String path = "src/main/resources/" + nombreFichero+"-"+idioma+".properties";

        File file = new File(path);

        if (!file.exists() || !file.isFile()) {
            System.out.println("Path:"+file.getAbsolutePath());
            return properties;
        }
        
        try {
            FileInputStream input = new FileInputStream(path);
            InputStreamReader isr = new InputStreamReader(input, "UTF-8");
            properties.load(isr);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return properties;
    }

    /**
     * Saves user credentials to a file.
     * @param nombre The username.
     * @param contrasenia The password.
     * @param email The email address.
     */
    public void savePropertiesUsuario(String nombre, String contrasenia,String email) {   
        String path = "src/main/resources/usuario.txt";
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }try {
        file.createNewFile();       
          try (BufferedWriter writer = new BufferedWriter(new FileWriter(file,true))) {
            writer.write(nombre);
              writer.newLine();
              writer.write(contrasenia);
              writer.newLine();
              writer.write(email);
        }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads user credentials from a file.
     * @return A list containing the username, password, and email.
     * @throws FileNotFoundException if the file does not exist.
     */
    public List<String> loadPropertiesUsuario() throws FileNotFoundException{
        List<String>arrayList=new ArrayList<>();
        String path = "src/main/resources/usuario.txt";
        File file = new File(path);
       BufferedReader reader=new BufferedReader(new FileReader(file));
      try {
        String line;
        while ((line=reader.readLine())!=null) {
            arrayList.add(line);
        }
        
    } catch (IOException e) {
        e.printStackTrace();
    }
        return arrayList;
    }

    /**
     * Abstract method to start the JavaFX application.
     * @param primaryStage The primary stage for the application.
     * @throws Exception if an error occurs during startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }
}
