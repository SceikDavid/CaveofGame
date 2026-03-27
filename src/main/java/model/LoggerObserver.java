package model;

public class LoggerObserver implements ProdottoObserver {
    public void update(Prodotto nuovoProdotto) {
        System.out.println("Nuovo prodotto aggiunto: " + nuovoProdotto.getNome());
    }
}