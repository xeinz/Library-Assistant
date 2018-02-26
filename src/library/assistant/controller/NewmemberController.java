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
import javafx.stage.Stage;
import library.assistant.model.MemberDAO;

/**
 * FXML Controller class
 *
 * @author user
 */
public class NewmemberController implements Initializable {

     @FXML
    private JFXTextField memberIdField;
   
    @FXML
    private JFXTextField nameField;
    
    @FXML
    private JFXTextField mobileField;
    
    
    @FXML
    private JFXTextField addressField;
    
    @FXML
    private JFXButton savebtn;
   
    @FXML
    private JFXButton cancelbtn;

    private MemberDAO memberDAO;
   
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         memberDAO = new MemberDAO();
    }    

    @FXML
    private void memberBtnInfo(ActionEvent event) {
        String id = memberIdField.getText();
        String name = nameField.getText();
        String mobile = mobileField.getText();
        String address = addressField.getText();
        
        if(id.isEmpty() || name.isEmpty() || mobile.isEmpty() || address.isEmpty()){
            System.out.println("Please fill all the Field.");
            return;
        }
        System.out.println("Continue...");
        
        try {
            memberDAO.addMember(id, name, mobile, address);
            Alert alert = new Alert (Alert.AlertType.INFORMATION);
             alert.setTitle("Success");
             alert.setHeaderText(null);
             alert.setContentText("Adding Book success.");
             alert.show();
        } catch (SQLException ex) {
            Logger.getLogger(NewmemberController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage oldstage =(Stage) savebtn.getScene().getWindow();
        oldstage.close();
    }
    
}
