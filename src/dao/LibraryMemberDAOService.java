package dao;

import model.LibraryMember;
import repository.LibraryMemberRepository;

public class LibraryMemberDAOService {

    private LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberDAOService() {
        this.libraryMemberRepository = new LibraryMemberRepository();
    }

    public LibraryMember getLibraryMemberById(String memberId){
        LibraryMember libraryMember = libraryMemberRepository.read(memberId);
        if(libraryMember != null){
            System.out.println(libraryMember);
        }else {
            System.out.println("No library member having this Id");
        }

        return libraryMember;
    }

    public void removeLibraryMember(String memberId) {
        LibraryMember libraryMember = getLibraryMemberById(memberId);
        if (libraryMember == null) return;

        libraryMemberRepository.delete(libraryMember);

        System.out.println("Removed " + libraryMember);
    }

    public void addLibraryMember(LibraryMember libraryMember) {
        if(libraryMember != null){
            libraryMemberRepository.create(libraryMember);
        }
    }
}
