package model;

import java.util.Objects;

public class Videogioco extends Prodotto {
    private int pegi;
    private String piattaforma;

    public Videogioco() {
        super();
    }

    public Videogioco(String nome, String descrizione, int quantita, double prezzo, String azienda, String piattaforma, int pegi) {
        super(nome, descrizione, quantita, prezzo, azienda);
        this.piattaforma = piattaforma;
        this.pegi = pegi;
    }

    public Videogioco(int id, String nome, double prezzo, String descrizione, int quantita, String azienda, String piattaforma, int pegi) {
        super(id, nome, prezzo, descrizione, quantita, azienda);
        this.piattaforma = piattaforma;
        this.pegi = pegi;
    }

    public int getPegi() { return pegi; }
    public void setPegi(int pegi) { this.pegi = pegi; }

    public String getPiattaforma() { return piattaforma; }
    public void setPiattaforma(String piattaforma) { this.piattaforma = piattaforma; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Videogioco that = (Videogioco) o;
        return pegi == that.pegi &&
                Objects.equals(piattaforma, that.piattaforma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pegi, piattaforma);
    }
}