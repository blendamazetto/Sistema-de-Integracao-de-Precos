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
public class PgDaoFactory extends DaoFactory {

    public PgDaoFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public UserDAO getUserDAO() {
        return new PgUserDAO(this.connection);
    }
    
}
