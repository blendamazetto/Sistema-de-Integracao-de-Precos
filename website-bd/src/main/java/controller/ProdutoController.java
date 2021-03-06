package controller;
import dao.DAO;
import dao.DAOFactory;
import java.io.FileNotFoundException;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import model.Produto;

@WebServlet(name = "ProdutoController", urlPatterns = {"", "/home", "/graficos", "/PesquisaProduto", "/graficosAux", "/graficosNotebook"})
public class ProdutoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {

        DAO<Produto> dao_produto;
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();

        switch (request.getServletPath()) { 
            case "": 
                dispatcher = request.getRequestDispatcher(request.getContextPath() + "/home");
                dispatcher.forward(request, response);
                break;
            
            case "/home": 
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_produto = daoFactory.getProdutoDAO();
                    
                    dao_produto.setArguments((String[]) session.getAttribute("listaArgumentos"));
                    List<Produto> lista_produtos = dao_produto.all();
                    request.setAttribute("lista_produtos", lista_produtos);
                } catch(ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }
                
                session.setAttribute("listaArgumentos", null);
                dispatcher = request.getRequestDispatcher("/view/page/index.jsp");
                dispatcher.forward(request, response);
                break;
            
                
            case "/graficos":          
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_produto = daoFactory.getProdutoDAO();
                    String[] aux = new String[9];
                    aux[0] = ((String) session.getAttribute("descricaoG"));
                    aux[1] = "";
                    aux[2] = "";
                    aux[3] = "";
                    aux[4] = "";
                    aux[5] = ((String) session.getAttribute("lojaG"));
                    aux[6] = "1";
                    aux[7] = "ASC";
                    aux[8] = "loja_vende_notebook.data_crawling";
                    dao_produto.setArguments(aux);
                    
                    List<Produto> lista_produtos = dao_produto.all();
                    request.setAttribute("listaGrafico", lista_produtos);
                    
                    request.setAttribute("classificacao", session.getAttribute("classificacaoG"));
                    request.setAttribute("descricao", session.getAttribute("descricaoG"));
                    request.setAttribute("valor", session.getAttribute("valorG"));
                    request.setAttribute("loja", session.getAttribute("lojaG"));
                    request.setAttribute("data", session.getAttribute("dataG"));
                    request.setAttribute("url", session.getAttribute("urlG"));
                    
                    
                } catch(ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }
                
                dispatcher = request.getRequestDispatcher("/view/page/graficos.jsp");
                dispatcher.forward(request, response);
  
            
            case "/graficosNotebook":                
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_produto = daoFactory.getProdutoDAO();
                    String[] auxAmazon = new String[9];
                    auxAmazon[0] = ((String) session.getAttribute("descricaoG"));
                    auxAmazon[1] = "";
                    auxAmazon[2] = "";
                    auxAmazon[3] = "";
                    auxAmazon[4] = "";
                    auxAmazon[5] = "Amazon";
                    auxAmazon[6] = "1";
                    auxAmazon[7] = "ASC";
                    auxAmazon[8] = "loja_vende_notebook.data_crawling";
                    dao_produto.setArguments(auxAmazon);
                    
                    List<Produto> lista_amazon = dao_produto.all();
                    request.setAttribute("listaAmazon", lista_amazon);
                    
                    
                    
                    String[] auxKabum = new String[9];
                    auxKabum[0] = ((String) session.getAttribute("descricaoG"));
                    auxKabum[1] = "";
                    auxKabum[2] = "";
                    auxKabum[3] = "";
                    auxKabum[4] = "";
                    auxKabum[5] = "Kabum";
                    auxKabum[6] = "1";
                    auxKabum[7] = "ASC";
                    auxKabum[8] = "loja_vende_notebook.data_crawling";
                    dao_produto.setArguments(auxKabum);
                    
                    List<Produto> lista_kabum = dao_produto.all();
                    request.setAttribute("listaKabum", lista_kabum);
                    
                    
                    String[] auxML = new String[9];
                    auxML[0] = ((String) session.getAttribute("descricaoG"));
                    auxML[1] = "";
                    auxML[2] = "";
                    auxML[3] = "";
                    auxML[4] = "";
                    auxML[5] = "MagazineLuiza";
                    auxML[6] = "1";
                    auxML[7] = "ASC";
                    auxML[8] = "loja_vende_notebook.data_crawling";
                    dao_produto.setArguments(auxML);
                    
                    List<Produto> lista_ml = dao_produto.all();
                    request.setAttribute("listaMagazineLuiza", lista_ml);
                    
                    request.setAttribute("id_notebook", session.getAttribute("id_notebookG"));
                    request.setAttribute("descricao", session.getAttribute("descricaoG"));
                    request.setAttribute("marca", session.getAttribute("marcaG"));
                    request.setAttribute("modelo", session.getAttribute("modeloG"));
                   
                    
                } catch(ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }
                
                dispatcher = request.getRequestDispatcher("/view/page/graficosNotebooks.jsp");
                dispatcher.forward(request, response);
        }
    }  

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        
        switch (request.getServletPath()){
            case "/graficosAux":

                String classificacao = request.getParameter("classificacao");
                String descricao = request.getParameter("descricao");
                String valor = request.getParameter("valor");
                String loja = request.getParameter("loja");
                String data = request.getParameter("data");
                String url = request.getParameter("url");

                System.out.println("Valores Lidos:" + classificacao + descricao + valor + loja + data);

                session.setAttribute("classificacaoG", classificacao);
                session.setAttribute("descricaoG", descricao);
                session.setAttribute("valorG", valor);
                session.setAttribute("lojaG", loja);
                session.setAttribute("dataG", data);
                session.setAttribute("urlG", url);
                
                response.sendRedirect(request.getContextPath() + "/graficos");               
                break;
                
            case "/PesquisaProduto":
                String[] aux = new String[9];
                aux[0] = request.getParameter("descricao");
                aux[1] = request.getParameter("data");
                aux[2] = request.getParameter("classificacao");
                aux[3] = request.getParameter("precoMinimo");
                aux[4] = request.getParameter("precoMaximo");
                aux[5] = request.getParameter("loja");
                aux[6] = request.getParameter("disponibilidade");
                aux[7] = request.getParameter("sentido");
                aux[8] = request.getParameter("ordenarPor");

                session.setAttribute("listaArgumentos", aux);
                response.sendRedirect(request.getContextPath() + "/home");
                break;
        }
    }
}
