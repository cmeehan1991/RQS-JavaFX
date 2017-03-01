/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package quote;

import connections.DbConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import tables.PackingListTable.*;

/**
 *
 * @author cmeehan
 */
public class NewQuote {

    private final String userID, customerName, contactName, contactEmail, contactPhone, phoneExtension, phoneType, tradeLane, pol, pod, tshp, commodityClass, handlingInstructions, commodityDescription, overseasApproval, operationalApproval, vesselScheduleApproval, oftCurrency, oft, oftUnit, mafiMinimumCurrency, mafiMinimumAmount, bafCurrency, baf, bafUnit, ecaCurrency, eca, ecaUnit, thcCurrency, thc, thcUnit, wfgCurrency, wfg, wfgUnit, docFee, warRisk, bookingNumber, reasonForDecline, internalComments, externalComments;
    private final boolean mafiMinimum, bafIncluded, ecaIncluded, thcIncluded, thcAttached, wfgIncluded, wfgAttached, docFeeIncluded, tariff, spot, contract, booking, ftf, decline;
    private final TableView packingListTable;

    public NewQuote(String userID, String customerName, String contactName, String contactEmail, String contactPhone, String phoneExtension, String phoneType, String tradeLane, String pol, String pod, String tshp, String commodityClass, String handlingInstructions, String commodityDescription, String operationalApproval, String overseasApproval, String vesselScheduleApproval, String oftCurrency, String oft, String oftUnit, Boolean mafiMinimum, String mafiMinimumCurrency, String mafiMinimumAmount, String bafCurrency, String baf, String bafUnit, Boolean bafIncluded, String ecaCurrency, String eca, String ecaUnit, Boolean ecaIncluded, String thcCurrency, String thc, String thcUnit, Boolean thcIncluded, Boolean thcAttached, String wfgCurrency, String wfg, String wfgUnit, Boolean wfgIncluded, Boolean wfgAttached, String docFee, Boolean docFeeIncluded, String warRisk, Boolean tariff, Boolean spot, Boolean contract, Boolean booking, String bookingNumber, Boolean ftf, Boolean decline, String reasonForDecline, String internalComments, String externalComments, TableView packingListTable) {
        this.userID = userID;
        this.customerName = customerName;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.phoneExtension = phoneExtension;
        this.phoneType = phoneType;
        this.tradeLane = tradeLane;
        this.pol = pol;
        this.pod = pod;
        this.tshp = tshp;
        this.commodityClass = commodityClass;
        this.handlingInstructions = handlingInstructions;
        this.commodityDescription = commodityDescription;
        this.operationalApproval = operationalApproval;
        this.overseasApproval = overseasApproval;
        this.vesselScheduleApproval = vesselScheduleApproval;
        this.oftCurrency = oftCurrency;
        this.oft = oft;
        this.oftUnit = oftUnit;
        this.mafiMinimum = mafiMinimum;
        this.mafiMinimumAmount = mafiMinimumAmount;
        this.mafiMinimumCurrency = mafiMinimumCurrency;
        this.bafCurrency = bafCurrency;
        this.baf = baf;
        this.bafUnit = bafUnit;
        this.bafIncluded = bafIncluded;
        this.ecaCurrency = ecaCurrency;
        this.eca = eca;
        this.ecaUnit = ecaUnit;
        this.ecaIncluded = ecaIncluded;
        this.thcCurrency = thcCurrency;
        this.thc = thc;
        this.thcUnit = thcUnit;
        this.thcIncluded = thcIncluded;
        this.thcAttached = thcAttached;
        this.wfgCurrency = wfgCurrency;
        this.wfg = wfg;
        this.wfgUnit = wfgUnit;
        this.wfgIncluded = wfgIncluded;
        this.wfgAttached = wfgAttached;
        this.docFee = docFee;
        this.docFeeIncluded = docFeeIncluded;
        this.warRisk = warRisk;
        this.tariff = tariff;
        this.spot = spot;
        this.booking = booking;
        this.bookingNumber = bookingNumber;
        this.decline = decline;
        this.reasonForDecline = reasonForDecline;
        this.ftf = ftf;
        this.contract = contract;
        this.internalComments = internalComments;
        this.externalComments = externalComments;
        this.packingListTable = packingListTable;
    }

    /*
    * This method will take the quote information and insert it into the ALL_QUOTES table
    * 
    * @returns String MySql generated key
     */
    public String insertNewQuote() {
        // Instantiate the database connection
        Connection conn = new DbConnection().connect();

        String sql = "INSERT INTO ALL_QUOTES (COMPANY, CONTACT, EMAIL, PHONE, EXTENSION, PHONE_TYPE, TRADE_LANE, POL, POD, TSHP, COMMODITY_CLASS, HANDLING_INSTRUCTIONS, COMMODITY_DESCRIPTION, OPERATIONAL_APPROVAL, OVERSEAS_APPROVAL, SCHEDULE_APPROVAL, OFT_CURRENCY, OFT_VALUE, OFT_UNIT, MAFI_MINIMUM, MAFI_MINIMUM_CURRENCY, MAFI_MINIMUM_CHARGE, BAF_CURRENCY, BAF_VALUE, BAF_UNIT, BAF_INCLUDED, ECA_CURRENCY, ECA_VALUE, ECA_UNIT, ECA_INCLUDED, THC_CURRENCY, THC_VALUE, THC_UNIT, THC_INCLUDED, THC_ATTACHED, WFG_CURRENCY, WFG_VALUE, WFG_UNIT, WFG_INCLUDED, WFG_ATTACHED, DOC_FEE_VALUE, DOC_FEE_INCLUDED, WAR_RISK, TYPE_TARIFF, TYPE_SPOT, TYPE_CONTRACT, TYPE_BOOKING, BOOKING_NUMBER, DATE_BOOKED, BOOKED_USER, TYPE_FTF, TYPE_DECLINE, REASON_FOR_DECLINE, INTERNAL_COMMENTS, EXTERNAL_COMMENTS, OWNER_ID) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            // Set up a prepared statement that will return the auto generated key. 
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, customerName);
            ps.setString(2, contactName);
            ps.setString(3, contactEmail);
            ps.setString(4, contactPhone);
            ps.setString(5, phoneExtension);
            ps.setString(6, phoneType);
            ps.setString(7, tradeLane);
            ps.setString(8, pol);
            ps.setString(9, pod);
            ps.setString(10, tshp);
            ps.setString(11, commodityClass);
            ps.setString(12, handlingInstructions);
            ps.setString(13, commodityDescription);
            ps.setString(14, operationalApproval);
            ps.setString(15, overseasApproval);
            ps.setString(16, vesselScheduleApproval);
            ps.setString(17, oftCurrency);
            ps.setString(18, oft);
            ps.setString(19, oftUnit);
            ps.setBoolean(20, mafiMinimum);
            ps.setString(21, mafiMinimumCurrency);
            ps.setString(22, mafiMinimumAmount);
            ps.setString(23, bafCurrency);
            ps.setString(24, baf);
            ps.setString(25, bafUnit);
            ps.setBoolean(26, bafIncluded);
            ps.setString(27, ecaCurrency);
            ps.setString(28, eca);
            ps.setString(29, ecaUnit);
            ps.setBoolean(30, ecaIncluded);
            ps.setString(31, thcCurrency);
            ps.setString(32, thc);
            ps.setString(33, thcUnit);
            ps.setBoolean(34, thcIncluded);
            ps.setBoolean(35, thcAttached);
            ps.setString(36, wfgCurrency);
            ps.setString(37, wfg);
            ps.setString(38, wfgUnit);
            ps.setBoolean(39, wfgIncluded);
            ps.setBoolean(40, wfgAttached);
            ps.setString(41, docFee);
            ps.setBoolean(42, docFeeIncluded);
            ps.setString(43, warRisk);
            ps.setBoolean(44, tariff);
            ps.setBoolean(45, spot);
            ps.setBoolean(46, contract);
            ps.setBoolean(47, booking);
            ps.setString(48, bookingNumber);
            if (booking) {
                ps.setString(49, getDate());
                ps.setString(50, userID);
            } else {
                ps.setString(49, "0000-00-00 00:00:00");
                ps.setString(50, null);
            }
            ps.setBoolean(51, ftf);
            ps.setBoolean(52, decline);
            ps.setString(53, reasonForDecline);
            ps.setString(54, internalComments);
            ps.setString(55, externalComments);
            ps.setString(56, userID);
            // Execute the statement
            ps.addBatch();
            ps.executeBatch();

            // Return the generated keys 
            ResultSet rs = ps.getGeneratedKeys();
            int lastKey = 1;
            if (rs.next()) {
                lastKey = rs.getInt(1);
                insertPackingList(packingListTable, lastKey, null);
            }
            return String.valueOf(lastKey);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        } finally {
            try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return null;
            }
        }
    }

    /*
    * This method will get the current date and time to insert into the DB when there is a new booking.
    * @returns String   "YYYY-mm-dd HH:mm:ss"
     */
    private String getDate() {
        Date date = new Date();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        return timeStamp;
    }

    /*
    * This method will insert the packing list items into the PACKING_LIST table.
    * 
    * @params  int, String      The unique key & version (if applicable) of the quote. 
    * @return boolean           Will return true if the packing list was successfully inserted.
     */
    private void insertPackingList(TableView packingListTable, int quoteID, String quoteVersion) throws SQLException{
        Connection conn = new DbConnection().connect();
        ObservableList<Cargo> cargo = packingListTable.getItems();
        String sql = "INSERT INTO PACKING_LIST (QUOTE_ID, QUOTE_VERSION, COMMODITY, QUANTITY, WEIGHT, LENGTH, WIDTH, HEIGHT, CUBIC_METERS, RT, TOTAL_CUBIC_METERS, TOTAL_RT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        cargo.forEach((item) -> {
            
            String commodity = item.getCommodity();
            Double quantity = Double.parseDouble(item.getQuantity());
            Double weight = Double.parseDouble(item.getWeight());
            Double length = Double.parseDouble(item.getLength());
            Double width = Double.parseDouble(item.getWidth());
            Double height = Double.parseDouble(item.getHeight());
            Double cubicMeters = Double.parseDouble(item.getCubicMeters());
            Double rt = (length * width)/10000/6.39;
            Double totalCubicMeters = cubicMeters * quantity;
            Double totalRT = rt * quantity;
            try {
                ps.setInt(1, quoteID);
                ps.setString(2, quoteVersion);
                ps.setString(3,commodity);
                ps.setDouble(4,quantity);
                ps.setDouble(5, weight);
                ps.setDouble(6,length);
                ps.setDouble(7, width);
                ps.setDouble(8, height);
                ps.setDouble(9, cubicMeters);
                ps.setDouble(10, rt);
                ps.setDouble(11, totalCubicMeters);
                ps.setDouble(12, totalRT);
                ps.addBatch();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        });
        ps.executeBatch();
    }
}
