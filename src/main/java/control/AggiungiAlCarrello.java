package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.*;

import java.io.IOException;

@WebServlet("/aggiungi-al-carrello")
public class AggiungiAlCarrello extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
        String address = "WEB-INF/index.jsp";

        int quantita = Integer.parseInt(request.getParameter("quantita"));
        String taglia = request.getParameter("taglia");

        Prodotto prod = (Prodotto) request.getSession().getAttribute("prodAgg");
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");

        ProdottiCarrello prodottiCarrello = new ProdottiCarrello();
        prodottiCarrello.setIdCarrello(carrello.getIdCarrello());
        prodottiCarrello.setIdProdotto(prod.getId());
        prodottiCarrello.setTaglia(taglia);
        prodottiCarrello.setQuantita(quantita);

        ProdottiCarrelloDAO prodcardao = new ProdottiCarrelloDAO();

        try {
            ProdottiCarrello verifica = prodcardao.doRetrieveByCarrelloAndProdotto(carrello.getIdCarrello(), prod.getId());

            if(verifica != null) {
                prodottiCarrello.setQuantita(prodottiCarrello.getQuantita() + verifica.getQuantita());
                prodcardao.doUpdateProdottiQuantita(prodottiCarrello);
            } else {
                prodcardao.doSaveProdottoCarrello(prodottiCarrello);
                carrello.addProdotto(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}