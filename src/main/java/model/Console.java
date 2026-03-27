package model;

import java.util.Objects;

public class Console extends Prodotto {
    private String modello;
    private String colore;
    private String memoria;

    public Console() {
        super();
    }

    public Console(String nome, String descrizione, int quantita, double prezzo, String azienda, String modello, String colore, String memoria) {
        super(nome, descrizione, quantita, prezzo, azienda);
        this.modello = modello;
        this.colore = colore;
        this.memoria = memoria;
    }

    public Console(int id, String nome, double prezzo, String descrizione, int quantita, String azienda, String modello, String colore, String memoria) {
        super(id, nome, prezzo, descrizione, quantita, azienda);
        this.modello = modello;
        this.colore = colore;
        this.memoria = memoria;
    }

    public String getModello() { return modello; }
    public void setModello(String modello) { this.modello = modello; }

    public String getColore() { return colore; }
    public void setColore(String colore) { this.colore = colore; }

    public String getMemoria() { return memoria; }
    public void setMemoria(String memoria) { this.memoria = memoria; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Console console = (Console) o;
        return Objects.equals(modello, console.modello) &&
                Objects.equals(colore, console.colore) &&
                Objects.equals(memoria, console.memoria);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), modello, colore, memoria);
    }
}