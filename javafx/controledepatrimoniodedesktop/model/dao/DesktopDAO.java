
package javafx.controledepatrimoniodedesktop.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.controledepatrimoniodedesktop.desktop.Desktop;

public class DesktopDAO {
    
private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Desktop desktop) {
        String sql = ("INSERT INTO \"DESKTOP\"(\"NOME\",\"FABRICANTE\",\"SERVICETAG\",\"MODELO\",\"MAC\") VALUES(?,?,?,?,?)");
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, desktop.getNome());
            stmt.setString(2, desktop.getFabricante());
            stmt.setString(3, desktop.getServicetag());
            stmt.setString(4, desktop.getModelo());
            stmt.setString(5, desktop.getMac());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DesktopDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Desktop desktop) {
        String sql = "UPDATE \"DESKTOP\" SET \"NOME\"=?,\"FABRICANTE\"=?,\"SERVICETAG\"=?,\"MODELO\"=?,\"MAC\"=? WHERE \"ID_DESKTOP\"=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, desktop.getNome());
            stmt.setString(2, desktop.getFabricante());
            stmt.setString(3, desktop.getServicetag());
            stmt.setString(4, desktop.getModelo());
            stmt.setString(5, desktop.getMac());
            stmt.setInt(6, desktop.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DesktopDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Desktop desktop) {
        String sql = "DELETE FROM \"DESKTOP\" WHERE \"ID_DESKTOP\"=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, desktop.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DesktopDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Desktop> listar() {
        String sql = "SELECT * FROM \"DESKTOP\"";
        List<Desktop> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Desktop desktop = new Desktop();
                desktop.setId(resultado.getInt("ID_DESKTOP"));
                desktop.setNome(resultado.getString("nome"));
                desktop.setFabricante(resultado.getString("fabricante"));
                desktop.setServicetag(resultado.getString("servicetag"));
                desktop.setModelo(resultado.getString("modelo"));
                desktop.setMac(resultado.getString("mac"));
                retorno.add(desktop);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DesktopDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Desktop buscar(Desktop desktop) {
        String sql = "SELECT * FROM \"DESKTOP\" WHERE \"ID_DESKTOP\"=?";
        Desktop retorno = new Desktop();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, desktop.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                desktop.setNome(resultado.getString("nome"));
                desktop.setFabricante(resultado.getString("fabricante"));
                desktop.setServicetag(resultado.getString("servicetag"));
                desktop.setModelo(resultado.getString("modelo"));
                desktop.setMac(resultado.getString("mac"));
                retorno = desktop;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DesktopDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    
}

