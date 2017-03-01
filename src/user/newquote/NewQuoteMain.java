/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.newquote;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author cmeehan
 */
public class NewQuoteMain extends Application {

    public TextField customerName;
    public String userID;
    public static String customerNameText;

    public NewQuoteMain(String userID) {
        if (userID == null) {
            return;
        } else {
            this.userID = userID;
            try {
                start(new Stage());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    public String userID() {
        return this.userID;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewQuoteFXML.fxml"));
        Parent root = (Parent)fxmlLoader.load();
        
        NewQuoteFXMLController controller = fxmlLoader.<NewQuoteFXMLController>getController();
        controller.userID = this.userID;
        
        Scene scene = new Scene(root);
                
        primaryStage.setTitle("New Quote | RQS");
        primaryStage.setScene(scene);
        scene.getStylesheets().add("css/newquotefxml.css");
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
