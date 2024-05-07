package service;

import daoservices.BranchLibraryRepositoryService;
import model.BranchLibrary;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.Scanner;

public class BranchLibraryService {
    private BranchLibraryRepositoryService databaseService;

    public BranchLibraryService() throws SQLException {
        this.databaseService = new BranchLibraryRepositoryService();
    }

    public void create(Scanner scanner) {
        System.out.println("Enter the name of the branch library:");
        String name = scanner.nextLine();
        System.out.println("Enter the address of the branch library:");
        String address = scanner.nextLine();
        BranchLibrary branchLibrary = new BranchLibrary(name, address);
        try {
            databaseService.addBranchLibrary(branchLibrary);
            System.out.println("Branch library added");
        } catch (InvalidDataException e) {
            System.out.println("Creation failed: " + e.getMessage());
        }
    }

    public void read(Scanner scanner) {
        System.out.println("Enter the name of the branch library:");
        String name = scanner.nextLine();
        try {
            BranchLibrary branchLibrary = databaseService.getBranchLibrary(name);
            System.out.println(branchLibrary);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void view() {
        try {
            System.out.println("BRANCH LIBRARIES:");
            databaseService.printAll();
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Enter the name of the branch library:");
        String name = scanner.nextLine();
        try {
            BranchLibrary branchLibrary = databaseService.getBranchLibrary(name);
            databaseService.removeBranchLibrary(branchLibrary);
            System.out.println("Branch library removed successfully\n");
        } catch (InvalidDataException e) {
            System.out.println("Removal failed: " + e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        try {
            System.out.println("Enter the name of the branch library you want to edit:");
            String name = scanner.nextLine();
            BranchLibrary branchLibrary = databaseService.getBranchLibrary(name);
            System.out.println("Enter the new name of the branch library:");
            String newName = scanner.nextLine();
            System.out.println("Enter the new address of the branch library:");
            String newAddress = scanner.nextLine();
            branchLibrary.setName(newName);
            branchLibrary.setAddress(newAddress);
            databaseService.updateBranchLibrary(branchLibrary);
            System.out.println("Branch library updated successfully!\n");
        } catch (InvalidDataException e) {
            System.out.println("SQLException " + e.getMessage());
        }
    }
}
