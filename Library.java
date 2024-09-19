package Models;

import DAOs.BookDAO;
import DAOs.TransactionDAO;
import DAOs.UserDAO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class Library {
    private int MAX_BOOK_LIMIT = 3; // Maximum number of books a user can borrow
    private int MAX_LOAN_DAYS = 14; // Maximum number of days a book can be borrowed


    public ArrayList<String> msgLog; // Log to store messages and events
    public UserDAO userDAO; // Data Access Object for User entity
    public BookDAO bookDAO; // Data Access Object for Book entity
    public TransactionDAO transactionDAO; // Data Access Object for Transaction entity

    public Library() throws SQLException {
        String url = "jdbc:sqlite:db_lib.db";
        msgLog = new ArrayList<>();
        userDAO = new UserDAO(url); // Initialize UserDAO with database URL
        bookDAO = new BookDAO(url); // Initialize BookDAO with database URL
        transactionDAO = new TransactionDAO(url); // Initialize TransactionDAO with database URL
    }

    // Method to add a book to the library
    public void addBook(Book b) throws SQLException {
        bookDAO.insertBook(b); // Insert book into the database using BookDAO
        msgLog.add("Book ID " + b.getID() + " added to Library");
    }

    // Method to add a user to the library
    public void addUser(User u) throws SQLException {
        userDAO.insertUser(u); // Insert user into the database using UserDAO
        msgLog.add("User ID " + u.getID() + " added to Library");
    }

    // Method to add a transaction to the library
    public void addTransaction(Transaction t) throws SQLException {
        transactionDAO.insertTransaction(t); // Insert transaction into the database using TransactionDAO
    }

    // Method to check if a book is available for borrowing
    public boolean isAvailable(int bookID) throws SQLException {
        ArrayList<Transaction> bookTransactions = transactionDAO.getByBook(bookID);
        for (Transaction t : bookTransactions) {
            if (t.isStatus()) { // If any transaction is active, book is not available
                return false;
            }
        }
        return true;
    }

    // Method to get the number of books borrowed by a user
    public int getBorrowCount(int userID) throws SQLException {
        ArrayList<Transaction> userTransactions = transactionDAO.getByUser(userID);
        int count = 0;
        for (Transaction t : userTransactions) {
            if (t.isStatus()) { // If transaction is active, book is borrowed
                count++;
            }
        }
        return count;
    }

    // Method to calculate due date based on issue date
    public LocalDate getDueDate(LocalDate issueDate) {
        return issueDate.plusDays(MAX_LOAN_DAYS);
    }

    // Method to issue a book to a user
    public boolean issueBook(int userID, int bookID) throws SQLException {
        if (!isAvailable(bookID)) {
            msgLog.add("This book is currently unavailable!");
            return false;
        }
        int borrowCount = getBorrowCount(userID);
        if (borrowCount >= MAX_BOOK_LIMIT) {
            msgLog.add("User has reached the maximum limit of borrowed books!");
            return false;
        }
        User u = userDAO.getUser(userID);
        if (u.getBalance() > 0) {
            msgLog.add("User has an outstanding balance of $" + u.getBalance() + "!");
            return false;
        }
        Transaction trans = new Transaction(bookID, userID);
        trans.setStatus(true);
        transactionDAO.insertTransaction(trans);
        msgLog.add(getBook(bookID).getName() + " has been issued to " + u.getName() + ".");
        msgLog.add("The due date is " + getDueDate(trans.getIssueDate()));
        return true;
    }

    // Method to return a book to the library
    public boolean returnBook(int bookID) throws SQLException {
        ArrayList<Transaction> transactions = transactionDAO.getByBook(bookID);
        Transaction trans = null;
        for (Transaction t : transactions) {
            if (t.isStatus()) {
                trans = t;
                break;
            }
        }
        if (trans == null) {
            msgLog.add("Book currently not borrowed");
            return false;
        }
        User u = userDAO.getUser(trans.getUserID());
        Book b = bookDAO.getBook(bookID);
        double fine = computeFine(trans);
        u.setBalance(u.getBalance() + fine);
        trans.setStatus(false);
        if (fine == 0) {
            msgLog.add("Thanks for returning " + b.getName() + "!");
        } else {
            msgLog.add("You returned " + b.getName() + " " + fine + " days late!");
            msgLog.add("Your outstanding balance is $" + u.getBalance());
        }
        return true;
    }

    // Method to collect fines from a user
    public void collectFine(User u) throws SQLException {
        if (u.getBalance() <= 0) {
            msgLog.add("User has no outstanding balances..");
        } else {
            msgLog.add("User dues collected....");
            u.setBalance(0);
            userDAO.updateUser(u); // Update user balance in the database using UserDAO
        }
    }

    // Method to compute fine based on issue date
    private double computeFine(Transaction t) {
        long days = t.getIssueDate().until(LocalDate.now()).getDays();
        return Math.max(0, days - MAX_LOAN_DAYS);
    }

    // Method to search for books by name
    public ArrayList<Book> searchBook(String name) throws SQLException {
        return bookDAO.getByQuery(name); // Search for books in the database using BookDAO
    }

    // Method to get a book by its ID
    public Book getBook(int bookID) throws SQLException {
        return bookDAO.getBook(bookID); // Retrieve book from the database using BookDAO
    }

    // Method to get a user by their ID
    public User getUser(int userID) throws SQLException {
        return userDAO.getUser(userID); // Retrieve user from the database using UserDAO
    }

    // Method to get the log of messages and events
    public ArrayList<String> getLog() {
        return msgLog;
    }
}
