package LibraryModel;
import java.sql.*;
import java.util.*;

public class Main {
    static Scanner obj = new Scanner(System.in);
    static Connection con;

    static {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CollegeLibrary", "root", "qwertyuiop1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        run();
    }

    private static void run() {
        System.out.println("\t\t\tWELCOME TO COLLEGE LIBRARY");
        System.out.println("\nEnter Your Credentials to Log In");

        System.out.print("Enter Student UID: ");
        String uid = obj.nextLine();

        if(uid.equals("LibraryAdmin")) //if someone types LibraryAdmin; add a student in db
        {
            AddStudent.addStudentData();
            run();
        }

        if(uid.equalsIgnoreCase("exit") || uid.equalsIgnoreCase("terminate") || uid.equalsIgnoreCase("end"))
        {
            System.exit(0);
        }

        if (!isOnlyDigits(uid)) {
            System.out.println("UID should be NUMERIC");
            System.out.println("Re-Enter a Valid UID");
            run(); // Correctly call the run method again to re-prompt for the UID
            return; // Ensure the current flow is terminated to prevent further execution
        }

        System.out.print("Enter Student Name: ");
        String stuName = obj.nextLine();

        System.out.print("Enter Login Password: ");
        String stuPassword = obj.nextLine();

        try {
            if (loginAuthenticator(uid, stuName, stuPassword)) {
                System.out.println("\nLOGIN SUCCESSFUL");
                libIntermediary(uid);
            } else {
                System.out.println("Intruder Alert - Exiting Library");
                System.exit(0);
            }
        } catch (SQLException e) {
            System.out.println("Some Database Error Occurred");
            System.out.println("Try Again after Some Time");
            System.exit(0);
        }
    }

    private static boolean loginAuthenticator(String uid, String name, String password) throws SQLException {
        String query = "SELECT * FROM StudentLog WHERE uid = ? AND studentName = ? AND passwords = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, uid);
            stmt.setString(2, name);
            stmt.setString(3, password);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static void libIntermediary(String uid) throws SQLException {
        int attempts = 0;
        while (attempts < 3) {
            System.out.println("What Action Do You Wish to Perform?");
            System.out.println("1) VIEW BOOKS\n2) LEND BOOK\n3) RETURN BOOK\n4) EXIT");
            String reply = obj.nextLine();

            if (reply.equals("1") || reply.contains("iew") || reply.equalsIgnoreCase("view")) {
                ViewBooks.viewInventory();
                libIntermediary(uid);
                return;
            } else if (reply.equals("2") || reply.contains("end") || reply.equalsIgnoreCase("lend")) {
                LendBook.lendBook(uid);
                libIntermediary(uid);
            } else if (reply.equals("3") || reply.contains("eturn") || reply.equalsIgnoreCase("return")) {
                ReturnBook.returnBook(uid);
                libIntermediary(uid);
                return;
            } else if (reply.equals("4") || reply.contains("it") || reply.equalsIgnoreCase("exit")) {
                cashOut();
                System.exit(0);
            } else {
                attempts++;
                if (attempts < 3) {
                    System.out.println("Wrong Choice - Retry");
                } else {
                    System.out.println("Too many wrong attempts. Exiting Code.");
                    System.exit(0);
                }
            }
        }
    }

    private static void cashOut() {
        System.out.println("KINDLY PAY THE DUE CHARGES TO THE LIBRARY");
        System.out.println("HAPPY READING!");
        System.exit(0);
    }

    private static boolean isOnlyDigits(String st) {
        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }
}
