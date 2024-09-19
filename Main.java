import Controllers.MainMenuController;
import Models.Book;
import Models.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Models.Library;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class Main extends Application {
    Library library;
    public static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException {
        //create starting users
//        User u1 = new User("John Stones","jstones@gmail.com","211 Ryder St. Tallahassee, FL 32304", LocalDate.of(1989,06,13), true);
//        User u2 = new User("Jack Bauer","jack24@gmail.com","7400 Bay Rd. University Center, MI 48710",LocalDate.of(1988,11,15),false);
//        User u3 = new User("Harry Kane","hkane@gmail.com","123 James Boyd Rd. Scranton, PA 28410",LocalDate.of(1988,2,1), false);
//        User u4 = new User("Tim Arnold","ta123@gmail.com","3412 Dinsmore Ave, MA 01710",LocalDate.of(1999,1,15), true);

        //u1.setBalance(10);
        //create starting books
        Book b1 = new Book("Programming with Java","Daniel Liang","Pearson","Education","1234568924",2020);
        Book b2 = new Book("Data Structures and Algorithms","Robert Lafore","Pearson","Education","98726213",2001);
        Book b3 = new Book("Harry Potter and The Chamber of Secrets","J.K. Rowling","Scholastic","Adventure","343255323",1998);
        Book b4 = new Book("Lord of the Rings - The Two Towers","Tolkien","Wiley","Thriller","989636362",1945);

        //this will be the library object that gets passed to every class that works with the library
        library = new Library();

//        //add starting users
//        library.addUser(u1);
//        library.addUser(u2);
//        library.addUser(u3);
//        library.addUser(u4);
//
//        //add starting books
//        library.addBook(b1);
//        library.addBook(b2);
//        library.addBook(b3);
//        library.addBook(b4);

        //set up stage and scene for application
        Main.primaryStage = primaryStage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/Views/Main_Menu.fxml"));
        Parent root = loader.load();

        MainMenuController mainMenuController = loader.getController();
        //this will pass the library object to the main menu controller
        mainMenuController.initData(library);

        //this will allow all subsequent controller loaded within this scene to have the same Library object
        primaryStage.setScene(new Scene(root,800,600));
        primaryStage.setTitle("Main Menu");
        primaryStage.show();
    }

    //launch application
    public static void main(String[] args) {
        launch();
    }
}

