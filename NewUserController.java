package Controllers;

import Models.Library;
import Models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

//controller for new user gui
public class NewUserController {

    Library library;

    //elements of new user gui
    @FXML
    private TextField txtUserName;
    @FXML
    private TextField txtUserEmail;
    @FXML
    private TextArea txtUserAddress;
    @FXML
    private RadioButton rdoFaculty;
    @FXML
    private RadioButton rdoStudent;
    @FXML
    private DatePicker btnBirthday;

    public void initData(Library library) {
        this.library = library;
        library.getLog();
    }

    //method for when register button is clicked. inputs are taken from the name, email, address fields, checks which radio button
    //is selected for student/faculty, and then gets the birthdate and assigns these values fetched to variables.
    //then check to make sure all fields are filled, return and send error if incomplete, if they are all complete create new User object
    //out of variables with field values, then finally send a success message with the name of the new User
    @FXML
    void handleRegisterUserButton(ActionEvent event) throws SQLException {
        String name = txtUserName.getText();
        String email = txtUserEmail.getText();
        String address = txtUserAddress.getText();
        boolean isStudent = rdoStudent.isSelected();
        LocalDate birthday = btnBirthday.getValue();
        //Get LocalDate from SQL date
        Date sqlDate = java.sql.Date.valueOf( birthday );

        //validation check for empty fields
        if (name.isEmpty() || email.isEmpty() || address.isEmpty() || btnBirthday.getValue() == null) {
            showAlert("Error", "All fields must be filled");
            return;
        }



        //creates new user from fetched details
        User newUser = new User(name, email, address, sqlDate.toLocalDate(), isStudent);
        //System.out.println(newUser);

        //add the new user to the list
        library.addUser(newUser);

        //close the Add New User window
        ((Stage) txtUserName.getScene().getWindow()).close();

        //show success message
        showAlert("Registration Successful", "Registered: " + newUser.getName());
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
}
