package control.filter;
// Un filtro è un componente che intercetta richieste e risposte prima che arrivino alla servlet o prima che tornino al client.

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(urlPatterns = {"/profilo", "/ordini", "/checkout"}) //dice a TomCat su quali filtri intervenire
public class AuthFilter implements Filter {

    @Override //doFilter metodo principale di ogni filtro
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) //chain è la catena dei filtri e della risorsa finale
            throws IOException, ServletException {

        // Cast da ServletRequest/ServletResponse ai tipi HTTP
        // perché abbiamo bisogno di metodi specifici disponibili SOLO nelle versioni HTTP come getSession() e sendRedirect()
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Recupera la sessione SOLO se esiste già
        // false = non crearne una nuova se l'utente non è autenticato
        HttpSession session = httpRequest.getSession(false);

        // Controlla se esiste una sessione valida
        // e se nella sessione è presente l'attributo "utente"
        boolean utenteLoggato = (session != null && session.getAttribute("utente") != null);

        if (utenteLoggato) {
            // Se l'utente è autenticato, la richiesta può continuare
            // verso la servlet o la JSP richiesta
            chain.doFilter(request, response);
        } else {
            // Se l'utente NON è autenticato, lo reindirizzo alla pagina di login
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
        }
    }
}