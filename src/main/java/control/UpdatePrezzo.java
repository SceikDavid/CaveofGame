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

@WebServlet("/update-prezzo")
public class UpdatePrezzo extends HttpServlet {

    private ProdottoDAO prodottoDAO;

    @Override
    public void init() throws ServletException {
        prodottoDAO = new ProdottoDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/HomeAdmin.jsp";
        int prodottoId = Integer.parseInt(request.getParameter("idProdotto"));
        String prezzoStr = request.getParameter("prezzo");

        if (prezzoStr != null && !prezzoStr.trim().isEmpty()) {
            double prezzoprodotto = Double.parseDouble(prezzoStr);
            try {
                Prodotto prodotto = prodottoDAO.doUpdatePrezzo(prezzoprodotto, prodottoId);
                if (prodotto != null) {
                    request.setAttribute("prodottoAggiornato", prodotto);
                } else {
                    request.setAttribute("errorUpdateID", "Prodotto non trovato, id non presente nel database");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } else {
            request.setAttribute("errorUpdateID", "Prezzo inserito non valido");
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(address);
        rd.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}