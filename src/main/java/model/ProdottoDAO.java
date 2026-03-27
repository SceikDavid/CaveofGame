package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottoDAO {

    public int doSave(Prodotto prodotto) {
        int idGenerato = 0;
        try (Connection con = ConPool.getConnection()) {
            con.setAutoCommit(false);

            PreparedStatement psProdotto = con.prepareStatement(
                    "INSERT INTO prodotto (nomeProdotto, descrizione, quantita, prezzo, Azienda) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            psProdotto.setString(1, prodotto.getNome());
            psProdotto.setString(2, prodotto.getDescrizione());
            psProdotto.setInt(3, prodotto.getQuantita());
            psProdotto.setDouble(4, prodotto.getPrezzo());
            psProdotto.setString(5, prodotto.getAzienda());

            psProdotto.executeUpdate();

            ResultSet rs = psProdotto.getGeneratedKeys();
            if (rs.next()) {
                idGenerato = rs.getInt(1);
                prodotto.setId(idGenerato);
            }

            if (prodotto instanceof Console) {
                Console c = (Console) prodotto;
                PreparedStatement psConsole = con.prepareStatement(
                        "INSERT INTO console (idProdotto, Modello, Colore, Memoria) VALUES (?, ?, ?, ?)");
                psConsole.setInt(1, idGenerato);
                psConsole.setString(2, c.getModello());
                psConsole.setString(3, c.getColore());
                psConsole.setString(4, c.getMemoria());
                psConsole.executeUpdate();
            }
            else if (prodotto instanceof Videogioco) {
                Videogioco v = (Videogioco) prodotto;
                PreparedStatement psVideo = con.prepareStatement(
                        "INSERT INTO videogioco (idProdotto, Pegi, Piattaforma) VALUES (?, ?, ?)");
                psVideo.setInt(1, idGenerato);
                psVideo.setInt(2, v.getPegi());
                psVideo.setString(3, v.getPiattaforma());
                psVideo.executeUpdate();
            }
            else if (prodotto instanceof Gadget) {
                Gadget g = (Gadget) prodotto;
                PreparedStatement psGadget = con.prepareStatement(
                        "INSERT INTO gadget (idProdotto, Tipologia) VALUES (?, ?)");
                psGadget.setInt(1, idGenerato);
                psGadget.setString(2, g.getTipologia());
                psGadget.executeUpdate();
            }

            con.commit();
            con.setAutoCommit(true);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return idGenerato;
    }

    public Prodotto doRetrieveById(int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT p.idProdotto, p.nomeProdotto, p.descrizione, p.quantita, p.prezzo, p.Azienda, " +
                    "c.Modello, c.Colore, c.Memoria, " +
                    "v.Pegi, v.Piattaforma, " +
                    "g.Tipologia " +
                    "FROM prodotto p " +
                    "LEFT JOIN console c ON p.idProdotto = c.idProdotto " +
                    "LEFT JOIN videogioco v ON p.idProdotto = v.idProdotto " +
                    "LEFT JOIN gadget g ON p.idProdotto = g.idProdotto " +
                    "WHERE p.idProdotto = ?";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return costruisciProdottoDaResultSet(rs);
            }
            return null;
        }
    }

    public List<Prodotto> doRetriveAll() {
        List<Prodotto> prodotti = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            String query = "SELECT p.idProdotto, p.nomeProdotto, p.descrizione, p.quantita, p.prezzo, p.Azienda, " +
                    "c.Modello, c.Colore, c.Memoria, " +
                    "v.Pegi, v.Piattaforma, " +
                    "g.Tipologia " +
                    "FROM prodotto p " +
                    "LEFT JOIN console c ON p.idProdotto = c.idProdotto " +
                    "LEFT JOIN videogioco v ON p.idProdotto = v.idProdotto " +
                    "LEFT JOIN gadget g ON p.idProdotto = g.idProdotto";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                prodotti.add(costruisciProdottoDaResultSet(rs));
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Prodotto deleteProdotto(int id) throws SQLException {
        Prodotto p = doRetrieveById(id);
        if (p != null) {
            try (Connection con = ConPool.getConnection()) {
                PreparedStatement ps = con.prepareStatement("DELETE FROM prodotto WHERE idProdotto = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        }
        return p;
    }

    public void doUpdateQuantita(Prodotto p) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE prodotto SET quantita = ? WHERE idProdotto = ?");
            ps.setInt(1, p.getQuantita());
            ps.setInt(2, p.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Prodotto costruisciProdottoDaResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("idProdotto");
        String nome = rs.getString("nomeProdotto");
        String desc = rs.getString("descrizione");
        int qt = rs.getInt("quantita");
        double prz = rs.getDouble("prezzo");
        String az = rs.getString("Azienda");

        if (rs.getString("Piattaforma") != null) {
            return new Videogioco(id, nome, prz, desc, qt, az, rs.getString("Piattaforma"), rs.getInt("Pegi"));
        }
        else if (rs.getString("Tipologia") != null) {
            return new Gadget(id, nome, prz, desc, qt, az, rs.getString("Tipologia"));
        }
        else if (rs.getString("Modello") != null || rs.getString("Colore") != null || rs.getString("Memoria") != null) {
            return new Console(id, nome, prz, desc, qt, az, rs.getString("Modello"), rs.getString("Colore"), rs.getString("Memoria"));
        }
        else {
            return new Prodotto(id, nome, prz, desc, qt, az);
        }
    }

    public Prodotto doUpdatePrezzo(double prezzo, int id) throws SQLException {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE prodotto SET prezzo = ? WHERE idProdotto = ?");
            ps.setDouble(1, prezzo);
            ps.setInt(2, id);
            int updated = ps.executeUpdate();
            if (updated > 0) {
                return doRetrieveById(id);
            }
            return null;
        }
    }

    public Prodotto doUpdateProdotto(Prodotto p) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE prodotto SET nomeProdotto=?, descrizione=?, quantita=?, prezzo=?, Azienda=? WHERE idProdotto=?"
            );
            ps.setString(1, p.getNome());
            ps.setString(2, p.getDescrizione());
            ps.setInt(3, p.getQuantita());
            ps.setDouble(4, p.getPrezzo());
            ps.setString(5, p.getAzienda());
            ps.setInt(6, p.getId());

            int updated = ps.executeUpdate();
            if (updated > 0) {
                return doRetrieveById(p.getId());
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}