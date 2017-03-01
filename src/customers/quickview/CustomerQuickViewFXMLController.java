/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customers.quickview;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import tables.QuickViewCustomerTable;
import tables.QuickViewCustomerTable.Customer;

/**
 * FXML Controller class
 *
 * @author cmeehan
 */
public class CustomerQuickViewFXMLController implements Initializable {

    @FXML
    TableView customerQuickViewTable;

    @FXML
    TextField customerName;

    private QuickViewCustomerTable qvct;

    private void loadTable() {
        qvct = new QuickViewCustomerTable(customerQuickViewTable);
    }

    @FXML
    protected void getDoubleClick(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                try {
                    getCompanyInformation(); 
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private void getCompanyInformation() throws IOException {
        Customer customer = (Customer) customerQuickViewTable.getSelectionModel().getSelectedItem();        
        new CustomerQuickViewMain().companyName(customer.getCompanyName());
    }

    @FXML
    protected void getSubmitButton(ActionEvent event) {
        try {
            getCompanyInformation();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    loadTable();
    }

}
