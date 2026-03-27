package model;

public class ProdottiCarrello {

    private int idProdCarr;
    private int quantita;
    private int idCarrello;
    private int idProdotto;

    private String taglia;

    public ProdottiCarrello(){
    }

    public ProdottiCarrello(int id, int quantita, int idcarrello, int idprodotto){
        this.idProdCarr = id;
        this.quantita = quantita;
        this.idCarrello = idcarrello;
        this.idProdotto = idprodotto;
    }

    public int getIdProdCarr() { return idProdCarr; }

    public void setIdProdCarr(int idProdCarr) { this.idProdCarr = idProdCarr; }

    public int getQuantita() { return quantita; }

    public void setQuantita(int quantita) { this.quantita = quantita; }

    public int getIdCarrello() { return idCarrello; }

    public void setIdCarrello(int idCarrello) { this.idCarrello = idCarrello; }

    public int getIdProdotto() { return idProdotto; }

    public void setIdProdotto(int idProdotto) { this.idProdotto = idProdotto; }

    public String getTaglia() {
        return taglia;
    }

    public void setTaglia(String taglia) {
        this.taglia = taglia;
    }
}
