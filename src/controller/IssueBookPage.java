package controller;

import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Book;
import model.Library;
import javafx.fxml.FXML;
import model.Transaction;
import model.User;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class IssueBookPage implements Initializable {

    @FXML
    private TextField txtUserSearch;
    @FXML
    private TextField txtBookSearch;
    @FXML
    private TextField txtUserID;
    @FXML
    private TextField txtBookID;
    @FXML
    private ListView<User> lstUserList;
    @FXML
    private ListView<Book> lstBookList;
    @FXML
    private Button btnIssueBook;
    @FXML
    private Label msgLabel;

    private boolean isFieldBlank = true;
    private ObservableList<User> userList;
    private ObservableList<Book> bookList;
    Library library;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userList = FXCollections.observableArrayList();
        bookList = FXCollections.observableArrayList();

        msgLabel.setText("");  //initializing message to blank
    }

    public void initData(Library library) throws SQLException {
        this.library = library;
        library.getLog(); //clears the log
        // initializing listviews
        lstUserList.setItems(userList);
        lstBookList.setItems(bookList);
        getUserList("");
        getBookList("");

        // adding listeners to search boxes
        txtUserSearch.textProperty().addListener((Observable, oldValue, newValue) -> {
            lstUserList.getSelectionModel().clearSelection();
            try {
                getUserList(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        txtBookSearch.textProperty().addListener((Observable, oldValue, newValue) -> {
            lstBookList.getSelectionModel().clearSelection();
            try {
                getBookList(newValue);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        // populating textboxes with list selection
        lstUserList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null)
                txtUserID.setText(newValue.getID() + "");
        });

        lstBookList.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue != null)
                txtBookID.setText(newValue.getID() + "");
        });

    }

    //get the current user list from the search query and add it to the userList observable list
    public void getUserList(String query) throws SQLException {
        //clears the observable list
        userList.clear();
        ArrayList<User> temp = library.searchUser(query); //get from model
        userList.addAll(temp);
    }

    //get the current book list from the search query and add it to the bookList observable list
    public void getBookList(String query) throws SQLException {
        //clear the observable list
        bookList.clear();
        ArrayList<Book> temp = library.searchBook(query); //get from model
        bookList.addAll(temp);
    }

    //method to launch the issue book page

    @FXML
    public void launchIssueBook(ActionEvent e) throws SQLException {
        String uid = txtUserID.getText();
        String bid = txtBookID.getText();
        if (uid.equals("") || bid.equals("")) {
            msgLabel.setText("User ID and/or Book ID cannot be empty ");
            msgLabel.setTextFill(Color.DARKRED);
            return;
        }

        boolean result = library.issueBook(Integer.parseInt(uid), Integer.parseInt(bid)); // update model
        msgLabel.setText(library.getLog()); //success or failure

        if (!result)
            msgLabel.setTextFill(Color.DARKRED);
        else
            msgLabel.setTextFill(Color.LIGHTGREEN);

    }


}
