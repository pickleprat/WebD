import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class InsertTable {
    public static void main(String[] args) throws SQLException {
        Connection conn = CreateTable.connectDB(); 
        String query = "INSERT INTO fari(organ, price) VALUES (?, ?), (?, ?)"; 
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, "lashes");
        stmt.setDouble(2, 453.34); 

        stmt.setString(3, "ears"); 
        stmt.setDouble(4, 3423432.3423); 
        stmt.executeUpdate(); 


    }



    
}
