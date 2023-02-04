package model;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class TransactionDAO {
    Connection connection;
    public int rows;


    public TransactionDAO(String url) throws SQLException {
        connection = DriverManager.getConnection(url);

    }

    //get all transactions from the database, store in array list and return them
    public ArrayList<Transaction> getAll() throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM LIBTRANSACTIONS");
        ResultSet rs = ps.executeQuery();
        ArrayList<Transaction> transactions = new ArrayList<>();
        //while rs has next, initialize each result in result set as a new transaction and add it to the transactions array
        //list
        while (rs.next()) {
            LocalDate date = rs.getDate(3).toLocalDate();
            Transaction temp = new Transaction(
                    rs.getInt(1),
                    rs.getInt(2)

            );
            //set the issue date and status from the result set values
            temp.setIssueDate(date);
            temp.setStatus(rs.getBoolean(4));
            transactions.add(temp);

        }
        return transactions;


    }

    //insert new transaction into database
    public void insert(Transaction t) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("INSERT INTO LIBTRANSACTIONS VALUES(?,?,?,?)");
//        ps.setInt(1,s.id);
        ps.setInt(1, t.getBookID());
        ps.setInt(2, t.getUserID());
        ps.setDate(3, Date.valueOf(t.getIssueDate()));
        ps.setBoolean(4, t.isStatus());
        ps.executeUpdate();
    }

    //delete transaction that involves a specific user and book
    public void delete(Transaction t) throws SQLException {

        PreparedStatement ps = connection.prepareStatement("DELETE FROM LIBTRANSACTIONS WHERE BOOKID=? AND USERID=?");
        ps.setInt(1, t.getBookID());
        ps.setInt(2, t.getUserID());

        ps.executeUpdate();


    }
    //this method will receive an updated transaction object, update the transaction by searching it using the book id and user id
    //and setting the parameters to the received transaction's value
    public void update(Transaction t) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("UPDATE LIBTRANSACTIONS SET BOOKID=?, USERID=?, ISSUEDATE=?, STATUS=? WHERE BOOKID=? AND USERID=?");
        ps.setInt(1, t.getBookID());
        ps.setInt(2, t.getUserID());
        ps.setDate(3, Date.valueOf(t.getIssueDate()));
        ps.setBoolean(4, t.isStatus());
        ps.setInt(5, t.getBookID());
        ps.setInt(6, t.getUserID());

        ps.executeUpdate();

    }

    //get transactions that are active
    public ArrayList<Transaction> geCurrent() throws SQLException {
        //search the database for transactions who's status is true
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM LIBTRANSACTIONS WHERE STATUS=1");
        ResultSet rs = ps.executeQuery();
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            //initialize each result to a new transaction object
            LocalDate date = rs.getDate(3).toLocalDate();
            Transaction temp = new Transaction(
                    rs.getInt(1),
                    rs.getInt(2)

            );
            //set the traction object issueDate and status from values in the result set
            temp.setIssueDate(date);
            temp.setStatus(rs.getBoolean(4));
            //add the transaction to the array list
            transactions.add(temp);

        }
        return transactions;


    }

    //get transactions with the user id that is received and return all transactions
    //associated with that user
    public ArrayList<Transaction> getByUser(int userID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM LIBTRANSACTIONS WHERE USERID=?");
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            LocalDate date = rs.getDate(3).toLocalDate();
            Transaction temp = new Transaction(
                    rs.getInt(1),
                    rs.getInt(2)

            );
            temp.setIssueDate(date);
            temp.setStatus(rs.getBoolean(4));
            transactions.add(temp);

        }
        return transactions;


    }


    //get transactions with the book id that is received and return all transactions
    //associated with that book
    public ArrayList<Transaction> getByBook(int bookID) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("SELECT * FROM LIBTRANSACTIONS WHERE BOOKID=?");
        ps.setInt(1, bookID);
        ResultSet rs = ps.executeQuery();
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            LocalDate date = rs.getDate(3).toLocalDate();
            Transaction temp = new Transaction(
                    rs.getInt(1),
                    rs.getInt(2)

            );
            temp.setIssueDate(date);
            temp.setStatus(rs.getBoolean(4));
            transactions.add(temp);

        }
        return transactions;


    }


}
