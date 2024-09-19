package DAOs;

import Models.Book;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    Connection connection;

    //constructor to initialize the BookDAO with the database URL
    public BookDAO(String url) throws SQLException {
        // Establishing a connection to the database
        connection = DriverManager.getConnection(url);
    }

    //method to insert a new book into the database
    public void insertBook(Book book) throws SQLException {
        //preparing SQL statement for insertion
        PreparedStatement ps = connection.prepareStatement("INSERT INTO books (name, author, publisher, genre, ISBN, year) VALUES (?, ?, ?, ?, ?, ?)");
        //setting values for placeholders in the SQL statement
        ps.setString(1, book.getName());
        ps.setString(2, book.getAuthor());
        ps.setString(3, book.getPublisher());
        ps.setString(4, book.getGenre());
        ps.setString(5, book.getISBN());
        ps.setLong(6, book.getYear());

        //executing the SQL statement to insert the book into the database
        ps.executeUpdate();
    }

    //method to delete a book from the database
    public void deleteBook(Book book) throws SQLException {
        //preparing SQL statement for deletion
        PreparedStatement ps = connection.prepareStatement("DELETE FROM books WHERE ID = ?");
        //setting the ID of the book to be deleted
        ps.setInt(1, book.getID());

        //executing the SQL statement to delete the book from the database
        ps.executeUpdate();
    }

    //method to update an existing book in the database
    public void updateBook(Book book) throws SQLException {
        //preparing SQL statement for updating
        PreparedStatement ps = connection.prepareStatement("UPDATE books SET name = ?, author = ?, publisher = ?, genre = ?, ISBN = ?, year = ? WHERE ID = ?");
        //setting values for placeholders in the SQL statement
        ps.setString(1, book.getName());
        ps.setString(2, book.getAuthor());
        ps.setString(3, book.getPublisher());
        ps.setString(4, book.getGenre());
        ps.setString(5, book.getISBN());
        ps.setLong(6, book.getYear());
        ps.setInt(7, book.getID());

        //executing the SQL statement to update the book in the database
        ps.executeUpdate();
    }

    //method to retrieve all books from the database
    public ArrayList<Book> getAllBooks() throws SQLException {
        //arrayList to hold the retrieved books
        ArrayList<Book> books = new ArrayList<>();
        //creating a statement to execute SQL query
        Statement statement = connection.createStatement();
        //executing the SQL query to select all books
        ResultSet resultSet = statement.executeQuery("SELECT * FROM books");

        //iterating through the result set
        while (resultSet.next()) {
            //retrieving book details from the result set
            int ID = resultSet.getInt("ID");
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            String genre = resultSet.getString("genre");
            String ISBN = resultSet.getString("ISBN");
            long year = resultSet.getLong("year");

            //creating a Book object with the retrieved details
            Book book = new Book(name, author, publisher, genre, ISBN, year);
            book.setID(ID);

            //adding the book to the ArrayList
            books.add(book);
        }

        //returning the ArrayList of books
        return books;
    }

    //method to retrieve a specific book from the database by its ID
    public Book getBook(int ID) throws SQLException {
        //preparing SQL statement to select a book by ID
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM books WHERE ID = ?");
        //setting the ID parameter in the SQL statement
        ps.setInt(1, ID);

        //executing the SQL query to select the book
        ResultSet resultSet = ps.executeQuery();
        //checking if a book was found
        if (resultSet.next()) {
            //retrieving book details from the result set
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            String genre = resultSet.getString("genre");
            String ISBN = resultSet.getString("ISBN");
            long year = resultSet.getLong("year");

            //creating a Book object with the retrieved details
            Book book = new Book(name, author, publisher, genre, ISBN, year);
            book.setID(ID);

            //returning the Book object
            return book;
        }

        //if no book was found with the given ID, return null
        return null;
    }


    //method to retrieve books by name query
    public ArrayList<Book> getByQuery(String nameQuery) throws SQLException {
        //arrayList to hold the retrieved books
        ArrayList<Book> books = new ArrayList<>();
        //prepare SQL statement to select books by name query
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM books WHERE name LIKE ?");
        //set the name query parameter in the SQL statement
        ps.setString(1, "%" + nameQuery + "%");
        //execute the SQL query
        ResultSet resultSet = ps.executeQuery();
        //iterate through the result set
        while (resultSet.next()) {
            //retrieve book details from the result set
            int ID = resultSet.getInt("ID");
            String name = resultSet.getString("name");
            String author = resultSet.getString("author");
            String publisher = resultSet.getString("publisher");
            String genre = resultSet.getString("genre");
            String ISBN = resultSet.getString("ISBN");
            long year = resultSet.getLong("year");
            //create a Book object with the retrieved details
            Book book = new Book(name, author, publisher, genre, ISBN, year);
            book.setID(ID);
            //add the book to the ArrayList
            books.add(book);
        }
        //return the ArrayList of books
        return books;
    }
}
