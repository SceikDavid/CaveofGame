package model;

import java.sql.Date;
import java.util.Objects;

public class Ordine {
    private int id;
    private double totale;
    private Date dataOrd;
    private String pagamento;
    private String spedizione;
    private String idCliente;
    private int idCarrello;
    private String prodotti;

    public Ordine() {
    }

    public Ordine(int id, double totale, Date dataOrd, String pagamento, String spedizione, int idCarrello, String idCliente) {
        this.id = id;
        this.totale = totale;
        this.dataOrd = dataOrd;
        this.pagamento = pagamento;
        this.spedizione = spedizione;
        this.idCarrello = idCarrello;
        this.idCliente = idCliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotale() {
        return totale;
    }

    public void setTotale(double totale) {
        this.totale = totale;
    }

    public Date getDataOrd() {
        return dataOrd;
    }

    public void setDataOrd(Date dataOrd) {
        this.dataOrd = dataOrd;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public String getSpedizione() {
        return spedizione;
    }

    public void setSpedizione(String spedizione) {
        this.spedizione = spedizione;
    }

    public int getIdCarrello() {
        return idCarrello;
    }

    public void setIdCarrello(int idCarrello) {
        this.idCarrello = idCarrello;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getProdotti() {
        return prodotti;
    }

    public void setProdotti(String prodotti) {
        this.prodotti = prodotti;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ordine ordine = (Ordine) o;
        return id == ordine.id &&
                Double.compare(ordine.totale, totale) == 0 &&
                idCarrello == ordine.idCarrello &&
                Objects.equals(dataOrd, ordine.dataOrd) &&
                Objects.equals(pagamento, ordine.pagamento) &&
                Objects.equals(spedizione, ordine.spedizione) &&
                Objects.equals(idCliente, ordine.idCliente) &&
                Objects.equals(prodotti, ordine.prodotti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, totale, dataOrd, pagamento, spedizione, idCarrello, idCliente, prodotti);
    }
}