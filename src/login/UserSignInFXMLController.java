/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author cmeehan
 */
public class UserSignInFXMLController implements Initializable {

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;

    @FXML
    protected void signIn(ActionEvent event) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (!username.equals("") && !password.equals("")) {
            if(new UserLoginMain().userLogIn(username, password) == true){
                Stage stage = (Stage) usernameTextField.getScene().getWindow();
                stage.close();
            }else{
                usernameTextField.requestFocus();
                passwordField.setText("");
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Login Error");
                alert.setHeaderText("Login Error: access denied");
                alert.setContentText("The username and password you entered do not match our records. Please try again or contact your systems administrator for assistance.");
                alert.showAndWait();
            }
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
