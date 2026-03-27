package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "TabellaProdotti", urlPatterns = {"/TabellaProdotti"})
public class TabellaProdotti extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProdottoDAO pDAO = new ProdottoDAO();
        List<Prodotto> prodotti = pDAO.doRetriveAll();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        StringBuilder tableHTML = new StringBuilder();
        tableHTML.append("<table class='table'>");
        tableHTML.append("<thead>");
        tableHTML.append("<tr>");
        tableHTML.append("<th>ID</th>");
        tableHTML.append("<th>Nome</th>");
        tableHTML.append("<th>Descrizione</th>");
        tableHTML.append("<th>Prezzo</th>");
        tableHTML.append("<th>Quantita</th>");
        tableHTML.append("<th>Azienda</th>");
        tableHTML.append("<th>Tipo</th>");
        tableHTML.append("</tr>");
        tableHTML.append("</thead>");
        tableHTML.append("<tbody>");

        for (Prodotto p : prodotti) {
            String tipo = "Generico";
            if (p instanceof Console) tipo = "Console";
            else if (p instanceof Videogioco) tipo = "Videogioco";
            else if (p instanceof Gadget) tipo = "Gadget";

            tableHTML.append("<tr>");
            tableHTML.append("<td>").append(p.getId()).append("</td>");
            tableHTML.append("<td>").append(p.getNome()).append("</td>");
            tableHTML.append("<td>").append(p.getDescrizione()).append("</td>");
            tableHTML.append("<td>").append(p.getPrezzo()).append("</td>");
            tableHTML.append("<td>").append(p.getQuantita()).append("</td>");
            tableHTML.append("<td>").append(p.getAzienda() != null ? p.getAzienda() : "N/A").append("</td>");
            tableHTML.append("<td>").append(tipo).append("</td>");
            tableHTML.append("</tr>");
        }

        tableHTML.append("</tbody>");
        tableHTML.append("</table>");

        out.println(tableHTML.toString());
    }
}