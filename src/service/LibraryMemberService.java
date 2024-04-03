package service;

import daoservices.LibraryMemberRepositoryService;
import model.LibraryMember;

import java.util.Scanner;

public class LibraryMemberService {

    private LibraryMemberRepositoryService databaseService;

    public LibraryMemberService(){
        this.databaseService = new LibraryMemberRepositoryService();
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
        databaseService.addLibraryMember(libraryMember);
        System.out.println("Library member enrolled");
    }

    public void read(Scanner scanner) {
        System.out.println("Enter the ID of the library member:");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        LibraryMember libraryMember = databaseService.getLibraryMemberById(memberId);
        if(libraryMember == null) {
            return;
        }
    }

    public void delete(Scanner scanner) {
        System.out.println("Enter the ID of the library member:");
        int id = scanner.nextInt();
        scanner.nextLine();
        LibraryMember libraryMember = databaseService.getLibraryMemberById(id);
        if(libraryMember == null) {
            return;
        }
        databaseService.removeLibraryMember(libraryMember.getMemberID());
    }

    public void update(Scanner scanner) {
        System.out.println("Enter the ID of the library member:");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        LibraryMember libraryMember = databaseService.getLibraryMemberById(memberId);
        if(libraryMember == null) {
            return;
        }
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
    }
}
