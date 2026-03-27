package test;

import control.DeleteProdotto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DeleteProdottoTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private ProdottoDAO prodottoDAO;

    @InjectMocks
    private DeleteProdotto servlet;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException, SQLException {
        int prodottoId = 1;
        Prodotto prodotto = new Prodotto();
        prodotto.setId(prodottoId);

        when(request.getParameter("id")).thenReturn(String.valueOf(prodottoId));
        when(prodottoDAO.deleteProdotto(prodottoId)).thenReturn(prodotto);

        servlet.doGet(request, response);

        verify(request).setAttribute("prodottoEliminato", prodotto);
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGetProdottoNonTrovato() throws ServletException, IOException, SQLException {
        int prodottoId = 1;

        when(request.getParameter("id")).thenReturn(String.valueOf(prodottoId));
        when(prodottoDAO.deleteProdotto(prodottoId)).thenReturn(null);

        servlet.doGet(request, response);

        verify(request).setAttribute("errorDeleteID", "Prodotto non trovato, id non presente nel database");
        verify(dispatcher).forward(request, response);
    }
}