package test;

import control.LogIn;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.UserDAO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class LogInTest {

    @InjectMocks
    private LogIn servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    private UserDAO userDAO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void testDoGetValidLogin() throws Exception {
        when(request.getParameter("logusername")).thenReturn("mattia00");
        when(request.getParameter("logpassword")).thenReturn("Password00@");

        servlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    public void testDoGetInvalidLogin() throws Exception {
        when(request.getParameter("logusername")).thenReturn("nonExistingUser");
        when(request.getParameter("logpassword")).thenReturn("wrongpassword");

        servlet.doGet(request, response);

        verify(request).setAttribute("errorMessageLogin", "Credenziali errate o Account Inesistente. Riprova o registrati");
        verify(dispatcher).forward(request, response);
    }
}