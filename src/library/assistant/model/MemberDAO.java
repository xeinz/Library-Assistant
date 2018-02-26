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
 * @author user
 */
public class MemberDAO {

    public void addMember(String id, String name, String mobile, String address) throws SQLException {
        
        Connection conn = Database.getInstance().getConnection();
        String insertSql = "insert into lbassistant.members(id,name,mobile,address) values (?,?,?,?)";
        PreparedStatement preStmt = conn.prepareStatement(insertSql);
        preStmt.setString(1, id);
        preStmt.setString(2, name);
        preStmt.setString(3, mobile);
        preStmt.setString(4, address);
        preStmt.execute();
    }

    public List<Member> getMembers() throws SQLException {
        
        Connection conn = Database.getInstance().getConnection();
        List<Member> list = new ArrayList<>();
        String selectSql = "select * from lbassistant.members";
        Statement stmt = conn.createStatement();
        ResultSet results = stmt.executeQuery(selectSql);
        while (results.next()) {

            String id = results.getString("id");
            String name = results.getString("name");
            String mobile = results.getString("mobile");
            String address = results.getString("address");
            Member member = new Member(id, name, mobile, address);
            list.add(member);

        }

        return list;
    }

    public Member getSearchMember(String memberId) throws SQLException {

        Connection conn = Database.getInstance().getConnection();
        String selectSql = "select * from lbassistant.members  where id =?";
        PreparedStatement preStmt = conn.prepareStatement(selectSql);
        preStmt.setString(1, memberId);
        ResultSet result = preStmt.executeQuery();
        Member member = null;
        if (result.next()) {

            String id = result.getString("id");
            String name = result.getString("name");
            String mobile = result.getString("mobile");
            String address = result.getString("address");
            member = new Member(id, name, mobile, address);
        }
        return member;
    }

    public void deleteMember(String id) throws SQLException {
        
        Connection conn = Database.getInstance().getConnection();
        String deleteSql = "delete from lbassistant.members where id=?";
        PreparedStatement preStmt = conn.prepareStatement(deleteSql);
        preStmt.setString(1, id);
        preStmt.execute();
    }
}
