package test;

import control.SearchServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Prodotto;
import model.ProdottoDAO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class SearchServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private ProdottoDAO prodottoDAO;

    @InjectMocks
    private SearchServlet searchServlet;

    @Test
    void testDoGet() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(request.getParameter("query")).thenReturn("exampleQuery");

        List<Prodotto> mockSearchResults = createMockSearchResults();
        when(prodottoDAO.doRetriveAll()).thenReturn(mockSearchResults);

        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        searchServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
    }

    private List<Prodotto> createMockSearchResults() {
        List<Prodotto> mockSearchResults = new ArrayList<>();
        // Usa il nuovo costruttore: int id, String nome, double prezzo, String descrizione, int quantita, String azienda
        mockSearchResults.add(new Prodotto(1, "Prodotto1", 10.0, "Descrizione1", 10, "Azienda1"));
        mockSearchResults.add(new Prodotto(2, "Prodotto2", 20.0, "Descrizione2", 20, "Azienda2"));
        return mockSearchResults;
    }
}