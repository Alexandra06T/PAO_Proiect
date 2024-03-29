package repository;

import model.Book;
import model.CheckOut;
import model.CheckOut;

import java.util.ArrayList;
import java.util.List;

public class CheckOutRepository {
    private static List<CheckOut> checkOuts = new ArrayList<>();

    public CheckOut read(int id) {
        if(!checkOuts.isEmpty()){
            for(CheckOut c : checkOuts){
                if(c.getId() == id){
                    return c;
                }
            }
        }
        return null;
    }

    public void delete(CheckOut checkOut) {
        checkOuts.remove(checkOut);
    }

    public void create(CheckOut checkOut) {
        checkOuts.add(checkOut);
    }
}
