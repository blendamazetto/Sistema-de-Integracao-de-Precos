package dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import model.Notebook;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.sql.SQLException;
import java.util.ArrayList;
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
                                            "FROM lojas_notebook.notebook";
    
    private static final String DESC_FILTER =
                                            " WHERE descricao LIKE ?";
    
    private static final String MARCA_FILTER =
                                            " WHERE marca LIKE ?";
    
    private static final String MODELO_FILTER =
                                            " WHERE modelo LIKE ?";
    
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
    
    private static final String QUERY_PRODUTO =
                                            "SELECT * " +
                                            "FROM lojas_notebook.loja_vende_notebook " +
                                            "WHERE id_notebook = ?" +
                                            "AND nome_loja = ?" +
                                            "AND data_crawling = ?;";
    
    private String descricaoNotebook;
    private String marcaNotebook;
    private String modeloNotebook;

    @Override
    public void setArguments(String[] args){
        this.descricaoNotebook = args[0];
        this.marcaNotebook = args[1];
        this.modeloNotebook = args[2];
    }


    @Override
    public List<Notebook> all() throws SQLException {
        List<Notebook> lista_notebooks = new ArrayList<>();
        String aux = ALL_QUERY;
        if (descricaoNotebook != null && !descricaoNotebook.isEmpty()){
            aux = aux + DESC_FILTER;
        }
        if (marcaNotebook != null && !marcaNotebook.isEmpty()){
            aux = aux + MARCA_FILTER;
        }
        if (modeloNotebook != null && !modeloNotebook.isEmpty()){
            aux = aux + MODELO_FILTER;
        }
        
        aux = aux + ";";
        
        try (PreparedStatement statement = connection.prepareStatement(aux)){
            int i = 1;
            if (descricaoNotebook != null && !descricaoNotebook.isEmpty()){
                statement.setString(i, "%" + descricaoNotebook + "%");
                i++;
            }
            if (marcaNotebook != null && !marcaNotebook.isEmpty()){
                statement.setString(i, "%" + marcaNotebook + "%");
                i++;
                
            }
            if (modeloNotebook != null && !modeloNotebook.isEmpty()){
                statement.setString(i, "%" + modeloNotebook + "%");
            }
            try (ResultSet result = statement.executeQuery()) {
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
        }
        return lista_notebooks;
    }
    
    @Override
    public void update() throws SQLException, FileNotFoundException, IOException, ParseException {
        
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.sql.Date dt; 
        
        String[] nome_loja = {"Amazon", "Kabum", "MagazineLuiza"};
        String[] crawling_path = {"json/resultsAmazon.json","json/resultsKabum.json","json/resultsMagazineLuiza.json"};
        
        for(int i = 0; i < 3; i++){
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(this.getClass().getClassLoader().getResource(crawling_path[i]).getPath()));
            JSONObject auxData = (JSONObject) jsonArray.get(0);
            String dataSt = (String) auxData.get("data");
            try {
                dt = new java.sql.Date(formatter.parse(dataSt).getTime());
            } catch (java.text.ParseException ex) {
                Logger.getLogger(PgNotebookDAO.class.getName()).log(Level.SEVERE, null, ex);
                return;
            }
            for (int j = 1; j < jsonArray.size(); j++) {
                JSONObject notebook = (JSONObject) jsonArray.get(j);
                String descricao = (String) notebook.get("descricao");
                String precoStr = (String) notebook.get("preco");
                String modelo = (String) notebook.get("modelo");
                modelo = modelo.replaceAll("\\s","");
                String marca = (String) notebook.get("marca");
                marca = marca.replaceAll("\\s","");
                String rating = (String) notebook.get("rating");
                String url = (String) notebook.get("url");
                
                double preco;               
                try {
                    String precoStraux = precoStr.replace(".","").replace(",",".");
                    preco = Double.parseDouble(precoStraux);
                } catch (NumberFormatException ex) {
                    preco = 0;
                }

                if (!(marca.isEmpty() && modelo.isEmpty())){
                    String aux;
                    String atributo;
                    if(!modelo.isEmpty()){
                        atributo = modelo;
                        aux = QUERY_NOTEBOOK_MODELO;
                    }
                    else{
                        atributo = descricao;
                        aux = QUERY_NOTEBOOK_DESCRICAO;
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
                            }
                        }
                    }                   
                    try (PreparedStatement statement = connection.prepareStatement(QUERY_NOTEBOOK_DESCRICAO)) {
                        statement.setString(1, descricao);
                        try (ResultSet result = statement.executeQuery()) {
                            if (result.next()) {
                                int idNotebook = result.getInt("id_notebook");
                                try (PreparedStatement statement3 = connection.prepareStatement(QUERY_PRODUTO)) {
                                    statement3.setInt(1, idNotebook);
                                    statement3.setString(2, nome_loja[i]);
                                    statement3.setDate(3, dt);
                                    try (ResultSet result2 = statement3.executeQuery()) {
                                        if (result2.next() == false) {
                                            try (PreparedStatement statement4 = connection.prepareStatement(ADD_QUERY_LOJA_VENDE_NOTEBOOK)) {
                                    
                                                statement4.setInt(1, idNotebook);                       
                                                statement4.setString(2, nome_loja[i]);
                                             
                                                if(preco == 0){
                                                    statement4.setNull(4, Types.NUMERIC);
                                                    statement4.setBoolean(5, false);
                                                }
                                                else{
                                                    statement4.setBoolean(5, true);
                                                    statement4.setDouble(4, preco);
                                                }
                                                if(rating.equals("")){
                                                    statement4.setNull(3, Types.NUMERIC);
                                                }
                                                else{
                                                    Double auxRating = 0.0;
                                                    auxRating += rating.charAt(0) - 48;
                                                    if(rating.length() > 2){
                                                        auxRating += (rating.charAt(2) - 48)/10;
                                                    }
                                                    
                                                    statement4.setDouble(3, auxRating);
                                                }
                                                statement4.setString(6, url);
                                                statement4.setDate(7, dt);

                                                statement4.executeUpdate();
                                            }   catch (SQLException ex) {
                                                Logger.getLogger(PgNotebookDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                                            }
                                        }
                                    }
                                }
                            }
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
