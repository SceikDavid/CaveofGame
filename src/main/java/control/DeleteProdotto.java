package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/delete-prodotto")
public class DeleteProdotto extends HttpServlet {
    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException {
        prodottoDAO = new ProdottoDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/HomeAdmin.jsp";
        int prodottoId = Integer.parseInt(request.getParameter("id"));

        try {
            Prodotto prodotto = prodottoDAO.deleteProdotto(prodottoId);
            if(prodotto != null){
                request.setAttribute("prodottoEliminato", prodotto);
            } else {
                request.setAttribute("errorDeleteID", "Prodotto non trovato nel database.");
            }
        } catch (SQLException e) {
            request.setAttribute("errorDeleteID", "Errore durante l'eliminazione.");
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}