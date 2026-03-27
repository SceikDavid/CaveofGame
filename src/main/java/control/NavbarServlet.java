package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.ProdottiCarrello;
import model.ProdottiCarrelloDAO;
import model.User;

import java.io.IOException;
import java.util.List;

@WebServlet(name="navbar", value="/nav")
public class NavbarServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/nav.jsp";
        HttpSession session = request.getSession();

        // Calcoliamo il numero totale di articoli nel carrello per il "badge" dell'interfaccia
        int totaleArticoli = 0;
        User user = (User) session.getAttribute("user");
        Carrello carrello = (Carrello) session.getAttribute("carrello");

        if (user != null && carrello != null) {
            ProdottiCarrelloDAO pcdao = new ProdottiCarrelloDAO();
            List<ProdottiCarrello> prodotti = pcdao.doRetrieveByCarrello(carrello.getIdCarrello());

            for (ProdottiCarrello pc : prodotti) {
                totaleArticoli += pc.getQuantita();
            }
        }

        // Passiamo questo numero alla JSP. Nella tua nav.jsp potrai stamparlo con ${totaleArticoliCarrello}
        request.setAttribute("totaleArticoliCarrello", totaleArticoli);

        RequestDispatcher dispatcher = request.getRequestDispatcher(address);
        dispatcher.forward(request, response);
    }
}