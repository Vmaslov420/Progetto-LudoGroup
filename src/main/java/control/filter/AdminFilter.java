package control.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.bean.Utente; //nel filtro dobbiamo recuperare il ruolo dell' Utente

import java.io.IOException;

@WebFilter(urlPatterns = {"/admin", "/admin/*"}) //admin protegge l'endpoint di base, mentre /admin/* protegge tutto ciò che sta sotto
public class AdminFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Cast ai tipi HTTP, perché ServletRequest e ServletResponse
        // sono tipi generici e non espongono metodi come getSession() o sendRedirect()
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Recupera la sessione solo se esiste già quindi (false), e non ()
        // Non vogliamo crearne una nuova per utenti non autenticati
        HttpSession session = httpRequest.getSession(false);

        // Variabile che conterrà l'utente loggato, se presente
        Utente utente = null;

        // Se la sessione esiste, provo a recuperare l'attributo "utente"
        if (session != null) {
            utente = (Utente) session.getAttribute("utente");
        }

        // Controllo 1: utente non autenticato
        // Se non c'è sessione o non c'è utente in sessione,
        // l'accesso all'area admin non è consentito e verrà rimandato al login con sendRedirect
        if (utente == null) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
            return; //fermiamo subito il metodo dopo l'errore o redirect
        }

        // Controllo 2: utente autenticato ma NON amministratore
        // Verifico che il ruolo sia "admin"
        if (utente.getRuolo() == null || !utente.getRuolo().equalsIgnoreCase("admin")) { //equalsIgnoreCase elimina il case sensitive, ADMIN Admin admin sono identici
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN); //server ha capito la richiesta ma l'utente non ha i permessi giusti
            return; //fermiamo subito il metodo dopo l'errore o redirect
        }

        // Se arrivo qui, significa che:
        // - l'utente è autenticato
        // - il ruolo è admin
        // quindi la richiesta può proseguire verso la servlet richiesta
        chain.doFilter(request, response);
    }
}