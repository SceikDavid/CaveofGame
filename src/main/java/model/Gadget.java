package model;

import java.util.Objects;

public class Gadget extends Prodotto {
    private String tipologia;

    public Gadget() {
        super();
    }

    public Gadget(String nome, String descrizione, int quantita, double prezzo, String azienda, String tipologia) {
        super(nome, descrizione, quantita, prezzo, azienda);
        this.tipologia = tipologia;
    }

    public Gadget(int id, String nome, double prezzo, String descrizione, int quantita, String azienda, String tipologia) {
        super(id, nome, prezzo, descrizione, quantita, azienda);
        this.tipologia = tipologia;
    }

    public String getTipologia() { return tipologia; }
    public void setTipologia(String tipologia) { this.tipologia = tipologia; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Gadget gadget = (Gadget) o;
        return Objects.equals(tipologia, gadget.tipologia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tipologia);
    }
}