package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import Models.Library;

public class MainMenuController implements Initializable {
    //main menu variables and controls
    @FXML
    Library library;
    @FXML
    private Button btnAddBook;
    @FXML
    private Button btnAddUser;
    @FXML
    private Button btnIssueBook;
    @FXML
    private Button btnReturnBook;
    @FXML
    private Button btnViewBooks;
    @FXML
    private Button btnViewUsers;

    //this is the function that will pass the library object to each controller
    public void initData(Library library) {
        this.library = library;
        library.getLog();
    }


    //loadScreen method was retrieved from walk through video, essentially is the set-up for each gui
    public Object loadScreen(String title, String url) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/" + url));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.show();
        return loader.getController();
    }

    //FUNCTIONS TO SEND USER TO THE GUIS ASSOCIATED WITH ALL THE BUTTONS ON THE MAIN MENU PAGE
    @FXML
    public void handleAddUserButton(ActionEvent event) throws IOException {
        NewUserController controller = (NewUserController) loadScreen("Add New User", "New_User_Registration.fxml");
        controller.initData(library);
    }
    @FXML
    void handleAddBookButton(ActionEvent event) throws IOException {
        NewBookController controller = (NewBookController) loadScreen("Add New Book", "New_Book_Registration.fxml");
        controller.initData(library);
    }
    @FXML
    void handleIssueBookButton(ActionEvent actionEvent) throws IOException, SQLException {
        IssueBookController controller = (IssueBookController) loadScreen("Issue Book", "Issue_Book.fxml");
        controller.initData(library);
    }
    @FXML
    void handleReturnBookButton(ActionEvent actionEvent) throws IOException, SQLException {
        ReturnBookController controller = (ReturnBookController) loadScreen("Return Book", "Return_Book.fxml");
        controller.initData(library);
    }
    @FXML
    void handleViewUserButton(ActionEvent actionEvent) throws IOException, SQLException {
        ViewUserController controller = (ViewUserController) loadScreen("User Details", "View_User.fxml");
        controller.initData(library);
    }
    @FXML
    void handleViewBooksButton(ActionEvent actionEvent) throws IOException, SQLException {
        ViewBooksController controller = (ViewBooksController) loadScreen("View Issued Books", "View_Issued_Books.fxml");
        controller.initData(library);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
