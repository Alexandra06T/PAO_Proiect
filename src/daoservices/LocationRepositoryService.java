package daoservices;

import model.BranchLibrary;
import model.Location;
import dao.LocationDao;

public class LocationRepositoryService {

    private LocationDao locationDao;

    public LocationRepositoryService() {
        this.locationDao = new LocationDao();
    }

    public Location getLocationByBranchAndName(BranchLibrary branchLibrary, String name){
        Location location = locationDao.read(branchLibrary, name);
        if(location != null){
            System.out.println(location);
        }else {
            System.out.println("No such location in this branch library");
        }

        return location;
    }

    public void removeLocation(BranchLibrary branchLibrary, String name) {
        Location location = getLocationByBranchAndName(branchLibrary, name);
        if (location == null) return;

        locationDao.delete(location);

        System.out.println("Removed " + location);
    }

    public void addLocation(Location location) {
        if(location != null){
            locationDao.create(location);
        }
    }
}
