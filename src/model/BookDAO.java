package model;

import java.sql.*;
import java.util.ArrayList;

public class BookDAO {
    Connection connection;
    public int rows;

    //constructor that receives url and initializes the connection
    public BookDAO(String url) throws SQLException {
        connection = DriverManager.getConnection(url);

    }

    //get all books from the database, store in array list and return them
    public ArrayList<Book> getAll() throws SQLException {
        //prepared statement that selects all books
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM BOOKS");
        //execute the query
        ResultSet rs = ps.executeQuery();
        ArrayList<Book> books = new ArrayList<>();
        while (rs.next()) {
            //while rs has next, initialize each result in result set as a new book and add it to the books array
            //list
            Book temp = new Book(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getLong(7)
            );
            books.add(temp);

        }
        return books;

    }

    //insert book into database
    public void insert(Book b) throws SQLException {
        //statement to insert new book
        PreparedStatement ps = connection.prepareStatement("INSERT INTO BOOKS VALUES(?,?,?,?,?,?,?)");

        //set the values of each parameter to the properties of the book received by the method
        ps.setString(2, b.getName());
        ps.setString(3, b.getAuthor());
        ps.setString(4, b.getPublisher());
        ps.setString(5, b.getGenre());
        ps.setString(6, b.getISBN());
        ps.setLong(4, b.getYear());
        ps.executeUpdate();

    }

    //method to delete book according to their book id
    public void delete(Book b) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM BOOKS WHERE ID=?");
        //set the parameter to the received book, book id
        ps.setInt(1, b.getID());

        ps.executeUpdate();


    }

    //method that searches the database according to the book id and returns the found book object
    public Book getBook(int bookID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM BOOKS WHERE ID=?");
        ps.setInt(1, bookID);
        ResultSet rs = ps.executeQuery();
        Book b = null;
        while (rs.next()) {
            b = new Book(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getLong(7)
            );

        }
        return b;
    }

    //this method will receive an updated book object, update the book by searching it using the book id
    //and setting the parameters to the received book's value
    public void update(Book b) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE BOOKS SET NAME=?, AUTHOR=?, PUBLISHER=?, GENRE=?, ISBN=?,YEAR=? WHERE ID=?");
        ps.setInt(7, b.getID());
        ps.setString(1, b.getName());
        ps.setString(2, b.getAuthor());
        ps.setString(3, b.getPublisher());
        ps.setString(4, b.getGenre());
        ps.setString(5, b.getISBN());
        ps.setLong(6, b.getYear());

        ps.executeUpdate();

    }

    //searches the book table using search query string
    public ArrayList<Book> getByQuery(String str) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM BOOKS WHERE NAME LIKE?");
        //set the parameter of the statement
        ps.setString(1, "%" + str + "%");
        ResultSet rs = ps.executeQuery();
        //initialize books array list
        ArrayList<Book> books = new ArrayList<>();
        while (rs.next()) {
            Book temp = new Book(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getInt(7)
            );
            //add each book in result set
            books.add(temp);

        }
        return books;

    }


}
