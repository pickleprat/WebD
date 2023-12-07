import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException; 
import java.util.Properties; 
import java.util.Map; 
import java.sql.Statement; 

public class App {
    public static Connection connectDB(String username, String password, String dbName, String host, String port){
        try{
            Properties prop = new Properties(); 
            prop.setProperty("password", password); 
            prop.setProperty("user", username); 
            String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName); 
            Connection conn = DriverManager.getConnection(url, prop); 
            return conn; 
        } 

        catch(SQLException e){
            e.printStackTrace();
        }

        return null; 
    }

    public static void main(String[] args) throws Exception {
        Map<String, String> env = System.getenv(); 

        // getting information about the database 
        String dbName = env.get("DB_NAME"); 
        String host = env.get("DB_HOST"); 
        String username = env.get("DB_USER"); 
        String port = env.get("DB_PORT"); 
        String password = env.get("DB_PASSWORD"); 

        // connecting via a connection object 
        Connection conn = connectDB(username, password, dbName, host, port);
	    String query = "SELECT * FROM public.home_order;"; 
        Statement stmt = conn.createStatement();
        ResultSet set = stmt.executeQuery(query); 	

        set.next(); 
        System.out.println(set.getString("orderid"));
        conn.close(); 
    }
}
