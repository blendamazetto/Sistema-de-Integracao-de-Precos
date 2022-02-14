package model;

public class Produto {
    
    private Double preco;
    
    private String classificacao;
    
    private String nomeLoja;
    
    private String dataCrawling;
    
    private String descricao;
    
    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public String getNomeLoja() {
        return nomeLoja;
    }

    public String getDataCrawling() {
        return dataCrawling;
    }

    public void setClassificacao(String classificação) {
        this.classificacao = classificação;
    }

    public void setNomeLoja(String nomeLoja) {
        this.nomeLoja = nomeLoja;
    }

    public void setDataCrawling(String dataCrawling) {
        this.dataCrawling = dataCrawling;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
}
