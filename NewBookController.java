package Controllers;

import Models.Book;
import Models.Library;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

//controller for new book gui
public class NewBookController implements Initializable {

    //controls on new book gui
    @FXML
     Button btnRegisterBook;
    @FXML
     ComboBox<String> cmbGenre;
    @FXML
     ListView<String> lstPublisher;
    @FXML
     TextField txtBookAuthor;
    @FXML
     TextField txtBookName;
    @FXML
     TextField txtBookYear;
    @FXML
     TextField txtISBN;
     String publisher;
     String genre;

    // Library object
    private Library library;

    public void initData(Library library) {
        this.library = library;
        library.getLog();
    }


    //method for when register button is clicked. inputs are taken from the name, year, author, and ISBN fields, checks which genre
    // and publisher is selected and assigns  these values fetched to variables. Then check to make sure all fields are filled,
    // return and send error if incomplete, if they are all complete create new Book object
    //out of variables with field values, then finally send a success message with the name of the new Book that was added to the arraylist
    @FXML
    void handleRegisterNewBook(ActionEvent event) throws SQLException {

        String bookName = txtBookName.getText();
        long bookYear = Long.parseLong(txtBookYear.getText());
        String author = txtBookAuthor.getText();
        String ISBN = txtISBN.getText();

        //validation check for empty fields
        if (bookName.isEmpty() || ISBN.isEmpty() || author.isEmpty() ||genre == null || publisher == null) {
            showAlert("Error", "All fields must be filled");
            return;
        }

        //make new book object based on user entries
        Book newBook = new Book(bookName, author, publisher, genre, ISBN, bookYear);
        //System.out.println(newBook);

        //add the new book to the list
        library.bookDAO.insertBook(newBook);

        //close the Add New User window
        ((Stage) txtBookName.getScene().getWindow()).close();

        //show success message
        showAlert("Registration Successful", "Registered: " + newBook.getName());
    }

    //this function makes an alert message, passing in the title and the message. In cases of error, the alert will have an error message,
    //and in cases of a successful addition, a success message will be sent
    void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    //method to get the selected genre
    public String getSelectedGenre() {
        return genre;
    }

    //method to get the selected publisher
    public String getSelectedPublisher() {
        return publisher;
    }

    //adds listeners to combo box and
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //populate the ComboBox with some genres
        ObservableList<String> genres = FXCollections.observableArrayList("Education", "Adventure", "Thriller", "History");
        cmbGenre.setItems(genres);

        //populate the ListView with some publishers
        ObservableList<String> publishers = FXCollections.observableArrayList("Pearson", "Disney", "Sports Illustrated", "Wikipedia", "New York Times");
        lstPublisher.setItems(publishers);

        //set selection mode for ListView to SINGLE to get single selection
        lstPublisher.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        //set listener for ListView selection changes
        lstPublisher.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            publisher = newValue; //update selectedPublisher variable when ListView selection changes
        });

        //set listener for ComboBox selection changes
        cmbGenre.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            genre = newValue; //update selectedGenre variable when ComboBox selection changes
        });
    }
}

