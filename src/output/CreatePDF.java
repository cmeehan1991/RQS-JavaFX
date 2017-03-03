/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package output;

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.Style;
import com.itextpdf.layout.border.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import connections.DbConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TableView;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.dom4j.DocumentException;

/**
 *
 * @author cmeehan
 */
public class CreatePDF {

    private final Style BOLD_FONT = new Style().setFont(PdfFontFactory.createFont(FontConstants.TIMES_BOLD)).setFontSize(14);
    private final Style NORMAL_FONT = new Style().setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN)).setFontSize(14);
    private final Style SECTION_HEADING = new Style().setFont(PdfFontFactory.createFont(FontConstants.TIMES_BOLD)).setFontSize(18);
    private final Style SECTION_SUBHEADING = new Style().setFont(PdfFontFactory.createFont(FontConstants.TIMES_ITALIC)).setFontSize(14);
    private final Style NOTIFICATION_FONT = new Style().setFont(PdfFontFactory.createFont(FontConstants.TIMES_ITALIC)).setFontSize(14).setFontColor(Color.ORANGE).setUnderline();
    private final Style WARNING_FONT = new Style().setFont(PdfFontFactory.createFont(FontConstants.TIMES_ITALIC)).setFontSize(14).setFontColor(Color.RED).setBackgroundColor(Color.YELLOW).setUnderline();
    private final Style TERMS_FONT = new Style().setFont(PdfFontFactory.createFont(FontConstants.TIMES_ROMAN)).setFontSize(10);
    private final Border NO_BORDER = Border.NO_BORDER;

    private final float[] TWO_COLUMN_TABLE = new float[]{2f, 5f};
    private final float[] FOUR_COLUMN_TABLE = new float[]{2, 5, 2, 5};

    private final String USER_HOME = System.getProperty("user.home");

    private final String quoteID, userID, companyName, contactName, contactEmail, contactPhone, contactExtension, contactPhoneType, tradeLane, pol, pod, tshp, commodityClass, handlingInstructions, commodityDescription, operationalApproval, overseasApproval, vesselScheduleApproval, oftCurrency, oft, oftUnit, mafiMinimumCurrency, mafiMinimumAmount, bafCurrency, baf, bafUnit, ecaCurrency, eca, ecaUnit, thcCurrency, thc, thcUnit, wfgCurrency, wfg, wfgUnit, docFee, warRisk, bookingNumber, reasonForDecline, internalComments, externalComments;
    private final boolean mafiMinimum, bafIncluded, ecaIncluded, thcIncluded, thcAttached, wfgIncluded, wfgAttached, docFeeIncluded, tariffRate, spotRate, contractRate, booking, ftfRate, declineRate;
    private final TableView packingListTable;

    public CreatePDF(String quoteID, String userID, String companyName, String contactName, String contactEmail, String contactPhone, String phoneExtension, String phoneType, String tradeLane, String pol, String pod, String tshp, String commodityClass, String handlingInstructions, String commodityDescription, String operationalApproval, String overseasApproval, String vesselScheduleApproval, String oftCurrency, String oft, String oftUnit, Boolean mafiMinimum, String mafiMinimumCurrency, String mafiMinimumAmount, String bafCurrency, String baf, String bafUnit, Boolean bafIncluded, String ecaCurrency, String eca, String ecaUnit, Boolean ecaIncluded, String thcCurrency, String thc, String thcUnit, Boolean thcIncluded, Boolean thcAttached, String wfgCurrency, String wfg, String wfgUnit, Boolean wfgIncluded, Boolean wfgAttached, String docFee, Boolean docFeeIncluded, String warRisk, Boolean tariff, Boolean spot, Boolean contract, Boolean booking, String bookingNumber, Boolean ftf, Boolean decline, String reasonForDecline, String internalComments, String externalComments, TableView packingListTable) throws IOException {

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
        PdfDocument pdf = new PdfDocument(new PdfWriter(localFile()));
        Document document = new Document(pdf);

        document.add(userInformationSection());
        document.add(customerInformationSection());
        document.add(transitInformationSection());
        document.add(rateInformationSection());

        document.close();

        // Upload the file to the server
        remoteFileUpload(quoteID);
    }

    private void remoteFileUpload(String quoteID) {
        String server = "ns8139.hostgator.com";
        int port = 21;
        String user = "rqs@cbmwebdevelopment.com";
        String pass = "Wadiver15!";

        FTPClient ftpClient = new FTPClient();
        try {
            ftpClient.connect(server, port);
            ftpClient.login(user, pass);
            ftpClient.enterLocalPassiveMode();

            ftpClient.setFileType(FTP.LOCAL_FILE_TYPE);

            File localFile = new File(USER_HOME + "/Desktop/Quotes/RQS" + quoteID + ".pdf");

            String remoteFile = quoteID + ".pdf";
            InputStream inputStream = new FileInputStream(localFile);
            boolean done = ftpClient.storeFile(remoteFile, inputStream);
            inputStream.close();

            if (done) {
                System.out.println("File uploaded successfully");
            }

        } catch (IOException ex) {
            System.out.println("Error uploading file" + ex.getMessage());
        }
    }

    private FileOutputStream localFile() throws FileNotFoundException {

        // Check for quotes directory. 
        // If it doesn't exist create it. 
        File quotesDir = new File(USER_HOME + "/Desktop/Quotes");

        if (!quotesDir.exists()) {
            System.out.println("Does not exist");
            quotesDir.mkdirs();
        } else {
            System.out.println("Directory Exists");
        }
        FileOutputStream localFile = new FileOutputStream(USER_HOME + "/Desktop/Quotes/RQS" + quoteID + ".pdf");
        return localFile;
    }

    private Table userInformationSection() {
        Table table = new Table(TWO_COLUMN_TABLE);
        Map userInformation = userInformation(userID);

        table.addCell(new Cell()
                .add(new Paragraph("\"K\" Line Rep:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph(userInformation.get("LAST NAME") + ", " + userInformation.get("FIRST NAME") + " - " + userInformation.get("ROLE"))
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Email:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph(userInformation.get("PRIMARY_EMAIL").toString())
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Tel:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph(userInformation.get("PRIMARY_PHONE").toString())
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Office:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph(userInformation.get("OFFICE_LOCATION").toString())
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        return table;
    }

    private Table customerInformationSection() {
        Table table = new Table(FOUR_COLUMN_TABLE);

        table.addCell(new Cell()
                .add(new Paragraph("Company:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell(2, 3)
                .add(new Paragraph(companyName)
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Contact:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph(contactName)
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Email:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph(contactEmail)
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Phone:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        String phone = null;
        if (contactExtension != null) {
            phone = contactPhone + " ext. " + contactExtension;
        } else {
            phone = contactPhone;
        }

        table.addCell(new Cell()
                .add(new Paragraph(phone)
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        return table;
    }

    private Table transitInformationSection() {
        Table table = new Table(FOUR_COLUMN_TABLE);
        Cell cell;

        table.addCell(new Cell()
                .add(new Paragraph("Trade Lane:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph(tradeLane)
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Load/Discharge Ports:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell(1, 3)
                .add(new Paragraph(pol + "/" + pod)
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        if (tshp != null) {
            table.addCell(new Cell()
                    .add(new Paragraph("via:")
                            .addStyle(BOLD_FONT))
                    .setBorder(NO_BORDER));

            table.addCell(new Cell()
                    .add(new Paragraph(tshp)
                            .addStyle(NORMAL_FONT))
                    .setBorder(NO_BORDER));
        }

        table.addCell(new Cell()
                .add(new Paragraph("Terms:*")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Port - Port")
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        if (vesselScheduleApproval.equals("PENDING")) {
            table.addCell(new Cell()
                    .add(new Paragraph("Subject to vessel scheduling")
                            .addStyle(NOTIFICATION_FONT))
                    .setBorder(NO_BORDER));
        }

        return table;
    }

    private String currency(String curr) {
        String currency = curr;
        switch (curr) {
            case "USD":
                currency = "$";
                break;
            case "EUR":
                currency = "€";
                break;
            case "JPY":
                currency = "¥";
                break;
            case "GBP":
                currency = "£";
                break;
            default:
                break;
        }
        return currency;
    }

    private Table rateInformationSection() {
        Table table = new Table(TWO_COLUMN_TABLE);
        Cell cell;

        table.addCell(new Cell()
                .add(new Paragraph("Base Ocean Freight:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph(currency(oftCurrency) + oft + " per " +oftUnit)
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        if(mafiMinimum){
            table.addCell(new Cell()
            .add(new Paragraph("MAFI minimum:*")
            .addStyle(BOLD_FONT))
            .setBorder(NO_BORDER));
            
            table.addCell(new Cell()
                .add(new Paragraph(currency(mafiMinimumCurrency) + mafiMinimumAmount + " per MAFI")
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));
        }
        
        table.addCell(new Cell(1, 2)
        .add(new Paragraph("Subject to")
        .addStyle(SECTION_SUBHEADING))
        .setBorder(NO_BORDER)
        .setHorizontalAlignment(HorizontalAlignment.CENTER));
        
        table.addCell(new Cell()
                .add(new Paragraph("Bunker Surcharge:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph()
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("ECA Surcharge:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph()
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Terminal Handling (Origin")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph()
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Wharfage (Origin):")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph()
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph("Documentation Fee:")
                        .addStyle(BOLD_FONT))
                .setBorder(NO_BORDER));

        table.addCell(new Cell()
                .add(new Paragraph()
                        .addStyle(NORMAL_FONT))
                .setBorder(NO_BORDER));
        
        return table;
    }
}
