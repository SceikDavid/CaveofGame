package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/elenco-servlet")
public class ElencoServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("buttonName");

        if (check != null) {
            String address = "./WEB-INF/index.jsp";

            switch (check.toLowerCase()) {
                case "console":
                    address = "./WEB-INF/WorkInProgress.jsp";
                    break;
                case "videogioco":
                    address = "./WEB-INF/WorkInProgress.jsp";
                    break;
                case "gadget":
                    address = "./WEB-INF/WorkInProgress.jsp";
                    break;
                case "ordina":
                    address = "./WEB-INF/ordine.jsp";
                    break;
            }

            RequestDispatcher rd = request.getRequestDispatcher(address);
            rd.forward(request, response);
        } else {
            response.getWriter().write("Parametro 'ButtonName' mancante o nullo.");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}