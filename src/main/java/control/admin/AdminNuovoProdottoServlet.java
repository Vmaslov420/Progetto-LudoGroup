package control.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Prodotto;
import model.bean.Categoria;
import model.dao.CategoriaDAO;
import model.dao.ProdottoDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/*
 * Questa servlet gestisce l'inserimento di un nuovo prodotto.
 *
 * Struttura classica:
 * - doGet()  -> mostra il form
 * - doPost() -> riceve i dati del form e salva nel database
 */
@WebServlet("/admin/prodotti/nuovo")
public class AdminNuovoProdottoServlet extends HttpServlet {

    /*
     * Mostra il form di inserimento prodotto.
     *
     * Usiamo GET perché qui non stiamo modificando dati:
     * stiamo solo mostrando una pagina.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            /*
             * Carico la lista categorie prima di mostrare il form.
             * Serve per popolare il menu a tendina nella JSP.
             */
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> listaCategorie = categoriaDAO.getTutte();

            request.setAttribute("listaCategorie", listaCategorie);
            request.setAttribute("modalita", "nuovo");

            request.getRequestDispatcher("/WEB-INF/views/admin/admin-prodotto-form.jsp")
                    .forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Errore durante il caricamento delle categorie", e);
        }
    }

    /*
     * Gestisce l'invio del form.
     *
     * Usiamo POST perché qui stiamo creando un nuovo record nel database.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * request.getParameter(...) legge i valori inviati dal form HTML.
         * I nomi devono coincidere con l'attributo name="" degli input.
         */
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String prezzoStr = request.getParameter("prezzo");
        String ivaStr = request.getParameter("iva");
        String stockStr = request.getParameter("stock");
        String immagine = request.getParameter("immagine");
        String categoriaIdStr = request.getParameter("categoriaId");

        /*
         * Validazione minima server-side.
         *
         * Anche se poi farai controlli JS lato client,
         * il server deve sempre validare i dati ricevuti.
         */
        if (nome == null || nome.trim().isEmpty()
                || prezzoStr == null || prezzoStr.trim().isEmpty()
                || ivaStr == null || ivaStr.trim().isEmpty()
                || stockStr == null || stockStr.trim().isEmpty()) {

            Prodotto prodotto = new Prodotto();
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setImmagine(immagine);

            mostraFormConErrore(request, response,
                        "compila tutti i campi obbligatori.", prodotto);
            return;
        }

        try {
            /*
             * Conversione dei dati da String ai tipi corretti.
             * perchè i valori arrivano dal form sempre come String,
             * quindi dobbiamo convertirli manualmente.
             */
            BigDecimal prezzo = new BigDecimal(prezzoStr);
            BigDecimal iva = new BigDecimal(ivaStr);
            int stock = Integer.parseInt(stockStr);

            /*
             * categoriaId è opzionale.
             * Se il campo è vuoto, salviamo null.
             */
            Integer categoriaId = null;
            if (categoriaIdStr != null && !categoriaIdStr.trim().isEmpty()) {
                categoriaId = Integer.parseInt(categoriaIdStr);
            }

            /*
             * Creo e valorizzo il bean Prodotto.
             * Il bean è il contenitore dei dati che passeremo al DAO.
             */
            Prodotto prodotto = new Prodotto();
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setIva(iva);
            prodotto.setStock(stock);
            prodotto.setImmagine(immagine);
            prodotto.setCategoriaId(categoriaId);

            /*
             * Salvataggio nel database tramite DAO.
             */
            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.inserisci(prodotto);

            /*
             * Redirect dopo successo.
             *
             * Perché redirect e non forward?
             * Per evitare che aggiornando la pagina il browser
             * reinvii il form e inserisca due volte lo stesso prodotto.
             *
             * Questo è il pattern Post/Redirect/Get.
             */
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");

        } catch (NumberFormatException e) {
            /*
             * Errore di conversione numerica:
             * ad esempio se prezzo, iva o stock non sono nel formato corretto.
             */
            Prodotto prodotto = new Prodotto ();
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setImmagine(immagine);

            mostraFormConErrore(request, response,
                    "Prezzo, IVA e stock devono avere un formato valido.", prodotto);

        } catch (SQLException e) {
            /*
             * Errore lato database.
             */
            throw new ServletException("Errore durante l'inserimento del prodotto", e);
        }
    }

    /*
    Metodi di supporto per non duplicare codice nei forward di errore
    Quando si torna al form dopo un errore
    bisogna ricaricare la lista categoria
    rimettere l'errore e prodotto nella request
    inoltrare di nuovo alla jsp
     */

    private void mostraFormConErrore(HttpServletRequest request, HttpServletResponse response, String errore, Prodotto prodotto) throws ServletException, IOException {

        try {
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> listaCategorie = categoriaDAO.getTutte();
            request.setAttribute("listaCategorie", listaCategorie);
        } catch (SQLException e) {
            request.setAttribute("listaCategorie", new ArrayList<Categoria>());
        }

        request.setAttribute("errore", errore);
        request.setAttribute("prodotto", prodotto);
        request.setAttribute("modalita", "nuovo");

        request.getRequestDispatcher("/WEB-INF/views/admin/admin-prodotto-form.jsp")
                .forward(request, response);
    }
}



