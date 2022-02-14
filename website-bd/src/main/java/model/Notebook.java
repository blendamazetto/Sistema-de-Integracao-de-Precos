package model;

public class Notebook {
    
    private Integer id_notebook;
    private String modelo;
    private String descricao;
    private String marca;

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Integer getId_notebook() {
        return id_notebook;
    }

    public void setId_notebook(Integer id_notebook) {
        this.id_notebook = id_notebook;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
  
}
