package control;

import model.dao.UtenteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/verifica-email")
public class VerificaEmailServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");

        response.setContentType("application/json;charset=UTF-8");

        if (email == null || email.trim().isEmpty()) {
            response.getWriter().write("{\"esiste\": false}");
            return;
        }

        try {
            UtenteDAO dao = new UtenteDAO();
            boolean esiste = dao.emailEsiste(email.trim());
            response.getWriter().write("{\"esiste\": " + esiste + "}");
        } catch (SQLException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"esiste\": false}");
        }
    }
}