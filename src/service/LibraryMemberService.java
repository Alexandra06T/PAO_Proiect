package service;

import daoservices.LibraryMemberRepositoryService;
import daoservices.TransactionRepositoryService;
import model.LibraryMember;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.Scanner;

public class LibraryMemberService {

    private LibraryMemberRepositoryService databaseService;
    private TransactionRepositoryService transactionRepositoryService;

    public LibraryMemberService() throws SQLException {
        this.databaseService = new LibraryMemberRepositoryService();
        this.transactionRepositoryService = new TransactionRepositoryService();
    }

    public void view() {
        try {
            System.out.println("LIBRARY MEMBERS:");
            databaseService.printAll();
            System.out.println();
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void create(Scanner scanner) {
        System.out.println("Enter the name of the member:");
        String name = scanner.nextLine();
        System.out.println("Enter the email address of the member:");
        String emailAddress = scanner.nextLine();
        if(!emailAddress.matches("[a-z]+(.[a-z0-9])*@[a-z0-9]+.(com|org|ro|gov)")) {
            System.out.println("The email address has an invalid format");
            return;
        }
        System.out.println("Enter the phone number of the member:");
        String phoneNumber = scanner.nextLine();
        //verificare simpla a unui numar de telefon
        if(!phoneNumber.matches("0([237])[0-9]{8}")) {
            System.out.println("The phone number has an invalid format");
            return;
        }
        System.out.println("Enter the address of the member:");
        String address = scanner.nextLine();
        LibraryMember libraryMember = new LibraryMember(name, emailAddress, phoneNumber, address);
        try {
            databaseService.addLibraryMember(libraryMember);
            System.out.println("Library member enrolled");
        } catch (InvalidDataException e) {
            System.out.println("Creation failed: " + e.getMessage());
        }
    }

    public void read(Scanner scanner) {
        System.out.println("Enter the ID of the library member:");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        try {
            LibraryMember libraryMember = databaseService.getLibraryMemberById(memberId);
            System.out.println(libraryMember);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Enter the ID of the library member:");
        int id = scanner.nextInt();
        scanner.nextLine();
        try {
            LibraryMember libraryMember = databaseService.getLibraryMemberById(id);
            if(transactionRepositoryService.getNrCurrentCheckIns(libraryMember.getMemberID()) == 0) {
                databaseService.removeLibraryMember(libraryMember);
                System.out.println("Library member removed");
            }
            else throw new InvalidDataException("The library member has not returned all the books!");
        } catch (InvalidDataException e) {
            System.out.println("Removal failed: " + e.getMessage());
        }
    }

    public void update(Scanner scanner) {
        System.out.println("Enter the ID of the library member:");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        try {
            LibraryMember libraryMember = databaseService.getLibraryMemberById(memberId);
            System.out.println("Enter the new name of the library member:");
            String newName = scanner.nextLine();
            System.out.println("Enter the new email address of the library member:");
            String newEmail = scanner.nextLine();
            System.out.println("Enter the new phone number of the library member:");
            String newPhone = scanner.nextLine();
            System.out.println("Enter the new address of the library member:");
            String newAddress = scanner.nextLine();
            libraryMember.setName(newName);
            libraryMember.setEmailAddress(newEmail);
            libraryMember.setPhoneNumber(newPhone);
            libraryMember.setAddress(newAddress);

            databaseService.updateLibraryMember(libraryMember);
            System.out.println("Library member's details updated successfully!");
        } catch (InvalidDataException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }
}
