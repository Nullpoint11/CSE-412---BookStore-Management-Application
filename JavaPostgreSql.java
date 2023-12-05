package com.example.phase3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JavaPostgreSql {

    private static final String url = "jdbc:postgresql://localhost:5432/BookStore";
    private static final String user = "postgres";
    private static final String password = "1234";

    // Method to get the maximum user ID
    public static int getMaxUserId() {
        String query = "SELECT MAX(user_id) FROM users";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    private static int getMaxBranchId() {
        String query = "SELECT MAX(branch_id) FROM bookstorebranches";
        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
        return 0;
    }

    // Method to write user data into the database
    public static void writeToDatabase(int userId, String userName, String userPhnno, String userEmail) {
        String query = "INSERT INTO users(user_id, name, phn_no, email_id) VALUES(?, ?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {
            pst.setInt(1, userId);
            pst.setString(2, userName);
            pst.setString(3, userPhnno);
            pst.setString(4, userEmail);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(JavaPostgreSql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void createReservation(String copyId, String userId, String reservationId) {
        String query = "INSERT INTO Reserves(copy_id, user_id, reservation_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, Integer.parseInt(copyId));
            pstmt.setInt(2, Integer.parseInt(userId));
            pstmt.setInt(3, Integer.parseInt(reservationId));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void insertBookBranchRelationships(int branchId, String isbn, int copyId) {
        String query = "INSERT INTO Has(branch_id, isbn, copy_id) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, branchId);
            pstmt.setString(2, isbn);
            pstmt.setInt(3, copyId);

            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertBook(int copyId, String isbn, String name, String author, int publishedYear, int copiesAvailable) throws SQLException {
        // First, create a new book copy
        String insertBookCopySql = "INSERT INTO BookCopies (copy_id, availability_status) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(insertBookCopySql)) {
            pstmt.setInt(1, copyId);
            String availabilityStatus = "Available";
            pstmt.setString(2, availabilityStatus);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Then, create a new book referencing the new book copy
        String insertBookSql = "INSERT INTO Book (isbn, copy_id, name, author, published_year, copies_available) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(insertBookSql)) {
            pstmt.setString(1, isbn);
            pstmt.setInt(2, copyId);
            pstmt.setString(3, name);
            pstmt.setString(4, author);
            pstmt.setInt(5, publishedYear);
            pstmt.setInt(6, copiesAvailable);
            pstmt.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateBookCopyStatus(int copyId, String newStatus) {
        String query = "UPDATE BookCopies SET availability_status = ? WHERE copy_id = ?;";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newStatus);
            pstmt.setInt(2, copyId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Reservation> showReservations(int userId) {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reserves WHERE user_id = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, userId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    // Assuming the Reservation class and the structure of the reserves table
                    reservations.add(new Reservation(
                            rs.getInt("reservation_id"),
                            rs.getInt("user_id"),
                            rs.getInt("copy_id")
                    ));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return reservations;
    }

    public static boolean authenticateUser(String userEmail, String userPhone) {
        String query = "SELECT COUNT(*) FROM users WHERE email_id = ? AND phn_no = ?";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setString(1, userEmail);
            pst.setString(2, userPhone);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public static void insertBookstoreBranch(String branchName, String branchAddress) {
        String query = "INSERT INTO bookstorebranches (branch_id, name, address) VALUES (?, ?, ?)";
        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            int branchId = getMaxBranchId() + 1;

            pst.setInt(1, branchId);
            pst.setString(2, branchName);
            pst.setString(3, branchAddress);
            pst.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static List<Book> searchBooks(String bookName, String author, String isbn) {
        List<Book> books = new ArrayList<>();
        ArrayList<String> criteria = new ArrayList<>();
        ArrayList<String> parameters = new ArrayList<>();

        // Add criteria only if the respective field is not empty
        if (!bookName.isEmpty()) {
            criteria.add("name LIKE ?");
            parameters.add("%" + bookName + "%");
        }
        if (!author.isEmpty()) {
            criteria.add("author LIKE ?");
            parameters.add("%" + author + "%");
        }
        if (!isbn.isEmpty()) {
            criteria.add("isbn LIKE ?");
            parameters.add("%" + isbn + "%");
        }

        // If no criteria were added, return an empty list (or all books, depending on the requirements)
        if (criteria.isEmpty()) {
            return books; // Or modify if you want to return all books when no criteria are provided
        }

        String query = "SELECT * FROM book WHERE " + String.join(" AND ", criteria);

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            // Set the parameters for the prepared statement
            for (int i = 0; i < parameters.size(); i++) {
                pst.setString(i + 1, parameters.get(i));
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("isbn"),
                        rs.getInt("copy_id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("published_year"),
                        rs.getInt("copies_available")
                ));
            }
        } catch (SQLException ex) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    public static List<Book> getBooksAtBranch(int branchId) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.* FROM book b INNER JOIN has h ON b.isbn = h.isbn WHERE h.branch_id = ?";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query)) {

            pst.setInt(1, branchId);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    books.add(new Book(
                            rs.getString("isbn"),
                            rs.getInt("copy_id"), // Assuming copy_id is part of the book table
                            rs.getString("name"),
                            rs.getString("author"),
                            rs.getInt("published_year"),
                            rs.getInt("copies_available")
                    ));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    public void deleteUser(int userId) {
        // Delete reservations for user
        String deleteReservationsQuery = "DELETE FROM reserves WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(deleteReservationsQuery)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, e);
        }

        String query = "DELETE FROM users WHERE user_id = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static List<Book> getBooks() {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM book";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                books.add(new Book(
                        rs.getString("isbn"),
                        rs.getInt("copy_id"),
                        rs.getString("name"),
                        rs.getString("author"),
                        rs.getInt("published_year"),
                        rs.getInt("copies_available")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return books;
    }

    // Method to retrieve book copies
    public static List<BookCopy> getBookCopies() {
        List<BookCopy> copies = new ArrayList<>();
        String query = "SELECT * FROM bookcopies";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                copies.add(new BookCopy(
                        rs.getInt("copy_id"),
                        rs.getString("availability_status")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return copies;
    }

    // Method to retrieve bookstore branches
    public static List<BookstoreBranch> getBookstoreBranches() {
        List<BookstoreBranch> branches = new ArrayList<>();
        String query = "SELECT * FROM bookstorebranches";

        try (Connection con = DriverManager.getConnection(url, user, password);
             PreparedStatement pst = con.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                branches.add(new BookstoreBranch(
                        rs.getInt("branch_id"),
                        rs.getString("name"),
                        rs.getString("address")
                ));
            }

        } catch (SQLException ex) {
            Logger.getLogger(JavaPostgreSql.class.getName()).log(Level.SEVERE, null, ex);
        }
        return branches;
    }
}