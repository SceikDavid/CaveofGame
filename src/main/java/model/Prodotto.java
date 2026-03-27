package model;

import java.util.Objects;

public class Prodotto {
    private int id;
    private String nome;
    private double prezzo;
    private String descrizione;
    private int quantita;
    private String azienda;

    public Prodotto() {}

    public Prodotto(int id, String nome, double prezzo, String descrizione, int quantita, String azienda) {
        this.id = id;
        this.nome = nome;
        this.prezzo = prezzo;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.azienda = azienda;
    }

    public Prodotto(String nome, String descrizione, int quantita, double prezzo, String azienda) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.quantita = quantita;
        this.prezzo = prezzo;
        this.azienda = azienda;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPrezzo() { return prezzo; }
    public void setPrezzo(double prezzo) { this.prezzo = prezzo; }

    public String getDescrizione() { return descrizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }

    public int getQuantita() { return quantita; }
    public void setQuantita(int quantita) { this.quantita = quantita; }

    public String getAzienda() { return azienda; }
    public void setAzienda(String azienda) { this.azienda = azienda; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Prodotto prodotto = (Prodotto) o;

        return id == prodotto.id &&
                Double.compare(prodotto.prezzo, prezzo) == 0 &&
                quantita == prodotto.quantita &&
                Objects.equals(nome, prodotto.nome) &&
                Objects.equals(descrizione, prodotto.descrizione) &&
                Objects.equals(azienda, prodotto.azienda);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, prezzo, descrizione, quantita, azienda);
    }

    public Prodotto cloneProd() {
        return new Prodotto(this.id, this.nome, this.prezzo, this.descrizione, this.quantita, this.azienda);
    }
}