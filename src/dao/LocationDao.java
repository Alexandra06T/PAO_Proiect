package dao;

import daoservices.DatabaseConnection;
import model.Book;
import model.BranchLibrary;
import model.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LocationDao implements DaoInterface<Location> {
    private static LocationDao locationDao;

    private Connection connection = DatabaseConnection.getConnection();

    private LocationDao() throws SQLException {}

    public static LocationDao getInstance() throws SQLException {
        if(locationDao == null) {
            locationDao = new LocationDao();
        }
        return locationDao;
    }

    @Override
    public List<Location> getAll() throws SQLException{
        String sql = "SELECT * FROM libraryms.location l JOIN libraryms.branchlibrary b ON l.branchlibraryID = b.ID";
        ResultSet rs = null;
        List<Location> locations = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while (rs.next()) {
                Location location = new Location(rs.getString("name"), rs.getInt("branchLibraryID"));
                location.setLocationID(rs.getInt("ID"));
                locations.add(location);
            }

        } finally {
            if(rs != null) {
                rs.close();
            }
            else locations = null;
        }
        return locations;
    }

    @Override
    public Location read(String id) throws SQLException{
        String sql = "SELECT * " +
                "FROM libraryms.location l INNER JOIN libraryms.branchlibrary b ON l.branchlibraryID = b.ID " +
                "WHERE l.ID = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            rs = statement.executeQuery();
            if(rs.next()) {
                Location location = new Location(rs.getString("name"), rs.getInt("branchLibraryID"));
                location.setLocationID(rs.getInt("ID"));
                return location;
            }
            return null;
        }
    }

    public Location readByName(String name, int branchLibraryId) throws SQLException{
        String sql = "SELECT * " +
                "FROM libraryms.location l INNER JOIN libraryms.branchlibrary b ON l.branchlibraryID = b.ID " +
                "WHERE UPPER(l.name) LIKE ? AND b.ID = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name.toUpperCase());
            statement.setInt(2, branchLibraryId);
            rs = statement.executeQuery();
            if(rs.next()) {
                Location location = new Location(rs.getString("name"), rs.getInt("branchLibraryID"));
                location.setLocationID(rs.getInt("ID"));
                return location;
            }
            return null;
        }
    }

    public void delete(Location location) throws SQLException {
        String sql = "DELETE FROM libraryms.location WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, location.getLocationID());
            statement.executeUpdate();
        }
    }

    @Override
    public void add(Location location) throws SQLException {
        String sql = "INSERT INTO libraryms.location (name, branchlibraryID) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, location.getName());
            statement.setInt(2, location.getBranchLibraryID());

            statement.executeUpdate();
        }
    }

    @Override
    public void update(Location location) throws SQLException {
        String sql = "UPDATE libraryms.location set name = ? where ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, location.getName());
            statement.setInt(2, location.getLocationID());
            statement.executeUpdate();
        }
    }
}
