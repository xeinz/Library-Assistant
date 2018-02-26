/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.assistant.util.MessageBox;

/**
 * FXML Controller class
 *
 * @author user
 */
public class SeverController implements Initializable {

    @FXML
    private PasswordField passwordField;
    @FXML
    private Spinner<Integer> portSpinnerField;
    @FXML
    private Button saveBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private TextField hostField;
    @FXML
    private TextField nameField;

    private Preferences prefs;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        prefs = Preferences.userRoot().node("lbassistant");
        String host = prefs.get("host", "localhost");
        int port = prefs.getInt("port", 3306);
        String name = prefs.get("name", "root");
        String pass = prefs.get("password", "");
        hostField.setText(host);
        nameField.setText(name);
        passwordField.setText(pass);
        // Setting Value to PortSpinner.
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(3300, 3320, 3306);
        portSpinnerField.setValueFactory(valueFactory);
    }

    @FXML
    private void saveSeverConfiguration(ActionEvent event) {

        prefs.put("host", hostField.getText());
        prefs.putInt("port", portSpinnerField.getValue());
        prefs.put("name", nameField.getText());
        prefs.put("password", passwordField.getText());

        MessageBox.showAndWaitMessage("Success", "Please restart the application.");
        Stage oldstage = (Stage) cancelBtn.getScene().getWindow();
        oldstage.close();

    }

    @FXML
    private void closeWindow(ActionEvent event) {

        Stage oldstage = (Stage) cancelBtn.getScene().getWindow();
        oldstage.close();

    }

}
