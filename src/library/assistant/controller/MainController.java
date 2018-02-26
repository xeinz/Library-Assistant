/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import library.assistant.database.Database;
import library.assistant.model.Book;
import library.assistant.model.BookDAO;
import library.assistant.model.Issue;
import library.assistant.model.IssueDAO;
import library.assistant.model.Member;
import library.assistant.model.MemberDAO;
import library.assistant.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author user
 */
public class MainController implements Initializable {

    @FXML
    private Button addbtn;
    @FXML
    private Button addmemberbtn;
    @FXML
    private Button bookListBtn;
    @FXML
    private Button memberListBtn;
    @FXML
    private JFXTextField bookIDField;
    @FXML
    private Text titleText;
    @FXML
    private Text authorText;
    @FXML
    private Text publisherText;

    private Database db;
    @FXML
    private JFXTextField memberIDField;
    @FXML
    private Text nameText;
    @FXML
    private Text mobileText;
    @FXML
    private Text addressText;
    @FXML
    private Text availableText;
    @FXML
    private JFXButton issueBookBtn;

    private BookDAO bookDAO;
    private MemberDAO memberDAO;
    private IssueDAO issueDAO;
    @FXML
    private JFXTextField issuedBookField;
    @FXML
    private Text bTitleText;
    @FXML
    private Text bAuthorText;
    @FXML
    private Text bPublisherText;
    @FXML
    private Text mNameText;
    @FXML
    private Text mMobileText;
    @FXML
    private Text mAddressText;
    @FXML
    private Text issueDateText;
    @FXML
    private Text renew_count;
    @FXML
    private JFXButton renewBtn;
    @FXML
    private JFXButton submissionBtn;
    @FXML
    private MenuItem severItem;
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        bookDAO = new BookDAO();
        memberDAO = new MemberDAO();
        issueDAO = new IssueDAO();

    }

    //   @FXML
    //   private void addNewBookWindow(ActionEvent event) throws IOException {
    //       Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/view/newbook.fxml"));
    //       Scene scene = new Scene(root);
    //       Stage stage = new Stage();
    //       stage.setScene(scene);
    //       stage.setTitle("New Book Window");
    //       stage.initOwner(addbtn.getScene().getWindow());
    //       stage.initModality(Modality.APPLICATION_MODAL);
    //       stage.show();
    //   }
    @FXML
    private void addNewBookWindow(ActionEvent event) throws IOException {

        loadWindow("New Book", "/library/assistant/view/newbook.fxml");
    }

    @FXML
    private void addNewMemberWindow(ActionEvent event) throws IOException {

        loadWindow("New Member", "/library/assistant/view/member.fxml");

    }

    @FXML
    private void BooksListViewWindow(ActionEvent event) throws IOException {

        loadWindow("Book List", "/library/assistant/view/bookslist.fxml");
    }

    @FXML
    private void MemberListViewWindow(ActionEvent event) throws IOException {

        loadWindow("Member List", "/library/assistant/view/memberslist.fxml");

    }

    private void loadWindow(String title, String url) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(url));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Library Assistant.");
        stage.initOwner(addbtn.getScene().getWindow());
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();

    }

    @FXML
    private void bookSearchInfo(ActionEvent event) {
        clearBookCache();

        String bookId = bookIDField.getText();
        if (bookId.isEmpty()) {
            System.out.println("Enter Book ID.");
            return;
        }

        try {
            Book book = bookDAO.getSearchBook(bookId);
            if (book != null) {
                titleText.setText(book.getTitle());
                authorText.setText(book.getAuthor());
                publisherText.setText(book.getPublisher());
                boolean isAvailable = book.isAvailable();
                String availableStr = isAvailable ? "Available" : "Not Available";
                // if (isAvailable) {
                //   availableStr = "Available";
                //} else {
                //  availableStr = "Not Available";
                //}
                availableText.setText(availableStr);

            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void memberSearchInfo(ActionEvent event) throws SQLException {
        clearMemberCache();
        String memberId = memberIDField.getText();
        if (memberId.isEmpty()) {
            System.out.println("Enter member ID.");
            return;
        }

        try {
            Member member = memberDAO.getSearchMember(memberId);
            if (member != null) {
                nameText.setText(member.getName());
                mobileText.setText(member.getMobile());
                addressText.setText(member.getAddress());
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void clearBookCache() {

        titleText.setText("-");
        authorText.setText("-");
        publisherText.setText("-");
        availableText.setText("-");
    }

    private void clearMemberCache() {

        nameText.setText("-");
        mobileText.setText("-");
        addressText.setText("-");
    }

    @FXML
    private void IssueBook(ActionEvent event) {

        String bookId = bookIDField.getText();
        String memberId = memberIDField.getText();

        if (bookId.isEmpty() || memberId.isEmpty()) {

            System.out.println("Enter book id and member id");
            return;
        }

        //  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        //  alert.setTitle("Confimation");
        //  alert.setHeaderText(null);
        //  alert.setContentText("Are you sure you want to issue this book");
        //  Optional<ButtonType> option = alert.showAndWait();
        Optional<ButtonType> option = MessageBox.showConfirmMessage("Confirmation", "Are you want to issue this book");
        if (option.get() == ButtonType.OK) {

            try {
                Book book = bookDAO.getSearchBook(bookId);
                if (book.isAvailable()) {
                    System.out.println("Continue...");
                    //book
                    bookDAO.getupdateBook(bookId, false);
                    //issue
                    issueDAO.saveIssueInfo(bookId, memberId);
                } else {
                    //System.out.println("This book is already issued.");
                    MessageBox.showError("Error", "This book is already issued.");

                }

            } catch (SQLException ex) {
                Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    @FXML
    private void searchIssuedBook(ActionEvent event) {

       
        clearIssuedCache();
        String bookId = issuedBookField.getText();

        if (bookId.isEmpty()) {

            MessageBox.showError("Error", "Please Fill in book id Field.");
            return;
        }

        try {
            Issue issue = issueDAO.getIssueInfo(bookId);
            if (issue != null) {

                Book book = bookDAO.getSearchBook(issue.getBookId());
                Member member = memberDAO.getSearchMember(issue.getMemberId());

                bTitleText.setText(book.getTitle());
                bAuthorText.setText(book.getAuthor());
                bPublisherText.setText(book.getPublisher());
                mNameText.setText(member.getName());
                mMobileText.setText(member.getMobile());
                mAddressText.setText(member.getAddress());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");
                String dateStr = dateFormat.format(issue.getIssueDate());
                issueDateText.setText("Issue Date: " + dateStr);
                // issueDateText.setText("Issue Date: "+issue.getIssueDate());
                renew_count.setText("Renew Count: " + issue.getRenewCount());
                
            } else {
                MessageBox.showError("Error", "Cannot find this book");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void clearIssuedCache() {
        bTitleText.setText("-");
        bAuthorText.setText("-");
        bPublisherText.setText("-");
        mNameText.setText("-");
        mMobileText.setText("-");
        mAddressText.setText("-");
        issueDateText.setText("-");
        renew_count.setText("-");
    }

    @FXML
    private void renewBook(ActionEvent event) {
        
         String bookId = issuedBookField.getText();
        
        try {
            if(issueDAO.checkBook(bookId)){
                
            issueDAO.updateIssueInfo(bookId);
            
            
            }else{
            MessageBox.showError("Error","Please search this book in issue table.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

    @FXML
    private void submissionBook(ActionEvent event) {
        //get book id from textfield
        //delete data from issue
        //update is_avaiable to true from book table
        String bookId = issuedBookField.getText();
        
        try {
            if(issueDAO.checkBook(bookId)){
                 issueDAO.deleteIssueInfo(bookId);
                 bookDAO.getupdateBook (bookId,true);
            
            }else{
            MessageBox.showError("Error","Please search this book in issue table.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    @FXML
    private void loadServerWindow(ActionEvent event) throws IOException {
        loadWindow("Sever", "/library/assistant/view/server.fxml");
    }
}
