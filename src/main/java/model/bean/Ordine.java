package model.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class Ordine {
    private int id;
    private int utenteId;
    private String nomeUtente;   // preso dalla JOIN con utente
    private Timestamp data;
    private BigDecimal totale;
    private String stato;
    private String metodoPagamento;
    private List<DettaglioOrdine> dettagli; // lista righe ordine

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }

    public String getNomeUtente() { return nomeUtente; }
    public void setNomeUtente(String nomeUtente) { this.nomeUtente = nomeUtente; }

    public Timestamp getData() { return data; }
    public void setData(Timestamp data) { this.data = data; }

    public BigDecimal getTotale() { return totale; }
    public void setTotale(BigDecimal totale) { this.totale = totale; }

    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }

    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }

    public List<DettaglioOrdine> getDettagli() { return dettagli; }
    public void setDettagli(List<DettaglioOrdine> dettagli) { this.dettagli = dettagli; }
}