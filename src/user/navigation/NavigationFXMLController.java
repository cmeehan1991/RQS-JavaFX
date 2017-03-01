/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.navigation;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import user.newquote.NewQuoteMain;

/**
 * FXML Controller class
 *
 * @author cmeehan
 */
public class NavigationFXMLController implements Initializable {
    
    @FXML
    private Hyperlink userInfo;
    
    @FXML
    protected void openWindow(ActionEvent event){
        Hyperlink link = (Hyperlink) event.getSource();
        String linkName = link.getId();
        switch(linkName){
            case "userInfo":
                System.out.println("User Info");
                break;
            case "newQuote":
                System.out.println("New Quote");
                NewQuoteMain newQuote = new NewQuoteMain("1");
                break;
            case "updateQuote":
                System.out.println("Update Quote");
                break;
            case "publishRate":
                System.out.println("Publish");
                break;
            case "customerCenter":
                System.out.println("Customer Center");
                break;
            case "queryManager":
                System.out.println("Query Manager");
            default:
                break;
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
