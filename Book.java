package Models;

public class Book {
    //model.Book attributes
    private int ID;
    private String name;
    private String author;
    private String publisher;
    private String genre;
    private String ISBN;
    private long year;
    //static variable that increments each time a new model.Book is created/added
    private static int booksAdded = 0;

    //parameterized constructor to accept all nonstatic variables but ID
    //booksAdded is incremented with each new model.Book created/added
    public Book(String name, String author, String publisher, String genre, String ISBN, long year) {
        this.name = name;
        this.author = author;
        this.publisher = publisher;
        this.genre = genre;
        this.ISBN = ISBN;
        this.year = year;
        this.ID = booksAdded++;
    }

    //overridden toString method for model.Book which returns book ID and name
    public String toString() {
        return ("ID: " + this.getID() + " Name: " + this.getName());
    }

    //overridden equals method to see if two Books have the same ID
    public boolean equals(Object obj) {
        //type cast Object to model.Book
        Book book = (Book) obj;
        //check if IDs match for caller and argument
        if (this.getID() == book.getID()) {
            return true;
        }
        else {
            return false;
        }
    }

    //GETTERS FOR BOOK'S PRIVATE NONSTATIC ATTRIBUTES
    public int getID() {
        return ID;
    }
    public String getName() {
        return name;
    }
    public String getAuthor() {
        return author;
    }
    public String getPublisher() {
        return publisher;
    }
    public String getGenre() {
        return genre;
    }
    public String getISBN() {
        return ISBN;
    }
    public long getYear() {
        return year;
    }

    //SETTERS FOR BOOK'S PRIVATE NONSTATIC ATTRIBUTES

    public void setID(int ID) {
        this.ID = ID;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
    public void setYear(long year) {
        this.year = year;
    }
}
