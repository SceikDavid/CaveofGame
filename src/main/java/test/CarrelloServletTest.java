package test;

import control.CarrelloServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Carrello;
import model.Prodotto;
import model.ProdottiCarrello;
import model.ProdottiCarrelloDAO;
import model.ProdottoDAO;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CarrelloServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private Carrello carrello;

    @Mock
    private User user;

    @Mock
    private ProdottiCarrelloDAO prodottiCarrelloDAO;

    @Mock
    private ProdottoDAO prodottoDAO;

    @InjectMocks
    private CarrelloServlet carrelloServlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("carrello")).thenReturn(carrello);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void testDoGetValidUser() throws Exception {
        List<ProdottiCarrello> prodottiCarrelloList = new ArrayList<>();
        ProdottiCarrello prodottoCarrello = new ProdottiCarrello();

        // Modificato da String a int
        prodottoCarrello.setIdProdotto(1);
        prodottoCarrello.setQuantita(1);
        prodottiCarrelloList.add(prodottoCarrello);

        Prodotto prodotto = new Prodotto();

        // Modificato da String a int
        prodotto.setId(1);
        prodotto.setPrezzo(59.99);

        // Modificato anyString() in anyInt() per allinearsi al nuovo ProdottoDAO
        when(prodottiCarrelloDAO.doRetrieveByCarrello(anyInt())).thenReturn(prodottiCarrelloList);
        when(prodottoDAO.doRetrieveById(anyInt())).thenReturn(prodotto);

        carrelloServlet.doGet(request, response);

        verify(request, times(1)).getRequestDispatcher("WEB-INF/carrello.jsp");
        verify(dispatcher).forward(request, response);
        verify(request.getSession(), times(1)).setAttribute(eq("pc"), anyList());
        verify(request.getSession(), times(1)).setAttribute(eq("totale"), anyDouble());
        verify(request.getSession(), times(1)).setAttribute(eq("prodottiDaStampare"), anyList());
    }
}