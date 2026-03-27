package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdottiCarrelloDAO {

    public void doSaveProdottoCarrello(ProdottiCarrello prodottiCarrello) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO prodotticarrello (quantita, idCarrello, idProdotto, taglia) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, prodottiCarrello.getQuantita());
            ps.setInt(2, prodottiCarrello.getIdCarrello());
            ps.setInt(3, prodottiCarrello.getIdProdotto());
            ps.setString(4, prodottiCarrello.getTaglia());
            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int idProdCarr = rs.getInt(1);
                prodottiCarrello.setIdProdCarr(idProdCarr);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdottiCarrello doRetrieveById(int id) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT idProdCarr, quantita, idCarrello, idProdotto, taglia FROM prodotticarrello WHERE idProdotto=?"
            );
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProdottiCarrello pc = new ProdottiCarrello();
                pc.setIdProdCarr(rs.getInt(1));
                pc.setQuantita(rs.getInt(2));
                pc.setIdCarrello(rs.getInt(3));
                pc.setIdProdotto(rs.getInt(4));
                pc.setTaglia(rs.getString(5));
                return pc;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdottiCarrello doRetrieveByCarrelloAndProdotto(int idCarrello, int idProdotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT idProdCarr, quantita, idCarrello, idProdotto, taglia FROM prodotticarrello WHERE idCarrello=? AND idProdotto=?"
            );
            ps.setInt(1, idCarrello);
            ps.setInt(2, idProdotto);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ProdottiCarrello pc = new ProdottiCarrello();
                pc.setIdProdCarr(rs.getInt(1));
                pc.setQuantita(rs.getInt(2));
                pc.setIdCarrello(rs.getInt(3));
                pc.setIdProdotto(rs.getInt(4));
                pc.setTaglia(rs.getString(5));
                return pc;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProdottiCarrello> doRetriveAllById(int idCarrello) {
        List<ProdottiCarrello> prodotticarrello = new ArrayList<>();
        try (Connection connection = ConPool.getConnection()) {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM prodotticarrello WHERE idCarrello=?");
            ps.setInt(1, idCarrello);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProdottiCarrello pc = new ProdottiCarrello();
                pc.setIdProdCarr(rs.getInt(1));
                pc.setQuantita(rs.getInt(2));
                pc.setIdCarrello(rs.getInt(3));
                pc.setIdProdotto(rs.getInt(4));
                pc.setTaglia(rs.getString(5));
                prodotticarrello.add(pc);
            }
            return prodotticarrello;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdateProdottiCarrello(ProdottiCarrello pc) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE prodotticarrello SET quantita=?, idCarrello=?, idProdotto=? WHERE idProdCarr=?"
            );
            ps.setInt(1, pc.getQuantita());
            ps.setInt(2, pc.getIdCarrello());
            ps.setInt(3, pc.getIdProdotto());
            ps.setInt(4, pc.getIdProdCarr());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ProdottiCarrello deleteProdottoCarrello(int id, int idCar) {
        ProdottiCarrello prodottiCarrello = doRetrieveById(id);
        if (prodottiCarrello != null) {
            Connection con = null;
            try {
                con = ConPool.getConnection();
                con.setAutoCommit(false);

                String sqlCarrello = "DELETE FROM prodotticarrello WHERE idProdotto = ? AND idCarrello = ?";
                PreparedStatement ps = con.prepareStatement(sqlCarrello);
                ps.setInt(1, id);
                ps.setInt(2, idCar);

                int rowsDeletedCarr = ps.executeUpdate();
                if (rowsDeletedCarr > 0) {
                    con.commit();
                } else {
                    con.rollback();
                }
            } catch (SQLException e) {
                if (con != null) {
                    try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
                }
                throw new RuntimeException("Errore durante l'eliminazione del prodotto.", e);
            } finally {
                if (con != null) {
                    try {
                        con.setAutoCommit(true);
                        con.close();
                    } catch (SQLException ex) { ex.printStackTrace(); }
                }
            }
        }
        return null;
    }

    public ProdottiCarrello deleteProdottoCarrelloByCarrello(int idCar) {
        Connection con = null;
        try {
            con = ConPool.getConnection();
            con.setAutoCommit(false);

            String sqlCarrello = "DELETE FROM prodotticarrello WHERE idCarrello=?";
            PreparedStatement ps = con.prepareStatement(sqlCarrello);
            ps.setInt(1, idCar);

            int rowsDeletedCarr = ps.executeUpdate();
            if (rowsDeletedCarr > 0) {
                con.commit();
            } else {
                con.rollback();
            }
        } catch (SQLException e) {
            if (con != null) {
                try { con.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            throw new RuntimeException("Errore durante l'eliminazione del prodotto dal carrello.", e);
        } finally {
            if (con != null) {
                try {
                    con.setAutoCommit(true);
                    con.close();
                } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
        return null;
    }

    public List<ProdottiCarrello> doRetrieveByCarrello(int idCarrello) {
        List<ProdottiCarrello> prod = new ArrayList<>();
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT idProdCarr, quantita, idCarrello, idProdotto, taglia FROM prodotticarrello WHERE idCarrello=?"
            );
            ps.setInt(1, idCarrello);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                ProdottiCarrello pc = new ProdottiCarrello();
                pc.setIdProdCarr(rs.getInt(1));
                pc.setQuantita(rs.getInt(2));
                pc.setIdCarrello(rs.getInt(3));
                pc.setIdProdotto(rs.getInt(4));
                pc.setTaglia(rs.getString(5));
                prod.add(pc);
            }
            return prod;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doUpdateProdottiQuantita(ProdottiCarrello pc) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "UPDATE prodotticarrello SET quantita=? WHERE idProdotto=? AND idCarrello=?"
            );
            ps.setInt(1, pc.getQuantita());
            ps.setInt(2, pc.getIdProdotto());
            ps.setInt(3, pc.getIdCarrello());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ProdottiCarrello> doRetriveAll() {
        List<ProdottiCarrello> prodotti = new ArrayList<>();
        try(Connection connection = ConPool.getConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM prodotticarrello")) {

            while(rs.next()){
                ProdottiCarrello p = new ProdottiCarrello();
                p.setIdProdCarr(rs.getInt(1));
                p.setQuantita(rs.getInt(2));
                p.setIdCarrello(rs.getInt(3));
                p.setIdProdotto(rs.getInt(4));
                p.setTaglia(rs.getString(5));
                prodotti.add(p);
            }
            return prodotti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}