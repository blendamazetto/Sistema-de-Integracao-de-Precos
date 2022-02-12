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
import model.Notebook;

/**
 *
 * @author blend
 */
public class PostgreNotebookDAO implements NotebookDAO {

    private final Connection connection;

    
    private static final String ALL_QUERY =
                                "SELECT id_notebook, modelo, descricao " +
                                "FROM j2ee.user " +
                                "ORDER BY id_notebook;";    
    
    public PostgreNotebookDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Notebook> all() throws SQLException {
        List<Notebook> notebookList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Notebook notebook = new Notebook();
                notebook.setId_notebook(result.getInt("id_notebook"));
                notebook.setModelo(result.getString("modelo"));
                notebook.setDescricao(result.getString("descricao"));
                

                notebookList.add(notebook);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostgreNotebookDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }
        return notebookList;
    }    
}