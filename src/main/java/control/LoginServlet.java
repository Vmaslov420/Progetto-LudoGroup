package control;

import model.bean.Utente;
import model.dao.UtenteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email != null) email = email.trim();
        if (password != null) password = password.trim();

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("errore", "Inserisci email e password.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                    .forward(request, response);
            return;
        }

        try {
            UtenteDAO dao = new UtenteDAO();
            Utente utente = dao.login(email, password);

            if (utente != null) {
                HttpSession session = request.getSession();
                session.setAttribute("utente", utente);
                session.setMaxInactiveInterval(30 * 60);

                response.sendRedirect(request.getContextPath() + "/");
            } else {
                request.setAttribute("errore", "Email o password non corrette.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp")
                        .forward(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Errore durante il login", e);
        }
    }
}