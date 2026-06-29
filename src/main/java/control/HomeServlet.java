package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/*
 * Servlet della home page.
 *
 * In questo caso non dobbiamo leggere dati dal database.
 * La servlet ha solo il compito di inoltrare la richiesta
 * alla JSP della home.
 *
 * Questo rispetta comunque il pattern MVC:
 * - Control: HomeServlet
 * - View: home.jsp
 */
@WebServlet("")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * forward() passa il controllo alla JSP sul server,
         * senza cambiare URL nel browser.
         *
         * Usiamo /WEB-INF così la JSP non può essere aperta direttamente
         * digitando l'URL: deve sempre passare dalla servlet.
         */
        request.getRequestDispatcher("/WEB-INF/views/home.jsp")
                .forward(request, response);
    }
}