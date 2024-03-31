package dao;

import model.CheckIn;
import model.LibraryMember;
import model.Reservation;
import model.Transaction;
import repository.LibraryMemberRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static utils.Constants.CHECKIN;

public class LibraryMemberDAOService {

    private LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberDAOService() {
        this.libraryMemberRepository = new LibraryMemberRepository();
    }

    public LibraryMember getLibraryMemberById(int memberId){
        LibraryMember libraryMember = libraryMemberRepository.read(memberId);
        if(libraryMember != null){
            System.out.println(libraryMember);
        }else {
            System.out.println("No library member having this Id");
        }

        return libraryMember;
    }

    public List<CheckIn> getCurrentCheckIns(int memberId) {
        List<CheckIn> checkIns = new ArrayList<>();
        LibraryMember libraryMember = libraryMemberRepository.read(memberId);
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
        LibraryMember libraryMember = libraryMemberRepository.read(memberId);
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

        libraryMemberRepository.delete(libraryMember);

        System.out.println("Removed " + libraryMember);
    }

    public void addLibraryMember(LibraryMember libraryMember) {
        if(libraryMember != null){
            libraryMemberRepository.create(libraryMember);
        }
    }
}
