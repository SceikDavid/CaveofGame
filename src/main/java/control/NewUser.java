package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Carrello;
import model.CarrelloDAO;
import model.User;
import model.UserDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@WebServlet("/new-user")
public class NewUser extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String indirizzo = request.getParameter("indirizzo");
        String telefono = request.getParameter("telefono");

        List<String> errori = new ArrayList<>();

        if (username == null || username.isEmpty() || !isValidUsername(username)) errori.add("Inserisci un username valido.");
        if (email == null || email.isEmpty() || !isValidEmail(email)) errori.add("Inserisci un'email valida.");
        if (password == null || password.isEmpty() || !isValidPassword(password)) errori.add("Inserisci una password valida (min 8 caratteri, 1 lettera, 1 numero, 1 simbolo).");
        if (nome == null || nome.isEmpty() || !isValidName(nome)) errori.add("Inserisci un nome valido.");
        if (cognome == null || cognome.isEmpty() || !isValidSurname(cognome)) errori.add("Inserisci un cognome valido.");
        if (indirizzo == null || indirizzo.isEmpty() || !isValidAddress(indirizzo)) errori.add("Inserisci un indirizzo valido.");
        if (telefono == null || telefono.isEmpty() || !isValidPhoneNumber(telefono)) errori.add("Inserisci un numero di telefono valido.");

        if (!errori.isEmpty()) {
            request.getSession().setAttribute("errori", errori);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Errorepage.jsp");
            dispatcher.forward(request, response);
        } else {
            User user = new User();
            user.setNome(nome);
            user.setCognome(cognome);
            user.setEmail(email);
            user.setUsername(username);
            user.setPassword(password);
            user.setIndirizzo(indirizzo);
            user.setTelefono(telefono);

            UserDAO udao = new UserDAO();
            udao.doSave(user);

            CarrelloDAO cdao = new CarrelloDAO();
            Carrello carrello = new Carrello(username);
            cdao.doCreateCarrello(carrello);
            carrello = cdao.doRetriveByUsername(username);

            request.getSession().setAttribute("carrello", carrello);
            request.setAttribute("user", user);
            request.getSession().setAttribute("user", user);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/account.jsp");
            rd.forward(request, response);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    public static boolean isValidUsername(String username) {
        return Pattern.compile("^[a-zA-Z0-9._]+$").matcher(username).matches();
    }
    public static boolean isValidEmail(String email) {
        return Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$").matcher(email).matches();
    }
    public static boolean isValidPassword(String password) {
        return Pattern.compile("^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&*()_+]).{8,}$").matcher(password).matches();
    }
    public static boolean isValidName(String nome) {
        return Pattern.compile("^[a-zA-Z ]+$").matcher(nome).matches();
    }
    public static boolean isValidSurname(String cognome) {
        return Pattern.compile("^[a-zA-Z ]+$").matcher(cognome).matches();
    }
    public static boolean isValidAddress(String indirizzo) {
        return Pattern.compile("^[a-zA-Z0-9 ]+$").matcher(indirizzo).matches();
    }
    public static boolean isValidPhoneNumber(String numero_telefono) {
        return Pattern.compile("^(\\+?[0-9]{1,3})?[0-9]{10}$").matcher(numero_telefono).matches();
    }
}