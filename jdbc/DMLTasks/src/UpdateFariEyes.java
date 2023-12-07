import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateFariEyes {
    public static void main(String[] args) throws SQLException{
        Connection conn = CreateTable.connectDB(); 
        String query = "UPDATE fari SET price=343423439423.343432 WHERE organ='eyes'"; 
        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.executeUpdate();  
        

    }
}
