package dao;

import daoservices.DatabaseConnection;
import model.BranchLibrary;
import model.Category;
import utils.InvalidDataException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchLibraryDao implements DaoInterface<BranchLibrary> {
    private static BranchLibraryDao branchLibraryDao;

    private Connection connection = DatabaseConnection.getConnection();

    public BranchLibraryDao() throws SQLException {}

    public static BranchLibraryDao getInstance() throws SQLException {
        if(branchLibraryDao == null){
            branchLibraryDao = new BranchLibraryDao();
        }
        return branchLibraryDao;
    }

    @Override
    public void add(BranchLibrary branchLibrary) throws SQLException {
        String sql = "INSERT INTO libraryms.branchlibrary (name, address) VALUES (?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, branchLibrary.getName());
            statement.setString(2, branchLibrary.getAddress());
            statement.executeUpdate();
        }
    }

    @Override
    public List<BranchLibrary> getAll() throws SQLException {
        String sql = "SELECT * FROM libraryms.branchlibrary";
        ResultSet rs = null;
        List<BranchLibrary> branchLibraries = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while(rs.next()) {
                BranchLibrary branchLibrary = new BranchLibrary(rs.getString("name"), rs.getString("address"));
                branchLibrary.setBranchLibraryID(rs.getInt("ID"));
                branchLibraries.add(branchLibrary);
            }

        }finally {
            if(rs != null) {
                rs.close();
            }
            else branchLibraries = null;
        }

        return branchLibraries;
    }

    @Override
    public BranchLibrary read(String name) throws SQLException {
        String sql = "SELECT * FROM libraryms.branchlibrary WHERE UPPER(name) LIKE ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, name);
            rs = statement.executeQuery();

            while (rs.next()){
                BranchLibrary branchLibrary = new BranchLibrary();
                branchLibrary.setBranchLibraryID(rs.getInt("ID"));
                branchLibrary.setName(rs.getString("name"));
                branchLibrary.setAddress(rs.getString("address"));
                return branchLibrary;
            }
        }finally {
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }

    public BranchLibrary readByID(String id) throws SQLException {
        String sql = "SELECT * FROM libraryms.branchlibrary WHERE ID = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, id);
            rs = statement.executeQuery();

            while (rs.next()){
                BranchLibrary branchLibrary = new BranchLibrary();
                branchLibrary.setBranchLibraryID(rs.getInt("ID"));
                branchLibrary.setName(rs.getString("name"));
                branchLibrary.setAddress(rs.getString("address"));
                return branchLibrary;
            }
        }finally {
            if(rs != null) {
                rs.close();
            }
        }
        return null;
    }

    @Override
    public void delete(BranchLibrary branchLibrary) throws SQLException {
        String sql = "DELETE FROM libraryms.branchlibrary WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, branchLibrary.getBranchLibraryID());
            statement.executeUpdate();
        }
    }

    @Override
    public void update(BranchLibrary branchLibrary) throws SQLException {
        String sql = "UPDATE libraryms.branchlibrary set name = ?, address = ? where ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, branchLibrary.getName());
            statement.setString(2, branchLibrary.getAddress().toUpperCase());
            statement.setInt(3, branchLibrary.getBranchLibraryID());
            statement.executeUpdate();
        }
    }

}
