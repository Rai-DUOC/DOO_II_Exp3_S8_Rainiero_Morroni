package model;

import config.DBConnection;
import model.Pelicula;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rai
 */
public class PeliculaDAO {

    private DBConnection dbConn = new DBConnection();

    //CRUD:
    // --- CREATE ---
    public boolean insertarPelicula(Pelicula pelicula) {
        String sql = "INSERT INTO Cartelera (titulo, director, anio, duracion, genero) VALUES (?, ?, ?, ?, ?)";
        Connection conn = dbConn.conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getDirector());
            ps.setInt(3, pelicula.getAnio());
            ps.setInt(4, pelicula.getDuracion());
            ps.setString(5, pelicula.getGenero());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbConn.desconectar(); // metodo de desconexion
        }
    }

    // --- UPDATE ---
    public boolean modificarPelicula(Pelicula pelicula) {
        String sql = "UPDATE Cartelera SET titulo=?, director=?, anio=?, duracion=?, genero=? WHERE id=?";
        Connection conn = dbConn.conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pelicula.getTitulo());
            ps.setString(2, pelicula.getDirector());
            ps.setInt(3, pelicula.getAnio());
            ps.setInt(4, pelicula.getDuracion());
            ps.setString(5, pelicula.getGenero());
            ps.setInt(6, pelicula.getId());
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbConn.desconectar();
        }
    }

    // --- DELETE ---
    public boolean eliminarPelicula(int id) {
        String sql = "DELETE FROM Cartelera WHERE id=?";
        Connection conn = dbConn.conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            dbConn.desconectar();
        }
    }

    // --- READ ---
    public Pelicula buscarPeliculaPorId(int id) {
        String sql = "SELECT * FROM Cartelera WHERE id=?";
        Connection conn = dbConn.conectar();
        Pelicula pelicula = null;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    pelicula = new Pelicula(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("director"),
                            rs.getInt("anio"),
                            rs.getInt("duracion"),
                            rs.getString("genero")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.desconectar();
        }
        return pelicula;
    }

    // --- Listar y Filtrar ---
    public List<Pelicula> listarTodas() {
        return ejecutarConsulta("SELECT * FROM Cartelera");
    }

    public List<Pelicula> listarPorGenero(String genero) {
        String sql = "SELECT * FROM Cartelera WHERE genero = ?";
        return ejecutarConsultaConParametro(sql, genero);
    }

    public List<Pelicula> listarPorRangoAnios(int anioInicio, int anioFin) {
        String sql = "SELECT * FROM Cartelera WHERE anio BETWEEN ? AND ?";
        List<Pelicula> lista = new ArrayList<>();
        Connection conn = dbConn.conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, anioInicio);
            ps.setInt(2, anioFin);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Pelicula(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("director"),
                            rs.getInt("anio"),
                            rs.getInt("duracion"),
                            rs.getString("genero")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.desconectar();
        }
        return lista;
    }

    // Metodo auxiliar para consultas sin parametros
    private List<Pelicula> ejecutarConsulta(String sql) {
        List<Pelicula> lista = new ArrayList<>();
        Connection conn = dbConn.conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new Pelicula(
                        rs.getInt("id"),
                        rs.getString("titulo"),
                        rs.getString("director"),
                        rs.getInt("anio"),
                        rs.getInt("duracion"),
                        rs.getString("genero")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.desconectar();
        }
        return lista;
    }

    // Metodo auxiliar para consultas con un parametro String
    private List<Pelicula> ejecutarConsultaConParametro(String sql, String parametro) {
        List<Pelicula> lista = new ArrayList<>();
        Connection conn = dbConn.conectar();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, parametro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Pelicula(
                            rs.getInt("id"),
                            rs.getString("titulo"),
                            rs.getString("director"),
                            rs.getInt("anio"),
                            rs.getInt("duracion"),
                            rs.getString("genero")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbConn.desconectar();
        }
        return lista;
    }
}

