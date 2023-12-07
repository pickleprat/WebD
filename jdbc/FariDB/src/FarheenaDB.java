import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class FarheenaDB {
    public static Connection connectDB(String dbName) throws SQLException {
        Map<String, String> env = System.getenv(); 
        String user = env.get("DB_USER"); 
        String password = env.get("DB_PASSWORD"); 
        String host = env.get("DB_HOST"); 
        String port = env.get("DB_PORT"); 
        String url = String.format("jdbc:postgresql://%s:%s/%s", host, port, dbName); 

        Properties props = new Properties(); 
        props.setProperty("user", user); 
        props.setProperty("password", password); 
        Connection conn = DriverManager.getConnection(url, props);
        return conn;  

    }

    public static boolean uploadImagesToFariDB(String path, Connection conn) throws IOException{ 
        File fObj = new File(path); 
        FileInputStream fs; 
        String query = "INSERT INTO fariImages(imageName, imageOid) VALUES (?, ?)"; 
        try{
            if(fObj.exists() && fObj.isDirectory()){
                // if the directory exists then iterate over images

                File[] imageList = fObj.listFiles(); 
                for(File image : imageList){
                    if(image.getName().endsWith(".jpg")){
                        fs = new FileInputStream(image); 
                        PreparedStatement ps = conn.prepareStatement(query); 
                        ps.setString(1, image.getName());
                        ps.setBinaryStream(2, fs, image.length());
                        ps.executeUpdate(); 
                        ps.close(); 
                        fs.close(); 
                    }
                }

            } 
        } 

        catch(FileNotFoundException fe){
            fe.printStackTrace();
            return false; 
        }

        catch(SQLException sql){
            sql.printStackTrace();
            return false; 
        }

        return true;  
    }

    public static Map<String, InputStream> loadImagesFromFariDB(Connection conn){
        InputStream fs; Map<String, InputStream> map = new HashMap<String, InputStream>(); 
        try{

            Statement stmt = conn.createStatement(); 
            String query = "SELECT * FROM fariimages;"; 
            ResultSet rs = stmt.executeQuery(query); 
            while(rs.next()){
                String imageName = rs.getString(2); 
                fs = rs.getBinaryStream(3); 
                map.put(imageName, fs); 
            }

            return map; 
        } 
        catch(SQLException sql){
            sql.printStackTrace();
        }

        return null; 
        
    }


    public static void main(String[] args) throws SQLException, FileNotFoundException, IOException{
        Connection conn = connectDB("faridb"); 
        String query = "CREATE TABLE IF NOT EXISTS fariImages(id SERIAL PRIMARY KEY, imageName VARCHAR(100) NOT NULL, imageOid bytea NOT NULL) ";
        Statement st = conn.createStatement(); 
        st.executeUpdate(query); 

        // loading the images 
        String path = "Images"; 
        boolean success = uploadImagesToFariDB(path, conn); 
        if(success){
            System.out.println("Images were uploaded successfully!");
        }
        else{
            System.out.println("Go cry");
        }

        Map<String, InputStream> imageMap = loadImagesFromFariDB(conn); 
        System.out.println(imageMap.size()); 
        conn.close(); 

    }
}
