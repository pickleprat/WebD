import java.sql.DriverManager; 
import java.util.Properties;
import java.sql.SQLException; 
import java.sql.Connection; 
import java.util.Map; 
import java.sql.ResultSet; 
import java.sql.Statement; 
import java.util.ArrayList; 

// creating a class Style to store database objects. 
class Style{
    private String styleID; 
    private String category; 

    public Style(String styleID, String category){
        this.styleID = styleID; 
        this.category = category; 
    }

    public String getStyleID(){
        return this.styleID; 
    }

    public void setStyleID(String newID){
        this.styleID = newID; 
    }

    public String getCategory(){
        return this.category; 
    }

    public void setCategory(String newCategory){
        this.styleID = newCategory;
    }
 
}

public class StyleConnect {
    public static Connection connectDB(){

        // have your database environment variables stored in the bashrc file. 
        Map<String, String> env = System.getenv(); 
        String username = env.get("DB_USER"); 
        String password = env.get("DB_PASSWORD"); 
        String dbName = env.get("DB_NAME"); 
        String host = env.get("DB_HOST"); 
        String port = env.get("DB_PORT"); 

        // establish a connection 
        Properties props = new Properties(); 
        props.setProperty("user", username);
        props.setProperty("password", password); 
        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName); 
        
        try{
            Connection conn = DriverManager.getConnection(url, props); 
            return conn; 
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        // return the connection object 
        return null; 

    }

    public static ArrayList<Style> processStyles(ResultSet set) throws SQLException {
        ArrayList<Style> styles = new ArrayList<Style>();
        String styleId; String styleCategory; 
        Style style; 

        while(set.next()){
            styleId = set.getString(1); 
            styleCategory = set.getString(2); 
            style = new Style(styleId, styleCategory);
            styles.add(style); 
        } 

        return styles; 
    }

    public static ResultSet executeQuery(String query, Connection conn) throws SQLException{
        Statement stmt = conn.createStatement(); 
        ResultSet set = stmt.executeQuery(query); 
        return set; 
    }


    public static void main(String[] args) throws SQLException {
        Connection conn = connectDB(); 
        String query = "SELECT * FROM public.home_style;"; 
        ResultSet set = executeQuery(query, conn); 
        ArrayList<Style> styles = processStyles(set); 
        System.out.println(styles.size()); 

    }
}
