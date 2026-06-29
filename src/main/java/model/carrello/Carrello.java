package model.carrello;

import model.bean.Prodotto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
 * Questa classe rappresenta l'intero carrello dell'utente.
 *
 * Il carrello contiene una lista di righe.
 * Ogni riga contiene:
 * - un prodotto
 * - una quantità
 *
 * Il carrello verrà salvato in sessione HTTP.
 * Quindi per ogni utente loggato (o anche ospite, se vuoi)
 * esisterà un oggetto Carrello separato.
 */
public class Carrello implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * Lista delle righe del carrello.
     *
     * Usiamo ArrayList perché:
     * - è semplice
     * - è sufficiente per il progetto
     * - possiamo iterarla facilmente per cercare un prodotto
     */
    private List<RigaCarrello> righe;

    /*
     * Costruttore.
     *
     * Inizializziamo subito la lista per evitare NullPointerException.
     */
    public Carrello() {
        this.righe = new ArrayList<>();
    }

    public List<RigaCarrello> getRighe() {
        return righe;
    }

    public void setRighe(List<RigaCarrello> righe) {
        this.righe = righe;
    }

    /*
     * Aggiunge un prodotto al carrello.
     *
     * Logica:
     * - se il prodotto NON è già presente, creiamo una nuova riga
     * - se il prodotto È già presente, aumentiamo la quantità
     *
     * Questo evita di avere righe duplicate per lo stesso prodotto.
     */
    public void aggiungiProdotto(Prodotto prodotto) {
        if (prodotto == null) {
            return;
        }

        for (RigaCarrello riga : righe) {
            /*
             * Confrontiamo gli ID dei prodotti.
             * Se troviamo lo stesso prodotto già nel carrello,
             * aumentiamo solo la quantità.
             */
            if (riga.getProdotto() != null
                    && riga.getProdotto().getId() == prodotto.getId()) {
                riga.setQuantita(riga.getQuantita() + 1);
                return;
            }
        }

        /*
         * Se arriviamo qui, il prodotto non era ancora nel carrello.
         * Creiamo una nuova riga con quantità iniziale = 1.
         */
        righe.add(new RigaCarrello(prodotto, 1));
    }

    /*
     * Rimuove completamente un prodotto dal carrello.
     *
     * Se il prodotto esiste nella lista, eliminiamo l'intera riga.
     */
    public void rimuoviProdotto(int idProdotto) {
        /*
         * Usiamo Iterator perché stiamo rimuovendo elementi
         * mentre scorriamo la lista.
         *
         * Se usassimo un semplice for-each con remove() diretto,
         * rischieremmo ConcurrentModificationException.
         */
        Iterator<RigaCarrello> iterator = righe.iterator();

        while (iterator.hasNext()) {
            RigaCarrello riga = iterator.next();

            if (riga.getProdotto() != null
                    && riga.getProdotto().getId() == idProdotto) {
                iterator.remove();
                return;
            }
        }
    }

    /*
     * Aggiorna la quantità di un prodotto già presente nel carrello.
     *
     * Regole:
     * - se quantita <= 0, rimuoviamo il prodotto dal carrello
     * - altrimenti aggiorniamo la quantità con il nuovo valore
     */
    public void aggiornaQuantita(int idProdotto, int quantita) {
        for (RigaCarrello riga : righe) {
            if (riga.getProdotto() != null
                    && riga.getProdotto().getId() == idProdotto) {

                if (quantita <= 0) {
                    rimuoviProdotto(idProdotto);
                } else {
                    riga.setQuantita(quantita);
                }

                return;
            }
        }
    }

    /*
     * Restituisce il numero totale di pezzi nel carrello.
     *
     * Attenzione:
     * qui sommiamo le quantità, non il numero di righe.
     *
     * Esempio:
     * - Prodotto A quantità 2
     * - Prodotto B quantità 3
     * Totale pezzi = 5
     */
    public int getTotaleArticoli() {
        int totale = 0;

        for (RigaCarrello riga : righe) {
            totale += riga.getQuantita();
        }

        return totale;
    }

    /*
     * Restituisce il totale economico del carrello.
     *
     * Sommiamo i subtotali di tutte le righe.
     */
    public BigDecimal getTotale() {
        BigDecimal totale = BigDecimal.ZERO;

        for (RigaCarrello riga : righe) {
            totale = totale.add(riga.getSubtotale());
        }

        return totale;
    }

    /*
     * Restituisce true se il carrello è vuoto.
     * Utile nella JSP per mostrare un messaggio tipo:
     * "Il tuo carrello è vuoto".
     */
    public boolean isVuoto() {
        return righe == null || righe.isEmpty();
    }

    /*
     * Svuota completamente il carrello.
     *
     * Questo metodo sarà molto utile più avanti
     * quando implementerai la conferma ordine.
     */
    public void svuota() {
        righe.clear();
    }
}