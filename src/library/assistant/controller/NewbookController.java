/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import library.assistant.database.Database;
import library.assistant.model.Book;
import library.assistant.model.BookDAO;
import library.assistant.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author user
 */
public class NewbookController implements Initializable {

    @FXML
    private JFXTextField bookidField;
    @FXML
    private JFXTextField bookTitleField;
    @FXML
    private JFXTextField authorField;
    @FXML
    private JFXButton savebtn;
    @FXML
    private JFXButton cancelbtn;
    @FXML
    private JFXTextField publisherField;

    private BookDAO bookDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookDAO = new BookDAO();
    }
     


    @FXML
    private void savebookInfo(ActionEvent event) {

        String id = bookidField.getText();
        String title = bookTitleField.getText();
        String author = authorField.getText();
        String publisher = publisherField.getText();

        //Validate
        if (id.isEmpty() || title.isEmpty() || author.isEmpty() || publisher.isEmpty()) {
            System.out.println("Please fill in all field.");
            return;

        }
        try {
            bookDAO.addBook(new Book(id, title, author, publisher, true));
            MessageBox.showMessage("Success", "Adding book success.");
            //   Alert alert = new Alert (AlertType.INFORMATION);
            //   alert.setTitle("Success");
            //   alert.setHeaderText(null);
            //   alert.setContentText("Adding Book success.");
            //   alert.show();
            
        } catch (SQLException ex) {
            //System.out.println("Fail.");
            MessageBox.showError("Error", "Adding book failed.");
            Logger.getLogger(NewbookController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void closeWindow(ActionEvent event) {

        Stage oldstage = (Stage) savebtn.getScene().getWindow();
        oldstage.close();
    }

}
