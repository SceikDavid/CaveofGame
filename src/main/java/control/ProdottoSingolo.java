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
import java.sql.SQLException;

@WebServlet("/prodotto-singolo")
public class ProdottoSingolo extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "WEB-INF/index.jsp";
        int idProdotto = Integer.parseInt(request.getParameter("idProdotto"));

        ProdottoDAO pdao = new ProdottoDAO();
        try {
            Prodotto prodotto = pdao.doRetrieveById(idProdotto);
            if(prodotto != null) {
                request.getSession().setAttribute("prodotto", prodotto);
                address = "WEB-INF/prodottoSingolo.jsp";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}