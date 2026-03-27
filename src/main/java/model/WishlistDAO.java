package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class WishlistDAO {

    public void addProdotto(String username, int idProdotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT IGNORE INTO wishlist (username, idProdotto) VALUES (?, ?)"
            );
            ps.setString(1, username);
            ps.setInt(2, idProdotto);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeProdotto(String username, int idProdotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "DELETE FROM wishlist WHERE username = ? AND idProdotto = ?"
            );
            ps.setString(1, username);
            ps.setInt(2, idProdotto);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Prodotto> getWishlist(String username) {
        List<Prodotto> wishlist = new ArrayList<>();
        ProdottoDAO prodottoDAO = new ProdottoDAO();

        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT idProdotto FROM wishlist WHERE username = ?"
            );
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idProdotto = rs.getInt("idProdotto");
                Prodotto p = prodottoDAO.doRetrieveById(idProdotto);
                if (p != null) {
                    wishlist.add(p);
                }
            }
            return wishlist;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isProdottoInWishlist(String username, int idProdotto) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT 1 FROM wishlist WHERE username = ? AND idProdotto = ?"
            );
            ps.setString(1, username);
            ps.setInt(2, idProdotto);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}