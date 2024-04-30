package dao;

import java.sql.SQLException;
import java.util.List;

public interface DaoInterface <T>{

    public void add(T entity)  throws SQLException;

    List<T> getAll() throws SQLException;

    public T read(String id) throws SQLException;

    public void delete(T entity) throws SQLException;

    public void update(T entity) throws SQLException;
}

//TODO: implement DaoInterface in other daos