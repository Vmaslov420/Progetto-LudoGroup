package model.bean;

public class Indirizzo {
    private int id;
    private int utenteId;
    private String via;
    private String citta;
    private String cap;
    private String provincia;
    private String tipo; // "fatturazione" o "spedizione"

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUtenteId() { return utenteId; }
    public void setUtenteId(int utenteId) { this.utenteId = utenteId; }

    public String getVia() { return via; }
    public void setVia(String via) { this.via = via; }

    public String getCitta() { return citta; }
    public void setCitta(String citta) { this.citta = citta; }

    public String getCap() { return cap; }
    public void setCap(String cap) { this.cap = cap; }

    public String getProvincia() { return provincia; }
    public void setProvincia(String provincia) { this.provincia = provincia; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}