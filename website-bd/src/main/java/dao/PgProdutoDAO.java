package dao;
import java.io.FileNotFoundException;
import java.io.IOException;
import model.Produto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import java.text.SimpleDateFormat;

public class PgProdutoDAO implements ProdutoDAO {
    private final Connection connection;

    public PgProdutoDAO(Connection connection) { this.connection = connection; }

    private static final String ALL_QUERY =
                                            "SELECT * "+
                                            "FROM lojas_notebook.loja_vende_notebook "+
                                            "INNER JOIN lojas_notebook.notebook "+
                                            "ON lojas_notebook.loja_vende_notebook.id_notebook = lojas_notebook.notebook.id_notebook";  
    
    private static final String DESC_FILTER =
                                                " notebook.descricao LIKE ?";
    
    private static final String DATE_FILTER =
                                                " loja_vende_notebook.data_crawling >= ?";
    
    private static final String CLASSIFICACAO_FILTER =
                                                " loja_vende_notebook.classificacao >= ?";
    
    private static final String PRECO_MIN_FILTER =
                                                " loja_vende_notebook.preco >= ?";
    
    private static final String PRECO_MAX_FILTER =
                                                " loja_vende_notebook.preco <= ?";
    
    private static final String LOJA_FILTER =
                                                " loja_vende_notebook.nome_loja = ?";
    
    private static final String DISP_FILTER =
                                                " loja_vende_notebook.disponibilidade = ?";
    
    
    private String descricao;
    private java.sql.Date dt; 
    private double classificacao;
    private double precoMin;
    private double precoMax;
    private String loja;
    private int disponibilidade;
    private String sentido;
    private String ordenarPor;
    
    @Override
    public void setArguments(String[] args){
        if (args == null){
            this.disponibilidade = 2;
            return;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.descricao = args[0];
        if(!args[1].isEmpty()){
            try {
                this.dt = new java.sql.Date(formatter.parse(args[1]).getTime());
            } catch (java.text.ParseException ex) {
                Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
                this.dt = null;
            }
        }
        if(args[2].isEmpty()){
            this.classificacao = 0;
        }
        else{
            this.classificacao = Double.parseDouble(args[2]);
        }
        if(args[3].isEmpty()){
            this.precoMin = 0;
        }
        else{
            this.precoMin = Double.parseDouble(args[3]);
        }
        if(args[4].isEmpty()){
            this.precoMax = 0;
        }
        else{
            this.precoMax = Double.parseDouble(args[4]);
        }
        this.loja = args[5];
        this.disponibilidade = args[6].charAt(0) - 48;
        this.sentido = args[7];
        this.ordenarPor = args[8];
    }
    
    @Override
    public List<Produto> all() throws SQLException {
        List<Produto> lista_produtos = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String aux = ALL_QUERY;
        boolean flag = false;
        if (descricao != null && !descricao.isEmpty()){
            aux = aux + " WHERE" + DESC_FILTER;
            flag = true;
        }
        if (dt != null){
            if (flag){
                aux = aux + " AND";
            }
            else{
                flag = true;
                aux = aux + " WHERE";
            }
            aux = aux + DATE_FILTER;
        }
        if (classificacao != 0){
            if (flag){
                aux = aux + " AND";
            }
            else{
                flag = true;
                aux = aux + " WHERE";
            }
            aux = aux + CLASSIFICACAO_FILTER;
        }
        if (precoMin != 0){
            if (flag){
                aux = aux + " AND";
            }
            else{
                flag = true;
                aux = aux + " WHERE";
            }
            aux = aux + PRECO_MIN_FILTER;
        }
        if (precoMax != 0){
            if (flag){
                aux = aux + " AND";
            }
            else{
                flag = true;
                aux = aux + " WHERE";
            }
            aux = aux + PRECO_MAX_FILTER;
        }
        if (loja != null && !loja.isEmpty()){
            if (flag){
                aux = aux + " AND";
            }
            else{
                flag = true;
                aux = aux + " WHERE";
            }
            aux = aux + LOJA_FILTER;
        }
        if (disponibilidade != 2){
            if (flag){
                aux = aux + " AND";
            }
            else{
                flag = true;
                aux = aux + " WHERE";
            }
            aux = aux + DISP_FILTER;
        }
        if (sentido != null && ordenarPor != null){
            aux = aux + " ORDER BY " + ordenarPor + " " + sentido;
        }
        aux = aux + ";";
        System.out.println(aux);
        try (PreparedStatement statement = connection.prepareStatement(aux)){
            int i = 1;
            if (descricao!= null && !descricao.isEmpty()){
                statement.setString(i, "%" + descricao + "%");
                i++;
            }
            if (dt != null){
                statement.setDate(i, dt);
                i++;
            }
            if (classificacao != 0){
                statement.setDouble(i, classificacao);
                i++;
            }
            if (precoMin != 0){
                statement.setDouble(i, precoMin);
                i++;
            }
            if (precoMax != 0){
                statement.setDouble(i, precoMax);
                i++;
            }
            if (loja != null && !loja.isEmpty()){
                statement.setString(i, loja);
                i++;
            }
            if (disponibilidade != 2){
                statement.setBoolean(i, disponibilidade == 1);
                i++;
            }
            
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Produto produto = new Produto();
                    produto.setDescricao(result.getString("descricao"));
                    produto.setClassificacao(result.getDouble("classificacao"));
                    produto.setNomeLoja(result.getString("nome_loja"));
                    produto.setPreco(result.getDouble("preco"));
                    produto.setDataCrawling(formatter.format(result.getDate("data_crawling")));
                    produto.setUrlProduto(result.getString("url_produto"));
                    lista_produtos.add(produto);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

                throw new SQLException("Erro ao listar produtos.");
            }
        }

        return lista_produtos;
    }
    
    @Override
    public void update() throws SQLException, FileNotFoundException, IOException, ParseException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    @Override
    public void create(Produto t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Produto read(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Integer id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
    
