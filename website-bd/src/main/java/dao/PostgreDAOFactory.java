/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.sql.Connection;

/**
 *
 * @author blend
 */
public class PostgreDAOFactory extends DAOFactory {

    public PostgreDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public NotebookDAO getNotebookDAO() {
        return new PostgreNotebookDAO(this.connection);
    }    
}