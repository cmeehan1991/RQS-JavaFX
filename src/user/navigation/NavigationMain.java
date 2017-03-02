/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.navigation;

import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author cmeehan
 */
public class NavigationMain extends Application {
    private final String USER_ID, RIGHTS;
    
    public NavigationMain(String userID, String rights){
        this.USER_ID = userID;
        this.RIGHTS = rights;
    }
    
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("NavigationFXML.fxml"));
        
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/css/navigationfxml.css");
        
        stage.setY(10.00);
        stage.setX(screenWidth() - stage.getWidth() / 2);
        stage.setTitle("RQS");
        stage.setScene(scene);
        stage.show();
        
        stage.setOnCloseRequest(event->{
            Platform.exit();
        });
        
    }
    
    private double screenWidth() {
        double width;
        Rectangle2D rectangle = Screen.getPrimary().getBounds();
        width = rectangle.getWidth();
        return width;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
