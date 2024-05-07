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

    public void printAll() {
        try {
            List<LibraryMember> libraryMembers = libraryMemberDao.getAll();
            if(libraryMembers != null){
                libraryMembers.forEach(System.out:: println);
            }else {
                System.out.println("There is no library member.");
            }

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<LibraryMember> getAll() {
        List<LibraryMember> libraryMembers = null;

        try {
            libraryMembers = libraryMemberDao.getAll();
            if(libraryMembers == null){
                System.out.println("There is no library member.");
            }

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return libraryMembers;
    }


    public LibraryMember getLibraryMemberById(int memberId){
        LibraryMember libraryMember = null;

        try {
            libraryMember = libraryMemberDao.read(String.valueOf(memberId));
            if(libraryMember == null)
                System.out.println("No library member having this id");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return libraryMember;
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

    public void removeLibraryMember(LibraryMember libraryMember) {
        if (libraryMember == null) return;
        try {
            libraryMemberDao.delete(libraryMember);
        } catch (SQLException e) {
            System.out.println("remove SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void addLibraryMember(LibraryMember libraryMember) {
        try {
            if(libraryMember != null){
                libraryMemberDao.add(libraryMember);
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateLibraryMember(LibraryMember libraryMember) {
        try {
            if(libraryMember != null){
                libraryMemberDao.update(libraryMember);
                System.out.println("Library member's details updated successfully!");
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }
}
