/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import library.assistant.database.Database;

/**
 *
 * Data Access
 */
public class BookDAO {

    //Create
    public void addBook(Book book) throws SQLException {

        Connection conn = Database.getInstance().getConnection();
        String insertSql = "insert into lbassistant.books (id,title,author,publisher,is_available) values(?,?,?,?,?)";
        PreparedStatement preStmt = conn.prepareStatement(insertSql);
        preStmt.setString(1, book.getId());
        preStmt.setString(2, book.getTitle());
        preStmt.setString(3, book.getAuthor());
        preStmt.setString(4, book.getPublisher());
        preStmt.setBoolean(5, book.isAvailable());
        preStmt.execute();

    }

    //Retrieve
    public List<Book> getBooks() throws SQLException {

        Connection conn = Database.getInstance().getConnection();
        List<Book> list = new ArrayList<>();
        String selectSql = "select * from lbassistant.books";
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(selectSql);
        while (results.next()) {

            String id = results.getString("id");
            String title = results.getString("title");
            String author = results.getString("author");
            String publisher = results.getString("publisher");
            boolean available = results.getBoolean("is_available");
            Book book = new Book(id, title, author, publisher, available);
            list.add(book);

        }

        return list;
    }

    public Book getSearchBook(String bookId) throws SQLException {
        Connection conn = Database.getInstance().getConnection();
        String selectSql = "select * from lbassistant.books where id=?";
        PreparedStatement preStmt = conn.prepareStatement(selectSql);
        preStmt.setString(1, bookId);
        ResultSet result = preStmt.executeQuery();
        Book book = null;
        if (result.next()) {

            String id = result.getString("id");
            String title = result.getString("title");
            String author = result.getString("author");
            String publisher = result.getString("publisher");
            boolean available = result.getBoolean("is_available");
            book = new Book(id, title, author, publisher, available);

        }
        return book;
    }
    //Update

    public void getupdateBook(String bookId, boolean b) throws SQLException {

        Connection conn = Database.getInstance().getConnection();
        String updateSql = "update lbassistant.books set is_available=? where id =?";
        PreparedStatement preStmt = conn.prepareStatement(updateSql);
        preStmt.setBoolean(1, b);
        preStmt.setString(2, bookId);
        preStmt.execute();
    }

    //Delete
    public void deleteBook(String id) throws SQLException {

        Connection conn = Database.getInstance().getConnection();
        String deleteSql = "delete from lbassistant.books where id=?";
        PreparedStatement preStmt = conn.prepareStatement(deleteSql);
        preStmt.setString(1, id);
        preStmt.execute();
    }

    

}
