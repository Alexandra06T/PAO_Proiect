package service;

import daoservices.BranchLibraryRepositoryService;
import model.BranchLibrary;

import java.util.Scanner;

public class BranchLibraryService {
    private BranchLibraryRepositoryService databaseService;

    public BranchLibraryService(){
        this.databaseService = new BranchLibraryRepositoryService();
    }

    public void create(Scanner scanner) {
        System.out.println("Enter the name of the branch library:");
        String name = scanner.nextLine();
        if(databaseService.getBranchLibrary(name) != null) {
            System.out.println("There is already a branch library having this name");
            return;
        }
        System.out.println("Enter the address of the branch library:");
        String address = scanner.nextLine();
        BranchLibrary branchLibrary = new BranchLibrary(name, address);
        databaseService.addBranchLibrary(branchLibrary);
        System.out.println("Branch library added");
    }

    public void read(Scanner scanner) {
        System.out.println("Enter the name of the branch library:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = databaseService.getBranchLibrary(name);
        if(branchLibrary != null) {
            System.out.println(branchLibrary);
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Enter the name of the branch library:");
        String name = scanner.nextLine();
        databaseService.removeBranchLibrary(name);
    }

    public void update(Scanner scanner) {
        System.out.println("Enter the name of the branch library you want to edit:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = databaseService.getBranchLibrary(name);
        if(branchLibrary == null) {
            return;
        }
        System.out.println("Enter the new name of the branch library:");
        String newName = scanner.nextLine();
        System.out.println("Enter the new address of the branch library:");
        String newAddress = scanner.nextLine();
        branchLibrary.setName(newName);
        branchLibrary.setAddress(newAddress);
    }
}
