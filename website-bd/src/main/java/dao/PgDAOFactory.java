package dao;

import java.sql.Connection;

public class PgDAOFactory extends DAOFactory {

    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public NotebookDAO getNotebookDAO() {
        return new PgNotebookDAO(this.connection);
    }
    
    @Override
    public ProdutoDAO getProdutoDAO() {
        return new PgProdutoDAO(this.connection);
    }
}
