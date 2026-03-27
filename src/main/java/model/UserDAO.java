package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public static User doRetrieveByUsername(String username) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT Username, Nome, Cognome, Email, Telefono, Indirizzo, Password, admin FROM utente WHERE Username=?"
            );
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User p = new User();
                p.setUsername(rs.getString("Username"));
                p.setNome(rs.getString("Nome"));
                p.setCognome(rs.getString("Cognome"));
                p.setEmail(rs.getString("Email"));
                p.setTelefono(rs.getString("Telefono"));
                p.setIndirizzo(rs.getString("Indirizzo"));
                p.setPassword(rs.getString("Password"));
                p.setAdmin(rs.getBoolean("admin"));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void doSave(User user) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO utente(Username, Nome, Cognome, Email, Telefono, Indirizzo, Password, admin) VALUES (?,?,?,?,?,?,?,?)"
            );
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getNome());
            ps.setString(3, user.getCognome());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getTelefono());
            ps.setString(6, user.getIndirizzo());
            ps.setString(7, user.getPassword());
            ps.setBoolean(8, user.isAdmin());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("INSERT error.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(String username) {
        try (Connection connection = ConPool.getConnection()) {
            String sqlCheck = "SELECT Username FROM utente WHERE Username = ?";
            PreparedStatement statementCheck = connection.prepareStatement(sqlCheck);
            statementCheck.setString(1, username);
            ResultSet resultSet = statementCheck.executeQuery();

            if (!resultSet.next()) {
                return;
            }

            String sqlCarrello = "DELETE FROM carrello WHERE username = ?";
            PreparedStatement statementCarrello = connection.prepareStatement(sqlCarrello);
            statementCarrello.setString(1, username);
            statementCarrello.executeUpdate();

            String sqlUtente = "DELETE FROM utente WHERE Username = ?";
            PreparedStatement statementUtente = connection.prepareStatement(sqlUtente);
            statementUtente.setString(1, username);
            statementUtente.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User doRetrieveByMail(String email) {
        try (Connection con = ConPool.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                    "SELECT Username, Nome, Cognome, Email, Telefono, Indirizzo, Password, admin FROM utente WHERE Email=?"
            );
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User p = new User();
                p.setUsername(rs.getString("Username"));
                p.setNome(rs.getString("Nome"));
                p.setCognome(rs.getString("Cognome"));
                p.setEmail(rs.getString("Email"));
                p.setTelefono(rs.getString("Telefono"));
                p.setIndirizzo(rs.getString("Indirizzo"));
                p.setPassword(rs.getString("Password"));
                p.setAdmin(rs.getBoolean("admin"));
                return p;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> doRetriveAll() {
        List<User> utenti = new ArrayList<>();
        try (Connection connection = ConPool.getConnection();
             Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM utente")) {

            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("Username"));
                user.setNome(rs.getString("Nome"));
                user.setCognome(rs.getString("Cognome"));
                user.setEmail(rs.getString("Email"));
                user.setTelefono(rs.getString("Telefono"));
                user.setIndirizzo(rs.getString("Indirizzo"));
                user.setPassword(rs.getString("Password"));
                user.setAdmin(rs.getBoolean("admin"));
                utenti.add(user);
            }
            return utenti;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}