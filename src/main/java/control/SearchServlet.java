package control;

import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/searchServlet")
public class SearchServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String searchQuery = request.getParameter("query");

        List<Prodotto> searchResults = performSearch(searchQuery);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(searchResults));
    }

    private List<Prodotto> performSearch(String query) {
        ProdottoDAO pdao = new ProdottoDAO();
        List<Prodotto> allProducts = pdao.doRetriveAll();
        List<Prodotto> searchResults = new ArrayList<>();

        if (query == null || query.trim().isEmpty()) {
            return searchResults;
        }

        String q = query.toLowerCase();

        for (Prodotto p : allProducts) {
            // Cerca in Nome, Descrizione o Azienda
            boolean matchNome = p.getNome() != null && p.getNome().toLowerCase().contains(q);
            boolean matchDesc = p.getDescrizione() != null && p.getDescrizione().toLowerCase().contains(q);
            boolean matchAzienda = p.getAzienda() != null && p.getAzienda().toLowerCase().contains(q);

            if (matchNome || matchDesc || matchAzienda) {
                searchResults.add(p);
            }
        }
        return searchResults;
    }
}