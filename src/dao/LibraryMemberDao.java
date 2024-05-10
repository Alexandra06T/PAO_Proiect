package dao;

import daoservices.DatabaseConnection;
import model.*;
import utils.FileManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.AUDIT_FILE;


public class LibraryMemberDao implements DaoInterface<LibraryMember> {

    private static LibraryMemberDao libraryMemberDao;

    private Connection connection = DatabaseConnection.getConnection();

    private LibraryMemberDao() throws SQLException {}

    public static LibraryMemberDao getInstance() throws SQLException {
        if(libraryMemberDao == null){
             libraryMemberDao = new LibraryMemberDao();
        }
        return libraryMemberDao;
    }

    @Override
    public void add(LibraryMember libraryMember) throws SQLException {
        String sql = "INSERT INTO libraryms.librarymember (name, emailaddress, phonenumber, address) VALUES (?, ?, ?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, libraryMember.getName());
            statement.setString(2, libraryMember.getEmailAddress());
            statement.setString(3, libraryMember.getPhoneNumber());
            statement.setString(4, libraryMember.getAddress());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "LibraryMember: add " + java.time.Instant.now());

    }

    @Override
    public List<LibraryMember> getAll() throws SQLException {
        String sql = "SELECT * FROM libraryms.librarymember";
        ResultSet rs = null;
        List<LibraryMember> libraryMembers = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while(rs.next()) {
                LibraryMember libraryMember = new LibraryMember(rs.getString("name"), rs.getString("emailAddress"), rs.getString("phoneNumber"), rs.getString("address"));
                libraryMember.setMemberID(rs.getInt("ID"));
                libraryMembers.add(libraryMember);
            }

        }finally {
            if(rs != null) {
                rs.close();
            }
            else libraryMembers = null;
            FileManagement.writeIntoFile(AUDIT_FILE, "LibraryMember: get all " + java.time.Instant.now());

        }

        return libraryMembers;
    }

    @Override
    public LibraryMember read(String memberId) throws SQLException {
        String sql = "SELECT * FROM libraryms.librarymember WHERE ID = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, Integer.parseInt(memberId));
            rs = statement.executeQuery();

            while (rs.next()){
                LibraryMember libraryMember = new LibraryMember();
                libraryMember.setMemberID(rs.getInt("ID"));
                libraryMember.setName(rs.getString("name"));
                libraryMember.setEmailAddress(rs.getString("emailAddress"));
                libraryMember.setPhoneNumber(rs.getString("phoneNumber"));
                libraryMember.setAddress(rs.getString("address"));
                return libraryMember;
            }
        }finally {
            if(rs != null) {
                rs.close();
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "LibraryMember: read " + java.time.Instant.now());

        }
        return null;
    }

    @Override
    public void delete(LibraryMember libraryMember) throws SQLException {
        String sql = "DELETE FROM libraryms.librarymember WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, libraryMember.getMemberID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "LibraryMember: delete " + java.time.Instant.now());

    }

    @Override
    public void update(LibraryMember libraryMember) throws SQLException {
        String sql = "UPDATE libraryms.librarymember set name = ?, emailaddress = ?, phonenumber = ?, address = ? where ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, libraryMember.getName());
            statement.setString(2, libraryMember.getEmailAddress());
            statement.setString(3, libraryMember.getPhoneNumber());
            statement.setString(4, libraryMember.getAddress());
            statement.setInt(5, libraryMember.getMemberID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "LibraryMember: update " + java.time.Instant.now());

    }
}
