package daoservices;

import dao.CheckInDao;
import dao.CheckOutDao;
import dao.ReservationDao;
import model.*;
import dao.LibraryMemberDao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.CHECKIN;

public class LibraryMemberRepositoryService {

    private LibraryMemberDao libraryMemberDao;
    private ReservationDao reservationDao;
    private CheckInDao checkInDao;
    private CheckOutDao checkOutDao;

    public LibraryMemberRepositoryService() {
        this.libraryMemberDao = new LibraryMemberDao();
    }

    public LibraryMember getLibraryMemberById(int memberId){
        LibraryMember libraryMember = libraryMemberDao.read(memberId);
        if(libraryMember != null){
            System.out.println(libraryMember);
        }else {
            System.out.println("No library member having this Id");
        }

        return libraryMember;
    }

    public List<CheckIn> getCurrentCheckIns(int memberId) {
        List<CheckIn> checkIns = new ArrayList<>();
        LibraryMember libraryMember = libraryMemberDao.read(memberId);
        List<Transaction> transactions = libraryMember.getTransactions();
        for(Transaction t : transactions) {
            if(t.getClass().getName().equals(CHECKIN)) {
                CheckIn checkIn = (CheckIn) t;
                if(!checkIn.isCheckedOut()) {
                    checkIns.add(checkIn);
                }
            }
        }

        return checkIns;
    }

    public boolean hasOverdueCopies(int memberId, LocalDate currentDate) {
        LibraryMember libraryMember = libraryMemberDao.read(memberId);
        List<Transaction> transactions = libraryMember.getTransactions();
        for(Transaction t : transactions) {
            if(t.getClass().getName().equals(CHECKIN)) {
                CheckIn checkIn = (CheckIn) t;
                if(!checkIn.isCheckedOut() && currentDate.isAfter(t.getDate().plusDays(((CheckIn) t).getNumberDays()))) {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeLibraryMember(int memberId) {
        LibraryMember libraryMember = getLibraryMemberById(memberId);
        if (libraryMember == null) return;

        //stergem toate rezervarile member-ului
        List<Reservation> reservationList = libraryMember.getReservations();
        for(Reservation r : reservationList) {
            reservationDao.delete(r);
        }

        //stergem toate tranzactiile member-ului
        List<Transaction> transactionList = libraryMember.getTransactions();
        for(Transaction t : transactionList) {
            switch (t){
                case CheckIn checkIn -> checkInDao.delete(checkIn);
                case CheckOut checkOut -> checkOutDao.delete(checkOut);
                default -> throw new IllegalStateException("Unexpected value: " + t);
            }
        }

        libraryMemberDao.delete(libraryMember);

        System.out.println("Removed " + libraryMember);
    }

    public void addLibraryMember(LibraryMember libraryMember) {
        if(libraryMember != null){
            libraryMemberDao.create(libraryMember);
        }
    }
}
