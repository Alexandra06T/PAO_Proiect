package model;

import java.util.ArrayList;
import java.util.List;

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
        this.locations = branchLibrary.getLocations();
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

    @Override
    public String toString() {
        return "BranchLibrary{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", locations=" + locations +
                '}';
    }
}
