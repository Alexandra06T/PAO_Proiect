package service;

import daoservices.BranchLibraryRepositoryService;
import daoservices.LocationRepositoryService;
import model.BranchLibrary;
import model.Location;

import java.util.Scanner;

public class LocationService {

    private LocationRepositoryService databaseService;
    private BranchLibraryRepositoryService branchLibraryRepositoryService;

    public LocationService(){
        this.databaseService = new LocationRepositoryService();
        this.branchLibraryRepositoryService = new BranchLibraryRepositoryService();
    }

    public void create(Scanner scanner) {
        BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
        if(branchLibrary == null) return;
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
        BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibrary(name);
        if(branchLibrary == null) {
            System.out.println("Couldn't find the branch library");
        }
        return branchLibrary;
    }

    public void read(Scanner scanner) {
        BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
        if(branchLibrary == null) return;
        System.out.println("Enter the name of the location you are looking for:");
        String name = scanner.nextLine();
        Location location = databaseService.getLocationByBranchAndName(branchLibrary, name);
        if(location == null) {
            System.out.println("Couldn't find the location");
            return;
        }
        System.out.println(location);
    }

    public void delete(Scanner scanner) {
        BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
        if(branchLibrary == null) return;
        System.out.println("Enter the name of the location you are looking for:");
        String name = scanner.nextLine();
        databaseService.removeLocation(branchLibrary, name);
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
