import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateTable {
    public static void main(String[] args) throws SQLException {
        Connection conn = CreateTable.connectDB(); 
        String query = "UPDATE fari SET organ='lash', price=2312832.32341 WHERE organ='lashes'"; 
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.executeUpdate();  
        
    }
}
