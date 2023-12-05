package com.example.phase3;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.List;

public class Controller {

    // Fields for user inputs
    public TextField nameField;
    public TextField phoneNoField;
    public TextField emailIdField;
    public TextField branchNameField;
    public TextField branchAddressField;

    // Method to handle user registration
    public void handleUserRegistration(ActionEvent event) {
        String name = nameField.getText();
        String phone = phoneNoField.getText();
        String email = emailIdField.getText();

        // Generate user_id automatically
        int userId = JavaPostgreSql.getMaxUserId() + 1;

        // Use the writeToDatabase method with the generated user_id
        JavaPostgreSql.writeToDatabase(userId, name, phone, email);
    }

    public void handleStoreInsertion(ActionEvent event) {
        String branchName = branchNameField.getText();
        String branchAddress = branchAddressField.getText();

        // Call the method in JavaPostgreSql to insert the new branch
        JavaPostgreSql.insertBookstoreBranch(branchName, branchAddress);
    }

    // Method to handle book search
    public List<Book> handleBookSearch(String bookName, String author, String isbn) {
        return JavaPostgreSql.searchBooks(bookName, author, isbn);
    }

    public List<Book> handleBooksAtBranch(int branchId) {
        return JavaPostgreSql.getBooksAtBranch(branchId);
    }

    public void addReservation(String copyId, String userId) {
        JavaPostgreSql db = new JavaPostgreSql();
        db.createReservation(copyId, userId, Integer.toString(generateUniqueReservationId()));
    }

    public void handleInsertBookBranchRelationships(int branchId, String isbn, int copyId) {
        JavaPostgreSql db = new JavaPostgreSql();
        db.insertBookBranchRelationships(branchId, isbn, copyId);
    }

    public List<Reservation> handleShowReservations(int userId) {
        return JavaPostgreSql.showReservations(userId);
    }

    public void handleBookInsertion(String isbn, int copyId, String name, String author, int publishedYear, int copiesAvailable) throws SQLException {
        JavaPostgreSql db = new JavaPostgreSql();
        db.insertBook(copyId, isbn, name, author, publishedYear, copiesAvailable);
    }

    // Method to generate a unique reservation ID
    private int generateUniqueReservationId() {
        // Simple implementation - consider a more robust approach for production
        return (int) (Math.random() * 1000000);
    }

    public void handleUpdateBookCopyStatus(int copyId, String newStatus) {
        JavaPostgreSql db = new JavaPostgreSql();
        db.updateBookCopyStatus(copyId, newStatus);
    }

    public void handleDeleteUser(int userId) {
        JavaPostgreSql db = new JavaPostgreSql();
        db.deleteUser(userId);
    }

    public boolean authenticateUser(String email, String phone) {
        return JavaPostgreSql.authenticateUser(email, phone);
    }

}