package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/cancella-ordine")
public class DeleteOrdineServlet extends HttpServlet {
    private OrdineDAO ordineDAO;

    public void init() {
        this.ordineDAO = new OrdineDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idOrdine = Integer.parseInt(request.getParameter("idOrdine"));
        String idClient = request.getParameter("idCliente");
        request.setAttribute("idCliente", idClient);
        String address = "/visualizza-ordini";

        Ordine ordine = ordineDAO.doRetrieveById(idOrdine);

        if (ordine != null) {
            int idCarrello = ordine.getIdCarrello();
            ProdottiCarrelloDAO pdDao = new ProdottiCarrelloDAO();
            List<ProdottiCarrello> carrelloList = pdDao.doRetriveAllById(idCarrello);
            ProdottoDAO prodottoDAO = new ProdottoDAO();

            for (ProdottiCarrello proCarrDB : carrelloList) {
                try {
                    Prodotto prodDB = prodottoDAO.doRetrieveById(proCarrDB.getIdProdotto());
                    if (prodDB != null) {
                        prodDB.setQuantita(prodDB.getQuantita() + proCarrDB.getQuantita());
                        prodottoDAO.doUpdateQuantita(prodDB);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            ordineDAO.deleteOrdine(idOrdine);
        }

        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}