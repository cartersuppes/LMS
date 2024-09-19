package Controllers;

import Models.Library;
import Models.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLException;

public class ViewBooksController {

    //GUI elements
    @FXML
    private TableColumn<Transaction, Integer> colBookID;
    @FXML
    private TableColumn<Transaction, String> colIssueDate;
    @FXML
    private TableColumn<Transaction, Integer> colUserID;
    @FXML
    private TableView<Transaction> tblBooks;

    Library library;

    public void initData(Library library) throws SQLException {
        this.library = library;
        library.getLog();

        //get the list of transactions from the library
        ObservableList<Transaction> transactionList = FXCollections.observableArrayList(library.transactionDAO.getAllTransactions());

        //set the transaction list to the table view
        tblBooks.setItems(transactionList);
    }

    //initialize method to set up the table view
    public void initialize() {
        //set up table columns
        colBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        colUserID.setCellValueFactory(new PropertyValueFactory<>("userID"));
        colIssueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));

    }

}
