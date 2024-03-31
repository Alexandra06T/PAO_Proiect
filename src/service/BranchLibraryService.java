package service;

import dao.BranchLibraryDAOService;
import model.BranchLibrary;

import java.util.Scanner;

public class BranchLibraryService {
    private BranchLibraryDAOService databaseService;

    public BranchLibraryService(){
        this.databaseService = new BranchLibraryDAOService();
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
        if(branchLibrary == null) {
            System.out.println("There is no branch library having this name");
            return;
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Enter the name of the branch library:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = databaseService.getBranchLibrary(name);
        if(branchLibrary == null) {
            System.out.println("There is no branch library having this name");
            return;
        }
        databaseService.removeBranchLibrary(branchLibrary.getName());
    }

    public void update(Scanner scanner) {
        System.out.println("Enter the name of the branch library you want to edit:");
        String name = scanner.nextLine();
        BranchLibrary branchLibrary = databaseService.getBranchLibrary(name);
        if(branchLibrary == null) {
            System.out.println("There is no branch library having this name");
            return;
        }
        System.out.println("Enter the new name of the branch library:");
        String newName = scanner.nextLine();
        System.out.println("Enter the new address of the branch library:");
        String newAddress = scanner.nextLine();
        branchLibrary.setName(name);
        branchLibrary.setAddress(newAddress);
    }
}
