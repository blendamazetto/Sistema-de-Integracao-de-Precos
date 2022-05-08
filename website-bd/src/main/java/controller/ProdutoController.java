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

@WebServlet(name = "ProdutoController", urlPatterns = {"", "/home", "/graficos"})
public class ProdutoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {

        DAO<Produto> dao_produto;
        RequestDispatcher dispatcher;

        switch (request.getServletPath()) { 
            case "": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_produto = daoFactory.getProdutoDAO();

                    List<Produto> lista_produtos = dao_produto.all();
                    request.setAttribute("lista_produtos", lista_produtos);
                } catch(ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/page/index.jsp");
                dispatcher.forward(request, response);
                break;
            }
            
             case "/home": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_produto = daoFactory.getProdutoDAO();

                    List<Produto> lista_produtos = dao_produto.all();
                    request.setAttribute("lista_produtos", lista_produtos);
                } catch(ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/page/index.jsp");
                dispatcher.forward(request, response);
                break;
            }                         
        }
    }  

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        RequestDispatcher dispatcher;

        switch (request.getServletPath()){
            case "/graficos":

                String classificacao = request.getParameter("classificacao");
                String descricao = request.getParameter("descricao");
                String valor = request.getParameter("valor");
                String loja = request.getParameter("loja");
                String data = request.getParameter("data");

                System.out.println("Valores Lidos:" + classificacao + descricao + valor + loja + data);

                session.setAttribute("classificacao", classificacao);
                session.setAttribute("descricao", descricao);
                session.setAttribute("valor", valor);
                session.setAttribute("loja", loja);
                session.setAttribute("data", data);
                
                dispatcher = request.getRequestDispatcher("/view/page/graficos.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }
}
