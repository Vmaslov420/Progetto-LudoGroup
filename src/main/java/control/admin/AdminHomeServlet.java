package control.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Utente;

import java.io.IOException;

@WebServlet("/admin") //risponde all'URL /admin e mostr la home dell'are admin
                      //la servlet gestisce solo la visualizazzione, l'accesso è gestito dal filtro
public class AdminHomeServlet extends HttpServlet {

    @Override //metodo GET poichè la pagina admin iniziale è una risorsa da visualizzare
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Recupero l'utente dalla sessione.
        // uso getSesssion() senza false perchè il controllo accessi lo fa già AdminFilter,
        // getSession() recupera solo il dato della sessione
        Utente utente = (Utente) request.getSession().getAttribute("utente");

        // Passo l'utente alla JSP tramite request attribute.
        // servlet = controller
        // request attribute = passaggio dati
        //JSP = view
        // La JSP userà questo oggetto per mostrare un messaggio di benvenuto.
        request.setAttribute("adminLoggato", utente);

        // Forward verso la JSP dentro WEB-INF.
        // L'utente non può aprire questa JSP direttamente da URL:
        // ci arriva solo passando dalla servlet.
        request.getRequestDispatcher("/WEB-INF/views/admin/admin.jsp")
                .forward(request, response);
    }
}