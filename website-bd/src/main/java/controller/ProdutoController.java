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

@WebServlet(name = "ProdutoController", urlPatterns = {"", "/home", "/graficos", "/PesquisaProduto", "/graficosAux"})
public class ProdutoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {

        DAO<Produto> dao_produto;
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();

        switch (request.getServletPath()) { 
            case "": {
                dispatcher = request.getRequestDispatcher(request.getContextPath() + "/home");
                dispatcher.forward(request, response);
                break;
            }
            
            case "/home": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_produto = daoFactory.getProdutoDAO();
                    
                    dao_produto.setArguments((String[]) session.getAttribute("listaArgumentos"));
                    List<Produto> lista_produtos = dao_produto.all();
                    request.setAttribute("lista_produtos", lista_produtos);
                } catch(ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/page/index.jsp");
                dispatcher.forward(request, response);
                break;
            }
                
            case "/graficos": {               
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_produto = daoFactory.getProdutoDAO();
                    String[] aux = new String[9];
                    aux[0] = ((String) session.getAttribute("descricao"));
                    aux[1] = "";
                    aux[2] = "";
                    aux[3] = "";
                    aux[4] = "";
                    aux[5] = ((String) session.getAttribute("loja"));
                    aux[6] = "1";
                    aux[7] = "ASC";
                    aux[8] = "loja_vende_notebook.data_crawling";
                    dao_produto.setArguments(aux);
                    
                    List<Produto> lista_produtos = dao_produto.all();
                    request.setAttribute("listaGrafico", lista_produtos);
                    
                    request.setAttribute("classificacao", session.getAttribute("classificacao"));
                    request.setAttribute("descricao", session.getAttribute("descricao"));
                    request.setAttribute("valor", session.getAttribute("valor"));
                    request.setAttribute("loja", session.getAttribute("loja"));
                    request.setAttribute("data", session.getAttribute("data"));
                    request.setAttribute("url", session.getAttribute("url"));
                    
                } catch(ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }
                
                dispatcher = request.getRequestDispatcher("/view/page/graficos.jsp");
                dispatcher.forward(request, response);
                
             }                        
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

                session.setAttribute("classificacao", classificacao);
                session.setAttribute("descricao", descricao);
                session.setAttribute("valor", valor);
                session.setAttribute("loja", loja);
                session.setAttribute("data", data);
                session.setAttribute("url", url);
                
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
