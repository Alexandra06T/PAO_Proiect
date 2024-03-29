package dao;


import model.BranchLibrary;
import repository.BranchLibraryRepository;

public class BranchLibraryDAOService {

    private BranchLibraryRepository branchLibraryRepository;

    public BranchLibraryDAOService() {
        this.branchLibraryRepository = new BranchLibraryRepository();
    }

    public BranchLibrary getBranchLibrary(String name){
        BranchLibrary branchLibrary = branchLibraryRepository.read(name);
        if(branchLibrary != null){
            System.out.println(branchLibrary);
        }else {
            System.out.println("No branch library having this name");
        }

        return branchLibrary;
    }

    public void removeBranchLibrary(String name) {
        BranchLibrary branchLibrary = getBranchLibrary(name);
        if (branchLibrary == null) return;

        branchLibraryRepository.delete(branchLibrary);

        System.out.println("Removed " + branchLibrary.getName());
    }

    public void addBranchLibrary(BranchLibrary branchLibrary) {
        if(branchLibrary != null){
            branchLibraryRepository.create(branchLibrary);
        }
    }

}
