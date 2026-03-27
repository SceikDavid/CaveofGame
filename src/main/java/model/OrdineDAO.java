package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdineDAO {

    public Ordine doRetrieveById(int id){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT idOrdine, PrezzoTotale, dataOrdine, metodoDiPagamento, indirizzoSpedizione, username, idCarrello, prodotti FROM ordine WHERE idOrdine=?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setId(rs.getInt("idOrdine"));
                ordine.setTotale(rs.getDouble("PrezzoTotale"));
                ordine.setDataOrd(rs.getDate("dataOrdine"));
                ordine.setPagamento(rs.getString("metodoDiPagamento"));
                ordine.setSpedizione(rs.getString("indirizzoSpedizione"));
                ordine.setIdCliente(rs.getString("username"));
                ordine.setIdCarrello(rs.getInt("idCarrello"));
                ordine.setProdotti(rs.getString("prodotti"));
                return ordine;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ordine> doRetrieveByIdCliente(String username) {
        List<Ordine> ordini = new ArrayList<>();

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT idOrdine, PrezzoTotale, dataOrdine, metodoDiPagamento, indirizzoSpedizione, username, idCarrello, prodotti FROM ordine WHERE username=?"
            );
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Ordine ordine = new Ordine();
                ordine.setId(rs.getInt("idOrdine"));
                ordine.setTotale(rs.getDouble("PrezzoTotale"));
                ordine.setDataOrd(rs.getDate("dataOrdine"));
                ordine.setPagamento(rs.getString("metodoDiPagamento"));
                ordine.setSpedizione(rs.getString("indirizzoSpedizione"));
                ordine.setIdCliente(rs.getString("username"));
                ordine.setIdCarrello(rs.getInt("idCarrello"));
                ordine.setProdotti(rs.getString("prodotti"));
                ordini.add(ordine);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return ordini;
    }

    public void doSave(Ordine ordine){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO ordine (PrezzoTotale, dataOrdine, metodoDiPagamento, indirizzoSpedizione, username, idCarrello, prodotti) VALUES(?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setDouble(1, ordine.getTotale());
            ps.setDate(2, ordine.getDataOrd());
            ps.setString(3, ordine.getPagamento());
            ps.setString(4, ordine.getSpedizione());
            ps.setString(5, ordine.getIdCliente());
            ps.setInt(6, ordine.getIdCarrello());
            ps.setString(7, ordine.getProdotti());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int id = rs.getInt(1);
                ordine.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Ordine> doRetriveAll(){
        List<Ordine> ordini = new ArrayList<>();

        try(Connection connection = ConPool.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM ordine")) {

            while(rs.next()){
                Ordine or = new Ordine();
                or.setId(rs.getInt("idOrdine"));
                or.setTotale(rs.getDouble("PrezzoTotale"));
                or.setDataOrd(rs.getDate("dataOrdine"));
                or.setPagamento(rs.getString("metodoDiPagamento"));
                or.setSpedizione(rs.getString("indirizzoSpedizione"));
                or.setIdCliente(rs.getString("username"));
                or.setIdCarrello(rs.getInt("idCarrello"));
                or.setProdotti(rs.getString("prodotti"));

                ordini.add(or);
            }

            return ordini;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdateOrdine(Ordine o){
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE ordine SET PrezzoTotale=?, dataOrdine=?, metodoDiPagamento=?, indirizzoSpedizione=?, username=?, idCarrello=?, prodotti=? WHERE idOrdine=?"
            );

            ps.setDouble(1, o.getTotale());
            ps.setDate(2, o.getDataOrd());
            ps.setString(3, o.getPagamento());
            ps.setString(4, o.getSpedizione());
            ps.setString(5, o.getIdCliente());
            ps.setInt(6, o.getIdCarrello());
            ps.setString(7, o.getProdotti());
            ps.setInt(8, o.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteOrdine(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM ordine WHERE idOrdine = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Errore durante l'eliminazione dell'ordine.", e);
        }
    }
}