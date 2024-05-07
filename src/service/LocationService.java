package service;

import daoservices.BranchLibraryRepositoryService;
import daoservices.LocationRepositoryService;
import model.BranchLibrary;
import model.Location;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.Scanner;

public class LocationService {

    private LocationRepositoryService databaseService;
    private BranchLibraryRepositoryService branchLibraryRepositoryService;

    public LocationService() throws SQLException {
        this.databaseService = new LocationRepositoryService();
        this.branchLibraryRepositoryService = new BranchLibraryRepositoryService();
    }

    public void create(Scanner scanner) {
        try {
            BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
            System.out.println("Enter the name of the new location:");
            String name = scanner.nextLine();
            Location location = new Location(name, branchLibrary.getBranchLibraryID());
            databaseService.addLocation(location);
            System.out.println("Location created successfully!\n");
        } catch (InvalidDataException e) {
            System.out.println("Creation failed: " + e.getMessage());
        }
    }

    private BranchLibrary chooseBranchLibrary(Scanner scanner) throws InvalidDataException {
        System.out.println("Enter the name of the branch library:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = branchLibraryRepositoryService.getBranchLibrary(name);
        if(branchLibrary == null) {
            throw new InvalidDataException("Couldn't find the branch library");
        }
        return branchLibrary;
    }

    public void view() {
        try {
            System.out.println("LOCATIONS:");
            databaseService.printAll();
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void read(Scanner scanner) {
        try {
            BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
            System.out.println("Enter the name of the location you are looking for:");
            String name = scanner.nextLine();
            Location location = databaseService.getLocationByBranchAndName(name, branchLibrary.getBranchLibraryID());
            System.out.print(location + "Adresa: " + branchLibrary.getName() + ", " + branchLibrary.getAddress() + '\n');
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        try {
            BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
            System.out.println("Enter the name of the location you are looking for:");
            String name = scanner.nextLine();
            Location location = databaseService.getLocationByBranchAndName(name, branchLibrary.getBranchLibraryID());
            databaseService.removeLocation(location);
            System.out.println("Location removed successfully!\n");
        } catch (InvalidDataException e) {
            System.out.println("Removal failed: " + e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        try {
            BranchLibrary branchLibrary = chooseBranchLibrary(scanner);
            System.out.println("Enter the name of the location you are looking for:");
            String name = scanner.nextLine();
            Location location = databaseService.getLocationByBranchAndName(name, branchLibrary.getBranchLibraryID());
            System.out.println("Enter the new name of the location:");
            String newName = scanner.nextLine();
            location.setName(newName);
            databaseService.updateLocation(location);
        } catch (InvalidDataException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }
}
