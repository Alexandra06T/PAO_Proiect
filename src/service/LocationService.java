package service;

import dao.BookDAOService;
import dao.BranchLibraryDAOService;
import dao.CopyDAOService;
import dao.LocationDAOService;
import model.BranchLibrary;
import model.Copy;
import model.Location;

import java.util.Scanner;

public class LocationService {

    private LocationDAOService databaseService;
    private BranchLibraryDAOService branchLibraryDAOService;
    private CopyDAOService copyDAOService;

    public LocationService(){
        this.databaseService = new LocationDAOService();
    }

    public void create(Scanner scanner) {
        BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
        System.out.println("Enter the name of the new location:");
        String name = scanner.nextLine();
        if(databaseService.getLocationByBranchAndName(branchLibrary, name) != null) {
            System.out.println("There is already a location in the branch library having this name");
            return;
        }
        Location location = new Location(name, branchLibrary);
        databaseService.addLocation(location);
    }

    private BranchLibrary chooseBranchLibrary(Scanner scanner) {
        System.out.println("Enter the name of the branch library:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = branchLibraryDAOService.getBranchLibrary(name);
        if(branchLibrary == null) {
            System.out.println("wrong name");
        }
        return branchLibrary;
    }

    public void read(Scanner scanner) {
        BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
        System.out.println("Enter the name of the location you are looking for:");
        String name = scanner.nextLine();
        Location location = databaseService.getLocationByBranchAndName(branchLibrary, name);
        if(location == null) {
            System.out.println("Couldn't find the location");
            return;
        }
    }

    public void delete(Scanner scanner) {
        BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
        System.out.println("Enter the name of the location you are looking for:");
        String name = scanner.nextLine();
        Location location = databaseService.getLocationByBranchAndName(branchLibrary, name);
        if(location == null) {
            System.out.println("Couldn't find the location");
            return;
        }
        databaseService.removeLocation(location.getBranchLibrary(), location.getName());
        branchLibrary.removeLocation(location);
    }

    public void update(Scanner scanner) {
        BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
        System.out.println("Enter the name of the location you are looking for:");
        String name = scanner.nextLine();
        Location location = databaseService.getLocationByBranchAndName(branchLibrary, name);
        if(location == null) {
            System.out.println("Couldn't find the location");
            return;
        }
        System.out.println("Enter the new name of the location:");
        String newName = scanner.nextLine();
        if(databaseService.getLocationByBranchAndName(branchLibrary, newName) != null) {
            System.out.println("There is already a location in this library having this name");
            return;
        }
        location.setName(newName);
    }
}
