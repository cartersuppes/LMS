package DAOs;

import Models.User;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserDAO {
    Connection connection;

    //constructor to initialize the UserDAO with the database URL
    public UserDAO(String url) throws SQLException {
        //establishing a connection to the database
        connection = DriverManager.getConnection(url);
    }

    //method to insert a new user into the database
    public void insertUser(User user) throws SQLException {
        // Preparing SQL statement for insertion
        PreparedStatement ps = connection.prepareStatement("INSERT INTO users (name, email, address, dateOfBirth, isStudent, balance) VALUES (?, ?, ?, ?, ?, ?)");
        // Setting values for placeholders in the SQL statement
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getAddress());
        // Formatting date of birth as string in 'yyyy-MM-dd' format
        ps.setString(4, user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ps.setBoolean(5, user.isStudent());
        ps.setDouble(6, user.getBalance());

        // Executing the SQL statement to insert the user into the database
        ps.executeUpdate();
    }

    //method to delete a user from the database
    public void deleteUser(User user) throws SQLException {
        //preparing SQL statement for deletion
        PreparedStatement ps = connection.prepareStatement("DELETE FROM users WHERE ID = ?");
        //setting the ID of the user to be deleted
        ps.setInt(1, user.getID());

        //executing the SQL statement to delete the user from the database
        ps.executeUpdate();
    }

    //method to update an existing user in the database
    public void updateUser(User user) throws SQLException {
        // Preparing SQL statement for updating
        PreparedStatement ps = connection.prepareStatement("UPDATE users SET name = ?, email = ?, address = ?, dateOfBirth = ?, isStudent = ?, balance = ? WHERE ID = ?");
        // Setting values for placeholders in the SQL statement
        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());
        ps.setString(3, user.getAddress());
        // Formatting date of birth as string in 'yyyy-MM-dd' format
        ps.setString(4, user.getDateOfBirth().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        ps.setBoolean(5, user.isStudent());
        ps.setDouble(6, user.getBalance());
        ps.setInt(7, user.getID());

        // Executing the SQL statement to update the user in the database
        ps.executeUpdate();
    }

    //method to retrieve all users from the database
    public ArrayList<User> getAllUsers() throws SQLException {
        // ArrayList to hold the retrieved users
        ArrayList<User> users = new ArrayList<>();
        // Creating a statement to execute SQL query
        Statement statement = connection.createStatement();
        // Executing the SQL query to select all users
        ResultSet resultSet = statement.executeQuery("SELECT * FROM users");

        // DateTimeFormatter for parsing date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Iterating through the result set
        while (resultSet.next()) {
            // Retrieving user details from the result set
            int ID = resultSet.getInt("ID");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");

            // Retrieving dateOfBirth as a string
            String dateOfBirthString = resultSet.getString("dateOfBirth");

            boolean isStudent = resultSet.getBoolean("isStudent");
            double balance = resultSet.getDouble("balance");

            // Creating a User object with the retrieved details
            User user = new User(name, email, address, LocalDate.parse(dateOfBirthString, formatter), isStudent);
            user.setID(ID);
            user.setBalance(balance);

            // Adding the user to the ArrayList
            users.add(user);
        }

        // Returning the ArrayList of users
        return users;
    }

    //method to retrieve a specific user from the database by their ID
    //method to retrieve a specific user from the database by their ID
    public User getUser(int ID) throws SQLException {
        // Preparing SQL statement to select a user by ID
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE ID = ?");
        // Setting the ID parameter in the SQL statement
        ps.setInt(1, ID);

        // Executing the SQL query to select the user
        ResultSet resultSet = ps.executeQuery();
        // Checking if a user was found
        if (resultSet.next()) {
            // Retrieving user details from the result set
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            // Retrieving dateOfBirth as a string
            String dateOfBirthString = resultSet.getString("dateOfBirth");
            boolean isStudent = resultSet.getBoolean("isStudent");
            double balance = resultSet.getDouble("balance");

            // Creating a User object with the retrieved details
            User user = new User(name, email, address, LocalDate.parse(dateOfBirthString), isStudent);
            user.setID(ID);
            user.setBalance(balance);

            // Returning the User object
            return user;
        }

        // If no user was found with the given ID, return null
        return null;
    }

    //method to retrieve users based on a name query
    public ArrayList<User> getByQuery(String nameQuery) throws SQLException {
        //arrayList to hold the retrieved users
        ArrayList<User> users = new ArrayList<>();
        //preparing SQL statement to select users by name query
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM users WHERE name LIKE ?");
        //setting the name query parameter in the SQL statement
        ps.setString(1, "%" + nameQuery + "%");

        //executing the SQL query to select users
        ResultSet resultSet = ps.executeQuery();
        //iterating through the result set
        while (resultSet.next()) {
            //retrieving user details from the result set
            int ID = resultSet.getInt("ID");
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String address = resultSet.getString("address");
            Date dateOfBirth = resultSet.getDate("dateOfBirth");
            boolean isStudent = resultSet.getBoolean("isStudent");
            double balance = resultSet.getDouble("balance");

            //creating a User object with the retrieved details
            User user = new User(name, email, address, dateOfBirth.toLocalDate(), isStudent);
            user.setID(ID);
            user.setBalance(balance);

            //adding the user to the ArrayList
            users.add(user);
        }

        //returning the ArrayList of users
        return users;
    }
}
