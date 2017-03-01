/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tables;

import connections.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author cmeehan
 */
public class QuickViewCustomerTable {

    public QuickViewCustomerTable(TableView tableView) {
        quickViewCustomerTable(tableView);
    }

    public final ObservableList<Customer> data = FXCollections.observableArrayList();

    /*
    * This method will populate the quick view table with data. 
    * If the user decides to search for a customer the data will update as the user types
    * as well as when the search button is pressed.
    * 
    * @params tableView, searchTerms
    * 
    */
    private void quickViewCustomerTable(TableView tableView) {

        // Get the table columns and assign the cell value for populating with data.
        TableColumn idColumn = (TableColumn) tableView.getColumns().get(0);
        TableColumn companyNameColumn = (TableColumn) tableView.getColumns().get(1);
        TableColumn companyContactColumn = (TableColumn) tableView.getColumns().get(2);

        idColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("companyID"));
        companyNameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("companyName"));
        companyContactColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("primaryContact"));

        // Run the SQL to search the database. 
        // If no search params are entered then we will return all of the customers. 
        Connection conn = new DbConnection().connect();
        String sql = "SELECT ID AS 'ID', COMPANY_NAME AS 'COMPANY_NAME', CONCAT(PRIMARY_CONTACT_LAST_NAME, ', ',PRIMARY_CONTACT_FIRST_NAME) AS 'CONTACT_NAME' FROM ALL_CUSTOMERS";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                do {
                    String companyID = rs.getString("ID");
                    String companyName = rs.getString("COMPANY_NAME");
                    String primaryContact = rs.getString("CONTACT_NAME");
                    data.add(new Customer(companyID, companyName, primaryContact));
                } while (rs.next());
            }
            // Populate the table.
            tableView.setItems(data);
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    public static class Customer {

        public SimpleStringProperty companyID;
        public SimpleStringProperty companyName;
        public SimpleStringProperty primaryContact;

        public Customer(){}
        
        private Customer(String id, String name, String contact) {
            this.companyID = new SimpleStringProperty(id);
            this.companyName = new SimpleStringProperty(name);
            this.primaryContact = new SimpleStringProperty(contact);
        }

        public String getCompanyID() {
            return companyID.get();
        }

        public void setID(String id) {
            this.companyID.set(id);
        }
        
        public String getCompanyName() {
            return companyName.get();
        }

        public void setCompanyName(String name) {
            this.companyName.set(name);
        }

        public String getPrimaryContact() {
            return primaryContact.get();
        }

        public void setPrimaryContact(String contact) {
            this.primaryContact.set(contact);
        }
    }

}
