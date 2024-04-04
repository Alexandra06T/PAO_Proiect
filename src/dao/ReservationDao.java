package dao;

import model.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public class ReservationDao {
    private static List<Reservation> reservations = new ArrayList<>();

    public Reservation readId(int id) {
        if(!reservations.isEmpty()){
            for(Reservation r : reservations){
                if(r.getId() == id){
                    return r;
                }
            }
        }
        return null;
    }

    public List<Reservation> readBook(Book book) {
        List<Reservation> reservationList = new ArrayList<>();
        if(!reservations.isEmpty()){
            for(Reservation r : reservations){
                if(r.getBook().equals(book) && r.getExpiryDate().isAfter(LocalDate.now())){
                    reservationList.add(r);
                }
            }
        }

        if(reservationList.isEmpty()) return null;
        return reservationList;
    }

    public List<Reservation> readMember(LibraryMember libraryMember) {
        List<Reservation> reservationList = new ArrayList<>();
        if(!reservations.isEmpty()){
            for(Reservation r : reservations){
                if(r.getLibraryMember().equals(libraryMember) && r.getExpiryDate().isAfter(LocalDate.now())){
                    reservationList.add(r);
                }
            }
        }

        if(reservationList.isEmpty()) return null;
        return reservationList;
    }

    public List<Reservation> readLocation(Location location) {
        List<Reservation> reservationList = new ArrayList<>();
        if(!reservations.isEmpty()){
            for(Reservation r : reservations){
                if(r.getPickupLocation().equals(location) && r.getExpiryDate().isAfter(LocalDate.now())){
                    reservationList.add(r);
                }
            }
        }

        if(reservationList.isEmpty()) return null;
        return reservationList;
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void create(Reservation reservation) {
        reservations.add(reservation);
    }
}
