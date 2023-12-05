package com.example.phase3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    private Controller controller = new Controller();

    @Override
    public void start(Stage primaryStage) {
        showUserOrAdminScene(primaryStage);
    }

    private void showUserOrAdminScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;");

        Label title = new Label("Bookstore Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label header = new Label("User or Admin?");
        header.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Button userBtn = new Button("User");
        userBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");
        userBtn.setOnAction(e -> showNewExistingUserScene(primaryStage));

        Button adminBtn = new Button("Admin");
        adminBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");
        adminBtn.setOnAction(e -> showAdminLoginScene(primaryStage));

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(userBtn, adminBtn);

        root.getChildren().addAll(title, header, buttonContainer);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Bookstore Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showNewExistingUserScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;");

        Label title = new Label("Bookstore Management System");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label header = new Label("New User or Existing User?");
        header.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Button newUserBtn = new Button("New User");
        newUserBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");
        newUserBtn.setOnAction(e -> showRegistrationScene(primaryStage));

        Button existingUserBtn = new Button("Existing User");
        existingUserBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");
        existingUserBtn.setOnAction(e -> showUserLoginScene(primaryStage));

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.getChildren().addAll(newUserBtn, existingUserBtn);

        root.getChildren().addAll(title, header, buttonContainer);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("New or Existing User");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showRegistrationScene(Stage primaryStage) {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;");

        Label header = new Label("User Registration");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        root.getChildren().add(header);

        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-text-fill: black;");
        nameLabel.setStyle("-fx-font-size: 18px;");

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone Number");
        phoneField.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Label phoneLabel = new Label("Phone Number");
        phoneLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-text-fill: black; -fx-font-size: 18px;");

        HBox nameContainer = new HBox(10);
        nameContainer.setAlignment(Pos.CENTER);
        nameContainer.getChildren().addAll(nameLabel, nameField);

        HBox phoneContainer = new HBox(10);
        phoneContainer.setAlignment(Pos.CENTER);
        phoneContainer.getChildren().addAll(phoneLabel, phoneField);

        HBox emailContainer = new HBox(10);
        emailContainer.setAlignment(Pos.CENTER);
        emailContainer.getChildren().addAll(emailLabel, emailField);

        root.getChildren().addAll(nameContainer, phoneContainer, emailContainer);

        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-font-size: 24px;");
        submitBtn.setOnAction(e -> {
            controller.nameField = nameField;
            controller.phoneNoField = phoneField;
            controller.emailIdField = emailField;
            controller.handleUserRegistration(e);
            showUserMainMenuScene(primaryStage);
        });

        root.getChildren().add(submitBtn);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("Create a User Account");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showUserLoginScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Set the background color to grey

        Label header = new Label("Existing User Login");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label emailLabel = new Label("Email");
        emailLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Optional: Set the font size for labels
        TextField emailField = new TextField();

        Label phoneLabel = new Label("Phone");
        phoneLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Optional: Set the font size for labels
        TextField phoneField = new TextField();

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set the font size
        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String phone = phoneField.getText();

            boolean isAuthenticated = controller.authenticateUser(email, phone);

            if (isAuthenticated) {
                showUserMainMenuScene(primaryStage);
            }
        });

        HBox emailContainer = new HBox(10);
        emailContainer.setAlignment(Pos.CENTER); // Align items to center
        emailContainer.getChildren().addAll(emailLabel, emailField);

        HBox phoneContainer = new HBox(10);
        phoneContainer.setAlignment(Pos.CENTER); // Align items to center
        phoneContainer.getChildren().addAll(phoneLabel, phoneField);

        root.getChildren().addAll(
                header,
                emailContainer,
                phoneContainer,
                loginBtn
        );

        Scene scene = new Scene(root, 800, 600); // Match the size of the other scene

        primaryStage.setTitle("User Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showUserMainMenuScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Set the background color to grey

        Label header = new Label("User Main Menu");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); // Add a header with styling

        Button searchBtn = new Button("Search Books");
        searchBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black; "); // Set the font size
        searchBtn.setOnAction(e -> showSearchScene(primaryStage));

        Button reservationsBtn = new Button("View Reservations");
        reservationsBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black; "); // Set the font size
        reservationsBtn.setOnAction(e -> showExistingReservationsScene(primaryStage));

        Button reserveBook = new Button("Reserve a Book");
        reserveBook.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set the font size
        reserveBook.setOnAction(e -> showReserveBookScene(primaryStage));

        root.getChildren().addAll(
                header,
                searchBtn,
                reservationsBtn,
                reserveBook
        );

        Scene scene = new Scene(root, 800, 600); // Match the size of the other scene

        primaryStage.setTitle("Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showReserveBookScene(Stage primaryStage) {

        Label copyidLabel = new Label("Enter Copy ID");
        copyidLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        // Text fields for user input
        TextField copyIdField = new TextField();

        HBox copyIdHBox = new HBox(10);
        copyIdHBox.getChildren().addAll(copyidLabel, copyIdField);

        Label useridLabel = new Label("Enter User ID");
        useridLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        TextField userIdField = new TextField();

        HBox userIdHBox = new HBox(10);
        userIdHBox.getChildren().addAll(useridLabel, userIdField);
        copyIdHBox.setAlignment(Pos.CENTER);

        // Button to submit reservation
        Button reserveButton = new Button("Reserve");
        reserveButton.setStyle("-fx-font-size: 18px;"); // Set font size to match the theme

        // Setting the action for the reserve button
        reserveButton.setOnAction(e -> {
            String copyId = copyIdField.getText();
            String userId = userIdField.getText();
            controller.addReservation(copyId, userId);

            // Clear the fields after reservation
            copyIdField.clear();
            userIdField.clear();

            showReserveSuccessScene(primaryStage);
        });

        // Label for title
        Label title = new Label("Reserve a Book");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // VBox layout with alignment and style
        VBox layout = new VBox(10); // 10 is the spacing between elements
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: grey;"); // Set background color

        copyIdHBox.setAlignment(Pos.CENTER);
        userIdHBox.setAlignment(Pos.CENTER);
        // Add components to the layout
        layout.getChildren().addAll(title, copyIdHBox, userIdHBox, reserveButton);

        // Setting the scene
        Scene scene = new Scene(layout, 800, 600); // Adjust size to match the theme
        primaryStage.setTitle("Bookstore - Reserve Book");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showReserveSuccessScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;");

        Label successLabel = new Label("Reservation Created Successfully!");
        successLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: black; -fx-font-weight: bold;"); // Set font size to match the theme

        Button continueBtn = new Button("Continue");
        continueBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font size to match the theme
        continueBtn.setOnAction(e -> {
            showUserMainMenuScene(primaryStage);
        });

        root.getChildren().addAll(successLabel, continueBtn);

        Scene scene = new Scene(root, 800, 600); // Adjust the size to match the theme
        primaryStage.setTitle("Reservation Success");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showSearchScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Set background color

        Label searchHeader = new Label("Book Search");
        searchHeader.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField bookNameField = new TextField();
        bookNameField.setPromptText("Book Name");
        Label bookNameLabel = new Label("Book Name");
        bookNameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        HBox bookNameHbox = new HBox(10);
        bookNameHbox.getChildren().addAll(bookNameLabel, bookNameField);
        bookNameHbox.setAlignment(Pos.CENTER);

        TextField authorField = new TextField();
        authorField.setPromptText("Author");
        Label authorLabel = new Label("Author");
        authorLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for label

        HBox autNameHbox = new HBox(10);
        autNameHbox.getChildren().addAll(authorLabel, authorField);
        autNameHbox.setAlignment(Pos.CENTER);

        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN Number");
        Label isbnLabel = new Label("ISBN Number");
        isbnLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for label

        HBox isbNameHbox = new HBox(10);
        isbNameHbox.getChildren().addAll(isbnLabel, isbnField);
        isbNameHbox.setAlignment(Pos.CENTER);

        Button searchBtn = new Button("Search");
        searchBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button
        ListView<String> resultsView = new ListView<>();

        searchBtn.setOnAction(e -> {
            String bookName = bookNameField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            List<Book> results = controller.handleBookSearch(bookName, author, isbn);

            resultsView.getItems().clear();

            for (Book book : results) {
                resultsView.getItems().add(book.toString());
            }
        });

        root.getChildren().addAll(searchHeader, bookNameHbox, autNameHbox, isbNameHbox, searchBtn, resultsView);

        Scene scene = new Scene(root, 800, 600); // Adjust size to match the first scene
        primaryStage.setTitle("Search Books");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showExistingReservationsScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Set background color

        Label reservationsLabel = new Label("Reservations");
        reservationsLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); // Set font style for header

        Label userIdLabel = new Label("User ID");
        userIdLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for label

        TextField userIdField = new TextField();

        Button showReservationsBtn = new Button("Show Reservations");
        showReservationsBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button

        ListView<String> reservationsView = new ListView<>();

        HBox userIdHBox = new HBox(10);
        userIdHBox.getChildren().addAll(userIdLabel, userIdField);
        userIdHBox.setAlignment(Pos.CENTER);

        showReservationsBtn.setOnAction(e -> {
            int userId = Integer.parseInt(userIdField.getText());
            List<Reservation> reservations = controller.handleShowReservations(userId);
            reservationsView.getItems().clear();
            for (Reservation reservation : reservations) {
                reservationsView.getItems().add(reservation.toString());
            }
        });

        root.getChildren().addAll(reservationsLabel, userIdHBox, showReservationsBtn);


        if (!root.getChildren().contains(reservationsView)) {
            root.getChildren().add(reservationsView); // Add only if not already added
        }

        Scene scene = new Scene(root, 800, 600); // Adjust size to match the first scene
        primaryStage.setTitle("Reservations");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Admin Scenes
    private void showAdminLoginScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Set background color

        Label header = new Label("Admin Login");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); // Set font style for header

        Label userIdLabel = new Label("User ID");
        userIdLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for label
        TextField userIdField = new TextField();

        Label passwordLabel = new Label("Password");
        passwordLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for label
        TextField passwordField = new TextField();

        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button

        loginBtn.setOnAction(e -> {
            String userId = userIdField.getText();
            String password = passwordField.getText();

            if (userId.equals("tester") && password.equals("1234")) {
                showAdminMainMenuScene(primaryStage);
            } else {
                Label errorLabel = new Label("Incorrect User ID or Password");
                errorLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: red;"); // Style for error message
                root.getChildren().add(errorLabel);
            }
        });

        HBox userIdContainer = new HBox(10);
        userIdContainer.setAlignment(Pos.CENTER); // Center alignment
        userIdContainer.getChildren().addAll(userIdLabel, userIdField);

        HBox passwordContainer = new HBox(10);
        passwordContainer.setAlignment(Pos.CENTER); // Center alignment
        passwordContainer.getChildren().addAll(passwordLabel, passwordField);

        root.getChildren().addAll(
                header,
                userIdContainer,
                passwordContainer,
                loginBtn
        );

        Scene scene = new Scene(root, 800, 600); // Adjust size to match the first scene
        primaryStage.setTitle("Admin Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAdminMainMenuScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Set background color

        // Creating buttons and setting their font styles
        Button insertStoreBtn = new Button("Insert New Store");
        insertStoreBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button
        insertStoreBtn.setOnAction(e -> showInsertStoreScene(primaryStage));

        Button updateStoreBtn = new Button("Update Books at Store");
        updateStoreBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button
        updateStoreBtn.setOnAction(e -> showStoreHasBookScene(primaryStage));

        Button showStoreBooksBtn = new Button("Show Books at a Store");
        showStoreBooksBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button
        showStoreBooksBtn.setOnAction(e -> showBooksAtBranch(primaryStage));

        Button insertBookBtn = new Button("Insert New Book");
        insertBookBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button
        insertBookBtn.setOnAction(e -> showInsertBookScene(primaryStage));

        Button updateBookCopiesBtn = new Button("Update Book Copies");
        updateBookCopiesBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button
        updateBookCopiesBtn.setOnAction(e -> showUpdateBookCopyScene(primaryStage));

        Button deleteUserBtn = new Button("Delete User");
        deleteUserBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button
        deleteUserBtn.setOnAction(e -> showDeleteUserScene(primaryStage));

        // Adding buttons to the root
        root.getChildren().addAll(
                insertStoreBtn,
                updateStoreBtn,
                showStoreBooksBtn,
                insertBookBtn,
                updateBookCopiesBtn,
                deleteUserBtn
        );

        Scene scene = new Scene(root, 800, 600); // Adjust size to match the other scenes
        primaryStage.setTitle("Admin Main Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showStoreHasBookScene(Stage primaryStage) {

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;");

        Label header = new Label("Update Book Copy at Store");
        header.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label branchIdLabel = new Label("Branch ID");
        branchIdLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        TextField branchIdField = new TextField();

        Label isbnLabel = new Label("ISBN");
        isbnLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        TextField isbnField = new TextField();

        Label copyIdLabel = new Label("Copy ID");
        copyIdLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        TextField copyIdField = new TextField();

        Button updateBtn = new Button("Update");
        updateBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        updateBtn.setOnAction(e -> {
            int branchId = Integer.parseInt(branchIdField.getText());
            String isbn = isbnField.getText();
            int copyId = Integer.parseInt(copyIdField.getText());

            controller.handleInsertBookBranchRelationships(branchId, isbn, copyId);

            showAdminSuccessScene(primaryStage);
        });

        HBox branchIdBox = new HBox(10);
        branchIdBox.setAlignment(Pos.CENTER);
        branchIdBox.getChildren().addAll(branchIdLabel, branchIdField);

        HBox isbnBox = new HBox(10);
        isbnBox.setAlignment(Pos.CENTER);
        isbnBox.getChildren().addAll(isbnLabel, isbnField);

        HBox copyIdBox = new HBox(10);
        copyIdBox.setAlignment(Pos.CENTER);
        copyIdBox.getChildren().addAll(copyIdLabel, copyIdField);

        root.getChildren().addAll(
                header,
                branchIdBox,
                isbnBox,
                copyIdBox,
                updateBtn
        );

        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Update Book Copy at Store");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showDeleteUserScene(Stage primaryStage) {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: grey;"); // Set background color

        Label label = new Label("Enter User ID:");
        label.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for label

        TextField userIdField = new TextField();
        userIdField.setPromptText("User ID");

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button
        deleteButton.setOnAction(e -> {
            int userId = Integer.parseInt(userIdField.getText());

            controller.handleDeleteUser(userId);

            showAdminSuccessScene(primaryStage);
        });

        layout.getChildren().addAll(label, userIdField, deleteButton);

        Scene scene = new Scene(layout, 800, 600); // Adjust size to match the first scene
        primaryStage.setTitle("Delete User");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showUpdateBookCopyScene(Stage primaryStage) {
        VBox root = new VBox(10); // Use VBox for consistent layout
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Set the grey background color

        Label titleLabel = new Label("Update Book Copy Status");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); // Set title style

        HBox copyIdBox = new HBox(10);
        Label copyIdLabel = new Label("Copy ID:");
        copyIdLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style
        TextField copyIdField = new TextField();
        copyIdBox.getChildren().addAll(copyIdLabel, copyIdField);

        HBox newStatusBox = new HBox(10);
        Label newStatusLabel = new Label("New Status:");
        newStatusLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style
        TextField newStatusField = new TextField();
        newStatusBox.getChildren().addAll(newStatusLabel, newStatusField);

        Button updateButton = new Button("Update");
        updateButton.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set button font style
        updateButton.setOnAction(e -> {
            int copyId = Integer.parseInt(copyIdField.getText());
            String newStatus = newStatusField.getText();
            controller.handleUpdateBookCopyStatus(copyId, newStatus);
            showAdminSuccessScene(primaryStage);
        });

        root.getChildren().addAll(titleLabel, copyIdBox, newStatusBox, updateButton);

        Scene scene = new Scene(root, 800, 600); // Match the scene size with showUserOrAdminScene
        primaryStage.setTitle("Update Book Copy Status");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showInsertBookScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Set background color

        // Create labels and text fields
        Label isbnLabel = new Label("ISBN");
        TextField isbnField = new TextField();
        isbnLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Label copyIdLabel = new Label("Copy ID");
        TextField copyIdField = new TextField();
        copyIdLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        nameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Label authorLabel = new Label("Author");
        TextField authorField = new TextField();
        authorLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Label publishedYearLabel = new Label("Published Year");
        TextField publishedYearField = new TextField();
        publishedYearLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        Label copiesAvailableLabel = new Label("Copies Available");
        TextField copiesAvailableField = new TextField();
        copiesAvailableLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;");

        // Create and style HBox containers
        HBox isbnContainer = createStyledHBox(isbnLabel, isbnField);
        HBox copyIdContainer = createStyledHBox(copyIdLabel, copyIdField);
        HBox nameContainer = createStyledHBox(nameLabel, nameField);
        HBox authorContainer = createStyledHBox(authorLabel, authorField);
        HBox publishedYearContainer = createStyledHBox(publishedYearLabel, publishedYearField);
        HBox copiesAvailableContainer = createStyledHBox(copiesAvailableLabel, copiesAvailableField);

        // Add containers to root
        root.getChildren().addAll(isbnContainer, copyIdContainer, nameContainer, authorContainer, publishedYearContainer, copiesAvailableContainer);

        // Create and style 'Create' button
        Button createButton = new Button("Create");
        createButton.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set font style for button
        createButton.setOnAction(e -> {
            String isbn = isbnField.getText();
            int copyId = Integer.parseInt(copyIdField.getText());
            String name = nameField.getText();
            String author = authorField.getText();
            int publishedYear = Integer.parseInt(publishedYearField.getText());
            int copiesAvailable = Integer.parseInt(copiesAvailableField.getText());

            try {
                controller.handleBookInsertion(isbn, copyId, name, author, publishedYear, copiesAvailable);
                showAdminSuccessScene(primaryStage);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        root.getChildren().add(createButton);

        Scene scene = new Scene(root, 800, 600); // Set scene size
        primaryStage.setScene(scene);
        primaryStage.setTitle("Insert New Book");
        primaryStage.show();
    }

    private HBox createStyledHBox(Node... children) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(children);
        return hbox;
    }

    private void showInsertStoreScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Apply grey background

        Label header = new Label("Insert New Store");
        header.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Adjust font style for header

        Label nameLabel = new Label("Name");
        nameLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Adjust font style for label
        TextField branchNameField = new TextField();

        Label addressLabel = new Label("Address");
        addressLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Adjust font style for label
        TextField addressField = new TextField();

        Button createStoreBtn = new Button("Create Store");
        createStoreBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Adjust font style for button
        createStoreBtn.setOnAction(e -> {
            controller.branchNameField = branchNameField;
            controller.branchAddressField = addressField;
            controller.handleStoreInsertion(e);
            showAdminSuccessScene(primaryStage);
        });

        HBox nameContainer = new HBox(10);
        nameContainer.setAlignment(Pos.CENTER); // Align HBox
        nameContainer.getChildren().addAll(nameLabel, branchNameField);

        HBox addressContainer = new HBox(10);
        addressContainer.setAlignment(Pos.CENTER); // Align HBox
        addressContainer.getChildren().addAll(addressLabel, addressField);

        root.getChildren().addAll(
                header,
                nameContainer,
                addressContainer,
                createStoreBtn
        );

        Scene scene = new Scene(root, 800, 600); // Adjust size to match the other scene
        primaryStage.setTitle("Insert New Store");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showAdminSuccessScene(Stage primaryStage) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER); // Aligning the VBox to the center
        root.setStyle("-fx-background-color: grey;"); // Setting the background color to grey

        Label successLabel = new Label("Success!!");
        successLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;"); // Setting the font style

        Button continueBtn = new Button("Continue");
        continueBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Setting the font style for the button
        continueBtn.setOnAction(e -> {
            showAdminMainMenuScene(primaryStage);
        });

        root.getChildren().addAll(successLabel, continueBtn);

        Scene scene = new Scene(root, 800, 600); // Adjusting the size of the scene to match the other scenes
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showBooksAtBranch(Stage primaryStage) {

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: grey;"); // Set background color to grey

        Label header = new Label("Books at Store");
        header.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;"); // Set header font style

        Label branchIdLabel = new Label("Branch ID");
        branchIdLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set label font style
        TextField branchIdField = new TextField();

        Button showBtn = new Button("Show");
        showBtn.setStyle("-fx-font-size: 18px; -fx-text-fill: black;"); // Set button font style

        ListView<String> booksListView = new ListView<>();

        showBtn.setOnAction(e -> {
            int branchId = Integer.parseInt(branchIdField.getText());
            List<Book> books = controller.handleBooksAtBranch(branchId);

            booksListView.getItems().clear();

            for (Book book : books) {
                booksListView.getItems().add(book.toString());
            }
        });

        HBox branchIdContainer = new HBox(10);
        branchIdContainer.setAlignment(Pos.CENTER); // Set alignment to center
        branchIdContainer.getChildren().addAll(branchIdLabel, branchIdField);

        root.getChildren().addAll(
                header,
                branchIdContainer,
                showBtn,
                booksListView
        );

        Scene scene = new Scene(root, 800, 600); // Adjusted to match the size of the first scene
        primaryStage.setTitle("Store Books");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}