package control;

import model.bean.Utente;
import model.dao.UtenteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/registrazione")
public class RegistrazioneServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/views/registrazione.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        String telefono = request.getParameter("telefono");

        if (nome != null) nome = nome.trim();
        if (cognome != null) cognome = cognome.trim();
        if (email != null) email = email.trim();
        if (password != null) password = password.trim();
        if (nickname != null) nickname = nickname.trim();
        if (telefono != null) telefono = telefono.trim();

        if (nome == null || nome.isEmpty() ||
                cognome == null || cognome.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty() ||
                nickname == null || nickname.isEmpty()) {

            request.setAttribute("errore", "Tutti i campi obbligatori devono essere compilati.");
            request.getRequestDispatcher("/WEB-INF/views/registrazione.jsp")
                    .forward(request, response);
            return;
        }

        try {
            Utente utente = new Utente();
            utente.setNome(nome);
            utente.setCognome(cognome);
            utente.setEmail(email);
            utente.setPasswordHash(password);
            utente.setNickname(nickname);
            utente.setTelefono(telefono);

            UtenteDAO dao = new UtenteDAO();
            boolean successo = dao.registra(utente);

            if (successo) {
                response.sendRedirect(request.getContextPath() + "/login?messaggio=registrazione_ok");
            } else {
                request.setAttribute("errore", "Email o nickname già presenti.");
                request.getRequestDispatcher("/WEB-INF/views/registrazione.jsp")
                        .forward(request, response);
            }

        } catch (SQLException e) {
            throw new ServletException("Errore durante la registrazione", e);
        }
    }
}