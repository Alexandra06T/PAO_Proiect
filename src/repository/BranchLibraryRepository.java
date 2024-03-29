package repository;

import model.Book;
import model.BranchLibrary;

import java.util.ArrayList;
import java.util.List;

public class BranchLibraryRepository {
    private static List<BranchLibrary> branchLibraries = new ArrayList<>();

    public BranchLibrary read(String name) {
        if(!branchLibraries.isEmpty()){
            for(BranchLibrary bl : branchLibraries){
                if(bl.getName().equals(name)){
                    return bl;
                }
            }
        }
        return null;
    }

    public void delete(BranchLibrary branchLibrary) {
        branchLibraries.remove(branchLibrary);
    }

    public void create(BranchLibrary branchLibrary) {
        branchLibraries.add(branchLibrary);
    }
}
