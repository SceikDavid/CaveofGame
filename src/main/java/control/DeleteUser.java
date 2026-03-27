package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import model.UserDAO;

import java.io.IOException;

@WebServlet("/delete-user")
public class DeleteUser extends HttpServlet {
    private UserDAO userDAO;

    public void init() throws ServletException{
        this.userDAO = new UserDAO();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address = "/WEB-INF/index.jsp";
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user != null) {
            this.userDAO.deleteUser(user.getUsername());
            session.invalidate();
        }

        RequestDispatcher r = request.getRequestDispatcher(address);
        r.forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}