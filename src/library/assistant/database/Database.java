/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import library.assistant.model.Book;
import library.assistant.model.Member;

/**
 *
 * @author user
 */
public class Database {

    private Connection conn;

    private static Database db;
    private Preferences prefs;

    private Database() throws SQLException {

        connect();
        createDatabase();
        createTables();

    }

    public static Database getInstance() throws SQLException {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    public void connect() throws SQLException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        prefs = Preferences.userRoot().node("lbassistant");
        String host = prefs.get("host", "localhost");
        int port = prefs.getInt("port", 3306);
        String name = prefs.get("name", "root");
        String pass =prefs.get("password","");
        conn = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/", name , pass);
    }

    public void createDatabase() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("create database if not exists lbassistant");
        System.out.println("Database Created..");
    }

    public void createTables() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("create table if not exists lbassistant.books(id varchar(255) primary key,title varchar(255), author varchar(255), publisher varchar(255), is_available boolean)");
        System.out.println("Book table Created..");

        Statement stmt2 = conn.createStatement();
        stmt2.execute("create table if not exists lbassistant.members(id varchar(255) primary key,name varchar(255), mobile varchar(255), address varchar(255))");
        System.out.println("Member table Created..");

        Statement stmt3 = conn.createStatement();
        stmt3.execute("create table if not exists lbassistant.issue(book_id varchar(255),member_id varchar(255), issue_date date, renew_count int, foreign key (book_id) references books(id), foreign key (member_id) references members(id))");
        System.out.println("Issue table Created..");

    }

    public void disconnect() throws SQLException {
        if (conn != null) {
            conn.close();
        }
    }

    public Connection getConnection() {
        return conn;
    }
}
