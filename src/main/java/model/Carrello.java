package model;

import java.util.ArrayList;
import java.util.List;

public class Carrello {
    private int idCarrello;
    private String username;
    private List<Prodotto> listaCarrello;
    private double totale;

    public Carrello(int idCarrello, String username, double totale) {
        this.idCarrello = idCarrello;
        this.username = username;
        this.totale = totale;
        this.listaCarrello = new ArrayList<>();
    }

    public Carrello(String username) {
        this.username = username;
        this.listaCarrello = new ArrayList<>();
    }

    public Carrello() {
        this.listaCarrello = new ArrayList<>();
    }

    public int getIdCarrello() {
        return idCarrello;
    }

    public void setIdCarrello(int idCarrello) {
        this.idCarrello = idCarrello;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Prodotto> getListaCarrello() {
        return listaCarrello;
    }

    public void setListaCarrello(List<Prodotto> listaCarrello) {
        this.listaCarrello = listaCarrello;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public boolean isEmpty() {
        return listaCarrello.isEmpty();
    }

    public void empty() {
        listaCarrello.clear();
    }

    public boolean contains(Prodotto prodotto) {
        return listaCarrello.contains(prodotto);
    }

    public List<Prodotto> getCarrello() {
        return listaCarrello;
    }

    public void setCarrello(List<Prodotto> carrello) {
        this.listaCarrello = carrello;
    }

    public void addProdotto(Prodotto prodotto) {
        listaCarrello.add(prodotto);
    }

    public Prodotto getProdottoById(int idProdotto) {
        for (Prodotto prodotto : listaCarrello) {
            if (prodotto.getId() == idProdotto) {
                return prodotto;
            }
        }
        return null;
    }

    public Double sumPrezzi() {
        Double costo = 0.0;
        for (Prodotto prodotto : listaCarrello) {
            int quantita = prodotto.getQuantita();
            Double prezzo = prodotto.getPrezzo();
            costo += prezzo * quantita;
        }
        return costo;
    }

    public Prodotto removeProdotto(int idProdotto) {
        for (Prodotto prodotto : listaCarrello) {
            if (prodotto.getId() == idProdotto) {
                listaCarrello.remove(prodotto);
                return prodotto;
            }
        }
        return null;
    }

    public void abbassaQuantitaProdotto(int idProdotto) {
        for (Prodotto prodotto : listaCarrello) {
            if (prodotto.getId() == idProdotto && prodotto.getQuantita() > 0) {
                prodotto.setQuantita(prodotto.getQuantita() - 1);
                if (prodotto.getQuantita() == 0) {
                    removeProdotto(prodotto.getId());
                }
                return;
            }
        }
    }

    public void alzaQuantitaProdotto(int idProdotto) {
        for (Prodotto prodotto : listaCarrello) {
            if (prodotto.getId() == idProdotto) {
                prodotto.setQuantita(prodotto.getQuantita() + 1);
                return;
            }
        }
    }
}