package dao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import org.json.simple.parser.ParseException;

public interface DAO<T> {

    public void create(T t) throws SQLException;
    public T read(Integer id) throws SQLException;
    public void update() throws SQLException, FileNotFoundException, IOException, ParseException;
    public void delete(Integer id) throws SQLException;
    public List<T> all() throws SQLException;

}
