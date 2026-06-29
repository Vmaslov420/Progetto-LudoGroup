package model.bean;

import java.math.BigDecimal;

/*
 * Questo è un JavaBean che rappresenta un prodotto del catalogo.
 *
 * Perché usiamo un bean?
 * - perché nel pattern MVC i dati vengono trasportati tra DAO, Servlet e JSP
 *   sotto forma di oggetti semplici;
 * - perché JSP e servlet lavorano bene con oggetti che hanno campi privati
 *   e metodi getter/setter pubblici.
 *
 * I campi sono private per rispettare l'incapsulamento:
 * il dato non viene modificato direttamente dall'esterno,
 * ma solo tramite i metodi setter.
 */
public class Prodotto {

    // Identificatore univoco del prodotto nel database
    private int id;

    // Nome visualizzato del prodotto
    private String nome;

    // Descrizione testuale del prodotto
    private String descrizione;

    // Prezzo del prodotto
    // Usiamo BigDecimal invece di double perché per i valori monetari
    // è più preciso ed evita errori di arrotondamento binario.
    private BigDecimal prezzo;

    // IVA del prodotto
    // Anche qui BigDecimal è la scelta corretta perché è un valore decimale.
    private BigDecimal iva;

    // Quantità disponibile a magazzino
    private int stock;

    // Percorso o nome del file immagine del prodotto
    private String immagine;

    /*
     * ID della categoria associata.
     * Usiamo Integer e NON int perché nel database categoria_id può essere NULL.
     * Se usassimo int, non potremmo rappresentare correttamente l'assenza di categoria.
     */
    private Integer categoriaId;

    /*
     * Nome della categoria.
     *
     * Questo campo non appartiene direttamente alla tabella prodotto,
     * ma viene valorizzato grazie alla JOIN con la tabella categoria.
     * Serve per mostrare nelle JSP il nome leggibile della categoria.
     */
    private String nomeCategoria;

    /*
     * Flag di cancellazione logica.
     *
     * Se true, il prodotto non viene eliminato fisicamente dal database,
     * ma viene marcato come "non più attivo".
     * Questa scelta è utile nei sistemi e-commerce perché evita
     * problemi con storico ordini e integrità referenziale.
     */
    private boolean eliminato;

    // Getter e setter dell'id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter e setter del nome
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    // Getter e setter della descrizione
    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    // Getter e setter del prezzo
    public BigDecimal getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(BigDecimal prezzo) {
        this.prezzo = prezzo;
    }

    // Getter e setter dell'IVA
    public BigDecimal getIva() {
        return iva;
    }

    public void setIva(BigDecimal iva) {
        this.iva = iva;
    }

    // Getter e setter dello stock
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Getter e setter dell'immagine
    public String getImmagine() {
        return immagine;
    }

    public void setImmagine(String immagine) {
        this.immagine = immagine;
    }

    // Getter e setter della categoria
    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

    // Getter e setter del nome categoria
    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    /*
     * Per i boolean in JavaBean si usa spesso la sintassi isXxx()
     * invece di getXxx().
     * Per questo troviamo isEliminato() e non getEliminato().
     */
    public boolean isEliminato() {
        return eliminato;
    }

    public void setEliminato(boolean eliminato) {
        this.eliminato = eliminato;
    }
}