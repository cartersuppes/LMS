package Controllers;
import Models.Book;
import Models.Library;
import Models.Transaction;
import Models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class ViewUserController {

    //GUI elements and controller variables

    @FXML
    private Button btnCollectFine;
    @FXML
    private ComboBox<User> cmbUsers;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblBalance;
    @FXML
    private Label lblBirthday;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblName;
    @FXML
    private Label lblUserType;
    @FXML
    private ListView<Book> lstBooks;
    User selectedUser;

    //library passed now to this controller
    Library library;

    //empty initialize to avoid null pointers
    @FXML
    void initialize() {
    }

    //initializes library then gets the users list and populates to combobox with the toStrings of the users in it
    public void initData(Library library) throws SQLException {
        this.library = library;
        library.getLog();
        ObservableList<User> userList = FXCollections.observableArrayList(library.userDAO.getAllUsers());
        cmbUsers.setItems(userList);
    }

    //runs when button is clicked, button becomes visible when the user's info is loaded. If user has an outstanding balance, button will appear
    //and when clicked the balance will reset to 0.00, and the balance label will change back to green
    @FXML
    void handleCollectFine(ActionEvent event) throws SQLException {
        library.collectFine(selectedUser);
        selectedUser.setBalance(0.0);
        lblBalance.setTextFill(Color.GREEN);
    }

    //this method runs when a new item is selected from the combobox. Method will take the selected User and populate all the labels on the GUI
    //and change the color of the balance based on the balance. Balance will decide whether the button is visible or not. Listview will populate
    //with the books CURRENTLY checked out by the selectedUser. This will be gotten from the transactions arraylist and check IDs matching the selectedUser
    //then finally populate the listview with those books
    @FXML
    void loadListInfo(ActionEvent event) throws SQLException {
        ArrayList<Book> bookList = new ArrayList<>();
        selectedUser = cmbUsers.getSelectionModel().getSelectedItem();

        //add null check
        if (selectedUser != null) {
            lblName.setText("Name: " + selectedUser.getName());
            lblEmail.setText("Email: " + selectedUser.getEmail());
            lblAddress.setText("Address: " + selectedUser.getAddress());
            lblBirthday.setText("Birth Date: " + selectedUser.getDateOfBirth().toString());

            //quick check of userType
            if (selectedUser.isStudent()) {
                lblUserType.setText("User type: Student");
            } else {
                lblUserType.setText("User type: Faculty");
            }

            //formats the balance to two decimal places
            lblBalance.setText("Balance: " + (String.format("%.2f", selectedUser.getBalance())));
            if (selectedUser.getBalance() > 0) {
                lblBalance.setTextFill(Color.RED);
                btnCollectFine.setVisible(true);
            } else {
                lblBalance.setTextFill(Color.GREEN);
                btnCollectFine.setVisible(false);
            }
            ObservableList<Transaction> transactionList = FXCollections.observableArrayList(library.transactionDAO.getAllTransactions());
            for (Transaction t : transactionList) {
                if (t.getUserID() == selectedUser.getID() && t.isStatus()) {
                    bookList.add(library.getBook(t.getBookID()));
                }
            }
            //populates listview of books checked out by user selected
            ObservableList<Book> checkedBooks = FXCollections.observableArrayList(bookList);
            lstBooks.setItems(checkedBooks);
        }
        else {
            library.msgLog.add("Null library.");
        }
    }
}