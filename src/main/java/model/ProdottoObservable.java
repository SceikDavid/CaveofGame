package model;

import java.util.ArrayList;
import java.util.List;

public class ProdottoObservable {
    private List<ProdottoObserver> observers = new ArrayList<>();

    public void addObserver(ProdottoObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(ProdottoObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Prodotto nuovoProdotto) {
        for (ProdottoObserver observer : observers) {
            observer.update(nuovoProdotto);
        }
    }
}

