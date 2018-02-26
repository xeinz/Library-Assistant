/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.main;


import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import library.assistant.database.Database;
import library.assistant.util.MessageBox;

/**
 *
 * @author user
 */
public class LibraryAssistant extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
       try{
        Database db = Database.getInstance();
       }catch (SQLException ex) {
           MessageBox.showAndWaitError("Error", "Cannot connect to sever.Please check server config.");
           ex.printStackTrace();
        }
        
        Parent root = FXMLLoader.load(getClass().getResource("/library/assistant/view/main.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Library Assistant");
        stage.setScene(scene);
        stage.getIcons().add(new Image("/library/assistant/icon/book.png"));
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
