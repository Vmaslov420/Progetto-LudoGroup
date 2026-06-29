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

/*
 * Questa servlet gestisce l'eliminazione logica di un prodotto.
 *
 * Flusso:
 * - doGet()  → carica il prodotto e mostra la pagina di conferma
 * - doPost() → esegue l'eliminazione logica e reindirizza alla lista
 *
 * Perché separare i due metodi?
 * Per seguire il significato dei metodi HTTP:
 * - GET  = mostrare qualcosa, mai modificare dati
 * - POST = eseguire un'azione che modifica dati
 *
 * Questo evita anche che qualcuno possa eliminare un prodotto
 * semplicemente aprendo un URL nel browser (GET).
 */
@WebServlet("/admin/prodotti/elimina")
public class AdminEliminaProdottoServlet extends HttpServlet {

    /*
     * doGet() mostra la pagina di conferma prima di procedere.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * Leggo l'id del prodotto dalla query string.
         * Esempio URL: /admin/prodotti/elimina?id=5
         *
         * getParameter() restituisce sempre String,
         * quindi controlliamo che non sia null o vuoto.
         */
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            /*
             * Se manca l'id
             * Torniamo alla lista prodotti.
             */
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            ProdottoDAO prodottoDAO = new ProdottoDAO();

            /*
             * Usiamo getByIdAdmin() perché vogliamo recuperare il prodotto
             * anche se fosse già stato eliminato logicamente.
             */
            Prodotto prodotto = prodottoDAO.getByIdAdmin(id);

            if (prodotto == null) {
                /*
                 * Prodotto non trovato: torniamo alla lista senza errori visibili.
                 */
                response.sendRedirect(request.getContextPath() + "/admin/prodotti");
                return;
            }

            /*
             * Passiamo il prodotto alla JSP di conferma.
             * Servirà per mostrare nome e ID nella pagina.
             */
            request.setAttribute("prodotto", prodotto);

            request.getRequestDispatcher("/WEB-INF/views/admin/admin-prodotto-conferma-elimina.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            /*
             * L'id non era un numero valido.
             * Torniamo alla lista senza crash.
             */
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");

        } catch (SQLException e) {
            throw new ServletException("Errore durante il caricamento del prodotto da eliminare", e);
        }
    }

    /*
     * doPost() viene chiamato quando l'utente conferma l'eliminazione.
     *
     * Il form nella JSP di conferma invia un POST con l'id del prodotto.
     * Solo in questo momento eseguiamo davvero l'eliminazione logica.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr == null || idStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
            return;
        }

        try {
            int id = Integer.parseInt(idStr);

            ProdottoDAO prodottoDAO = new ProdottoDAO();

            /*
             * Chiamiamo elimina() che fa una cancellazione logica:
             * UPDATE prodotto SET eliminato = TRUE WHERE id_prodotto = ?
             *
             * Non eliminiamo la riga fisicamente dal database.
             * Questo protegge l'integrità dello storico ordini.
             */
            prodottoDAO.elimina(id);

            /*
             * Redirect alla lista prodotti dopo eliminazione.
             * Post/Redirect/Get: evita il reinvio del POST
             * se l'utente aggiorna la pagina dopo l'eliminazione.
             */
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");

        } catch (SQLException e) {
            throw new ServletException("Errore durante l'eliminazione del prodotto", e);
        }
    }
}