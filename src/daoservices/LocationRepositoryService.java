package daoservices;

import dao.ReservationDao;
import model.BranchLibrary;
import model.Location;
import dao.LocationDao;
import model.Reservation;

import java.util.List;

public class LocationRepositoryService {

    private LocationDao locationDao;
    private ReservationDao reservationDao;

    public LocationRepositoryService() {
        this.locationDao = new LocationDao();
    }

    public Location getLocationByBranchAndName(BranchLibrary branchLibrary, String name){
        Location location = locationDao.read(branchLibrary, name);
        if(location != null){
            System.out.println(location);
        }

        return location;
    }

    public void removeLocation(BranchLibrary branchLibrary, String name) {
        Location location = getLocationByBranchAndName(branchLibrary, name);
        if (location == null) {
            System.out.println("Couldn't find the location");
            return;
        }

        //sterg toate rezervarile pentru locatie
        List<Reservation> reservationList = location.getReservations();
        for(Reservation r : reservationList) {
            reservationDao.delete(r);
        }

        location.getBranchLibrary().removeLocation(location);

        locationDao.delete(location);

    }

    public void addLocation(Location location) {
        if(location != null){
            locationDao.create(location);
            location.getBranchLibrary().addLocation(location);
        }
    }
}
