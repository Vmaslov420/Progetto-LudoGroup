package model.carrello;

import model.bean.Prodotto;

import java.io.Serializable;
import java.math.BigDecimal;

/*
 * Questa classe rappresenta UNA singola riga del carrello.
 *
 * Una riga del carrello contiene:
 * - il prodotto scelto dall'utente
 * - la quantità di quel prodotto
 * - prodotto = quel gioco
 * - quantita = 3
 *
 * Implementiamo Serializable perché il carrello verrà salvato in sessione
 * e avere oggetti serializzabili è una buona pratica.
 */
public class RigaCarrello implements Serializable {

    private static final long serialVersionUID = 1L;

    /*
     * Il prodotto associato a questa riga.
     * Riutilizziamo direttamente il bean Prodotto che hai già.
     */
    private Prodotto prodotto;

    /*
     * Quantità del prodotto nel carrello.
     */
    private int quantita;

    /*
     * Costruttore vuoto.
     * Utile se in futuro servirà creare l'oggetto
     * e valorizzarlo dopo con i setter.
     */
    public RigaCarrello() {
    }

    /*
     * Costruttore comodo per creare subito una riga completa.
     */
    public RigaCarrello(Prodotto prodotto, int quantita) {
        this.prodotto = prodotto;
        this.quantita = quantita;
    }

    public Prodotto getProdotto() {
        return prodotto;
    }

    public void setProdotto(Prodotto prodotto) {
        this.prodotto = prodotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    /*
     * Restituisce il subtotale della riga:
     * prezzo del prodotto * quantità
     *
     * Esempio:
     * prezzo = 20.00
     * quantità = 3
     * subtotale = 60.00
     *
     * Usiamo BigDecimal perché i prezzi non vanno gestiti con double,
     * altrimenti si rischiano errori di arrotondamento.
     */
    public BigDecimal getSubtotale() {
        if (prodotto == null || prodotto.getPrezzo() == null) {
            return BigDecimal.ZERO;
        }

        return prodotto.getPrezzo().multiply(BigDecimal.valueOf(quantita));
    }
}