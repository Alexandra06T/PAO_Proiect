package dao;

import model.BranchLibrary;
import model.Location;
import repository.LocationRepository;

public class LocationDAOService {

    private LocationRepository locationRepository;

    public LocationDAOService() {
        this.locationRepository = new LocationRepository();
    }

    public Location getLocationByBranchAndName(BranchLibrary branchLibrary, String name){
        Location location = locationRepository.read(branchLibrary, name);
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

        locationRepository.delete(location);

        System.out.println("Removed " + location);
    }

    public void addLocation(Location location) {
        if(location != null){
            locationRepository.create(location);
        }
    }
}
