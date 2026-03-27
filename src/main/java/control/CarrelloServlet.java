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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/carrello-servlet")
public class CarrelloServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "WEB-INF/carrello.jsp";
        double totale = 0.0;

        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");
        User user = (User) request.getSession().getAttribute("user");

        List<Prodotto> prodottiStampa = new ArrayList<>();
        List<ProdottiCarrello> prodottiCarrello = new ArrayList<>();

        if(user != null && carrello != null) {
            int idCarr = carrello.getIdCarrello();
            ProdottiCarrelloDAO pcdao = new ProdottiCarrelloDAO();
            ProdottoDAO pdao = new ProdottoDAO();

            try {
                prodottiCarrello = pcdao.doRetrieveByCarrello(idCarr);

                for(ProdottiCarrello p : prodottiCarrello) {
                    Prodotto prodotto = pdao.doRetrieveById(p.getIdProdotto());
                    totale += (prodotto.getPrezzo() * p.getQuantita());
                    prodottiStampa.add(prodotto);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        request.getSession().setAttribute("pc", prodottiCarrello);
        request.getSession().setAttribute("totale", totale);
        request.getSession().setAttribute("prodottiDaStampare", prodottiStampa);

        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}