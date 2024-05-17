package dao;

import daoservices.DatabaseConnection;
import model.CheckIn;
import model.CheckOut;
import model.CheckOut;
import model.Transaction;
import utils.FileManagement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.AUDIT_FILE;

public class CheckOutDao implements DaoInterface<CheckOut> {
    private static CheckOutDao checkOutDao;

    private Connection connection = DatabaseConnection.getConnection();

    private CheckOutDao() throws SQLException {}

    public static CheckOutDao getInstance() throws SQLException {
        if(checkOutDao == null) {
            checkOutDao = new CheckOutDao();
        }
        return checkOutDao;
    }

    @Override
    public List<CheckOut> getAll() throws SQLException{
        String sql = "SELECT * FROM libraryms.checkout c INNER JOIN libraryms.transaction t ON c.checkoutID = t.ID " +
                "WHERE UPPER(t.transactiontype) LIKE 'CHECKOUT'";
        ResultSet rs = null;
        List<CheckOut> checkOuts = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            rs = statement.executeQuery();
            while (rs.next()) {
                CheckOut checkOut = new CheckOut();
                checkOut.setTransactionID(rs.getInt("t.ID"));
                checkOut.setBookCopyID(rs.getInt("t.bookcopyID"));
                checkOut.setLibraryMemberID(rs.getInt("t.librarymemberID"));
                checkOut.setDate(rs.getDate("t.date").toLocalDate());
                checkOut.setCheckOutID(rs.getInt("c.checkoutID"));
                checkOut.setBookStatus(rs.getString("bookstatus"));
                checkOut.setOverdueDays(rs.getInt("overduedays"));
                checkOut.setPenalty(rs.getDouble("penalty"));
                checkOut.setCheckinID(rs.getInt("checkinID"));
                checkOuts.add(checkOut);
            }

        } finally {
            if(rs != null) {
                rs.close();
            }
            FileManagement.writeIntoFile(AUDIT_FILE, "CheckOut: get all " + java.time.Instant.now());

        }
        if(checkOuts.isEmpty()) return null;
        return checkOuts;
    }

    @Override
    public CheckOut read(String id) throws SQLException{
        String sql = "SELECT * FROM libraryms.checkout c INNER JOIN libraryms.transaction t ON c.checkoutID = t.ID " +
                "WHERE UPPER(t.transactiontype) LIKE 'CHECKOUT' AND c.checkoutID = ?";
        ResultSet rs = null;
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, id);
            rs = statement.executeQuery();
            FileManagement.writeIntoFile(AUDIT_FILE, "CheckOut: read " + java.time.Instant.now());

            if(rs.next()) {
                CheckOut checkOut = new CheckOut();
                checkOut.setTransactionID(rs.getInt("t.ID"));
                checkOut.setBookCopyID(rs.getInt("t.bookcopyID"));
                checkOut.setLibraryMemberID(rs.getInt("t.librarymemberID"));
                checkOut.setDate(rs.getDate("t.date").toLocalDate());
                checkOut.setCheckOutID(rs.getInt("c.checkoutID"));
                checkOut.setBookStatus(rs.getString("bookstatus"));
                checkOut.setOverdueDays(rs.getInt("overduedays"));
                checkOut.setPenalty(rs.getDouble("penalty"));
                checkOut.setCheckinID(rs.getInt("checkinID"));
                return checkOut;
            }
            return null;
        }
    }

    public List<CheckOut> getCheckOut(int libraryMemberID, int locationID, String bookID) throws SQLException {
        String sql = "SELECT * FROM libraryms.checkout c INNER JOIN libraryms.transaction t ON c.checkoutID = t.ID " +
                "INNER JOIN libraryms.bookcopy b ON b.ID = t.bookcopyID " +
                "WHERE UPPER(t.transactiontype) LIKE 'CHECKOUT' AND t.librarymemberID = ? " +
                "AND b.locationID = ? AND b.bookID LIKE ?";
        ResultSet rs = null;
        List<CheckOut> checkOuts = new ArrayList<>();
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, libraryMemberID);
            statement.setInt(2, locationID);
            statement.setString(3, bookID);
            rs = statement.executeQuery();
            FileManagement.writeIntoFile(AUDIT_FILE, "CheckOut: get check out by library member, location and book " + java.time.Instant.now());

            while(rs.next()) {
                CheckOut checkOut = new CheckOut();
                checkOut.setTransactionID(rs.getInt("t.ID"));
                checkOut.setBookCopyID(rs.getInt("t.bookcopyID"));
                checkOut.setLibraryMemberID(rs.getInt("t.librarymemberID"));
                checkOut.setDate(rs.getDate("t.date").toLocalDate());
                checkOut.setCheckOutID(rs.getInt("c.checkoutID"));
                checkOut.setPenalty(rs.getDouble("c.penalty"));
                checkOut.setOverdueDays(rs.getInt("c.overduedays"));
                checkOut.setBookStatus(rs.getString("c.bookstatus"));
                checkOut.setCheckinID(rs.getInt("c.checkinID"));
                checkOuts.add(checkOut);
            }
            if(checkOuts.isEmpty()) return null;
            return checkOuts;
        }
    }

    @Override
    public void delete(CheckOut checkOut) throws SQLException {
        String sqlcheckout = "DELETE FROM libraryms.checkout c WHERE c.checkoutID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sqlcheckout)) {
            statement.setInt(1, checkOut.getCheckOutID());
            statement.executeUpdate();
        }
        String sqltransaction = "DELETE FROM libraryms.transaction t WHERE t.ID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sqltransaction)) {
            statement.setInt(1, checkOut.getTransactionID());
            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "CheckOut: delete " + java.time.Instant.now());

    }

    @Override
    public void add(CheckOut checkOut) throws SQLException {
        ResultSet rs = null;
        int key = 0;
        String sql = "INSERT INTO libraryms.transaction (date, transactiontype, librarymemberID, bookcopyID) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, Date.valueOf(checkOut.getDate()));
            statement.setString(2, "checkout");
            statement.setInt(3, checkOut.getLibraryMemberID());
            statement.setInt(4, checkOut.getBookCopyID());

            statement.executeUpdate();
            rs = statement.getGeneratedKeys();
            if(rs.next())
                key = rs.getInt(1);
        }
        String sql2 = "INSERT INTO libraryms.checkout VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql2)) {
            statement.setInt(1, key);
            statement.setString(2, checkOut.getBookStatus());
            statement.setInt(3, checkOut.getOverdueDays());
            statement.setDouble(4, checkOut.getPenalty());
            statement.setInt(5, checkOut.getCheckinID());

            statement.executeUpdate();
        }
        String sql3 = "UPDATE libraryms.checkin SET checkedout = true WHERE checkinID = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql3)) {
            statement.setInt(1, checkOut.getCheckinID());

            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "CheckOut: add " + java.time.Instant.now());

    }

    @Override
    public void update(CheckOut checkOut) throws SQLException {
        String sql = "UPDATE libraryms.checkout set bookstatus = ? where checkout.checkoutID = ?";
        try(PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, checkOut.getBookStatus());
            statement.setInt(2, checkOut.getCheckOutID());

            statement.executeUpdate();
        }
        FileManagement.writeIntoFile(AUDIT_FILE, "CheckOut: update " + java.time.Instant.now());

    }
}
