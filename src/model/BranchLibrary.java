package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BranchLibrary {

    private String name;
    private String address;
    private List<Location> locations;

    public BranchLibrary() {}

    public BranchLibrary(String name, String address) {
        this.name = name;
        this.address = address;
        this.locations = new ArrayList<>();
    }

    public BranchLibrary(BranchLibrary branchLibrary) {
        this.name = branchLibrary.getName();
        this.address = branchLibrary.getAddress();
        this.locations = new ArrayList<>();
        for(Location l: branchLibrary.getLocations()) {
            this.locations.add(new Location(l));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void addLocation(Location location) {
        locations.add(new Location(location));
    }

    public void removeLocation(Location location) {
        locations.remove(location);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchLibrary that = (BranchLibrary) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address) && Objects.equals(locations, that.locations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, locations);
    }

    @Override
    public String toString() {
        return "BranchLibrary{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", locations=" + locations +
                '}';
    }
}
