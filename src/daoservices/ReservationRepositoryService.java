package daoservices;

import model.Book;
import model.LibraryMember;
import model.Location;
import model.Reservation;
import dao.ReservationDao;

import java.util.List;

public class ReservationRepositoryService {

    private ReservationDao reservationDao;

    public ReservationRepositoryService() {
        this.reservationDao = new ReservationDao();
    }

    public Reservation getReservationById(int id){
        Reservation reservation = reservationDao.readId(id);
        if(reservation == null)
            System.out.println("No reservation having this id");

        return reservation;
    }

    public List<Reservation> getReservationByBook(Book book){
        List<Reservation> reservationList = reservationDao.readBook(book);
        if(reservationList != null){
            for(Reservation r: reservationList) {
                System.out.println(r.getId());
                System.out.println(r.getLibraryMember());
                System.out.println(r.getPickupLocation());
                System.out.println(r.getExpiryDate());
            }
        }else {
            System.out.println("No reservations for this book");
        }

        return reservationList;
    }

    public List<Reservation> getReservationByMember(LibraryMember libraryMember){
        List<Reservation> reservationList = reservationDao.readMember(libraryMember);
        if(reservationList != null){
            for(Reservation r: reservationList) {
                System.out.println(r.getId());
                System.out.println(r.getBook());
                System.out.println(r.getPickupLocation());
                System.out.println(r.getExpiryDate());
                System.out.println("---------------------------");
            }
        }

        return reservationList;
    }

    public List<Reservation> getReservationByLocation(Location location){
        List<Reservation> reservationList = reservationDao.readLocation(location);
        if(reservationList != null){
            for(Reservation r: reservationList) {
                System.out.println(r.getId());
                System.out.println(r.getBook());
                System.out.println(r.getPickupLocation());
                System.out.println(r.getExpiryDate());
            }
        }else {
            System.out.println("No reservation for this location");
        }

        return reservationList;
    }

    public void removeReservation(int id) {
        Reservation reservation = getReservationById(id);
        if (reservation == null) return;

        reservationDao.delete(reservation);

        System.out.println("Removed " + reservation);
    }

    public void addReservation(Reservation reservation) {
        if(reservation != null){
            reservation.getBook().addReservation(reservation);
            reservation.getLibraryMember().addReservation(reservation);
            reservation.getPickupLocation().addReservation(reservation);
            reservationDao.create(reservation);
        }
    }
}
