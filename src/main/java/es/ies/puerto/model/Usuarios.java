package es.ies.puerto.model;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author emsantamaria
 */
public class Usuarios {
    private List<Usuario>usuarios;
    CsvOperations csvOperations;
    Bbdd bbdd;
    String path="jdbc:sqlite:src/main/resources/db/Usuarios.db";
    /**
     * Constructor inicializador
     * Initializes the Usuarios class, setting up database and CSV operations.
     * @throws ClassNotFoundException 
     */
    public Usuarios() {
        this.bbdd = new Bbdd(path);       
        csvOperations=new CsvOperations();
        this.usuarios=bbdd.obtenerUsuarios();
        if(this.usuarios==null){
            this.usuarios=new ArrayList<>();
        }

        try {
            bbdd.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if a user exists by their username and password.
     * @param nombre The username.
     * @param password The password.
     * @return True if the user exists, otherwise false.
     */
    public boolean iniciarSesion(String nombre,String password){
        if(nombre==null||nombre.isBlank()){
            return false;
        }
        if(password==null||password.isBlank()){
            return false;
        }
        Usuario buscado=new Usuario( password, nombre);
        for (Usuario usuario : usuarios) {
            if(usuario.equals(buscado)){
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an email is already associated with a user.
     * @param email The email to check.
     * @return True if the email exists, otherwise false.
     */
    public boolean findEmail(String email){
        for (Usuario usuario : usuarios) {
            if(usuario.getEmail().equals(email)){
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves a user by their email.
     * @param email The email of the user.
     * @return The user if found, otherwise null.
     */
    public Usuario darUsuarioPorEmail(String email){
        for (Usuario usuario : usuarios) {
            if(usuario.getEmail().equals(email)){
                return usuario;
            }
        }
        return null;
    }

    /**
     * Retrieves a user by their username and password.
     * @param nombre The username.
     * @param password The password.
     * @return The user if found, otherwise null.
     */
    public Usuario recibirUsurio(String nombre,String password){
        if(!iniciarSesion(nombre, password)){
            return null;
        }
        Usuario usuario=new Usuario(password, nombre);
        return usuarios.get(usuarios.indexOf(usuario));
    }

    /**
     * Adds a new user to the list and database.
     * @param usuario The user to add.
     * @return True if the user was added, otherwise false.
     */
    public boolean aniadir(Usuario usuario){
        this.bbdd=new Bbdd(path);
        if(usuarios.contains(usuario)){
            return false;
        }
        bbdd.insertData(usuario);
        return  usuarios.add(usuario);
    }

    /**
     * Removes a user from the list and database.
     * @param usuario The user to remove.
     * @return True if the user was removed, otherwise false.
     * @throws ClassNotFoundException if the database class cannot be loaded.
     * @throws SQLException if a database access error occurs.
     */
    public boolean remove(Usuario usuario) throws ClassNotFoundException, SQLException{
        this.bbdd=new Bbdd(path);
        if(!usuarios.contains(usuario)){
            return false;
        }
        bbdd.delete(usuario.getId());
        bbdd.closeConnection();
        return usuarios.remove(usuario);
    } 

    /**
     * Updates a user's information in the database.
     * @param usuario The user with updated information.
     * @throws SQLException if a database access error occurs.
     * @throws ClassNotFoundException if the database class cannot be loaded.
     */
    public void update(Usuario usuario) throws SQLException, ClassNotFoundException{
        bbdd=new Bbdd(path);
        bbdd.updateUser(usuario);
        usuarios=bbdd.obtenerUsuarios();
        csvOperations.usuarioIniciado(usuario);
        bbdd.closeConnection();
    }
}
