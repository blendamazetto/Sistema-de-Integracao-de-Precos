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
import model.Notebook;
import connection.factory.PostgreConnectionFactory;
import java.io.IOException;

/**
 *
 * @author blend
 */
public class NotebookDAO {
    
    private Connection connection;
    
    private static final String ALL_QUERY =
                                "SELECT id_notebook, modelo, descricao " +
                                "FROM j2ee.user " +
                                "ORDER BY id_notebook;";
	
	public NotebookDAO() throws IOException, SQLException, ClassNotFoundException {
            
        this.connection = new PostgreConnectionFactory().getConnection();
        }
	
	public List<Notebook> buscar(String nome){
		
		List<Notebook> notebooks = new ArrayList<>();
		
		try {
			PreparedStatement prstate = connection.prepareStatement(ALL_QUERY);
			
			prstate.setString(1, ("%"+nome+"%").toUpperCase());
					
			ResultSet resultado = prstate.executeQuery();
			
			while (resultado.next()){
				Notebook notebook = new Notebook();
				notebook.setId_notebook(resultado.getInt("id_notebook"));
				notebook.setModelo(resultado.getString("modelo"));
				notebook.setDescricao(resultado.getString("descricao"));
				
				notebooks.add(notebook);
			}
			resultado.close();
			prstate.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException(e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				throw new RuntimeException(e);
			}
		}	
		return notebooks;	
	}	
    
}
