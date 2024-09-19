package DAOs;

import Models.Transaction;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionDAO {
    Connection connection;

    //constructor to initialize the TransactionDAO with the database URL
    public TransactionDAO(String url) throws SQLException {
        //establishing a connection to the database
        connection = DriverManager.getConnection(url);
    }

    //method to insert a new transaction into the database
    public void insertTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (bookID, userID, issueDate, status) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            // setting values for placeholders in the SQL statement
            ps.setInt(1, transaction.getBookID());
            ps.setInt(2, transaction.getUserID());
            ps.setDate(3, Date.valueOf(transaction.getIssueDate()));
            ps.setBoolean(4, transaction.isStatus());

            // executing the SQL statement to insert the transaction into the database
            ps.executeUpdate();
        } catch (SQLException e) {
            // Handle any SQL exceptions appropriately
            e.printStackTrace();
        }
    }

    //method to delete a transaction from the database
    public void deleteTransaction(Transaction transaction) throws SQLException {
        //preparing SQL statement for deletion
        PreparedStatement ps = connection.prepareStatement("DELETE FROM transactions WHERE bookID = ?");
        //setting the bookID of the transaction to be deleted
        ps.setInt(1, transaction.getBookID());

        //executing the SQL statement to delete the transaction from the database
        ps.executeUpdate();
    }

    //method to update an existing transaction in the database
    public void updateTransaction(Transaction transaction) throws SQLException {
        //preparing SQL statement for updating
        PreparedStatement ps = connection.prepareStatement("UPDATE transactions SET userID = ?, issueDate = ?, status = ? WHERE bookID = ?");
        //setting values for placeholders in the SQL statement
        ps.setInt(1, transaction.getUserID());
        ps.setDate(2, Date.valueOf(transaction.getIssueDate()));
        ps.setBoolean(3, transaction.isStatus());
        ps.setInt(4, transaction.getBookID());

        //executing the SQL statement to update the transaction in the database
        ps.executeUpdate();
    }

    //method to retrieve all transactions from the database
    public ArrayList<Transaction> getAllTransactions() throws SQLException {
        //arrayList to hold the retrieved transactions
        ArrayList<Transaction> transactions = new ArrayList<>();
        //creating a statement to execute SQL query
        Statement statement = connection.createStatement();
        //executing the SQL query to select all transactions
        ResultSet resultSet = statement.executeQuery("SELECT * FROM transactions");

        //iterating through the result set
        while (resultSet.next()) {
            //retrieving transaction details from the result set
            int bookID = resultSet.getInt("bookID");
            int userID = resultSet.getInt("userID");
            LocalDate issueDate = resultSet.getDate("issueDate").toLocalDate();
            boolean status = resultSet.getBoolean("status");

            //creating a Transaction object with the retrieved details
            Transaction transaction = new Transaction(bookID, userID);
            transaction.setIssueDate(issueDate);
            transaction.setStatus(status);

            //adding the transaction to the ArrayList
            transactions.add(transaction);
        }

        //returning the ArrayList of transactions
        return transactions;
    }

    //method to retrieve currently checked out transactions
    public ArrayList<Transaction> getCurrent() throws SQLException {
        //arrayList to hold the currently checked out transactions
        ArrayList<Transaction> currentTransactions = new ArrayList<>();
        //preparing SQL statement to select currently checked out transactions
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM transactions WHERE status = ?");
        //setting the status parameter in the SQL statement
        ps.setBoolean(1, true);

        //executing the SQL query to select currently checked out transactions
        ResultSet resultSet = ps.executeQuery();
        //iterating through the result set
        while (resultSet.next()) {
            //retrieving transaction details from the result set
            int bookID = resultSet.getInt("bookID");
            int userID = resultSet.getInt("userID");
            LocalDate issueDate = resultSet.getDate("issueDate").toLocalDate();
            boolean status = resultSet.getBoolean("status");

            //creating a Transaction object with the retrieved details
            Transaction transaction = new Transaction(bookID, userID);
            transaction.setIssueDate(issueDate);
            transaction.setStatus(status);

            //adding the transaction to the ArrayList
            currentTransactions.add(transaction);
        }

        //returning the ArrayList of currently checked out transactions
        return currentTransactions;
    }

    //method to retrieve transactions by user ID
    public ArrayList<Transaction> getByUser(int userID) throws SQLException {
        //arrayList to hold the transactions of the specified user
        ArrayList<Transaction> userTransactions = new ArrayList<>();
        //preparing SQL statement to select transactions by user ID
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM transactions WHERE userID = ?");
        //setting the userID parameter in the SQL statement
        ps.setInt(1, userID);

        //executing the SQL query to select transactions by user ID
        ResultSet resultSet = ps.executeQuery();
        //iterating through the result set
        while (resultSet.next()) {
            //retrieving transaction details from the result set
            int bookID = resultSet.getInt("bookID");
            LocalDate issueDate = resultSet.getDate("issueDate").toLocalDate();
            boolean status = resultSet.getBoolean("status");

            //creating a Transaction object with the retrieved details
            Transaction transaction = new Transaction(bookID, userID);
            transaction.setIssueDate(issueDate);
            transaction.setStatus(status);

            //adding the transaction to the ArrayList
            userTransactions.add(transaction);
        }

        //returning the ArrayList of transactions for the specified user
        return userTransactions;
    }

    //method to retrieve transactions by book ID
    public ArrayList<Transaction> getByBook(int bookID) throws SQLException {
        //arrayList to hold the transactions related to the specified book
        ArrayList<Transaction> bookTransactions = new ArrayList<>();
        //preparing SQL statement to select transactions by book ID
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM transactions WHERE bookID = ?");
        //setting the bookID parameter in the SQL statement
        ps.setInt(1, bookID);

        //executing the SQL query to select transactions by book ID
        ResultSet resultSet = ps.executeQuery();
        //iterating through the result set
        while (resultSet.next()) {
            //retrieving transaction details from the result set
            int userID = resultSet.getInt("userID");
            LocalDate issueDate = resultSet.getDate("issueDate").toLocalDate();
            boolean status = resultSet.getBoolean("status");

            //creating a Transaction object with the retrieved details
            Transaction transaction = new Transaction(bookID, userID);
            transaction.setIssueDate(issueDate);
            transaction.setStatus(status);

            //adding the transaction to the ArrayList
            bookTransactions.add(transaction);
        }

        //returning the ArrayList of transactions for the specified book
        return bookTransactions;
    }
}
