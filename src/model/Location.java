package model;

import java.util.ArrayList;
import java.util.List;

public class Location {

    private String name;
    private BranchLibrary branchLibrary;
    private List<Copy> copies;

    public Location() {}

    public Location(String name, BranchLibrary branchLibrary) {
        this.name = name;
        this.branchLibrary = new BranchLibrary(branchLibrary);
        this.copies = new ArrayList<>();
    }

    public Location(Location location) {
        this.name = location.getName();
        this.branchLibrary = new BranchLibrary(location.getBranchLibrary());
        this.copies = location.getCopies();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BranchLibrary getBranchLibrary() {
        return branchLibrary;
    }

    public void setBranchLibrary(BranchLibrary branchLibrary) {
        this.branchLibrary = branchLibrary;
    }

    public List<Copy> getCopies() {
        return copies;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", branchLibrary=" + branchLibrary +
                ", copies=" + copies +
                '}';
    }
}
