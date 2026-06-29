package control.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Prodotto;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/*
 * Questa servlet gestisce la visualizzazione della lista prodotti lato admin.
 *
 * Nel pattern MVC:
 * - la Servlet fa da Controller;
 * - il DAO recupera i dati dal database;
 * - la JSP mostra i dati all'utente.
 *
 * Quindi questa classe NON genera HTML direttamente:
 * prepara i dati e li passa alla JSP.
 */
@WebServlet("/admin/prodotti") //servlet risponde all'url /admin/prodotti
public class AdminProdottiServlet extends HttpServlet {

    /*
     * doGet() perché questa servlet serve a visualizzare una pagina, non a salvare dati.
     * Quando l'admin apre /admin/prodotti, il container chiama questo metodo.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * Creo un'istanza del DAO.
         *
         * Il DAO contiene tutta la logica di accesso al database.
         * In questo modo la servlet resta pulita e focalizzata
         * solo sul controllo del flusso dell'applicazione.
         */
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        try {
            /*
             * Recupero la lista completa dei prodotti lato admin.
             *
             * Perché uso getTuttiAdmin() e non getTutti()?
             * Perché nell'area amministratore voglio poter vedere
             * anche eventuali prodotti eliminati logicamente.
             */
            List<Prodotto> listaProdotti = prodottoDAO.getTuttiAdmin();

            /*
             * Salvo la lista nella request. Perché questi dati servono solo per questa singola risposta.
             * La request è la scelta corretta per dati temporanei
             * passati dalla servlet alla JSP tramite forward.
             */
            request.setAttribute("listaProdotti", listaProdotti);

            /*
             * Forward verso la JSP.
             *
             * Usiamo forward() e non sendRedirect() perché:
             * - vogliamo passare i dati della request alla JSP;
             * - non serve una nuova richiesta del browser;
             * - la JSP è interna e sta in WEB-INF.
             */
            request.getRequestDispatcher("/WEB-INF/views/admin/admin-prodotti.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            /*
             * Se il DAO solleva un errore SQL, lo trasformiamo in ServletException.
             * Perché la servlet non sa "gestire" il database nel dettaglio,
             * ma deve segnalare al container che c'è stato un errore lato server.
             */
            throw new ServletException("Errore durante il recupero dei prodotti per l'area admin", e);
        }
    }
}