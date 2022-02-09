/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author blend
 */
public class PgUserDAO implements UserDAO {
    
    private final Connection connection;
    
    private static final String READ_QUERY = 
                                "SELECT login, nome, nascimento, avatar" +
                                "FROM j2ee.user" +
                                "WHERE id = ?;";
    
    private static final String ALL_QUERY = 
                                "SELECT id, login" +
                                "FROM j2ee.user" +
                                "ORDER BY id;";

    public PgUserDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void authenticate(User usuario) throws SQLException, SecurityException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User getByLogin(String login) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void create(User t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void updatee(User t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<User> all() throws SQLException {
        List<User> userList = new ArrayList<>();
        
        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
                ResultSet result = statement.executeQuery()) {
            while(result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setLogin(result.getString("Login"));
                
                userList.add(user);
            }
        } catch (SQLException ex){
                Logger.getLogger(PgUserDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                
                throw new SQLException("Erro ao listar usuarios ");                
       }
        return userList;
    }
}
    
