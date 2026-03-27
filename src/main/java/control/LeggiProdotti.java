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
import java.util.List;

@WebServlet("/leggi-prodotto")
public class LeggiProdotti extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoria = request.getParameter("btn");
        request.getSession().setAttribute("categoria", categoria);

        String address = "WEB-INF/index.jsp";
        ProdottoDAO pdao = new ProdottoDAO();
        List<Prodotto> prodotti = pdao.doRetriveAll();

        if (prodotti != null) {
            request.getSession().setAttribute("prodotti", prodotti);
            address = "WEB-INF/catalog.jsp";
        }

        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}