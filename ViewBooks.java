package LibraryModel;
import java.sql.*;

public class ViewBooks {
    static Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CollegeLibrary", "root", "qwertyuiop1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void viewInventory() {
        String query = "SELECT * FROM BookInventory";

        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Book Inventory:");
            System.out.println("---------------------------------------------------------------------------");
            System.out.printf("%-30s %-15s %-10s %-20s %-10s%n", "Book Name", "ISBN Code", "Quantity", "Last Issued Date", "Status");
            System.out.println("---------------------------------------------------------------------------");

            while (rs.next()) {
                String bookName = rs.getString("bookName");
                String isbnCode = rs.getString("ISBNcode");
                int quantity = rs.getInt("quantity");
                Date lastIssuedDate = rs.getDate("lastIssuedDate");
                String status = rs.getString("status");

                System.out.printf("%-30s %-15s %-10d %-20s %-10s%n", bookName, isbnCode, quantity, lastIssuedDate, status);
            }

            System.out.println("---------------------------------------------------------------------------");
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR");
            e.printStackTrace();
        }
    }
}
