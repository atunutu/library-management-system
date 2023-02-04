package model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class UserDA0 {
    Connection connection;
    public int rows;

    public UserDA0(String url) throws SQLException {

        connection = DriverManager.getConnection(url);

    }

    //search transaction database table and return all transactions
    public ArrayList<User> getAll() throws SQLException {

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS");
        ResultSet rs = ps.executeQuery();
        ArrayList<User> users = new ArrayList<>();

        while (rs.next()) {
            //while rs has next, initialize each result in result set as a new book and add it to the users array
            //list
            //initialize sql date from the dateOfBirth column
            java.sql.Date d = rs.getDate(5);
            System.out.println(d);
            //convert the sql date to local date
            LocalDate date = d.toLocalDate();

            //create new user from result set values and add them to arraylist
            User temp = new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    date,
                    rs.getBoolean(6)

            );
            users.add(temp);

        }
        return users;
    }

    //insert user into database
    public void insert(User u) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO USERS VALUES(?,?,?,?,?,?,?)");

        //set the values of each parameter to the properties of the user received by the method
        ps.setString(2, u.getName());
        ps.setString(3, u.getEmail());
        ps.setString(4, u.getAddress());
        LocalDate date = u.getDateOfBirth();
        System.out.println(Date.valueOf(date));
        ps.setDate(5, Date.valueOf(date));
        ps.setBoolean(6, u.getStudent());
        ps.setDouble(7, u.getBalance());
        ps.executeUpdate();
    }

    //method to delete user according to their user id
    public void delete(User u) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM USERS WHERE ID=?");
        ps.setInt(1, u.getID());

        ps.executeUpdate();


    }

    //method that searches the database according to the user id and returns the found user object
    public User getUser(int userID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS WHERE ID=?");
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        User u = null;
        while (rs.next()) {
            //convert the date in result set to local date
            LocalDate date = rs.getDate(5).toLocalDate();
            //initialize the resultset values to a new user and return it
            u = new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    date,
                    rs.getBoolean(6)
            );

        }
        return u;
    }

    //this method will receive an updated user object, update the book by searching it using the user id
    //and setting the parameters to the received user's value
    public void update(User u) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE USERS SET NAME=?, EMAIL=?, ADDRESS=?, DATEOFBIRTH=?, ISSTUDENT=?,BALANCE=? WHERE ID=?");
        ps.setInt(7, u.getID());
        ps.setString(1, u.getName());
        ps.setString(2, u.getEmail());
        ps.setString(3, u.getAddress());
        ps.setDate(4, Date.valueOf(u.getDateOfBirth()));
        ps.setBoolean(5, u.getStudent());
        ps.setDouble(6, u.getBalance());

        ps.executeUpdate();

    }
    //searches the user database using search query string
    public ArrayList<User> getByQuery(String str) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS WHERE NAME LIKE?");
        //set the parameters of the prepared statement
        ps.setString(1, "%" + str + "%");
        ResultSet rs = ps.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()) {
            //turn the result set date to local date
            LocalDate date = rs.getDate(5).toLocalDate();
            User temp = new User(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    date,
                    rs.getBoolean(6)

            );
            //add the user from the result set to the array list
            users.add(temp);

        }
        return users;

    }
}
