package control.carrello;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.bean.Prodotto;
import model.carrello.Carrello;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.sql.SQLException;

/*
 * Questa servlet gestisce l'aggiunta di un prodotto al carrello.
 *
 * Flusso:
 * 1. Legge l'id del prodotto dalla request
 * 2. Recupera il prodotto dal database
 * 3. Recupera il carrello dalla sessione (o lo crea se non esiste)
 * 4. Aggiunge il prodotto al carrello
 * 5. Salva il carrello in sessione
 * 6. Reindirizza a una pagina successiva
 *
 * Il carrello NON viene salvato nel database in questa fase:
 * viene mantenuto nella sessione HTTP, come richiesto dalla checklist.
 */
@WebServlet("/carrello/aggiungi")
public class AggiungiAlCarrelloServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * Recuperiamo l'id del prodotto dal form.
         * Il valore arriva come String, quindi dovremo convertirlo in int.
         */
        String idParam = request.getParameter("id");

        /*
         * Validazione minima:
         * se l'id manca o è vuoto, non possiamo sapere quale prodotto aggiungere.
         */
        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID prodotto mancante");
            return;
        }

        try {
            /*
             * Convertiamo l'id da String a int.
             * Se il parametro non è numerico, scatterà NumberFormatException.
             */
            int idProdotto = Integer.parseInt(idParam);

            ProdottoDAO prodottoDAO = new ProdottoDAO();

            /*
             * Recuperiamo il prodotto dal database. perchè il browser può essere manipolato
             * Usiamo getById() lato utente, così otteniamo solo prodotti validi/visibili.
             */
            Prodotto prodotto = prodottoDAO.getById(idProdotto);

            /*
             * Se il prodotto non esiste, restituiamo 404.
             */
            if (prodotto == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Prodotto non trovato");
                return;
            }

            /*
             * Recuperiamo la sessione.
             * getSession() crea una nuova sessione se non esiste già.
             */
            HttpSession session = request.getSession();

            /*
             * Proviamo a recuperare il carrello già salvato in sessione.
             */
            Carrello carrello = (Carrello) session.getAttribute("carrello");

            /*
             * Se il carrello non esiste ancora, lo creiamo.
             * Questo succede tipicamente la prima volta che l'utente
             * aggiunge un prodotto.
             */
            if (carrello == null) {
                carrello = new Carrello();
            }

            /*
             * Aggiungiamo il prodotto al carrello.
             * La logica interna del carrello farà:
             * - nuova riga se il prodotto non c'è
             * - aumento quantità se il prodotto è già presente
             */
            carrello.aggiungiProdotto(prodotto);

            /*
             * Salviamo di nuovo il carrello nella sessione.
             * Anche se era già presente, è corretto rimetterlo
             * per mantenere esplicito l'aggiornamento.
             */
            session.setAttribute("carrello", carrello);

            /*
             * Messaggio di conferma semplice.
             * Lo salviamo in sessione perché stiamo facendo redirect.
             * Dopo il redirect, gli attributi request andrebbero persi.
             */
            session.setAttribute("messaggioConferma", "Prodotto aggiunto al carrello con successo.");

            /*
             * Usiamo Post/Redirect/Get:
             * dopo una POST che modifica lo stato (aggiunta al carrello),
             * reindirizziamo l'utente a una pagina GET.
             *
             * Qui lo riportiamo al dettaglio del prodotto.
             * In alternativa potresti reindirizzare direttamente al carrello.
             */
            response.sendRedirect(request.getContextPath() + "/dettaglio-prodotto?id=" + idProdotto);

        } catch (NumberFormatException e) {
            /*
             * L'id non era un numero valido.
             */
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID prodotto non valido");

        } catch (SQLException e) {
            throw new ServletException("Errore durante l'aggiunta al carrello", e);
        }
    }
}