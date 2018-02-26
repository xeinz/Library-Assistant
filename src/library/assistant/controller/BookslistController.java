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
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import library.assistant.database.Database;
import library.assistant.model.Book;
import library.assistant.model.BookDAO;

/**
 * FXML Controller class
 *
 * @author user
 */
public class BookslistController implements Initializable {

    @FXML
    private TableView<Book> bookTableView;
    @FXML
    private TableColumn<Book, String> bookIDColumn;
    @FXML
    private TableColumn<Book, String> titleColumn;
    @FXML
    private TableColumn<Book, String> authorColumn;
    @FXML
    private TableColumn<Book, String> publisherColumn;

    private Database db;
    @FXML
    private MenuItem deleteItem;
    
    private BookDAO bookDAO;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        bookDAO = new BookDAO();
        initColumn();
        loadTableData();
    }

    private void initColumn() {

        bookIDColumn.setCellValueFactory(new PropertyValueFactory("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory("publisher"));
    }

    private void loadTableData() {

        try {
            List<Book> list = bookDAO.getBooks();
            bookTableView.getItems().addAll(list);
        } catch (SQLException ex) {
            Logger.getLogger(BookslistController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void deleteBook(ActionEvent event) {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();

        if (selectedBook != null) {

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confimation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete this book");
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == ButtonType.OK) {
                try {

                    bookDAO.deleteBook(selectedBook.getId());
                    System.out.println(selectedBook.getId());
                    bookTableView.getItems().remove(selectedBook);
                } catch (SQLException ex) {
                    Logger.getLogger(BookslistController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //bookTableView.getItems().remove(selectedBook);
        System.out.println("Delete row.");
    }

}
