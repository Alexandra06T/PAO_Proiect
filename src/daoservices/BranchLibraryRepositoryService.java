package daoservices;


import dao.LocationDao;
import model.BranchLibrary;
import dao.BranchLibraryDao;
import model.Location;

import java.util.List;

public class BranchLibraryRepositoryService {

    private BranchLibraryDao branchLibraryDao;
    private LocationDao locationDao;

    public BranchLibraryRepositoryService() {
        this.branchLibraryDao = new BranchLibraryDao();
    }

    public BranchLibrary getBranchLibrary(String name){
        BranchLibrary branchLibrary = branchLibraryDao.read(name);
        if(branchLibrary != null){
            System.out.println(branchLibrary);
        }

        return branchLibrary;
    }

    public void removeBranchLibrary(String name) {
        BranchLibrary branchLibrary = getBranchLibrary(name);
        if (branchLibrary == null) {
            System.out.println("There is no branch library having this name");
            return;
        }

        List<Location> locationList = branchLibrary.getLocations();
        //sterg toate locations din branchLibrary
        for(Location l : locationList) {
            locationDao.delete(l);
        }

        branchLibraryDao.delete(branchLibrary);
    }

    public void addBranchLibrary(BranchLibrary branchLibrary) {
        if(branchLibrary != null){
            branchLibraryDao.create(branchLibrary);
        }
    }

}
