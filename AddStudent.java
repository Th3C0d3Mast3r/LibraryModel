package LibraryModel;
import java.sql.*;
import java.util.*;

public class AddStudent
{
    static Scanner obj = new Scanner(System.in);
    static Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CollegeLibrary", "root", "qwertyuiop1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addStudentData() {
        System.out.println("WELCOME LIBRARY ADMIN\n\n");
        System.out.print("Enter Student UID: ");
        String uids = obj.nextLine();

        try {
            if (uidExists(uids)) {
                System.out.println("UID already exists");
                addStudentData(); // Recursively call the method again
                return; // Exit the current method to avoid further execution
            }
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR");
            e.printStackTrace();
            return;
        }

        System.out.print("Enter Student Name: ");
        String studName = obj.nextLine();

        System.out.print("Enter Student Mail-Id: ");
        String mailId = obj.nextLine();

        System.out.print("Enter Student's Password: ");
        String passcode = obj.nextLine();

        String query = "INSERT INTO StudentLog (uid, studentName, studentMail, passwords) VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, uids);
            stmt.setString(2, studName);
            stmt.setString(3, mailId);
            stmt.setString(4, passcode);

            stmt.executeUpdate();

            System.out.println("\n\nENTRY SUCCESSFUL\n\n");
        } catch (SQLException e) {
            System.out.println("DATABASE ERROR");
            e.printStackTrace();
        }
    }

    private static boolean uidExists(String uid) throws SQLException {
        String query = "SELECT 1 FROM StudentLog WHERE uid = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, uid);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // If a result is returned, the UID exists
            }
        }
    }
}
