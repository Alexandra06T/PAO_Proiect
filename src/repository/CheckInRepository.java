package repository;

import model.Book;
import model.BranchLibrary;
import model.CheckIn;
import model.CheckIn;

import java.util.ArrayList;
import java.util.List;

public class CheckInRepository {
    private static List<CheckIn> checkIns = new ArrayList<>();

    public CheckIn read(int id) {
        if(!checkIns.isEmpty()){
            for(CheckIn c : checkIns){
                if(c.getId() == id){
                    return c;
                }
            }
        }
        return null;
    }

    public void delete(CheckIn checkIn) {
        checkIns.remove(checkIn);
    }

    public void create(CheckIn checkIn) {
        checkIns.add(checkIn);
    }
}
