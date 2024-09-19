package Controllers;

import Models.Book;
import Models.Library;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReturnBookController {

    //GUI elements
    @FXML
    private Button btnReturnBook;
    @FXML
    private ListView<String> lstBookReturn;
    @FXML
    private TextField txtBookID;
    @FXML
    private TextField txtBookSearch;
    @FXML
    private Label lblMessage;

    //same library object
    Library library;
    public void initData(Library library) throws SQLException {
        this.library = library;
        library.getLog();
        //populate book list view with all books initially, this will avoid a null pointer exception
        populateBookListView();
    }

    //method to initialize event listeners for the textboxes for searching books and users, also adds event listener for the listview,
    //listviews when something is selected, it gets assigned to a variable and that variable then extracts the id by parsing the toString
    @FXML
    void initialize() {
        //listener for the book search text field
        txtBookSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                library.bookDAO.getByQuery(newValue);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        //listener for selecting a book from the list view
        lstBookReturn.setOnMouseClicked(event -> {
            String selectedBook = lstBookReturn.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                //extracting the book ID from the selected book string
                String[] parts = selectedBook.split(" ");
                if (parts.length > 1) {
                    String id = parts[1].trim();
                    //setting the book ID in the text field
                    txtBookID.setText(id);
                }
            }
        });
        //listener for the return book button
        btnReturnBook.setOnAction(event -> {
            try {
                returnBook();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //method to populate the book list view with search results, this makes an array out of books in library and then converts to the toString,
    //before adding to the listview of books
    private void populateBookListView() throws SQLException {
        ArrayList<Book> bookList = library.bookDAO.getAllBooks();
        ObservableList<String> bookStrings = FXCollections.observableArrayList();

        for (Book book : bookList) {
            bookStrings.add(book.toString());
        }

        lstBookReturn.setItems(bookStrings);
    }


    //method to search for books based on the query, converts array to the toStrings and then adds to listview of users
    private void searchBooks(String query) throws SQLException {
        ArrayList<Book> bookList = library.bookDAO.getAllBooks();
        ObservableList<String> bookStrings = FXCollections.observableArrayList();

        for (Book book : bookList) {
            //check if the book title contains the query
            if (book.getName().toLowerCase().contains(query.toLowerCase())) {
                bookStrings.add(book.toString());
            }
        }

        lstBookReturn.setItems(bookStrings);
    }


    //method to handle returning a book, this is associated with the button on the GUI. Takes the ID and will call the library's return book method,
    //then based on that method's outcome, a message gets logged and I then extract it from the most recent addition to the msgLog, and display
    //to the user, so they know why there was a failure.
    private void returnBook() throws SQLException {
        String bookIdText = txtBookID.getText();
        if (!bookIdText.isEmpty()) {
            int bookID = Integer.parseInt(bookIdText);
            //attempt to return the book
            if (library.returnBook(bookID)) {
                //book returned successfully, display success message
                lblMessage.setText("Success. " + library.msgLog.get(library.msgLog.size() - 1));
            } else {
                //error occurred while returning book, display error message
                lblMessage.setText("Failed. " + library.msgLog.get(library.msgLog.size() - 1));
            }
        } else {
            //no book selected, display error message
            lblMessage.setText("Please select a book to return.");
        }
    }

}
