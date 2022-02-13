package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.Notebook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PgNotebookDAO implements NotebookDAO {
    private Connection connection;

    public PgNotebookDAO(Connection connection) { this.connection = connection; }

    private static final String ALL_QUERY =
                                            "SELECT * " +
                                            "FROM lojas_notebook.notebook;";
    
    private static final String ADD_QUERY_NOTEBOOK =
                                "INSERT INTO lojas_notebook.notebook(modelo, descricao, marca) " +
                                "VALUES(?, ?, ?);";
    
    private static final String ADD_QUERY_LOJA =
                                "INSERT INTO lojas_notebook.loja(nome_loja, url_site) " +
                                "VALUES(?, ?);";
    
    private static final String ADD_QUERY_LOJA_VENDE_NOTEBOOK =
                                "INSERT INTO lojas_notebook.loja(nome_loja, classificacao, preco, disponibilidade, notebook_nome, url_produto) " +
                                "VALUES(?, ?, ?, ?, ?, ?);";

    @Override
    public List<Notebook> all() throws SQLException {
        List<Notebook> lista_notebooks = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Notebook notebook = new Notebook();
                
                notebook.setId_notebook(result.getInt("id_notebook"));
                notebook.setModelo(result.getString("modelo"));
                notebook.setDescricao(result.getString("descricao"));

                lista_notebooks.add(notebook);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgNotebookDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar jogos.");
        }

        return lista_notebooks;
    }
    
    @Override
    public void update() throws SQLException, FileNotFoundException, IOException, ParseException {
        String nome_loja = "Amazon";

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("../../crawlings/resultsAmazon.json"));

        for (Object o : jsonArray) {
            JSONObject notebook = (JSONObject) o;

            String descricao = (String) notebook.get("descricao");
            String preco = (String) notebook.get("preco");
            String modelo = (String) notebook.get("modelo");
            String marca = (String) notebook.get("marca");
            String rating = (String) notebook.get("rating");
            String url = (String) notebook.get("url");
            
            try (PreparedStatement statement = connection.prepareStatement(ADD_QUERY_NOTEBOOK)) {
            statement.setString(1, modelo);
            statement.setString(2, descricao);
            statement.setString(3, marca); 
            
            statement.executeUpdate();
            }
            
            try (PreparedStatement statement = connection.prepareStatement(ADD_QUERY_LOJA)) {
            statement.setString(1, nome_loja);
            statement.setString(2, url); 
            
            statement.executeUpdate();
            }
            
            try (PreparedStatement statement = connection.prepareStatement(ADD_QUERY_LOJA_VENDE_NOTEBOOK)) {
            statement.setString(1, nome_loja);
            statement.setString(2, rating);
            statement.setString(3, preco); 
            statement.setString(4, "1");
            statement.setString(5, modelo); 
            statement.setString(6, url);
            
            statement.executeUpdate();
            }
        }
    }
    

    @Override
    public void create(Notebook t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Notebook read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
