package LibraryModel;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

public class ReturnBook {
    static Scanner obj = new Scanner(System.in);
    static Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CollegeLibrary", "root", "qwertyuiop1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void returnBook(String uid) throws SQLException {
        System.out.print("Enter the Book Name: ");
        String bookName = obj.nextLine();

        System.out.print("Enter ISBN Code: ");
        String isbnCode = obj.nextLine();

        LocalDate today = LocalDate.now();
        String returnDate = today.toString(); //gives the date in YYYY-MM-DD format

        // Update the status of the book in the database to 'RETURNED'
        String logQuery = "UPDATE BookLog SET bookStatus = 'RETURNED', returnDate = ? WHERE uid = ? AND bookName = ? AND ISBNcode = ? AND bookStatus = 'ISSUED'";

        try (PreparedStatement logStmt = con.prepareStatement(logQuery)) {
            logStmt.setString(1, returnDate);
            logStmt.setString(2, uid);
            logStmt.setString(3, bookName);
            logStmt.setString(4, isbnCode);

            int rowsUpdated = logStmt.executeUpdate();
            if (rowsUpdated > 0) {
                // Update BookInventory table
                String updateQuery = "UPDATE BookInventory SET quantity = quantity + 1, status = 'AVAILABLE' WHERE ISBNcode = ?";
                try (PreparedStatement updateStmt = con.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, isbnCode);
                    updateStmt.executeUpdate();
                }
                System.out.println("Book Returned Successfully.");
            } else {
                System.out.println("No matching book found or the book is already returned.");
            }
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR");
            e.printStackTrace();
        }
    }
}
