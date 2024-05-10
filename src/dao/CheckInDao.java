package dao;

import daoservices.DatabaseConnection;
import model.Book;
import model.CheckIn;
import model.Transaction;
import utils.FileManagement;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static utils.Constants.AUDIT_FILE;

public class CheckInDao implements DaoInterface<CheckIn> {
    private static CheckInDao checkInDao;

    private Connection connection = DatabaseConnection.getConnection();

    private CheckInDao() throws SQLException {}

    public static CheckInDao getInstance() throws SQLException {
        if(checkInDao == null) {
            checkInDao = new CheckInDao();
        }
        return checkInDao;
    }

    @Override
    public List<CheckIn> getAll() throws SQLException{
        String sql = "SELECT * FROM libraryms.checkin c INNER JOIN libraryms.transaction t ON c.checkinID = t.ID " +
                "WHERE UPPER(t.transactiontype) LIKE 'CHECKIN'";
        ResultSet rs = null;
        List<CheckIn> checkIns = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while (rs.next()) {
                CheckIn checkIn = new CheckIn();
                checkIn.setTransactionID(rs.getInt("ID"));
                checkIn.setBookCopyID(rs.getInt("bookcopyID"));
                checkIn.setLibraryMemberID(rs.getInt("librarymemberID"));
                checkIn.setDate(rs.getDate("date").toLocalDate());
                checkIn.setCheckInID(rs.getInt("checkinID"));
                checkIn.setCheckedOut(rs.getBoolean("checkedout"));
                checkIn.setNumberDays(rs.getInt("numberdays"));
                checkIn.setType(rs.getString("type"));
                checkIns.add(checkIn);
            }

        } finally {
            if(rs != null) {
                rs.close();
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "CheckIn: get all " + java.time.Instant.now());

        }
        if(checkIns.isEmpty()) return null;
        return checkIns;
    }

    @Override
    public CheckIn read(String id) throws SQLException{
        String sql = "SELECT * FROM libraryms.checkin c INNER JOIN libraryms.transaction t ON c.checkinID = t.ID " +
                "WHERE UPPER(t.transactiontype) LIKE 'CHECKIN' AND c.checkinID = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            rs = statement.executeQuery();
            FileManagement.writeIntoFile(AUDIT_FILE, "CheckIn: read " + java.time.Instant.now());

            if(rs.next()) {
                CheckIn checkIn = new CheckIn();
                checkIn.setTransactionID(rs.getInt("t.ID"));
                checkIn.setBookCopyID(rs.getInt("t.bookcopyID"));
                checkIn.setLibraryMemberID(rs.getInt("t.librarymemberID"));
                checkIn.setDate(rs.getDate("t.date").toLocalDate());
                checkIn.setCheckInID(rs.getInt("c.checkinID"));
                checkIn.setCheckedOut(rs.getBoolean("c.checkedout"));
                checkIn.setNumberDays(rs.getInt("c.numberdays"));
                checkIn.setType(rs.getString("type"));
                return checkIn;
            }
            return null;
        }
    }

    public List<CheckIn> getCheckIn(int libraryMemberID, int locationID, String bookID) throws SQLException {
        String sql = "SELECT * FROM libraryms.checkin c INNER JOIN libraryms.transaction t ON c.checkinID = t.ID " +
                "INNER JOIN libraryms.bookcopy b ON b.ID = t.bookcopyID " +
                "WHERE UPPER(t.transactiontype) LIKE 'CHECKIN' AND t.librarymemberID = ? " +
                "AND b.locationID = ? AND b.bookID LIKE ?";
        ResultSet rs = null;
        List<CheckIn> checkIns = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, libraryMemberID);
            statement.setInt(2, locationID);
            statement.setString(3, bookID);
            rs = statement.executeQuery();
            FileManagement.writeIntoFile(AUDIT_FILE, "CheckIn: get by library member, location and book " + java.time.Instant.now());

            while(rs.next()) {
                CheckIn checkIn = new CheckIn();
                checkIn.setTransactionID(rs.getInt("t.ID"));
                checkIn.setBookCopyID(rs.getInt("t.bookcopyID"));
                checkIn.setLibraryMemberID(rs.getInt("t.librarymemberID"));
                checkIn.setDate(rs.getDate("t.date").toLocalDate());
                checkIn.setCheckInID(rs.getInt("c.checkinID"));
                checkIn.setCheckedOut(rs.getBoolean("c.checkedout"));
                checkIn.setNumberDays(rs.getInt("c.numberdays"));
                checkIn.setType(rs.getString("c.type"));
                checkIns.add(checkIn);
            }
            if(checkIns.isEmpty()) return null;
            return checkIns;
        }
    }

    @Override
    public void delete(CheckIn checkIn) throws SQLException {
        String sqlcheckin = "DELETE FROM libraryms.checkin c WHERE c.checkinID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sqlcheckin)) {
            statement.setInt(1, checkIn.getCheckInID());
            statement.executeUpdate();
        }
        String sqltransaction = "DELETE FROM libraryms.transaction t WHERE t.ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sqltransaction)) {
            statement.setInt(1, checkIn.getTransactionID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "CheckIn: delete " + java.time.Instant.now());

    }

    @Override
    public void add(CheckIn checkIn) throws SQLException {
        ResultSet rs = null;
        int key = 0;
        String sql = "INSERT INTO libraryms.transaction (date, transactiontype, librarymemberID, bookcopyID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(checkIn.getDate()));
            statement.setString(2, "checkin");
            statement.setInt(3, checkIn.getLibraryMemberID());
            statement.setInt(4, checkIn.getBookCopyID());


            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if(rs.next())
                key = rs.getInt(1);
        }

        String sql3 = "INSERT INTO libraryms.checkin (checkinID, numberdays, checkedout, type) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql3)) {
            statement.setInt(1, key);
            statement.setInt(2, checkIn.getNumberDays());
            statement.setBoolean(3, checkIn.isCheckedOut());
            statement.setString(4, checkIn.getType());

            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "CheckIn: add " + java.time.Instant.now());
    }

    @Override
    public void update(CheckIn checkIn) throws SQLException {
        String sql = "UPDATE libraryms.checkin set numberdays = ? where checkin.checkinID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, checkIn.getNumberDays());
            statement.setInt(2, checkIn.getCheckInID());

            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "CheckIn: update " + java.time.Instant.now());

    }

    public int getNrCurrentCheckIns(int memberId) throws SQLException {
        String sql = "SELECT COUNT(c.checkinID) AS nr FROM libraryms.checkin c INNER JOIN libraryms.transaction t ON (c.checkinID = t.ID) " +
                "WHERE t.librarymemberID = ? AND c.checkedout IS FALSE";
        ResultSet rs = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberId);
            rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("nr");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "CheckIn: get number of current check ins " + java.time.Instant.now());

        }
        return 0;
    }

    public int getNrOverdueCopies(int memberID, LocalDate currentDate) throws SQLException {
        String sql = "SELECT COUNT(*) AS nr FROM libraryms.checkin c INNER JOIN libraryms.transaction t ON c.checkinID = t.ID " +
                "WHERE t.librarymemberID = ? AND c.checkedout IS FALSE AND DATEDIFF(?, t.date) > c.numberdays";
        ResultSet rs = null;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, memberID);
            statement.setDate(2, Date.valueOf(currentDate));
            rs = statement.executeQuery();
            if(rs.next()) {
                return rs.getInt("nr");
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "CheckIn: get number of overdue copies of a library member " + java.time.Instant.now());

        }
        return 0;
    }

    public List<CheckIn> getCurrentCheckIns(int libraryMemberID, int locationID, String bookID) throws SQLException{
        String sql = "SELECT * FROM libraryms.checkin c INNER JOIN libraryms.transaction t ON c.checkinID = t.ID " +
                "INNER JOIN libraryms.bookcopy b ON b.ID = t.bookcopyID " +
                "WHERE UPPER(t.transactiontype) LIKE 'CHECKIN' AND t.librarymemberID = ? AND b.locationID = ? AND b.bookID LIKE ?";
        ResultSet rs = null;
        List<CheckIn> checkIns = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, libraryMemberID);
            statement.setInt(2, locationID);
            statement.setString(3, bookID);
            rs = statement.executeQuery();
            while (rs.next()) {
                CheckIn checkIn = new CheckIn();
                checkIn.setTransactionID(rs.getInt("t.ID"));
                checkIn.setBookCopyID(rs.getInt("t.bookcopyID"));
                checkIn.setLibraryMemberID(rs.getInt("t.librarymemberID"));
                checkIn.setDate(rs.getDate("t.date").toLocalDate());
                checkIn.setCheckInID(rs.getInt("c.checkinID"));
                checkIn.setCheckedOut(rs.getBoolean("c.checkedout"));
                checkIn.setNumberDays(rs.getInt("c.numberdays"));
                checkIn.setType(rs.getString("type"));
                checkIns.add(checkIn);
            }

        } finally {
            if(rs != null) {
                rs.close();
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "CheckIn: get current check ins " + java.time.Instant.now());

        }
        if(checkIns.isEmpty()) return null;
        return checkIns;
    }
}
