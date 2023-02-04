package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Window;
import model.Library;
import model.User;
//import view.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddUserPage implements Initializable {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextArea txtAddress;
    @FXML
    private RadioButton radStudent;
    @FXML
    private RadioButton radFaculty;
    @FXML
    private DatePicker dtDateOfBirth;
    @FXML
    private Button btnRegister;

    private ToggleGroup userGroup;
    Library library;

    public void initData(Library library) {
        this.library = library;
        library.getLog(); //clears the log
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //initialize toggle group and add the faculty and student radio buttons
        userGroup = new ToggleGroup();
        radFaculty.setToggleGroup(userGroup);
        radStudent.setToggleGroup(userGroup);
    }

    public void registerNewUser(ActionEvent e) throws SQLException {
        //initialize his window as owner, used when invoking alert helper
        Window owner = btnRegister.getScene().getWindow();
        //gets the type of user register
        boolean type = true;
        RadioButton selected = (RadioButton) userGroup.getSelectedToggle();
        if (selected.getText().equals("Student"))
            type = true;
        else if (selected.getText().equals("Faculty"))
            type = false;

        //create new user and add it to library
        User newUser = new User(txtName.getText(), txtEmail.getText(), txtAddress.getText(), dtDateOfBirth.getValue(), type);

        library.addUser(newUser); // update model

        //hide the current window
        ((Node) (e.getSource())).getScene().getWindow().hide(); //hides the current window

        //invoke the alert
        AlertHelper.showAlert(Alert.AlertType.INFORMATION, owner, "Registration Successful", "Registered " + newUser.getName());
    }


}
