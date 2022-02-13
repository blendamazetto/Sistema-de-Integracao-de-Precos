/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            return new NotebookDAO();
        } catch (IOException | SQLException | ClassNotFoundException ex) {
            Logger.getLogger(PostgreDAOFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }    
}