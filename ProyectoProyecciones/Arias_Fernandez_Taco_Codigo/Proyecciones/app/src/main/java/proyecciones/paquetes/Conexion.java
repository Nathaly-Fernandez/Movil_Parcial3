package proyecciones.paquetes;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion  {
    String USUARIO="root";
    String PASSWORD="";
    String usuario="";
    String pass="";
    String bd = "proyecciones";
    String sv = "10.0.2.2";
    String pt = "3306";
    String us = "root";
    String ct = "";
    Connection conexionMySQL;



    public Connection conectar(){
        String USUARIO="root";
        String PASSWORD="root";
        String usuario="";
        String pass="";


        boolean estadoConexion = false;
        Connection conexionMySQL = null;

        String driver = "com.mysql.jdbc.Driver";
        String urlMySQL = "jdbc:mysql://" + sv + ":" + pt + "/";
        try {
            Class.forName(driver).newInstance();
            conexionMySQL = DriverManager.getConnection(urlMySQL + bd ,us,ct);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return conexionMySQL;
    }





}


