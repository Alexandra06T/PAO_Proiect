package dao;

import daoservices.DatabaseConnection;
import model.Book;
import model.BranchLibrary;
import model.Location;
import utils.FileManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Constants.AUDIT_FILE;

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
        String sql = "SELECT * FROM libraryms.location";
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
            FileManagement.writeIntoFile(AUDIT_FILE, "Location: get all " + java.time.Instant.now());

        }
        return locations;
    }

    @Override
    public Location read(String id) throws SQLException{
        String sql = "SELECT * " +
                "FROM libraryms.location WHERE ID = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            rs = statement.executeQuery();
            FileManagement.writeIntoFile(AUDIT_FILE, "Location: read " + java.time.Instant.now());

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
            FileManagement.writeIntoFile(AUDIT_FILE, "Location: read by nam " + java.time.Instant.now());

            if(rs.next()) {
                Location location = new Location(rs.getString("name"), rs.getInt("branchLibraryID"));
                location.setLocationID(rs.getInt("ID"));
                return location;
            }
            return null;
        }
    }

    @Override
    public void delete(Location location) throws SQLException {
        String sql = "DELETE FROM libraryms.location WHERE ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, location.getLocationID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Location: delete " + java.time.Instant.now());

    }

    @Override
    public void add(Location location) throws SQLException {
        String sql = "INSERT INTO libraryms.location (name, branchlibraryID) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, location.getName());
            statement.setInt(2, location.getBranchLibraryID());

            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Location: add " + java.time.Instant.now());

    }

    @Override
    public void update(Location location) throws SQLException {
        String sql = "UPDATE libraryms.location set name = ? where ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, location.getName());
            statement.setInt(2, location.getLocationID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "Location: update " + java.time.Instant.now());

    }
}
