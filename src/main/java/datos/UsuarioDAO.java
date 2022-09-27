package datos;

import static datos.Conexion.*;
import domain.UsuarioDTO;
import java.util.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO implements IUsuario {

    private Connection conexionTransaccional;

    public UsuarioDAO() {

    }

    public UsuarioDAO(Connection conexionTransaccional) {
        this.conexionTransaccional = conexionTransaccional;

    }

    @Override
    public List<UsuarioDTO> SELECT() throws SQLException {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet resulSet = null;
        UsuarioDTO usuario = null;

        List<UsuarioDTO> usuarios = new ArrayList<>();
        try {
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_SELECT);
            resulSet = stmt.executeQuery();
            while (resulSet.next()) {
                int idUsuario = resulSet.getInt("id_usuario");
                String username = resulSet.getString("username");
                String password = resulSet.getString("password");

                usuario = new UsuarioDTO(idUsuario, username, password);
                usuarios.add(usuario);
            }
        } finally {
            try {
                close(resulSet);
                close(stmt);
                if (this.conexionTransaccional == null) {
                    Conexion.close(conn);
                }

            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return usuarios;
    }

    @Override
    public int INSERT(UsuarioDTO usuario) throws SQLException {

        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {

            System.out.println("ejecutando query: " + SQL_INSERT);
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_INSERT);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());

            registros = stmt.executeUpdate();
            System.out.println("Registros afectados: " + registros);
        } finally {
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    Conexion.close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
    }

    @Override
    public int UPDATE(UsuarioDTO usuario) throws SQLException {

        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            System.out.println("Ejecutando query: "+ SQL_UPDATE);
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_UPDATE);
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getPassword());
            stmt.setInt(3, usuario.getIdUsuario());

            registros = stmt.executeUpdate();
            System.out.println("Registros actualizados: "+ registros);
        } finally {
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    Conexion.close(conn);
                }

            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
    }

    @Override
    public int DELETE(UsuarioDTO usuario) throws SQLException {

        Connection conn = null;
        PreparedStatement stmt = null;
        int registros = 0;
        try {
            System.out.println("Ejecutando query: "+ SQL_DELETE);
            conn = this.conexionTransaccional != null ? this.conexionTransaccional : Conexion.getConnection();
            stmt = conn.prepareStatement(SQL_DELETE);
            stmt.setInt(1, usuario.getIdUsuario());

            registros = stmt.executeUpdate();
            System.out.println("Registros eliminados: "+ registros);
        } finally {
            try {
                close(stmt);
                if (this.conexionTransaccional == null) {
                    Conexion.close(conn);
                }
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }
        return registros;
    }
}
