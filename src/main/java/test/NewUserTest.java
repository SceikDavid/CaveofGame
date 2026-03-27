package test;

import control.NewUser;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NewUserTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private NewUser newUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        Mockito.reset(request, response, session, requestDispatcher);
    }

    @Test
    void testDoGet() throws ServletException, IOException {
        when(request.getParameter("nome")).thenReturn("John");
        when(request.getParameter("cognome")).thenReturn("Doe");
        when(request.getParameter("email")).thenReturn("gio@gmail.com");
        when(request.getParameter("password")).thenReturn("Password00@");
        when(request.getParameter("username")).thenReturn("ciaopashc");
        when(request.getParameter("telefono")).thenReturn("1234567890");
        when(request.getParameter("indirizzo")).thenReturn("via casa");

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(Mockito.anyString())).thenReturn(requestDispatcher);

        newUser.doGet(request, response);

        verify(session, Mockito.times(1)).setAttribute(Mockito.eq("carrello"), Mockito.any());
        verify(requestDispatcher).forward(request, response);
    }
}