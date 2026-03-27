package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.User;
import model.WishlistDAO;

import java.io.IOException;
import java.util.List;

@WebServlet("/view-wishlist")
public class ViewWishlistServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");

        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        WishlistDAO wishlistDAO = new WishlistDAO();
        List<Prodotto> prodottiWishlist = wishlistDAO.getWishlist(user.getUsername());

        request.setAttribute("wishlist", prodottiWishlist);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/wishlist.jsp");
        rd.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}