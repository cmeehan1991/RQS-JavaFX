/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user.newquote;

import com.itextpdf.text.DocumentException;
import connections.DbConnection;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import output.CreatePDF;
import quote.NewQuote;
import tables.PackingListTable;
import tables.PackingListTable.Cargo;

/**
 * FXML Controller class
 *
 * @author cmeehan
 */
public class NewQuoteFXMLController implements Initializable {

    public String userID;
    @FXML
    TableView packingListTable;
    @FXML
    ComboBox companyNameComboBox, reasonForDeclineComboBox, phoneTypeComboBox, tradeLaneComboBox, commodityClassComboBox, handlingInstructionsComboBox, oftCurrencyComboBox, oftUnitComboBox, mafiMinimumCurrencyComboBox, bafCurrencyComboBox, bafUnitComboBox, ecaCurrencyComboBox, ecaUnitComboBox, thcCurrencyComboBox, thcUnitComboBox, wfgCurrencyComboBox, wfgUnitComboBox, docFeeComboBox, warRiskComboBox;

    @FXML
    TextField contactNameTextField, contactEmailTextField, contactPhoneTextField, phoneExtensionTextField, polTextField, podTextField, tshpTextField, commodityDescriptionTextField, oftTextField, mafiMinimumTextField, bafTextField, ecaTextField, thcTextField, wfgTextField, bookingNumberTextField;
    @FXML
    CheckBox mafiMinimumCheckBox, bafIncludedCheckBox, ecaIncludedCheckBox, thcIncludedCheckBox, thcAttachedCheckBox, wfgIncludedCheckBox, wfgAttachedCheckBox, docFeeIncludedCheckBox, tariffTypeCheckBox, spotTypeCheckBox, contractTypeCheckBox, bookingTypeCheckBox, ftfTypeCheckBox, declineTypeCheckBox;
    @FXML
    TextArea internalCommentsTextArea, externalCommentsTextArea;

    @FXML
    RadioButton operationalApprovalYesRadioButton, operationalApprovalNoRadioButton, operationalApprovalPendingRadioButton,overseasApprovalYesRadioButton, overseasApprovalNoRadioButton, overseasApprovalPendingRadioButton, vesselScheduleYesRadioButton, vesselScheduleNoRadioButton, vesselSchedulePendingRadioButton;

    private String companyName, contactName, contactEmail, contactPhone, phoneExtension, phoneType, tradeLane, pol, pod, tshp, commodityClass, handlingInstructions, commodityDescription, operationalApproval, overseasApproval, vesselScheduleApproval, oftCurrency, oft, oftUnit, mafiMinimumCurrency, mafiMinimumAmount, bafCurrency, baf, bafUnit, ecaCurrency, eca, ecaUnit, thcCurrency, thc, thcUnit, wfgCurrency, wfg, wfgUnit, docFee, warRisk, bookingNumber, reasonForDecline, internalComments, externalComments;
    private boolean mafiMinimum, bafIncluded, ecaIncluded, thcIncluded, thcAttached, wfgIncluded, wfgAttached, docFeeIncluded, tariff, spot, contract, booking, ftf, decline;

    TableView tableView;

    /*
    * This will save the quote. 
    * Currently using the function to test the packinglist input
     */
    @FXML
    protected void saveQuote(ActionEvent event) {

    }

    /*
    * This event will get all of the input values
     */
    @FXML
    protected void submitQuote(ActionEvent event) {

        // Get the values from the user input and assign to variables
        companyName = companyNameComboBox.getValue().toString();
        contactName = contactNameTextField.getText();
        contactEmail = contactEmailTextField.getText();
        contactPhone = contactPhoneTextField.getText();
        phoneExtension = phoneExtensionTextField.getText();
        phoneType = phoneTypeComboBox.getSelectionModel().getSelectedItem().toString();
        tradeLane = tradeLaneComboBox.getSelectionModel().getSelectedItem().toString();
        pol = polTextField.getText();
        pod = podTextField.getText();
        tshp = tshpTextField.getText();
        commodityClass = commodityClassComboBox.getSelectionModel().getSelectedItem().toString();
        handlingInstructions = handlingInstructionsComboBox.getSelectionModel().getSelectedItem().toString();
        commodityDescription = commodityDescriptionTextField.getText();
        
        // Get the operational approval status
        if(operationalApprovalYesRadioButton.isSelected()){
            operationalApproval = "YES";
        }else if(operationalApprovalNoRadioButton.isSelected()){
            operationalApproval = "NO";
        }else if(operationalApprovalPendingRadioButton.isSelected()){
            operationalApproval = "PENDING";
        }else{
            operationalApproval = "N/A";
        }
        
        // Get the overseas approval status
        if(overseasApprovalYesRadioButton.isSelected()){
           overseasApproval = "YES";
        }else if(overseasApprovalNoRadioButton.isSelected()){
            overseasApproval = "NO";
        }else if(overseasApprovalPendingRadioButton.isSelected()){
            operationalApproval = "PENDING";
        }else{
            overseasApproval = "N/A";
        }
        
        // Get the vessel schedule approval status
        if(vesselScheduleYesRadioButton.isSelected()){
            vesselScheduleApproval = "YES";
        }else if(vesselScheduleNoRadioButton.isSelected()){
            vesselScheduleApproval = "NO";
        }else if(vesselSchedulePendingRadioButton.isSelected()){
            vesselScheduleApproval = "PENDING";
        }else{
            vesselScheduleApproval = "N/A";
        }
        oftCurrency = oftCurrencyComboBox.getSelectionModel().getSelectedItem().toString();
        oft = oftTextField.getText();
        oftUnit = oftUnitComboBox.getSelectionModel().getSelectedItem().toString();
        mafiMinimum = mafiMinimumCheckBox.isSelected();
        mafiMinimumCurrency = mafiMinimumCurrencyComboBox.getSelectionModel().getSelectedItem().toString();
        mafiMinimumAmount = mafiMinimumTextField.getText();
        bafCurrency = bafCurrencyComboBox.getSelectionModel().getSelectedItem().toString();
        baf = bafTextField.getText();
        bafUnit = bafUnitComboBox.getSelectionModel().getSelectedItem().toString();
        bafIncluded = bafIncludedCheckBox.isSelected();
        ecaCurrency = ecaCurrencyComboBox.getSelectionModel().getSelectedItem().toString();
        eca = ecaTextField.getText();
        ecaUnit = ecaUnitComboBox.getSelectionModel().getSelectedItem().toString();
        ecaIncluded = ecaIncludedCheckBox.isSelected();
        thcCurrency = thcCurrencyComboBox.getSelectionModel().getSelectedItem().toString();
        thc = thcTextField.getText();
        thcUnit = thcUnitComboBox.getSelectionModel().getSelectedItem().toString();
        thcIncluded = thcIncludedCheckBox.isSelected();
        thcAttached = thcAttachedCheckBox.isSelected();
        wfgCurrency = wfgCurrencyComboBox.getSelectionModel().getSelectedItem().toString();
        wfg = wfgTextField.getText();
        wfgUnit = wfgUnitComboBox.getSelectionModel().getSelectedItem().toString();
        wfgIncluded = wfgIncludedCheckBox.isSelected();
        wfgAttached = wfgAttachedCheckBox.isSelected();
        docFee = docFeeComboBox.getSelectionModel().getSelectedItem().toString();
        docFeeIncluded = docFeeIncludedCheckBox.isSelected();
        warRisk = warRiskComboBox.getSelectionModel().getSelectedItem().toString();
        tariff = tariffTypeCheckBox.isSelected();
        spot = spotTypeCheckBox.isSelected();
        contract = contractTypeCheckBox.isSelected();
        booking = bookingTypeCheckBox.isSelected();
        bookingNumber = bookingNumberTextField.getText();
        ftf = ftfTypeCheckBox.isSelected();
        decline = declineTypeCheckBox.isSelected();
        reasonForDecline = reasonForDeclineComboBox.getSelectionModel().getSelectedItem().toString();
        internalComments = internalCommentsTextArea.getText();
        externalComments = externalCommentsTextArea.getText();

        String newQuote = new NewQuote(userID, companyName, contactName, contactEmail, contactPhone, phoneExtension, phoneType, tradeLane, pol, pod, tshp, commodityClass, handlingInstructions, commodityDescription, operationalApproval, overseasApproval, vesselScheduleApproval, oftCurrency, oft, oftUnit, mafiMinimum, mafiMinimumCurrency, mafiMinimumAmount, bafCurrency, baf, bafUnit, bafIncluded, ecaCurrency, eca, ecaUnit, ecaIncluded, thcCurrency, thc, thcUnit, thcIncluded, thcAttached, wfgCurrency, wfg, wfgUnit, wfgIncluded, wfgAttached, docFee, docFeeIncluded, warRisk, tariff, spot, contract, booking, bookingNumber, ftf, decline, reasonForDecline, internalComments, externalComments, packingListTable).insertNewQuote();

        if (newQuote != null && !newQuote.equals("0")) {
            Dialog<ButtonType> confirmation = new Dialog<>();
            confirmation.getDialogPane().getButtonTypes().add(ButtonType.YES);
            confirmation.getDialogPane().getButtonTypes().add(ButtonType.NO);
            confirmation.setTitle("Success");
            confirmation.setHeaderText("Quote successfully Inserted");
            confirmation.setContentText("Quote ID: RQS" + newQuote + "." + "\n" + "Would you like to email this quote?");
            confirmation.showAndWait().filter(response -> Objects.equals(response, ButtonType.YES)).ifPresent(response -> {
                try {
                    new CreatePDF(newQuote, userID, companyName, contactName, contactEmail, contactPhone, phoneExtension, phoneType, tradeLane, pol, pod, tshp, commodityClass, handlingInstructions, commodityDescription, operationalApproval, overseasApproval, vesselScheduleApproval,oftCurrency, oft, oftUnit, mafiMinimum, mafiMinimumCurrency, mafiMinimumAmount, bafCurrency, baf, bafUnit, bafIncluded, ecaCurrency, eca, ecaUnit, ecaIncluded, thcCurrency, thc, thcUnit, thcIncluded, thcAttached, wfgCurrency, wfg, wfgUnit, wfgIncluded, wfgAttached, docFee, docFeeIncluded, warRisk, tariff, spot, contract, booking, bookingNumber, ftf, decline, reasonForDecline, internalComments, externalComments, packingListTable).rateQuote();
                } catch (FileNotFoundException | DocumentException ex) {
                    System.out.println(ex.getMessage());
                }
                confirmation.close();
            });
            confirmation.showAndWait().filter(response -> Objects.equals(response, ButtonType.NO)).ifPresent(response -> confirmation.close());
        } else {
            Dialog<ButtonType> confirmation = new Dialog<>();
            confirmation.getDialogPane().getButtonTypes().add(ButtonType.YES);
            confirmation.getDialogPane().getButtonTypes().add(ButtonType.NO);
            confirmation.setTitle("Error");
            confirmation.setHeaderText("There has been an error inserting the new quote.");
            confirmation.setContentText("Would you like to save this quote before closing?");
            confirmation.showAndWait().filter(response -> Objects.equals(response, ButtonType.YES)).ifPresent(response -> {
                System.out.println("YES");
                confirmation.close();
            });
            confirmation.showAndWait().filter(response -> Objects.equals(response, ButtonType.NO)).ifPresent(response -> confirmation.close());
        }

    }

    /*
    * This method will add a row to the packing list table when the button is clicked.
     */
    @FXML
    protected void addRow(ActionEvent event) {
        ObservableList<Cargo> items = FXCollections.observableArrayList(
                new Cargo(null, null, null, null, null, null, null)
        );
        packingListTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        packingListTable.getItems().addAll(items);

    }

    @FXML
    protected void getCustomer() throws InterruptedException, ExecutionException {

        companyNameComboBox.getItems().clear();

        if (companyNameComboBox.getEditor().getText().length() > 0) {
            companyNameComboBox.setVisibleRowCount(10);
            companyNameComboBox.show();
        } else {
            companyNameComboBox.hide();
        }

        Task<Map> task = new Task<Map>() {
            @Override
            protected Map call() throws Exception {
                Map<String, String> companyInfo = customers(companyNameComboBox.getEditor().getText());
                return companyInfo;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(false);
        thread.start();

        task.setOnSucceeded((WorkerStateEvent t) -> {
            Map<String, String> companyInfo = task.getValue();
            companyInfo.forEach((key, value) -> {
                Platform.runLater(() -> {
                    companyNameComboBox.getItems().addAll(value);
                });
            });
        });
    }


    /*
    * This method will return an array of company names that will subsequently populate the Customer Name combobox
    * 
    * @params String    This string is the input text from the combobox 
    * @results Array    This array is the result of the typed text in the combo box
     */
    private HashMap<String, String> customers(String input) throws SQLException {
        // Initialize the database connection 
        Connection conn = new DbConnection().connect();

        //The array list that will hold the results from the query.
        HashMap<String, String> results = new HashMap<>();

        // Instantiate the  sql statement to be used if there is input or if 
        // This is just the first time that the combobox has been loaded.
        String sql = null;

        // If the input is null that means that the user has not typed anything into the combobox
        // or the combobox has just been initialized.
        // This could also be applied if the user deletes all input from the combobox.
        if (input == null || input.isEmpty()) {
            sql = "SELECT ID, COMPANY_NAME FROM ALL_CUSTOMERS ORDER BY COMPANY_NAME ASC";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    // We are using this style while loop because there will be at least one result returned
                    // if rs.next() returned true. 
                    do {
                        results.put(rs.getString("ID"), rs.getString("COMPANY_NAME"));
                    } while (rs.next());
                } else {
                    System.out.println("None");
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            // We are going to widen the search as much as possible by search for each individual letter. 
            // In order to do so we must convert the search terms into an array by splitting between each character.
            // Then we will convert the array into a list, which can be mapped and have % inserted between each char.
            String[] inputArr = input.split("");
            List<String> inputList = Arrays.asList(inputArr);
            String likeInput = "%" + inputList.stream().map(i -> (i)).collect(Collectors.joining("%")) + "%";

            sql = "SELECT ID, COMPANY_NAME FROM ALL_CUSTOMERS WHERE COMPANY_NAME = ? OR COMPANY_NAME LIKE ? ORDER BY COMPANY_NAME ASC";
            try {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, input);
                ps.setString(2, likeInput);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    // We are using this style while loop because there will be at least one result returned
                    // if rs.next() returned true. 
                    do {
                        results.put(rs.getString("ID"), rs.getString("COMPANY_NAME"));
                    } while (rs.next());
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            } finally {
                conn.close();
            }
        }
        return results;
    }

    @FXML
    protected void disableCurrency(ActionEvent event) {
        System.out.println("Change");
        System.out.println(bafUnitComboBox.getSelectionModel().getSelectedItem().toString());
        bafUnit = (bafUnitComboBox.getSelectionModel() != null) ? bafUnitComboBox.getSelectionModel().getSelectedItem().toString() : null;
        if (bafUnit.equals("%")) {
            bafCurrencyComboBox.setDisable(true);
            bafCurrencyComboBox.getSelectionModel().select(0);
        } else {
            bafCurrencyComboBox.setDisable(false);
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Populate the company name combo box on intialization with all customers.
        try {
            getCustomer();
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println(ex.getMessage());
        }
        companyNameComboBox.setVisibleRowCount(10);
        companyNameComboBox.setOnKeyReleased((KeyEvent event) -> {
            try {
                getCustomer();
            } catch (InterruptedException | ExecutionException ex) {
                System.out.println(ex.getMessage());
            }
        });

        // Initialize the packing list table
        this.tableView = packingListTable.getSelectionModel().getTableView();
        tableView.setEditable(true);
        new PackingListTable(tableView);

    }
}
