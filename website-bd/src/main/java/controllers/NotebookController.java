/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import model.Notebook;
import dao.NotebookDAO;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author blend
 */

@Named
@ViewScoped
public class NotebookController implements Serializable
{
	private Notebook notebook;
	
	private List<Notebook> listaNotebooks;

	public List<Notebook> getlistaNotebooks() 
	{
		return this.listaNotebooks;
	}

	public void setListaProdutos(List<Notebook> listaNotebooks) 
	{
		this.listaNotebooks = listaNotebooks;
	}

	public Notebook getProduto() 
	{
		return notebook;
	}

	public void setProduto(Notebook notebook) 
	{
		this.notebook = notebook;
	}
	
	public void buscarPorNome() 
	{
            try {
                NotebookDAO dao = new NotebookDAO();
                
                this.listaNotebooks = dao.buscar(this.notebook.getModelo());
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                Logger.getLogger(NotebookController.class.getName()).log(Level.SEVERE, null, ex);
            }
	}		
	
	public List<Notebook> listarProdutos() 
	{
            try {
                NotebookDAO dao = new NotebookDAO();
                return dao.buscar("");
            } catch (IOException | SQLException | ClassNotFoundException ex) {
                Logger.getLogger(NotebookController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
	}
	
	@PostConstruct
	public void init() {
		notebook = new Notebook();
	}
}
