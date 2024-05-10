package dao;

import daoservices.DatabaseConnection;
import model.Category;
import utils.InvalidDataException;
import utils.FileManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Constants.AUDIT_FILE;

public class CategoryDao implements DaoInterface<Category> {
    private static CategoryDao categoryDao;

    private Connection connection = DatabaseConnection.getConnection();

    private CategoryDao() throws SQLException {}

    public static CategoryDao getInstance() throws SQLException {
        if(categoryDao == null){
            categoryDao = new CategoryDao();
        }
        return categoryDao;
    }



    @Override
    public void add(Category category) throws SQLException {
        String sql = "INSERT INTO libraryms.category VALUES (?, ?);";

        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setInt(1, category.getCategoryIndex());
            statement.setString(2, category.getName());
            statement.executeUpdate();
        }

        FileManagement.writeIntoFile(AUDIT_FILE, "Category: add " + java.time.Instant.now());
    }

    @Override
    public List<Category> getAll() throws SQLException {
        String sql = "SELECT * FROM libraryms.category";
        ResultSet rs = null;
        List<Category> categories = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while(rs.next()) {
                Category category = new Category(rs.getString("name"), rs.getInt("categoryIndex"));
                categories.add(category);
            }

        }finally {
            if(rs != null) {
                rs.close();
            }
            else categories = null;
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Category: get all " + java.time.Instant.now());

        return categories;
    }

    @Override
    public Category read(String id) throws SQLException {
        String sql = "SELECT * FROM libraryms.category c WHERE c.categoryIndex = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, id);
            rs = statement.executeQuery();

            while (rs.next()){
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setCategoryIndex(rs.getInt("categoryIndex"));
                return category;
            }
        }finally {
            if(rs != null) {
                rs.close();
            }
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Category: read " + java.time.Instant.now());
        return null;
    }

    public Category readByName(String name) throws SQLException {
        String sql = "SELECT * FROM libraryms.category c WHERE UPPER(c.name) LIKE UPPER(?)";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            rs = statement.executeQuery();

            while (rs.next()){
                Category category = new Category();
                category.setName(rs.getString("name"));
                category.setCategoryIndex(rs.getInt("categoryIndex"));
                return category;
            }

        }finally {
            if(rs != null) {
                rs.close();
            }
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Category: read by name " + java.time.Instant.now());
        return null;
    }

    @Override
    public void delete(Category category) throws SQLException {
        String sql = "DELETE FROM libraryms.category c WHERE c.name LIKE ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, category.getName());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Category: delete " + java.time.Instant.now());
    }

    @Override
    public void update(Category category) throws SQLException {
        String sql = "UPDATE libraryms.category set name = ? where categoryIndex = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql);) {
            statement.setString(1, category.getName());
            statement.setInt(2, category.getCategoryIndex());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Category: update " + java.time.Instant.now());
    }

}
