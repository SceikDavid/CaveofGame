package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.CarrelloDAO;
import model.UserDAO;
import model.User;

import java.io.IOException;

@WebServlet("/log-in")
public class LogIn extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/index.jsp";
        String username = request.getParameter("logusername");
        String password = request.getParameter("logpassword");

        User user = UserDAO.doRetrieveByUsername(username);

        if (user == null || !password.equals(user.getPassword())) {
            String error = "Credenziali errate o Account Inesistente. Riprova o registrati";
            request.setAttribute("errorMessageLogin", error);
        } else {
            request.getSession().setAttribute("user", user);

            if (user.isAdmin()) {
                address = "/WEB-INF/HomeAdmin.jsp";
            } else {
                CarrelloDAO cdao = new CarrelloDAO();
                Carrello carrello = cdao.doRetriveByUsername(user.getUsername());

                if (carrello == null) {
                    carrello = new Carrello(user.getUsername());
                    cdao.doCreateCarrello(carrello);
                }

                request.getSession().setAttribute("carrello", carrello);
                address = "/WEB-INF/account.jsp";
            }
        }

        RequestDispatcher r = request.getRequestDispatcher(address);
        r.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}