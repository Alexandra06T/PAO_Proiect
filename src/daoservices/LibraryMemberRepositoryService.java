package daoservices;

import dao.CheckInDao;
import dao.CheckOutDao;
import dao.ReservationDao;
import model.*;
import dao.LibraryMemberDao;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utils.Constants.CHECKIN;

public class LibraryMemberRepositoryService {

    private LibraryMemberDao libraryMemberDao = LibraryMemberDao.getInstance();
    private ReservationDao reservationDao;
    private CheckInDao checkInDao;
    private CheckOutDao checkOutDao;

    public LibraryMemberRepositoryService() throws SQLException {}

    public void printAll() throws InvalidDataException {
        try {
            List<LibraryMember> libraryMembers = libraryMemberDao.getAll();
            if(libraryMembers == null)
                throw new InvalidDataException("There is no library member.");
            libraryMembers.forEach(System.out:: println);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<LibraryMember> getAll() throws InvalidDataException {
        try {
            List<LibraryMember> libraryMembers = libraryMemberDao.getAll();
            if(libraryMembers == null){
                throw new InvalidDataException("There is no library member.");
            }
            return libraryMembers;

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return null;
    }


    public LibraryMember getLibraryMemberById(int memberId) throws InvalidDataException{
        try {
            LibraryMember libraryMember = libraryMemberDao.read(String.valueOf(memberId));
            if(libraryMember == null)
                throw new InvalidDataException("No library member having this id");
            return libraryMember;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return null;
    }

    public int getNrCurrentCheckIns(int memberID) {
        try {
             return libraryMemberDao.getNrCurrentCheckIns(memberID);
        }
        catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return 0;
    }

    public boolean hasOverdueCopies(int memberId, LocalDate currentDate) {
//        LibraryMember libraryMember = libraryMemberDao.read(String.valueOf(memberId));
//        List<Transaction> transactions = libraryMember.getTransactions();
//        for(Transaction t : transactions) {
//            if(t instanceof CheckIn checkIn) {
//                if(!checkIn.isCheckedOut() && currentDate.isAfter(t.getDate().plusDays(((CheckIn) t).getNumberDays()))) {
//                    return true;
//                }
//            }
//        }
        return false;
    }

    public void removeLibraryMember(LibraryMember libraryMember) throws InvalidDataException {
        if (libraryMember == null) throw new InvalidDataException("Invalid library member");
        try {
            libraryMemberDao.delete(libraryMember);
        } catch (SQLException e) {
            System.out.println("remove SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void addLibraryMember(LibraryMember libraryMember) throws InvalidDataException {
        try {
            if(libraryMember == null)
                throw new InvalidDataException("Invalid library member");
            libraryMemberDao.add(libraryMember);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateLibraryMember(LibraryMember libraryMember) throws InvalidDataException {
        try {
            if(libraryMember == null)
                throw new InvalidDataException("Invalid library member");
            libraryMemberDao.update(libraryMember);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }
}
