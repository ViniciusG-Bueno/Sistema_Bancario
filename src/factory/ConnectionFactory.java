package factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    static final String URL = "jdbc:mysql://localhost:3306/sistema_bancario";
    static final String USER = "root";
    static final String PASSWORD = "";

    public static Connection getMySQLConnection() throws SQLException{
        Connection conexao = DriverManager.getConnection(URL, USER, PASSWORD);
        return conexao;
    }
}
