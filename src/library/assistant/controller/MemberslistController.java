/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.assistant.model.Member;
import library.assistant.model.MemberDAO;

/**
 * FXML Controller class
 *
 * @author user
 */
public class MemberslistController implements Initializable {

    @FXML
    private TableView<Member> memberTable;
    @FXML
    private TableColumn<Member, String> memberIDColumn;
    @FXML
    private TableColumn<Member, String> nameColumn;
    @FXML
    private TableColumn<Member, String> mobileColumn;
    @FXML
    private TableColumn<Member, String> addressColumn;

    
    private MemberDAO memberDAO;
    @FXML
    private MenuItem deleteItem;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        memberDAO = new MemberDAO();
        loadTableData();
        initColumn();
        
    }    

    private void initColumn() {
        
        memberIDColumn.setCellValueFactory(new PropertyValueFactory("id"));
         nameColumn.setCellValueFactory(new PropertyValueFactory("name"));
          mobileColumn.setCellValueFactory(new PropertyValueFactory("mobile"));
           addressColumn.setCellValueFactory(new PropertyValueFactory("address"));
    }

    private void loadTableData() {
        
        
        try {
           List<Member> list = memberDAO.getMembers();
             memberTable.getItems().addAll(list);
        } catch (SQLException ex) {
            Logger.getLogger(BookslistController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    
    }

    @FXML
    private void deleteMember(ActionEvent event) {
        
        Member selectedMember = memberTable.getSelectionModel().getSelectedItem();
        if(selectedMember != null)
        {
        
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confimation");
            alert.setHeaderText(null);
            alert.setContentText("Are yout sure do you want to delete this member");
            Optional<ButtonType> option = alert.showAndWait();
            
            if(option.get() == ButtonType.OK)
            {
                try {
                    memberDAO.deleteMember(selectedMember.getId());
                     memberTable.getItems().remove(selectedMember);
                } catch (SQLException ex) {
                    Logger.getLogger(MemberslistController.class.getName()).log(Level.SEVERE, null, ex);
                }
           
            
            }
        
        }
        
        System.out.println("Delete row.");
        
    }
    
}
