/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;
import dao.DAO;
import dao.DAOFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Notebook;

/**
 *
 * @author blend
 */

@WebServlet(
        name = "UserController",
        urlPatterns = {
            "/produtos",
            "/produtos/read"
        }
)
public class ProdutoController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        DAO<Notebook> dao;
        Notebook notebook;
        RequestDispatcher dispatcher;

        switch (request.getServletPath()) {
            case "/produtos": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getNotebookDAO();
                    
                    List<Notebook> notebookList = dao.all();
                    request.setAttribute("notebookList", notebookList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/user/index.jsp");
                dispatcher.forward(request, response);
                break;
            }     
        }       
    }
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
