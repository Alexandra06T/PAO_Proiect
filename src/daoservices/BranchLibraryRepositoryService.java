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

    public void printAll() throws InvalidDataException {
        try {
            List<BranchLibrary> branchLibraries = branchLibraryDao.getAll();
            if(branchLibraries == null)
                throw new InvalidDataException("There is no branch library");
            branchLibraries.forEach(System.out:: println);

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public List<BranchLibrary> getAll() throws InvalidDataException {
        try {
            List<BranchLibrary> branchLibraries = branchLibraryDao.getAll();
            if(branchLibraries == null){
                throw new InvalidDataException("There is no branch library.");
            }
            return branchLibraries;

        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return null;
    }

    public BranchLibrary getBranchLibrary(String name) throws InvalidDataException {
        try {
            BranchLibrary branchLibrary = branchLibraryDao.read(name);
            if(branchLibrary == null){
                throw new InvalidDataException("There is no branch library having this name");
            }
            return branchLibrary;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return null;
    }

    public BranchLibrary getBranchLibraryByID(int id) throws InvalidDataException {
        try {
            BranchLibrary branchLibrary = branchLibraryDao.readByID(String.valueOf(id));
            if(branchLibrary == null){
                throw new InvalidDataException("There is no branch library having this id");
            }
            return branchLibrary;
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }

        return null;
    }

    public void removeBranchLibrary(BranchLibrary branchLibrary) throws InvalidDataException {
        if (branchLibrary == null) throw new InvalidDataException("Invalid branch library");
        try {
            branchLibraryDao.delete(branchLibrary);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void addBranchLibrary(BranchLibrary branchLibrary) throws InvalidDataException {
        try {
            if(branchLibrary == null)
                throw new InvalidDataException("Invalid branch library");

            //verificam sa nu mai existe o alta categorie avand acelasi nume
            if(branchLibraryDao.read(branchLibrary.getName()) != null)
                throw new InvalidDataException("There is already a branch library having this name!");

            branchLibraryDao.add(branchLibrary);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

    public void updateBranchLibrary(BranchLibrary branchLibrary) throws InvalidDataException {
        try {
            if(branchLibrary == null)
                throw new InvalidDataException("Invalid branch library");
            //verificam sa nu mai existe o alta categorie avand acelasi nume
            BranchLibrary dupl = branchLibraryDao.read(branchLibrary.getName());

            if(dupl != null && dupl.getBranchLibraryID() != branchLibrary.getBranchLibraryID())
                throw new InvalidDataException("There is already a branch library having this name!");

            branchLibraryDao.update(branchLibrary);
        } catch (SQLException e) {
            System.out.println("SQLException " + e.getSQLState() + " " + e.getMessage());
        }
    }

}