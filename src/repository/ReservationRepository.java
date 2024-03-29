package repository;

import model.Book;
import model.BranchLibrary;
import model.Location;
import model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationRepository {
    private static List<Reservation> reservations = new ArrayList<>();

    public Reservation read(int id) {
        if(!reservations.isEmpty()){
            for(Reservation r : reservations){
                if(r.getId() == id){
                    return r;
                }
            }
        }
        return null;
    }

    public void delete(Reservation reservation) {
        reservations.remove(reservation);
    }

    public void create(Reservation reservation) {
        reservations.add(reservation);
    }
}
