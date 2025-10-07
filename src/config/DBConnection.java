package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rai
 */
public class DBConnection {
    
    String db = "Cine_DB";
    String url = "jdbc:mysql://localhost:3306/Cine_DB";
    String user = "root";
    String password = "123456";
    String driver = "com.mysql.cj.jdbc.Driver";
    Connection cx;

    public DBConnection() {
    }

    public Connection conectar() {
        try {
            Class.forName(driver);
            cx = DriverManager.getConnection(url, user, password);
            System.out.println("Conectado a DB " + db);
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("No se logro conectar a DB " + db);
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cx;
    }

    public void desconectar() {
        try {
            cx.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //Para probar la conexion.
    public static void main(String[] args) {
        DBConnection conexion = new DBConnection();
        conexion.conectar();
    }
}
