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
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PgNotebookDAO implements NotebookDAO {
    private final Connection connection;

    public PgNotebookDAO(Connection connection) { this.connection = connection; }

    private static final String ALL_QUERY =
                                            "SELECT * " +
                                            "FROM lojas_notebook.notebook;";
    
    private static final String ADD_QUERY_NOTEBOOK =
                                            "INSERT INTO lojas_notebook.notebook(modelo, descricao, marca) " +
                                            "VALUES(?, ?, ?);";
    
    private static final String ADD_QUERY_LOJA_VENDE_NOTEBOOK =
                                            "INSERT INTO lojas_notebook.loja_vende_notebook(id_notebook, nome_loja, classificacao, preco, disponibilidade, url_produto, data_crawling) " +
                                            "VALUES(?, ?, ?, ?, ?, ?, ?);";
    
    private static final String QUERY_NOTEBOOK_DESCRICAO =
                                            "SELECT * " +
                                            "FROM lojas_notebook.notebook " +
                                            "WHERE descricao = ?;";
    
    private static final String QUERY_NOTEBOOK_MODELO =
                                            "SELECT * " +
                                            "FROM lojas_notebook.notebook " +
                                            "WHERE modelo = ?;";
    
    private static final String QUERY_NOTEBOOK_ID =
                                            "SELECT * " +
                                            "FROM lojas_notebook.notebook " +
                                            "WHERE descricao = ?;";

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
                 notebook.setMarca(result.getString("marca"));

                lista_notebooks.add(notebook);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgNotebookDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar notebooks.");
        }
        return lista_notebooks;
    }
    
    @Override
    public void update() throws SQLException, FileNotFoundException, IOException, ParseException {
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dataCrawling = formatter.format(new Date());
        
        java.util.Date d = new java.util.Date();
        java.sql.Date dt = new java.sql.Date (d.getTime()); 
        
        String[] nome_loja = {"Amazon", "Kabum", "MagazineLuiza"};
        String[] crawling_path = {"D:\\blend\\Documents\\comp\\gitbd\\Sistema-de-Integracao-de-Precos\\crawlings\\Amazon\\resultsAmazon.json","D:\\blend\\Documents\\comp\\gitbd\\Sistema-de-Integracao-de-Precos\\crawlings\\Kabum\\resultsKabum.json","D:\\blend\\Documents\\comp\\gitbd\\Sistema-de-Integracao-de-Precos\\crawlings\\MagazineLuiza\\resultsMagazineLuiza.json"};
        
        for(int i = 0; i < 3; i++){
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(crawling_path[i]));

            for (Object o : jsonArray) {
                JSONObject notebook = (JSONObject) o;
                String descricao = (String) notebook.get("descricao");
                String precoStr = (String) notebook.get("preco");
                String modelo = (String) notebook.get("modelo");
                String marca = (String) notebook.get("marca");
                String rating = (String) notebook.get("rating");
                String url = (String) notebook.get("url");
                
                double preco;               
                try {
                    String precoStraux = precoStr.replace(".","").replace(",",".");
                    preco = Double.parseDouble(precoStraux);
                } catch (NumberFormatException ex) {
                    preco = 0;
                }

                int idNotebook = 0;
                if (!(marca.equals("") && modelo.equals(""))){
                    String aux;
                    String atributo;
                    if(modelo.equals("")){
                            atributo = descricao;
                            aux = QUERY_NOTEBOOK_DESCRICAO;
                        }
                        else{
                            atributo = modelo;
                            aux = QUERY_NOTEBOOK_MODELO;
                        }
                    try (PreparedStatement statement = connection.prepareStatement(aux)) {
                        statement.setString(1, atributo);
                        
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next() == false) {
                                try (PreparedStatement statement2 = connection.prepareStatement(ADD_QUERY_NOTEBOOK)) {
                                    statement2.setString(1, modelo);
                                    statement2.setString(2, descricao);
                                    statement2.setString(3, marca);
                                    
                                    statement2.executeUpdate();
                                    
                                } catch (SQLException ex) {
                                    Logger.getLogger(PgNotebookDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                                }
                            } else {
                            }
                        }
                    }                   
                    try (PreparedStatement statement = connection.prepareStatement(QUERY_NOTEBOOK_ID)) {
                        statement.setString(1, descricao);
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next()) {
                                idNotebook = result.getInt("id_notebook");
                            }
                        }                          
                            
                        try (PreparedStatement statement3 = connection.prepareStatement(ADD_QUERY_LOJA_VENDE_NOTEBOOK)) {

                            statement3.setInt(1, idNotebook);                       
                            statement3.setString(2, nome_loja[i]);
                            statement3.setString(3, rating);
                            statement3.setDouble(4, preco);
                            if(preco == 0){
                                statement3.setBoolean(5, false);
                            }
                            else{
                                statement3.setBoolean(5, true);
                            }
                            statement3.setString(6, url);
                            statement3.setDate(7, dt);

                            statement3.executeUpdate();
                        }   catch (SQLException ex) {
                            Logger.getLogger(PgNotebookDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                        }
                    }
                }
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
