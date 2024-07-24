package LibraryModel;
import java.sql.*;
import java.time.*;
import java.util.*;

public class LendBook
{
    static Scanner obj=new Scanner(System.in);
    static Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CollegeLibrary", "root", "qwertyuiop1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void lendBook(String uid) throws SQLException {
        System.out.print("Enter the Book Name: ");
        String bookName = obj.nextLine();

        System.out.println("Enter ISBN Code: ");
        String isbnCode = obj.nextLine();

        LocalDate today = LocalDate.now();
        String issueDate = today.toString(); //gives the date in YYYY-MM-DD format

        // Check if the book is available in inventory
        String checkQuery = "SELECT quantity FROM BookInventory WHERE ISBNcode = ? AND status = 'AVAILABLE'";
        try (PreparedStatement checkStmt = con.prepareStatement(checkQuery)) {
            checkStmt.setString(1, isbnCode);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int quantity = rs.getInt("quantity");
                if (quantity > 0) {
                    // Update BookLog table
                    String logQuery = "INSERT INTO BookLog (uid, bookName, ISBNcode, issueDate, bookStatus) VALUES (?, ?, ?, ?, ?)";
                    try (PreparedStatement logStmt = con.prepareStatement(logQuery)) {
                        logStmt.setString(1, uid);
                        logStmt.setString(2, bookName);
                        logStmt.setString(3, isbnCode);
                        logStmt.setString(4, issueDate);
                        logStmt.setString(5, "ISSUED");
                        logStmt.executeUpdate();
                    }

                    // Update BookInventory table
                    String updateQuery = "UPDATE BookInventory SET quantity = quantity - 1, lastIssuedDate = ?, status = IF(quantity - 1 > 0, 'AVAILABLE', 'ISSUED') WHERE ISBNcode = ?";
                    try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                        updateStmt.setString(1, issueDate);
                        updateStmt.setString(2, isbnCode);
                        updateStmt.executeUpdate();
                    }

                    System.out.println("ENTRY SUCCESSFUL");
                } else {
                    System.out.println("Book not available in inventory.");
                }
            } else {
                System.out.println("Book not found in inventory or already issued.");
            }
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR");
            e.printStackTrace();
        }
    }
}
