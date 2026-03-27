package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarrelloDAO {

    public Carrello doRetrieveById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT idCarrello, username, totale FROM carrello WHERE idCarrello=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Carrello c = new Carrello();
                c.setIdCarrello(rs.getInt(1));
                c.setUsername(rs.getString(2));
                c.setTotale(rs.getDouble(3));
                return c;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(Carrello carrello) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO carrello (totale, username) VALUES(?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, carrello.getTotale());
            ps.setString(2, carrello.getUsername());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idCarr = rs.getInt(1);
                carrello.setIdCarrello(idCarr);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doCreateCarrello(Carrello carrello) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO carrello (username, totale) VALUES(?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, carrello.getUsername());
            ps.setDouble(2, 0.0);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                int idCarr = rs.getInt(1);
                carrello.setIdCarrello(idCarr);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Carrello> doRetriveAll() {
        List<Carrello> carrelli = new ArrayList<>();

        try (Connection connection = ConPool.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT idCarrello, username, totale FROM carrello")) {

            while (rs.next()) {
                Carrello cs = new Carrello();
                cs.setIdCarrello(rs.getInt("idCarrello"));
                cs.setUsername(rs.getString("username"));
                cs.setTotale(rs.getDouble("totale"));

                carrelli.add(cs);
            }
            return carrelli;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdateCarrello(Carrello c) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE carrello SET totale = ?, username = ? WHERE idCarrello = ?"
            );
            ps.setDouble(1, c.getTotale());
            ps.setString(2, c.getUsername());
            ps.setInt(3, c.getIdCarrello());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCarrello(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM carrello WHERE idCarrello = ?");
            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> findInCarr(int id) {
        List<Prodotto> composto = new ArrayList<>();

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT idProdotto FROM prodotticarrello WHERE idCarrello=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Prodotto p = new Prodotto();
                p.setId(rs.getInt(1));
                composto.add(p);
            }
            return composto;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Carrello doRetriveByUsername(String username) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT idCarrello, username, totale FROM carrello WHERE username=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Carrello c = new Carrello();
                c.setIdCarrello(rs.getInt(1));
                c.setUsername(rs.getString(2));
                c.setTotale(rs.getDouble(3));
                return c;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}