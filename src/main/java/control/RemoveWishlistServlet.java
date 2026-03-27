package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import model.WishlistDAO;

import java.io.IOException;

@WebServlet("/remove-wishlist")
public class RemoveWishlistServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            int idProdotto = Integer.parseInt(request.getParameter("id"));
            WishlistDAO wishlistDAO = new WishlistDAO();
            wishlistDAO.removeProdotto(user.getUsername(), idProdotto);
        }

        response.sendRedirect("view-wishlist");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}