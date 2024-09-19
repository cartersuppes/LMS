package Models;

import java.sql.Date;
import java.time.LocalDate;

public class User {
    //private User attributes
    private int ID;
    private String name;
    private String email;
    private String address;
    private LocalDate dateOfBirth;
    private boolean isStudent;
    private double balance;
    //static int variable that is incremented with each model.User created/added
    private static int usersAdded = 0;

    //model.User constructor without ID, usersAdded is incremented with each model.User created/added
    public User(String name, String email, String address, LocalDate dateOfBirth, boolean isStudent) {
        this.name = name;
        //new users balance should be initially set to 0
        this.balance = 0.0;
        this.email = email;
        this.address = address;
        this.dateOfBirth =  dateOfBirth;
        this.isStudent = isStudent;
        this.ID = usersAdded++;
    }

    //GETTERS FOR PRIVATE NONSTATIC ATTRIBUTES OF USER
    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
    public String getAddress() {
        return address;
    }
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    public boolean isStudent() {
        return isStudent;
    }
    public double getBalance() {
        return balance;
    }

    //SETTERS FOR PRIVATE NONSTATIC ATTRIBUTES OF USER
    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth.toLocalDate();
    }
    public void setStudent(boolean student) {
        isStudent = student;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    //overridden toString method for printing ID and Name
    public String toString() {
        return ("ID: " + this.getID() + " Name: " + this.getName());
    }

    //overridden equals method to check if two users have the same ID
    public boolean equals(Object obj) {
        // Check if the object is null or not an instance of User
        if (obj == null || !(obj instanceof User)) {
            return false;
        }

        // Type cast Object to type User
        User user = (User) obj;

        // Check if the IDs are the same
        return this.getID() == user.getID();
    }
}
