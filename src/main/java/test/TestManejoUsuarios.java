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
            IUsuario usuarioDao = new UsuarioDAO();
            List<UsuarioDTO> usuarios = usuarioDao.SELECT();

            for (UsuarioDTO usuario : usuarios) {
                System.out.println("Usuario DTO: " + usuario);
            }
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
