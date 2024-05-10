package daoservices;

import dao.BranchLibraryDao;
import dao.ReservationDao;
import model.Book;
import model.BranchLibrary;
import model.Location;
import dao.LocationDao;
import model.Reservation;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.List;

import static java.util.stream.Collectors.groupingBy;

public class LocationRepositoryService {

    private LocationDao locationDao = LocationDao.getInstance();
    private BranchLibraryDao branchLibraryDao = BranchLibraryDao.getInstance();
    private ReservationDao reservationDao;

    public LocationRepositoryService() throws SQLException {}

    public void printAll() throws InvalidDataException {
        try {
            List<Location> locations = locationDao.getAll();
            if(locations == null)
                throw new InvalidDataException("There is no location.");

            locations.stream().collect(groupingBy(Location::getBranchLibraryID)).forEach((br, l) -> {
                try {
                    BranchLibrary b = branchLibraryDao.readByID(String.valueOf(br));
                    System.out.println(b.getName().toUpperCase());
                    l.stream().forEach(System.out::print);
                    System.out.println();
                } catch (SQLException e) {
                    System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
                }
            });

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<Location> getAll() throws InvalidDataException {
        try {
            List<Location> locations = locationDao.getAll();
            if(locations == null)
                throw new InvalidDataException("There is no location.");
            return locations;

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;
    }

    public Location getLocationByID(int id) throws InvalidDataException {
        try {
            Location location = locationDao.read(String.valueOf(id));
            if(location == null){
                throw new InvalidDataException("There is no location having this id");
            }
            return location;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;

    }


    public Location getLocationByBranchAndName(String name, int branchLibraryID) throws InvalidDataException {
        try {
            Location location = locationDao.readByName(name, branchLibraryID);
            if(location == null){
                throw new InvalidDataException("There is no location having this name");
            }
            return location;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
        return null;

    }

    public void removeLocation(Location location) throws InvalidDataException {
        if (location == null) {
           throw new InvalidDataException("Invalid location");
        }
        try {
            locationDao.delete(location);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void addLocation(Location location) throws InvalidDataException{
        if (location == null)
            throw new InvalidDataException("Invalid book");
        try {
            if(locationDao.readByName(location.getName(), location.getLocationID()) != null) {
                throw new InvalidDataException("There is already a location in this branch library having this name");
            }
            locationDao.add(location);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateLocation(Location location) throws InvalidDataException {
        if(location == null)
            throw new InvalidDataException("Invalid location");
        try {
            Location dupl = locationDao.readByName(location.getName(), location.getBranchLibraryID());
            if(dupl != null && dupl.getLocationID() != location.getLocationID())
                throw new InvalidDataException("There is already a location in this branch library having this name");
            locationDao.update(location);
            System.out.println("Location updated successfully!");
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }
}
