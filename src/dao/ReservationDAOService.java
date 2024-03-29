package dao;

import model.Book;
import model.LibraryMember;
import model.Reservation;
import repository.ReservationRepository;

import java.util.List;

public class ReservationDAOService {

    private ReservationRepository reservationRepository;

    public ReservationDAOService() {
        this.reservationRepository = new ReservationRepository();
    }

    public Reservation getReservationById(int id){
        Reservation reservation = reservationRepository.readId(id);
        if(reservation != null){
            System.out.println(reservation);
        }else {
            System.out.println("No reservation having this id");
        }

        return reservation;
    }

    public List<Reservation> getReservationByBook(Book book){
        List<Reservation> reservationList = reservationRepository.readBook(book);
        if(reservationList != null){
            for(Reservation r: reservationList) {
                System.out.println(r.getId());
                System.out.println(r.getLibraryMember());
                System.out.println(r.getPickupLocation());
                System.out.println(r.getExpiryDate());
            }
        }else {
            System.out.println("No reservation for this book");
        }

        return reservationList;
    }

    public List<Reservation> getReservationByMember(LibraryMember libraryMember){
        List<Reservation> reservationList = reservationRepository.readMember(libraryMember);
        if(reservationList != null){
            for(Reservation r: reservationList) {
                System.out.println(r.getId());
                System.out.println(r.getBook());
                System.out.println(r.getPickupLocation());
                System.out.println(r.getExpiryDate());
            }
        }else {
            System.out.println("No reservation for this library member");
        }

        return reservationList;
    }

    public void removeReservation(int id) {
        Reservation reservation = getReservationById(id);
        if (reservation == null) return;

        reservationRepository.delete(reservation);

        System.out.println("Removed " + reservation);
    }

    public void addReservation(Reservation reservation) {
        if(reservation != null){
            reservationRepository.create(reservation);
        }
    }
}
