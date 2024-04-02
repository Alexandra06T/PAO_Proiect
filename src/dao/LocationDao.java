package dao;

import model.BranchLibrary;
import model.Location;

import java.util.ArrayList;
import java.util.List;

public class LocationDao {
    private static List<Location> locations = new ArrayList<>();

    public Location read(BranchLibrary branchLibrary, String name) {
        if(!locations.isEmpty()){
            for(Location l : locations){
                if(l.getBranchLibrary().equals(branchLibrary) && l.getName().equals(name)){
                    return l;
                }
            }
        }
        return null;
    }

    public void delete(Location location) {
        locations.remove(location);
    }

    public void create(Location location) {
        locations.add(location);
    }
}
