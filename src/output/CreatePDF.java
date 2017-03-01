/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package output;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import connections.DbConnection;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TableView;

/**
 *
 * @author cmeehan
 */
public class CreatePDF {

    private final Font BOLD_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
    private final Font NORMAL_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.NORMAL);
    private final Font SECTION_HEADING = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private final Font SECTION_SUBHEADING = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.ITALIC);
    private final Font NOTIFICATION_FONT = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLDITALIC);
    private final int NO_BORDER = PdfPCell.NO_BORDER;

    private final String quoteID, userID, companyName, contactName, contactEmail, contactPhone, contactExtension, contactPhoneType, tradeLane, pol, pod, tshp, commodityClass, handlingInstructions, commodityDescription, operationalApproval, overseasApproval, vesselScheduleApproval, oftCurrency, oft, oftUnit, mafiMinimumCurrency, mafiMinimumAmount, bafCurrency, baf, bafUnit, ecaCurrency, eca, ecaUnit, thcCurrency, thc, thcUnit, wfgCurrency, wfg, wfgUnit, docFee, warRisk, bookingNumber, reasonForDecline, internalComments, externalComments;
    private final boolean mafiMinimum, bafIncluded, ecaIncluded, thcIncluded, thcAttached, wfgIncluded, wfgAttached, docFeeIncluded, tariffRate, spotRate, contractRate, booking, ftfRate, declineRate;
    private final TableView packingListTable;

    public CreatePDF(String quoteID, String userID, String companyName, String contactName, String contactEmail, String contactPhone, String phoneExtension, String phoneType, String tradeLane, String pol, String pod, String tshp, String commodityClass, String handlingInstructions, String commodityDescription, String operationalApproval, String overseasApproval, String vesselScheduleApproval, String oftCurrency, String oft, String oftUnit, Boolean mafiMinimum, String mafiMinimumCurrency, String mafiMinimumAmount, String bafCurrency, String baf, String bafUnit, Boolean bafIncluded, String ecaCurrency, String eca, String ecaUnit, Boolean ecaIncluded, String thcCurrency, String thc, String thcUnit, Boolean thcIncluded, Boolean thcAttached, String wfgCurrency, String wfg, String wfgUnit, Boolean wfgIncluded, Boolean wfgAttached, String docFee, Boolean docFeeIncluded, String warRisk, Boolean tariff, Boolean spot, Boolean contract, Boolean booking, String bookingNumber, Boolean ftf, Boolean decline, String reasonForDecline, String internalComments, String externalComments, TableView packingListTable) {

        this.quoteID = quoteID;
        this.userID = userID;

        this.companyName = companyName;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
        this.contactPhone = contactPhone;
        this.contactExtension = phoneExtension;
        this.contactPhoneType = phoneType;

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
        this.mafiMinimumCurrency = mafiMinimumCurrency;
        this.mafiMinimumAmount = mafiMinimumAmount;
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

        this.tariffRate = tariff;
        this.spotRate = spot;
        this.contractRate = contract;
        this.booking = booking;
        this.bookingNumber = bookingNumber;
        this.ftfRate = ftf;
        this.declineRate = decline;
        this.reasonForDecline = reasonForDecline;

        this.internalComments = internalComments;
        this.externalComments = externalComments;

        this.packingListTable = packingListTable;
        
        // Set font properties
        NOTIFICATION_FONT.setColor(255,0,0);
    }

    /*
    * Get the user's information based on the user ID passed from the quote form.
    *
    * @param    String userID
    * @return   Map<String, String> User information
     */
    private Map<String, String> userInformation(String userID) {
        Map<String, String> userInformation = new HashMap<>();

        // Connect to the sql database
        Connection conn = new DbConnection().connect();

        // Get the first and last name, email, and position from the user who issued the quote
        String sql = "SELECT FIRST_NAME, LAST_NAME, ROLE, IF(OFFICE_LOCATION_STATE IS NOT NULL, CONCAT(OFFICE_LOCATION_CITY, ', ', OFFICE_LOCATION_STATE, ', ', OFFICE_LOCATION_COUNTRY), CONCAT(OFFICE_LOCATION_CITY,', ', OFFICE_LOCATION_COUNTRY)) AS 'OFFICE_LOCATION', PRIMARY_EMAIL, PRIMARY_PHONE FROM ALL_USERS WHERE ID = ?";
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                userInformation.put("IS USER", "TRUE");
                userInformation.put("FIRST NAME", rs.getString("FIRST_NAME"));
                userInformation.put("LAST NAME", rs.getString("LAST_NAME"));
                userInformation.put("OFFICE_LOCATION", rs.getString("OFFICE_LOCATION"));
                userInformation.put("PRIMARY_EMAIL", rs.getString("PRIMARY_EMAIL"));
                userInformation.put("PRIMARY_PHONE", rs.getString("PRIMARY_PHONE"));
                userInformation.put("ROLE", rs.getString("ROLE"));
            } else {
                userInformation.put("IS USER", "FALSE");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return userInformation;
    }

    /*
    * This method creates a PDF document with the quote information. 
    * The PDF is saved on the user's machine and two diretories on the server. 
    * One directory is a group directory where all PDF quotes are saved. 
    * The other directory is for the individual user - userID_quotes/
     */
    public void rateQuote() throws FileNotFoundException, DocumentException {

        String userHome = System.getProperty("user.home");
        FileOutputStream localFile = new FileOutputStream(userHome + "/Desktop/test.pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, localFile);
        document.open();
        PdfPTable table = new PdfPTable(1);
        PdfPCell cell;

        cell = new PdfPCell(userInformationSection());
        cell.setPaddingTop(10f);
        cell.setPaddingBottom(10f);
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(customerInformationSection());
        cell.setPaddingTop(10f);
        cell.setPaddingBottom(10f);
        table.addCell(cell);

        cell = new PdfPCell(transitInformationSection());
        cell.setPaddingTop(10f);
        cell.setPaddingBottom(10f);
        table.addCell(cell);
        
        cell = new PdfPCell(rateInformationSection());
        cell.setPaddingTop(10f);
        cell.setPaddingBottom(10f);
        table.addCell(cell);

        document.addHeader("Header", "This is the header");
        document.add(table);
        document.close();
    }

    private PdfPTable userInformationSection() {
        PdfPTable table = new PdfPTable(new float[]{1, 5});
        PdfPCell cell;
        Map userInformation = userInformation(userID);

        cell = new PdfPCell(new Paragraph("\"K\" Line Rep:", BOLD_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(userInformation.get("LAST NAME") + ", " + userInformation.get("FIRST NAME") + " - " + userInformation.get("ROLE"), NORMAL_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Email:", BOLD_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(userInformation.get("PRIMARY_EMAIL").toString(), NORMAL_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Tel:", BOLD_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(userInformation.get("PRIMARY_PHONE").toString(), NORMAL_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Office:", BOLD_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(userInformation.get("OFFICE_LOCATION").toString(), NORMAL_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        return table;
    }

    private PdfPTable customerInformationSection() {
        PdfPTable table = new PdfPTable(new float[]{1, 5});
        PdfPCell cell;

        cell = new PdfPCell(new Paragraph("Company", BOLD_FONT));
        cell.setBorder(NO_BORDER);

        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(companyName, NORMAL_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Contact:", BOLD_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(contactName, NORMAL_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        table.addCell("Email:");
        table.addCell(contactEmail);

        table.addCell("Phone:");
        if (contactExtension != null) {
            table.addCell(contactPhone + " ext. " + contactExtension);
        } else {
            table.addCell(contactPhone);
        }

        table.addCell("Type:");
        table.addCell(contactPhoneType);

        return table;
    }

    private PdfPTable transitInformationSection() {
        PdfPTable table = new PdfPTable(new float[]{1, 5});
        PdfPCell cell;

        cell = new PdfPCell(new Paragraph("Trade Lane:", BOLD_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(tradeLane, NORMAL_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Load/Discharge Ports:", BOLD_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(pol + "/" + pod, NORMAL_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);

        if (tshp != null) {
            cell = new PdfPCell(new Paragraph("via:", BOLD_FONT));
            cell.setBorder(NO_BORDER);
            table.addCell(cell);

            cell = new PdfPCell(new Paragraph(tshp, NORMAL_FONT));
            cell.setBorder(NO_BORDER);
            table.addCell(cell);
        }

        if (vesselScheduleApproval.equals("PENDING")) {
            cell = new PdfPCell(new Paragraph("Subject to vessel scheduling."));
            cell.setBorder(NO_BORDER);
            table.addCell(cell);
        }

        return table;
    }
    
    private PdfPTable rateInformationSection(){
        PdfPTable table = new PdfPTable(new float[]{1, 5});
        PdfPCell cell;
        
        cell = new PdfPCell(new Paragraph("Base OFT:", BOLD_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph(oftCurrency + oft + " per " + oftUnit , NORMAL_FONT));
        cell.setBorder(NO_BORDER);
        table.addCell(cell);
        
        cell = new PdfPCell(new Paragraph("Subject to", SECTION_SUBHEADING));
        cell.setBorder(NO_BORDER);
        cell.setColspan(2);
        table.addCell(cell);
        
        
        return table;
    }
}
