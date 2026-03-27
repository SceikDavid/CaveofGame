package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/crea-ordine")
public class CreaOrdine extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = null;
        ProdottiCarrelloDAO pdao = new ProdottiCarrelloDAO();

        String via = request.getParameter("address");
        String city = request.getParameter("city");
        String cap = request.getParameter("cap");
        String card = request.getParameter("card-num");
        String cvc = request.getParameter("security");
        String scad = request.getParameter("scad");
        String button = request.getParameter("button");

        double totale = 0.0;
        if (request.getSession().getAttribute("totale") != null) {
            totale = (double) request.getSession().getAttribute("totale");
        }

        List<String> errori = new ArrayList<>();

        if ("back".equals(button)) {
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/carrello.jsp");
            rd.forward(request, response);
            return;
        }

        if (via == null || via.isEmpty() || !isValidAddress(via)) errori.add("Compila correttamente il campo indirizzo");
        if (city == null || city.isEmpty() || !isValidCity(city)) errori.add("Compila correttamente il campo città");
        if (cap == null || cap.isEmpty() || !isValidCap(cap)) errori.add("Compila correttamente il campo cap");
        if (card == null || card.isEmpty() || !isValidCard(card)) errori.add("Compila correttamente il campo numero carta");
        if (scad == null || scad.isEmpty() || !isValidScad(scad)) errori.add("Compila correttamente il campo scadenza");
        if (cvc == null || cvc.isEmpty() || !isValidCvc(cvc)) errori.add("Compila correttamente il campo cvc");

        if (!errori.isEmpty()) {
            request.setAttribute("errori", errori);
            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/Errorepage.jsp");
            rd.forward(request, response);
            return;
        }

        Ordine ordine = new Ordine();
        OrdineDAO ordineDAO = new OrdineDAO();
        Calendar calendar = Calendar.getInstance();
        Date data = new Date(calendar.getTime().getTime());
        User user = (User) request.getSession().getAttribute("user");
        Carrello carrello = (Carrello) request.getSession().getAttribute("carrello");

        ordine.setIdCliente(user.getUsername());
        ordine.setIdCarrello(carrello.getIdCarrello());
        ordine.setSpedizione(via + " " + city + " " + cap);
        ordine.setPagamento("Numero Carta: " + card + " Scad: " + scad + " CVC: " + cvc);
        ordine.setTotale(totale);
        ordine.setDataOrd(data);

        List<ProdottiCarrello> pc = pdao.doRetriveAllById(carrello.getIdCarrello());
        StringBuilder prodottiBuilder = new StringBuilder();
        for (ProdottiCarrello ps : pc) {
            prodottiBuilder.append("Id prodotto: ").append(ps.getIdProdotto())
                    .append(", quantita': ").append(ps.getQuantita())
                    .append(", taglia: ").append(ps.getTaglia() != null ? ps.getTaglia() : "N/A").append("\r\n");
        }

        ordine.setProdotti(prodottiBuilder.toString());
        ordineDAO.doSave(ordine);

        pdao.deleteProdottoCarrelloByCarrello(carrello.getIdCarrello());

        address = "/WEB-INF/index.jsp";
        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
    }

    public static boolean isValidAddress(String target) {
        return Pattern.compile("^[a-zA-Z0-9 ]{3,}$").matcher(target).matches();
    }
    public static boolean isValidCity(String target) {
        return Pattern.compile("^[a-zA-Z ]{3,}$").matcher(target).matches();
    }
    public static boolean isValidCap(String target) {
        return Pattern.compile("^[0-9]{5}$").matcher(target).matches();
    }
    public static boolean isValidCard(String target) {
        return Pattern.compile("^[0-9]{16}$").matcher(target).matches();
    }
    public static boolean isValidScad(String target) {
        return Pattern.compile("^([0-9]{2})+/+([0-9]{2})$").matcher(target).matches();
    }
    public static boolean isValidCvc(String target) {
        return Pattern.compile("^[0-9]{3}$").matcher(target).matches();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}