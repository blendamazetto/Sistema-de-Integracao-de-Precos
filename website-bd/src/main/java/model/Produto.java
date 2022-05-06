package model;

public class Produto {
    
    private Double preco;
    
    private Double classificacao;
    
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

    public Double getClassificacao() {
        return classificacao;
    }
    
    public String getNomeLoja() {
        return nomeLoja;
    }

    public String getDataCrawling() {
        return dataCrawling;
    }

    public void setClassificacao(Double classificação) {
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
    
    public String getPrecoAsString(){
        if(preco == 0.0){
            return "";
        }
        return String.format("R$%.2f", preco);
    }
    
    public String getClassificacaoAsString(){
        if(classificacao == 0.0){
            return "";
        }
        return String.format("%.1f de 5 estrelas", classificacao);
    }
    
}
