package daoservices;


import dao.LocationDao;
import model.BranchLibrary;
import dao.BranchLibraryDao;
import model.Category;
import model.Location;
import utils.InvalidDataException;

import java.sql.SQLException;
import java.util.List;

public class BranchLibraryRepositoryService {

    private BranchLibraryDao branchLibraryDao = BranchLibraryDao.getInstance();
    private LocationDao locationDao;

    public BranchLibraryRepositoryService() throws SQLException {}

    public void printAll() {
        try {
            List<BranchLibrary> branchLibraries = branchLibraryDao.getAll();
            if(branchLibraries != null){
                branchLibraries.forEach(System.out:: println);
            }else {
                System.out.println("There is no branch library.");
            }

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<BranchLibrary> getAll() {
        List<BranchLibrary> branchLibraries = null;

        try {
            branchLibraries = branchLibraryDao.getAll();
            if(branchLibraries == null){
                System.out.println("There is no branch library.");
            }

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return branchLibraries;
    }

    public BranchLibrary getBranchLibrary(String name) {
        BranchLibrary branchLibrary = null;
        try {
            branchLibrary = branchLibraryDao.read(name);
            if(branchLibrary == null){
                System.out.println("There is no branch library having this name");
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return branchLibrary;
    }

    public void removeBranchLibrary(BranchLibrary branchLibrary) {
        if (branchLibrary == null) return;
        try {
            branchLibraryDao.delete(branchLibrary);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void addBranchLibrary(BranchLibrary branchLibrary) throws InvalidDataException {
        try {
            if(branchLibrary != null){
                //verificam sa nu mai existe o alta categorie avand acelasi nume
                if(branchLibraryDao.read(branchLibrary.getName()) != null)
                    throw new InvalidDataException("There is already a branch library having this name!");
                branchLibraryDao.add(branchLibrary);
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateBranchLibrary(BranchLibrary branchLibrary) throws InvalidDataException {
        try {
            if(branchLibrary != null){
                //verificam sa nu mai existe o alta categorie avand acelasi nume
                BranchLibrary dupl = branchLibraryDao.read(branchLibrary.getName());

                if(dupl != null && dupl.getBranchLibraryID() != branchLibrary.getBranchLibraryID())
                    throw new InvalidDataException("There is already a branch library having this name!");

                branchLibraryDao.update(branchLibrary);
            }
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

}