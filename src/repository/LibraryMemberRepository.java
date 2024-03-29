package repository;

import model.Book;
import model.CheckIn;
import model.LibraryMember;

import java.util.ArrayList;
import java.util.List;

public class LibraryMemberRepository {
    private static List<LibraryMember> libraryMembers = new ArrayList<>();

    public LibraryMember read(String memberId) {
        if(!libraryMembers.isEmpty()){
            for(LibraryMember lm : libraryMembers){
                if(lm.getMemberID().equals(memberId)){
                    return lm;
                }
            }
        }
        return null;
    }

    public void delete(LibraryMember libraryMember) {
        libraryMembers.remove(libraryMember);
    }

    public void create(LibraryMember libraryMember) {
        libraryMembers.add(libraryMember);
    }
}
