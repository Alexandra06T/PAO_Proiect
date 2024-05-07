package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BranchLibrary {

    private int branchLibraryID;
    private String name;
    private String address;

    public BranchLibrary() {}

    public BranchLibrary(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public BranchLibrary(BranchLibrary branchLibrary) {
        this.branchLibraryID = branchLibrary.getBranchLibraryID();
        this.name = branchLibrary.getName();
        this.address = branchLibrary.getAddress();
    }

    public int getBranchLibraryID() {
        return branchLibraryID;
    }

    public void setBranchLibraryID(int branchLibraryID) {
        this.branchLibraryID = branchLibraryID;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BranchLibrary that = (BranchLibrary) o;
        return Objects.equals(name, that.name) && Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address);
    }

    @Override
    public String toString() {
        return name + '\n' + address + '\n';
    }
}
