package daoservices;


import model.BranchLibrary;
import dao.BranchLibraryDao;

public class BranchLibraryRepositoryService {

    private BranchLibraryDao branchLibraryDao;

    public BranchLibraryRepositoryService() {
        this.branchLibraryDao = new BranchLibraryDao();
    }

    public BranchLibrary getBranchLibrary(String name){
        BranchLibrary branchLibrary = branchLibraryDao.read(name);
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

        branchLibraryDao.delete(branchLibrary);

        System.out.println("Removed " + branchLibrary.getName());
    }

    public void addBranchLibrary(BranchLibrary branchLibrary) {
        if(branchLibrary != null){
            branchLibraryDao.create(branchLibrary);
        }
    }

}
