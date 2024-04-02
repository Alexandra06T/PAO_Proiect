package dao;

import model.LibraryMember;

import java.util.HashMap;

public class LibraryMemberDao {
    private static HashMap<Integer, LibraryMember> libraryMembers = new HashMap<>();

    public LibraryMember read(int memberId) {
        return libraryMembers.get(memberId);
    }

    public void delete(LibraryMember libraryMember) {
        libraryMembers.remove(libraryMember.getMemberID());
    }

    public void create(LibraryMember libraryMember) {
        libraryMembers.put(libraryMember.getMemberID(), libraryMember);
    }
}
