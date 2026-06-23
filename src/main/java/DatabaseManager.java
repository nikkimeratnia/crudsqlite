import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String URL="jdbc:sqlite:pashmak.db";
    public static Connection getConnectionn() throws SQLException {
       Connection conn=DriverManager.getConnection(URL);
       return conn;


    }
}