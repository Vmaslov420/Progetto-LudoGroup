package control.admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.bean.Prodotto;
import model.bean.Categoria;
import model.dao.ProdottoDAO;
import model.dao.CategoriaDAO;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Questa servlet gestisce la modifica di un prodotto esistente.
 *
 * Flusso:
 * - doGet()  -> carica il prodotto esistente e mostra il form precompilato
 * - doPost() -> legge i nuovi valori e aggiorna il prodotto nel database
 */
@WebServlet("/admin/prodotti/modifica")
public class AdminModificaProdottoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * Leggiamo l'id dalla query string:
         * /admin/prodotti/modifica?id=5
         */
        String idParam = request.getParameter("id");

        if (idParam == null || idParam.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
            return;
        }

        try {
            int id = Integer.parseInt(idParam);

            ProdottoDAO prodottoDAO = new ProdottoDAO();


            /*
             * Usiamo getByIdAdmin() perché lato admin vogliamo poter recuperare
             * anche prodotti eventualmente eliminati logicamente.
             */
            Prodotto prodotto = prodottoDAO.getByIdAdmin(id);

            if (prodotto == null) {
                response.sendRedirect(request.getContextPath() + "/admin/prodotti");
                return;
            }

            /*
             * Carichiamo anche le categorie per popolare il select.
             */
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> listaCategorie = categoriaDAO.getTutte();


            /*
             * Passiamo il prodotto alla JSP.
             * La JSP userà questo bean per precompilare i campi del form.
             */
            request.setAttribute("listaCategorie", listaCategorie);
            request.setAttribute("prodotto", prodotto);
            request.setAttribute("modalità", "modifica"); /* Attributo utile per capire in JSP se siamo in modalità modifica. */


            request.getRequestDispatcher("/WEB-INF/views/admin/admin-prodotto-form.jsp")
                    .forward(request, response);

        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");
        } catch (SQLException e) {
            throw new ServletException("Errore durante il caricamento del prodotto da modificare", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");
        String nome = request.getParameter("nome");
        String descrizione = request.getParameter("descrizione");
        String prezzoStr = request.getParameter("prezzo");
        String ivaStr = request.getParameter("iva");
        String stockStr = request.getParameter("stock");
        String immagine = request.getParameter("immagine");
        String categoriaIdStr = request.getParameter("categoriaId");

        /*
         * Validazione minima lato server.
         */
        if (idStr == null || idStr.trim().isEmpty()
                || nome == null || nome.trim().isEmpty()
                || prezzoStr == null || prezzoStr.trim().isEmpty()
                || ivaStr == null || ivaStr.trim().isEmpty()
                || stockStr == null || stockStr.trim().isEmpty()) {

           Prodotto prodotto = new Prodotto(); /* Ricostruiamo un prodotto provvisorio per non perdere i dati inseriti*/

           try {
               prodotto.setId(Integer.parseInt(idStr));
           } catch (Exception ignored) {
           }


            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setImmagine(immagine);

            mostraFormConErrore(request, response, "compila tutti i campi obbligatori.", prodotto);

            return;
        }

        try {
            int id = Integer.parseInt(idStr);
            BigDecimal prezzo = new BigDecimal(prezzoStr);
            BigDecimal iva = new BigDecimal(ivaStr);
            int stock = Integer.parseInt(stockStr);

            Integer categoriaId = null;
            if (categoriaIdStr != null && !categoriaIdStr.trim().isEmpty()) {
                categoriaId = Integer.parseInt(categoriaIdStr);
            }

            /*
             * Costruiamo il bean con i nuovi dati da salvare.
             */
            Prodotto prodotto = new Prodotto();
            prodotto.setId(id);
            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setPrezzo(prezzo);
            prodotto.setIva(iva);
            prodotto.setStock(stock);
            prodotto.setImmagine(immagine);
            prodotto.setCategoriaId(categoriaId);

            ProdottoDAO prodottoDAO = new ProdottoDAO();
            prodottoDAO.modifica(prodotto);

            /*
             * Redirect finale alla lista prodotti.
             * Anche qui usiamo Post/Redirect/Get.
             */
            response.sendRedirect(request.getContextPath() + "/admin/prodotti");

        } catch (NumberFormatException e) {

            Prodotto prodotto = new Prodotto();

            try {
                prodotto.setId(Integer.parseInt(idStr));
            } catch (Exception ignored) {
            }


            prodotto.setNome(nome);
            prodotto.setDescrizione(descrizione);
            prodotto.setImmagine(immagine);

            request.setAttribute("prodotto", prodotto);
            request.setAttribute("modalita", "modifica");

            mostraFormConErrore(request, response, "ID, prezzo, IVA e stock devono avere un formato valido.", prodotto);


        } catch (SQLException e) {
            throw new ServletException("Errore durante la modifica del prodotto", e);
        }
    }

    private void mostraFormConErrore(HttpServletRequest request,
                                     HttpServletResponse response,
                                     String errore,
                                     Prodotto prodotto)
            throws ServletException, IOException {

        try {
            CategoriaDAO categoriaDAO = new CategoriaDAO();
            List<Categoria> listaCategorie = categoriaDAO.getTutte();
            request.setAttribute("listaCategorie", listaCategorie);
        } catch (SQLException e) {
            request.setAttribute("listaCategorie", new ArrayList<Categoria>());
        }

        request.setAttribute("errore", errore);
        request.setAttribute("prodotto", prodotto);
        request.setAttribute("modalita", "modifica");

        request.getRequestDispatcher("/WEB-INF/views/admin/admin-prodotto-form.jsp")
                .forward(request, response);
    }



}