package es.ies.puerto.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @version 1.0
 * @author emsantamaria
 */
public class Bbdd {
    private Connection con;

    /**
     * Establishes a connection to the database using the provided URL.
     * @param url The database URL.
     */
    public Bbdd(String url) {
       try {
        Class.forName("org.sqlite.JDBC");
        this.con=DriverManager.getConnection(url);
       } catch (Exception e) {
        e.printStackTrace();
       }
    }

    /**
     * Inserts a new user into the database.
     * @param usuario The user to be inserted.
     */
    public void insertData(Usuario usuario){
        String qry="INSERT INTO usuario(nombre,contrasenia,email)VALUES(?, ?, ?)";
        try {
            PreparedStatement st=con.prepareStatement(qry);
            st.setString(1, usuario.getNombre());
            st.setString(2, usuario.getPassword());
            st.setString(3, usuario.getEmail());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a list of genres associated with a user into the database.
     * @param generos List of genres.
     * @param id User ID.
     */
    public void insertDataGeneros(List<String>generos,int id){
        for (int i = 0; i < generos.size(); i++) {
            String qry="insert into usuario_genero(id, genero)values(?, ?)";
            try {
                PreparedStatement st=con.prepareStatement(qry);
                st.setInt(1, id);
                st.setString(2, generos.get(i));
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Inserts a list of liked mangas associated with a user into the database.
     * @param gustados List of liked mangas.
     * @param id User ID.
     */
    public void insertDataMangas(List<Manga>gustados,int id){
        for (int i = 0; i < gustados.size(); i++) {
            String qry="insert into usuario_mangas(id,genero1,genero2,genero3,nombre,categoria,numCapitulos,autor,dibujante,enlace,direccionImagen)values(?,?,?,?,?,?,?,?,?,?,?)";
            try {
                PreparedStatement st=con.prepareStatement(qry);
                st.setInt(1, id);
                st.setString(2, gustados.get(i).getGeneros().get(0));
                st.setString(3, gustados.get(i).getGeneros().get(1));
                st.setString(4, gustados.get(i).getGeneros().get(2));
                st.setString(5, gustados.get(i).getNombre());
                st.setString(6, gustados.get(i).getCategoria());
                st.setInt(7, gustados.get(i).getNumCapitulos());
                st.setString(8, gustados.get(i).getAutor());
                st.setString(9, gustados.get(i).getDibujante());
                st.setString(10, gustados.get(i).getEnlace());
                st.setString(11, gustados.get(i).getDireccionImagen());
                st.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Retrieves all users from the database.
     * @return List of users.
     */
    public List<Usuario> obtenerUsuarios(){
        List<Usuario>usuarios=new ArrayList<>();
        String qry="select * from usuario";
        PreparedStatement sentencia;
        try {
            sentencia = con.prepareStatement(qry);
            ResultSet resultSet=sentencia.executeQuery();
            while (resultSet.next()) {
                int id=resultSet.getInt("id");
                String nombre=resultSet.getString("nombre");
                String contrasenia=resultSet.getString("contrasenia");
                String email=resultSet.getString("email");
                List<String>generos=obtenerGeneros(id);
                List<Manga>mangas=obtenerMangas(id);
                Usuario usuario=new Usuario(id, mangas, generos, contrasenia, email, nombre);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return usuarios;
    }

    /**
     * Retrieves all genres associated with a user.
     * @param id User ID.
     * @return List of genres.
     * @throws SQLException if a database access error occurs.
     */
    public List<String> obtenerGeneros(int id) throws SQLException{
        List<String>generos=new ArrayList<>();
        PreparedStatement st=con.prepareStatement("select * from usuario_genero where id="+id);
        ResultSet resultSet=st.executeQuery();
        while (resultSet.next()) {
            String genero=resultSet.getString("genero");
            if(!generos.contains(genero)){
                generos.add(genero); 
            }
        }
        return generos;
    }

    /**
     * Retrieves all mangas associated with a user.
     * @param id User ID.
     * @return List of mangas.
     */
    public List<Manga> obtenerMangas(int id){
        List<Manga>mangas=new ArrayList<>();
        String qry="select * from usuario_mangas where id=?";
        PreparedStatement st;
        try {
            st = con.prepareStatement(qry);
            st.setInt(1, id);
        ResultSet resultSet=st.executeQuery();
        while (resultSet.next()) {
            String genero1=resultSet.getString("genero1");
            String genero2=resultSet.getString("genero2");
            String genero3=resultSet.getString("genero3");
            List<String>generos=new ArrayList<>(Arrays.asList(genero1,genero2,genero3));
            String nombre=resultSet.getString("nombre");
            String categori=resultSet.getString("categoria");
            int numCapitulos=resultSet.getInt("numCapitulos");
            String autor=resultSet.getString("autor");
            String dibujante=resultSet.getString("dibujante");
            String enlace=resultSet.getString("enlace");
            String direccion=resultSet.getString("direccionImagen");
            Manga manga=new Manga(nombre, generos, categori, numCapitulos, autor, dibujante, enlace, direccion);
           if(!mangas.contains(manga)){
            mangas.add(manga);
           }
            
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mangas;
    }

    /**
     * Closes the database connection.
     * @throws SQLException if a database access error occurs.
     */
    public void closeConnection() throws SQLException{
        con.close();
    }

    /**
     * Finds a user by their name.
     * @param nombre The name of the user.
     * @return The user object if found.
     * @throws SQLException if a database access error occurs.
     */
    public Usuario findUsuario(String nombre) throws SQLException{
        Usuario usuario=new Usuario();
        PreparedStatement sentencia=con.prepareStatement("select * from usuario where nombre='"+nombre+"'");
        ResultSet resultSet=sentencia.executeQuery();
        while (resultSet.next()) {
            int id=resultSet.getInt("id");
            String nombreSearch=resultSet.getString("nombre");
            String password=resultSet.getString("contrasenia");
            String email=resultSet.getString("email");
            usuario=new Usuario(password, nombreSearch, email, id);
        }
        return usuario;
    }

    /**
     * Deletes a user and their associated data from the database.
     * @param id User ID.
     */
    public void delete(int id){
        String qry="delete from usuario where id=?";
        try {
            PreparedStatement st=con.prepareStatement(qry);
            st.setInt(1, id);
            st.executeUpdate();
            deleteGeneros(id);
            deleteMangas(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes all genres associated with a user from the database.
     * @param id User ID.
     */
    public void deleteGeneros(int id){
        String qry="DELETE from usuario_genero where id=?";
        try {
            PreparedStatement st=con.prepareStatement(qry);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     * Deletes all mangas associated with a user from the database.
     * @param id User ID.
     */
    public void deleteMangas(int id){
        String qry="DELETE from usuario_mangas where id=?";
        try {
            PreparedStatement st=con.prepareStatement(qry);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (Exception e) {
        }
    }

    /**
     * Updates a user's information in the database.
     * @param usuario The user object with updated information.
     */
    public void updateUser(Usuario usuario){
        String qry="update usuario set nombre=?, contrasenia=?, email=? where id=?";
        try {
            PreparedStatement st=con.prepareStatement(qry);
            st.setString(1, usuario.getNombre());
            st.setString(2, usuario.getPassword());
            st.setString(3, usuario.getEmail());
            st.setInt(4, usuario.getId());
            st.executeUpdate();
            deleteGeneros(usuario.getId());
            deleteMangas(usuario.getId());
            insertDataGeneros(usuario.getGeneros(), usuario.getId());
            insertDataMangas(usuario.getMangasGustados(), usuario.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
