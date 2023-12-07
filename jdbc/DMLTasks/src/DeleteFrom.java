import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException; 

public class DeleteFrom {
    public static void main(String[] args) throws SQLException {
        Connection conn = CreateTable.connectDB(); 
        String query = "DELETE FROM fari WHERE organId=7"; 
        PreparedStatement pstmt = conn.prepareStatement(query); 
        pstmt.executeUpdate(); 

    }
}
