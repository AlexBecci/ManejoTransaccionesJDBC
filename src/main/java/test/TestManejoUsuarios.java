package test;
import datos.UsuarioDAO;
import datos.*;
import domain.*;
import java.sql.*;
import java.util.*;

public class TestManejoUsuarios {

    public static void main(String[] args) {
        Connection conexion = null;

        try {
            conexion = Conexion.getConnection();
            if (conexion.getAutoCommit()) {
                conexion.setAutoCommit(false);
            }
            UsuarioDAO usuarioJDBC = new UsuarioDAO(conexion);

            Usuario cambioPersona = new Usuario();
            cambioPersona.setIdUsuario(2);
            cambioPersona.setUsername("CristianFlores");
            cambioPersona.setPassword("Cris2019");
            usuarioJDBC.UPDATE(cambioPersona);

            Usuario usuarioNuevo = new Usuario();
            usuarioNuevo.setUsername("SonGoku");
            //usuarioNuevo.setPassword("GohanHijo11111111111111111111111111111111111111111111111111111111111111");
            usuarioNuevo.setPassword("GohanHijo");
            usuarioJDBC.INSERT(usuarioNuevo);

            conexion.commit();
            System.out.println("Se ha hecho commit de la transaccion");
        } catch (SQLException ex) {
            try {
                ex.printStackTrace(System.out);
                System.out.println("Entramos al rollback");
                conexion.rollback();
            } catch (SQLException ex1) {
                ex.printStackTrace(System.out);
            }
        }

    }
}
