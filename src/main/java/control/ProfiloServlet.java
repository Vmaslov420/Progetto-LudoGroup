package control;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.bean.Utente;

import java.io.IOException;

@WebServlet("/profilo")
public class ProfiloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false); //getSession(false) restituisce la sessione esistente ma NON crea una nuova a differenza di getSession()

        if (session == null) { //non c'è una sessione attiva, reindirizza al login e return per fermare il metodo
            response.sendRedirect(request.getContextPath() + "/login"); //fa una nuova richiesta all'URL specificato
            return;
        }

        //forward avviene dentro il server, il browser non sa del passaggio interno e l'URL non cambia (si usa per mostrare una view interna)
        //SendRedirect fa rispondere il server con una nuova richiesta HTTP e cambia l'URL (per spostare il client su un altra risorsa)


        Utente utente = (Utente) session.getAttribute("utente"); //recupero la sessione dell'utente autenticato

        request.setAttribute("utenteProfilo", utente);
        request.getRequestDispatcher("/WEB-INF/views/profilo.jsp") //jsp dentro WEB-INF cosi non puoi aprire direttametnte l'URL /profilo ma devi passare per la servlet che controlla il login
                .forward(request, response);
    }
}