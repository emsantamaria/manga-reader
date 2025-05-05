package es.ies.puerto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import es.ies.puerto.model.CsvOperations;
import es.ies.puerto.model.Usuario;

public class UsuarioTest {
    
    @Test
     void UsuarioTest(){
        CsvOperations csvOperations=new CsvOperations();
        Usuario usuario=new Usuario(null,null,"goku","goku",null);
        assertEquals(usuario, csvOperations.readUsuario());
    }
}
