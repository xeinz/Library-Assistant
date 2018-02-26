/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package library.assistant.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import library.assistant.database.Database;

/**
 *
 * @author user
 */
public class IssueDAO {

    public void saveIssueInfo(String bookId, String memberId) throws SQLException {

        Connection conn = Database.getInstance().getConnection();
        String insertSql = "insert into lbassistant.issue(book_id, member_id, issue_date,renew_count) values(?,?,curdate(),0)";
        PreparedStatement preStmt = conn.prepareStatement(insertSql);
        preStmt.setString(1, bookId);
        preStmt.setString(2, memberId);
        preStmt.execute();
    }

    public Issue getIssueInfo(String bookId) throws SQLException {

        Connection conn = Database.getInstance().getConnection();
        String selectSql = "select * from lbassistant.issue where book_id=?";
        PreparedStatement preStmt = conn.prepareStatement(selectSql);
        preStmt.setString(1, bookId);
        ResultSet result = preStmt.executeQuery();
        Issue issue = null;
        if (result.next()) {

            String memberId = result.getString("member_id");
            Date issueDate = result.getDate("issue_date");
            int renewCount = result.getInt("renew_count");
            issue = new Issue(bookId, memberId, issueDate, renewCount);

        }
        return issue;
    }

    public void deleteIssueInfo(String bookId) throws SQLException {
        
        Connection conn = Database.getInstance().getConnection();
        String deleteSql = "delete from lbassistant.issue where book_id =?";
        PreparedStatement preStmt = conn.prepareStatement(deleteSql);
        preStmt.setString(1, bookId);
        preStmt.execute();
    }

    public boolean checkBook(String bookId) throws SQLException {
        
        Connection conn = Database.getInstance().getConnection();
        String selectSql = "select count(*) as count from lbassistant.issue where book_id =?";
        PreparedStatement preStmt = conn.prepareStatement(selectSql);
        preStmt.setString(1, bookId);
        ResultSet result = preStmt.executeQuery();
        result.next();
        int count = result.getInt("count");
        boolean check = count==0? false:true;
        return check;
    }

    public void updateIssueInfo(String bookId) throws SQLException {
        
          Connection conn = Database.getInstance().getConnection();
        String updateSql = "update lbassistant.issue set renew_count=renew_count+1 where book_id =?";
        PreparedStatement preStmt = conn.prepareStatement(updateSql);
        preStmt.setString(1, bookId);
        preStmt.execute();
    }
}
