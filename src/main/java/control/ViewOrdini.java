package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Ordine;
import model.OrdineDAO;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/visualizza-ordini")
public class ViewOrdini extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OrdineDAO ordineDAO = new OrdineDAO();
        List<Ordine> ordini = ordineDAO.doRetriveAll();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append("<table class='table'>");
        tableHTML.append("<thead>");
        tableHTML.append("<tr>");
        tableHTML.append("<th>ID Ordine</th>");
        tableHTML.append("<th>Totale Ordine</th>");
        tableHTML.append("<th>Data Ordine</th>");
        tableHTML.append("<th>Pagamento</th>");
        tableHTML.append("<th>Spedizione</th>");
        tableHTML.append("<th>ID Carrello</th>");
        tableHTML.append("<th>Username Cliente</th>");
        tableHTML.append("<th>Prodotti</th>");
        tableHTML.append("</tr>");
        tableHTML.append("</thead>");
        tableHTML.append("<tbody>");

        for (Ordine ordine : ordini) {
            tableHTML.append("<tr>");
            tableHTML.append("<td>").append(ordine.getId()).append("</td>");
            tableHTML.append("<td>").append(ordine.getTotale()).append("</td>");
            tableHTML.append("<td>").append(ordine.getDataOrd()).append("</td>");
            tableHTML.append("<td>").append(ordine.getPagamento()).append("</td>");
            tableHTML.append("<td>").append(ordine.getSpedizione()).append("</td>");
            tableHTML.append("<td>").append(ordine.getIdCarrello()).append("</td>");
            tableHTML.append("<td>").append(ordine.getIdCliente()).append("</td>");
            tableHTML.append("<td>").append(ordine.getProdotti()).append("</td>");
            tableHTML.append("</tr>");
        }

        tableHTML.append("</tbody>");
        tableHTML.append("</table>");

        out.println(tableHTML.toString());
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}