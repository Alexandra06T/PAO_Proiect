package repository;

import model.Book;
import model.CheckIn;
import model.LibraryMember;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LibraryMemberRepository {
    private static HashMap<String, LibraryMember> libraryMembers = new HashMap<>();

    public LibraryMember read(String memberId) {
        return libraryMembers.get(memberId);
    }

    public void delete(LibraryMember libraryMember) {
        libraryMembers.remove(libraryMember.getMemberID());
    }

    public void create(LibraryMember libraryMember) {
        libraryMembers.put(libraryMember.getMemberID(), libraryMember);
    }
}
