package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.*;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

@WebServlet("/new-prodotto")
@MultipartConfig
public class AddProdotto extends HttpServlet {

    private ProdottoObservable observable = new ProdottoObservable();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String address;
        observable.addObserver(new LoggerObserver());

        String nome = request.getParameter("nome");
        String desc = request.getParameter("descrizione");
        int quantita = Integer.parseInt(request.getParameter("quantita"));
        double prezzo = Double.parseDouble(request.getParameter("prezzo"));
        String azienda = request.getParameter("azienda");
        String tipoProdotto = request.getParameter("tipoProdotto");

        Prodotto prodotto = null;
        String errore = "";

        if(nome == null || nome.isEmpty() || nome.length() > 100) errore += "Nome del prodotto non valido. ";
        if(prezzo <= 0) errore += "Prezzo non valido. ";
        if(desc != null && desc.length() > 3000) errore += "Descrizione troppo lunga. ";
        if(tipoProdotto == null || tipoProdotto.isEmpty()) errore += "Tipo prodotto non definito. ";

        if(!errore.isEmpty()) {
            request.setAttribute("errore", errore);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Errorepage.jsp");
            dispatcher.forward(request, response);
            return;
        }

        switch (tipoProdotto.toLowerCase()) {
            case "console":
                String modello = request.getParameter("modello");
                String colore = request.getParameter("colore");
                String memoria = request.getParameter("memoria");
                prodotto = new Console(nome, desc, quantita, prezzo, azienda, modello, colore, memoria);
                break;

            case "videogioco":
                String piattaforma = request.getParameter("piattaforma");
                int pegi = Integer.parseInt(request.getParameter("pegi"));
                prodotto = new Videogioco(nome, desc, quantita, prezzo, azienda, piattaforma, pegi);
                break;

            case "gadget":
                String tipologia = request.getParameter("tipologia");
                prodotto = new Gadget(nome, desc, quantita, prezzo, azienda, tipologia);
                break;

            default:
                prodotto = new Prodotto(nome, desc, quantita, prezzo, azienda);
                break;
        }

        ProdottoDAO pdao = new ProdottoDAO();
        int idGenerato = pdao.doSave(prodotto);
        prodotto.setId(idGenerato);

        String path = "/image/PathOggetti/" + prodotto.getId();

        File folder = new File(getServletContext().getRealPath(path));
        folder.mkdir();

        Collection<Part> imageParts = request.getParts();
        for(Part part: imageParts){
            String fileName = extractFileNamePart(part);
            if(fileName != null && !fileName.isEmpty()) {
                String filePath = folder.getAbsolutePath() + File.separator + fileName;
                part.write(filePath);
            }
        }

        observable.notifyObservers(prodotto);

        address = "/WEB-INF/HomeAdmin.jsp";
        request.setAttribute("prodotto", prodotto);

        RequestDispatcher rd = request.getRequestDispatcher(address);
        rd.forward(request, response);
    }

    private String extractFileNamePart(Part part){
        String cd = part.getHeader("content-disposition");
        String [] items = cd.split(";");
        for(String s : items){
            if(s.trim().startsWith("filename")){
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return null;
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}