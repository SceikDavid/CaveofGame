package test;

import control.DirectServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DirectServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @InjectMocks
    private DirectServlet directServlet;

    @Test
    void testDoGetAdmin() throws ServletException, IOException {
        User adminUser = new User();
        adminUser.setAdmin(true);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(adminUser);
        when(request.getRequestDispatcher("WEB-INF/HomeAdmin.jsp")).thenReturn(requestDispatcher);

        directServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetNonAdmin() throws ServletException, IOException {
        User nonAdminUser = new User();
        nonAdminUser.setAdmin(false);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(nonAdminUser);
        when(request.getRequestDispatcher("WEB-INF/account.jsp")).thenReturn(requestDispatcher);

        directServlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }
}