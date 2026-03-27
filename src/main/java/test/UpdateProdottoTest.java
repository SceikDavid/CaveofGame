package test;

import control.UpdateProdotto;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.IOException;
import java.lang.reflect.Field;

import static org.mockito.Mockito.*;

class UpdateProdottoTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ProdottoDAO prodottoDAO;

    @InjectMocks
    private UpdateProdotto updateProdotto;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);
        prodottoDAO = mock(ProdottoDAO.class);
        updateProdotto = new UpdateProdotto();

        try {
            Field field = UpdateProdotto.class.getDeclaredField("prodottoDAO");
            field.setAccessible(true);
            field.set(updateProdotto, prodottoDAO);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testDoGet_SuccessfulUpdate() throws ServletException, IOException {
        when(request.getParameter("idp")).thenReturn("1");
        when(request.getParameter("nome")).thenReturn("NuovoNome");
        when(request.getParameter("prezzo")).thenReturn("10");
        when(request.getParameter("descrizione")).thenReturn("NuovaDescrizione");
        when(request.getParameter("azienda")).thenReturn("NuovaAzienda");
        when(request.getParameter("quantita")).thenReturn("5");

        Prodotto updatedProdotto = new Prodotto();
        updatedProdotto.setId(1);
        updatedProdotto.setNome("NuovoNome");
        updatedProdotto.setPrezzo(10.0);
        updatedProdotto.setDescrizione("NuovaDescrizione");
        updatedProdotto.setAzienda("NuovaAzienda");
        updatedProdotto.setQuantita(5);

        when(prodottoDAO.doUpdateProdotto(any(Prodotto.class))).thenReturn(updatedProdotto);

        updateProdotto.doGet(request, response);

        verify(prodottoDAO, times(1)).doUpdateProdotto(any(Prodotto.class));
    }

    @Test
    void testDoGet_FailedUpdate() throws ServletException, IOException {
        when(request.getParameter("idp")).thenReturn("1");
        when(request.getParameter("nome")).thenReturn("NuovoNome");
        when(request.getParameter("prezzo")).thenReturn("10");
        when(request.getParameter("descrizione")).thenReturn("NuovaDescrizione");
        when(request.getParameter("azienda")).thenReturn("NuovaAzienda");
        when(request.getParameter("quantita")).thenReturn("5");

        when(prodottoDAO.doUpdateProdotto(any(Prodotto.class))).thenReturn(null);

        updateProdotto.doGet(request, response);

        verify(prodottoDAO, times(1)).doUpdateProdotto(any(Prodotto.class));
    }
}