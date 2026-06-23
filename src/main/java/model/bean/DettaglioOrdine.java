package model.bean;

import java.math.BigDecimal;

public class DettaglioOrdine {
    private int id;
    private int ordineId;
    private Integer prodottoId; // Integer (nullable) perché ON DELETE SET NULL
    private String nomeProdotto; // salvato storicamente
    private BigDecimal prezzoUnitario; // salvato storicamente
    private BigDecimal iva;            // salvato storicamente
    private int quantita;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getOrdineId() { return ordineId; }
    public void setOrdineId(int ordineId) { this.ordineId = ordineId; }

    public Integer getProdottoId() { return prodottoId; }
    public void setProdottoId(Integer prodottoId) { this.prodottoId = prodottoId; }

    public String getNomeProdotto() { return nomeProdotto; }
    public void setNomeProdotto(String nomeProdotto) { this.nomeProdotto = nomeProdotto; }

    public BigDecimal getPrezzoUnitario() { return prezzoUnitario; }
    public void setPrezzoUnitario(BigDecimal prezzoUnitario) { this.prezzoUnitario = prezzoUnitario; }

    public BigDecimal getIva() { return iva; }
    public void setIva(BigDecimal iva) { this.iva = iva; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    // Metodo utile per calcolare il subtotale nella JSP
    public BigDecimal getSubtotale() {
        return prezzoUnitario.multiply(BigDecimal.valueOf(quantita));
    }
}