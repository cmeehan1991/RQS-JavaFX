/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import connections.DbConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import user.navigation.NavigationMain;

/**
 *
 * @author cmeehan
 */
public class UserLoginMain extends Application {

    private final Connection CONN = new DbConnection().connect();
    private Parent root = null;
    private Stage stage = null;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        this.root = FXMLLoader.load(getClass().getResource("UserSignInFXML.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add("css/usersigninfxml.css");
        
        this.stage = primaryStage;
        primaryStage.setTitle("User Log In | \"K\" Line RQS");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /*
    * This method will take the username and password that the user input and attempt to log in.
    * If the username or password are invalid then the user will be notified.
    * If the username and password are valid and the user has access the sign in window will close 
    * and the navigation window will open. 
    * 
    * @params username, password
    *
     */
    protected boolean userLogIn(String... params) {
        System.out.println("Loggin In");
        String username = params[0];
        String password = params[1];
        boolean hasAccess = false;

        String sql = "SELECT ID, HAS_ACCESS, RIGHTS FROM ALL_USERS WHERE USERNAME = ? AND PASSWORD = md5(?)";
        try {
            PreparedStatement stmt = CONN.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String userID = rs.getString("ID");
                String rights = rs.getString("RIGHTS");
                hasAccess = rs.getBoolean("HAS_ACCESS");
                new NavigationMain(userID, rights).start(new Stage());
            }
        } catch (SQLException | IOException ex) {
            System.out.println(ex.getMessage());
        }
        return hasAccess;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
