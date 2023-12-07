import java.sql.Connection; 
import java.util.Map; 
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties; 
import java.sql.Statement; 

public class CreateTable {
    public static Connection connectDB() throws SQLException{
        Map<String, String> env = System.getenv(); 
        String user = env.get("DB_USER"); 
        String password = env.get("DB_PASSWORD"); 
        String host = env.get("DB_HOST"); 
        String port = env.get("DB_PORT"); 
        String dbName = env.get("DB_NAME"); 
        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName); 

        Properties props = new Properties(); 
        props.setProperty("user", user); 
        props.setProperty("password", password); 

        Connection conn = DriverManager.getConnection(url, props);
        return conn; 

    }

    public static void main(String[] args) throws SQLException{
        Connection conn = connectDB(); 
        String query = "CREATE TABLE fari(organID SERIAL PRIMARY KEY, organ VARCHAR(50) NOT NULL, price DOUBLE PRECISION NOT NULL)"; 
        Statement stmt = conn.createStatement(); 
        stmt.executeUpdate(query); 
        conn.close(); 

    }
}
