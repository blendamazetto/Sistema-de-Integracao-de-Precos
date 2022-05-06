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
                                            "ON lojas_notebook.loja_vende_notebook.id_notebook = lojas_notebook.notebook.id_notebook;";   

    @Override
    public List<Produto> all() throws SQLException {
        List<Produto> lista_produtos = new ArrayList<>();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Produto produto = new Produto();
                produto.setDescricao(result.getString("descricao"));
                produto.setClassificacao(result.getDouble("classificacao"));
                produto.setNomeLoja(result.getString("nome_loja"));
                produto.setPreco(result.getDouble("preco"));
                produto.setDataCrawling(formatter.format(result.getDate("data_crawling")));
                
                lista_produtos.add(produto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgProdutoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar produtos.");
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
    
