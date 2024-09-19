package Controllers;

import Models.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import Models.User;
import Models.Book;

import java.sql.SQLException;
import java.util.ArrayList;

public class IssueBookController {
    //for getting the arraylists made in controllers
    private NewUserController userController = new NewUserController();
    private NewBookController bookController = new NewBookController();
    @FXML
    TextField txtBookSearch;

    @FXML
    ListView<String> lstBooks;

    @FXML
    TextField txtUserSearch;

    @FXML
    ListView<String> lstUsers;

    @FXML
    TextField txtBookID;

    @FXML
    TextField txtUserID;

    @FXML
    Button btnIssueBook;

    @FXML
    Label lblMessage;

    Library library;
    public void initData(Library library) throws SQLException {
        this.library = library;
        library.getLog();
        //populate listviews with user and book data after assigning the library object
        populateUserListView();
        populateBookListView();
    }


    //initialize will add some event listeners for my listviews and textfields. These event listeners are created upon initialization
    @FXML
    void initialize() {

        //add event listeners for book search and user search text fields
        txtBookSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchBooks(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
        txtUserSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                searchUsers(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //add event listener for book list view
        lstBooks.setOnMouseClicked(event -> {
            String selectedBook = lstBooks.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                //extract the ID number from the selectedBook string
                String[] parts = selectedBook.split(" ");
                if (parts.length > 1) {
                    String id = parts[1].trim();
                    //set the ID in the txtBookID textbox
                    txtBookID.setText(id);
                }
            }
        });

        //add event listener for user list view
        lstUsers.setOnMouseClicked(event -> {
            String selectedUser = lstUsers.getSelectionModel().getSelectedItem();
            if (selectedUser != null) {
                //extract the ID number from the selectedUser string
                String[] parts = selectedUser.split(" ");
                if (parts.length > 1) {
                    String id = parts[1].trim();
                    //set the ID in the txtUserID textbox
                    txtUserID.setText(id);
                }
            }
        });
    }

    //this will make an array out of the toString's of the users array in library then populate the listview with the toStrings of these user objects
    private void populateUserListView() throws SQLException {
        ArrayList<User> userList = library.userDAO.getAllUsers();
        ObservableList<String> userStrings = FXCollections.observableArrayList();

        for (User user : userList) {
            userStrings.add(user.toString());
        }

        lstUsers.setItems(userStrings);
    }

    //this will make an array out of the toString's of the books array in library then populate the listview with the toStrings of these book objects
    private void populateBookListView() throws SQLException {
        ArrayList<Book> bookList = library.bookDAO.getAllBooks();
        ObservableList<String> bookStrings = FXCollections.observableArrayList();

        for (Book book : bookList) {
            bookStrings.add(book.toString());
        }

        lstBooks.setItems(bookStrings);
    }

    //this is the method for searching books, it will takes the list of books from library and then see if the search query is contained in
    //any of the books in the arraylist, then populates the listview with the matches.
    private void searchBooks(String query) throws SQLException {
        ArrayList<Book> bookList = library.bookDAO.getAllBooks();
        ObservableList<String> bookStrings = FXCollections.observableArrayList();

        for (Book book : bookList) {
            //check if the book title contains the query
            if (book.getName().toLowerCase().contains(query.toLowerCase())) {
                bookStrings.add(book.toString());
            }
        }

        lstBooks.setItems(bookStrings);
    }

    //this is the method for searching users, it will takes the list of users from library and then see if the search query is contained in
    //any of the users in the arraylist, then populates the listview with the matches.
    private void searchUsers(String query) throws SQLException {
        ArrayList<User> userList = library.userDAO.getAllUsers();
        ObservableList<String> userStrings = FXCollections.observableArrayList();

        for (User user : userList) {
            //check if the username contains the query
            if (user.getName().toLowerCase().contains(query.toLowerCase())) {
                userStrings.add(user.toString());
            }
        }

        lstUsers.setItems(userStrings);
    }

    //this creates a new transaction to add to the transactions arraylist. Retrieves the book and user ID's from the textbox that automatically
    //populates when items are clicked in the listviews
    @FXML
    void handleIssueBook(ActionEvent event) throws SQLException {
        String bookIDText = txtBookID.getText();
        String userIDText = txtUserID.getText();

        //check if both book ID and user ID are provided
        if (!bookIDText.isEmpty() && !userIDText.isEmpty()) {
            int bookID = Integer.parseInt(bookIDText);
            int userID = Integer.parseInt(userIDText);

            //issue the book
            boolean success = library.issueBook(userID, bookID);

            if (success) {
                //book issued successfully
                populateBookListView(); //update book list view
                populateUserListView(); //update user list view
                lblMessage.setText("Book issued successfully.");
            } else {
                //display error message, and add the last added string to the message log to explain the failure to issue book
                lblMessage.setText("Failed to issue the book. " + library.msgLog.get(library.msgLog.size() - 1));
            }
        } else {
            //display error message if either book ID or user ID is not provided
            lblMessage.setText("Please select a book and a user before issuing.");
        }
    }
}
