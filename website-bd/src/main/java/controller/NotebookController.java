package controller;
import dao.DAO;
import dao.DAOFactory;
import java.io.FileNotFoundException;
import model.Notebook;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

@WebServlet(name = "NotebookController", urlPatterns = {"/notebooks", "/update"})
public class NotebookController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, FileNotFoundException {
        DAO<Notebook> dao_notebook;
        RequestDispatcher dispatcher;
        HttpSession session = request.getSession();

        switch (request.getServletPath()) {
            
            case "/notebooks": 
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_notebook = daoFactory.getNotebookDAO();
                    String[] args = {(String) session.getAttribute("descricao"), (String) session.getAttribute("marca"), (String) session.getAttribute("modelo")};
                    dao_notebook.setArguments(args);
                    List<Notebook> lista_notebook = dao_notebook.all();
                    request.setAttribute("lista_notebook", lista_notebook);
                } catch(ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/page/notebooks.jsp");
                dispatcher.forward(request, response);
                break;
            
            case "/update":                          
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao_notebook = daoFactory.getNotebookDAO();
                    dao_notebook.update();
                    List<Notebook> lista_notebook = dao_notebook.all();
                    request.setAttribute("lista_notebook", lista_notebook);
                    
                } catch(ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                } catch (ParseException ex) {
                Logger.getLogger(NotebookController.class.getName()).log(Level.SEVERE, null, ex);
                }
               dispatcher = request.getRequestDispatcher("/view/page/notebooks.jsp");
               dispatcher.forward(request, response);
                
               break;
            
        }
    }    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        switch (request.getServletPath()){
            case "/RequisicaoPesquisa":

                String descricao = request.getParameter("descricao");
                String marca = request.getParameter("marca");
                String modelo = request.getParameter("modelo");

                System.out.println("Valores Lidos:" + descricao + marca + modelo);

                session.setAttribute("descricao", descricao);
                session.setAttribute("marca", marca);
                session.setAttribute("modelo", modelo);
                response.sendRedirect(request.getContextPath() + "/ResultadoPesquisa");
                break;
        }
    }
}
