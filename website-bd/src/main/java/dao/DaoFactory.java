/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import jdbc.ConnectionFactory;

/**
 *
 * @author blend
 */
public abstract class DaoFactory implements AutoCloseable{
    
    protected Connection connection;
    
    public static DaoFactory getInstance() throws ClassNotFoundException, IOException, SQLException{
        Connection connection = ConnectionFactory.getInstance().getConnection();
        DaoFactory factory;
        
        if(ConnectionFactory.getDbServer().equals("postgresql")) {
            factory = new PgDaoFactory(connection);
        }
        else {
            throw new RuntimeException("Servidor nao encontrado");
        }
        return factory;
    }
    public void beginTransaction() throws SQLException {
        try {
            connection.setAutoCommit(false);
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            
            throw new SQLException("Erro ao abrir transacao");
        }
    }
    
    public void commitTransaction() throws SQLException {
        try {
            connection.commit();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            
            throw new SQLException("Erro ao finalizar transacao");
        }
    }
    
    public void rollbackTransaction() throws SQLException {
        try {
            connection.rollback();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            
            throw new SQLException("Erro ao finalizar transacao");
        }
    }
    
    public void closeConnection() throws SQLException {
        try {
            connection.close();
        } catch (SQLException ex){
            System.err.println(ex.getMessage());
            
            throw new SQLException("Erro ao fechar conexao com o banco de dados");
        }
    }
    
    public abstract UserDAO getUserDAO();
    
    @Override
    public void close() throws SQLException{
        closeConnection();
    } 
}
