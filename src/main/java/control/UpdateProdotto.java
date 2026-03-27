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

@WebServlet("/modifica-prodotto")
public class UpdateProdotto extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/HomeAdmin.jsp";

        try {
            int prodottoId = Integer.parseInt(request.getParameter("idp"));
            String nomeProdotto = request.getParameter("nome");
            double prezzo = Double.parseDouble(request.getParameter("prezzo"));
            String descrizione = request.getParameter("descrizione");
            String azienda = request.getParameter("azienda");
            int quantita = Integer.parseInt(request.getParameter("quantita"));

            Prodotto p = new Prodotto();
            p.setId(prodottoId);
            p.setNome(nomeProdotto);
            p.setPrezzo(prezzo);
            p.setDescrizione(descrizione);
            p.setAzienda(azienda);
            p.setQuantita(quantita);

            ProdottoDAO pdao = new ProdottoDAO();
            p = pdao.doUpdateProdotto(p);

            if (p != null) {
                request.setAttribute("prodottoModificato", p);
            } else {
                address = "/WEB-INF/Errorepage.jsp";
                request.setAttribute("errore", "Errore durante l'aggiornamento del prodotto nel database.");
            }
        } catch (Exception e) {
            address = "/WEB-INF/Errorepage.jsp";
            request.setAttribute("errore", "Parametri di modifica non validi.");
        }

        RequestDispatcher rd = getServletContext().getRequestDispatcher(address);
        rd.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}