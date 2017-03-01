/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers.quickview;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author cmeehan
 */
public final class CustomerQuickViewMain extends Application {

    private String companyID;
    private TextField companyName;
    Stage stage, prevStage;
    protected Label companyNameLabel;
    
   public CustomerQuickViewMain(){};
    
    public CustomerQuickViewMain(Stage prevStage) throws IOException {
        this.prevStage = prevStage;
        start(new Stage());
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerQuickViewFXML.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        Scene scene = new Scene(root);

        this.stage = primaryStage;
        stage.setTitle("Customer Quick View | RQS");
        stage.setScene(scene);
        stage.show();
    }
    
    public void companyName(String companyName) throws IOException{

                
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
