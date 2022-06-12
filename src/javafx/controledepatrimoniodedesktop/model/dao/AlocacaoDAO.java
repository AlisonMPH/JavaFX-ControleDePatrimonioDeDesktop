package javafx.controledepatrimoniodedesktop.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.controledepatrimoniodedesktop.desktop.Alocacao;
import javafx.controledepatrimoniodedesktop.desktop.Desktop;
import javafx.controledepatrimoniodedesktop.desktop.Localizacao;
import javafx.controledepatrimoniodedesktop.desktop.Usuario;

public class AlocacaoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean inserir(Alocacao alocacao) {
        String sql = ("INSERT INTO \"ALOCACAO\"(\"FK_DESKTOP_ALOCACAO\",\"FK_LOCALIZACAO_ALOCACAO\",\"FK_USUARIO_ALOCACAO\") VALUES(?,?,?)");
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alocacao.getDesktop().getId());
            stmt.setInt(2, alocacao.getLocalizacao().getId());
            stmt.setInt(3, alocacao.getUsuario().getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean alterar(Alocacao alocacao) {
        String sql = "UPDATE \"ALOCACAO\" SET \"FK_DESKTOP_ALOCACAO\"=?,\"FK_LOCALIZACAO_ALOCACAO\"=?,\"FK_USUARIO_ALOCACAO\"=? WHERE \"ID_ALOCACAO\"=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alocacao.getDesktop().getId());
            stmt.setInt(2, alocacao.getLocalizacao().getId());
            stmt.setInt(3, alocacao.getUsuario().getId());
            stmt.setInt(4, alocacao.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public boolean remover(Alocacao alocacao) {
        String sql = "DELETE FROM \"ALOCACAO\" WHERE \"ID_ALOCACAO\"=?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alocacao.getId());
            stmt.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }

    public List<Alocacao> listar() {
        String sql = "SELECT * FROM \"ALOCACAO\"";
        List<Alocacao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Desktop desktop = new Desktop();
                Localizacao localizacao = new Localizacao();
                Usuario usuario = new Usuario();
                Alocacao alocacao = new Alocacao();

                alocacao.setId(resultado.getInt("ID_ALOCACAO"));
                desktop.setId(resultado.getInt("FK_DESKTOP_ALOCACAO"));
                localizacao.setId(resultado.getInt("FK_LOCALIZACAO_ALOCACAO"));
                usuario.setId(resultado.getInt("FK_USUARIO_ALOCACAO"));

                DesktopDAO desktopDAO = new DesktopDAO();
                desktopDAO.setConnection(connection);
                desktop = desktopDAO.buscar(desktop);

                LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
                localizacaoDAO.setConnection(connection);
                localizacao = localizacaoDAO.buscar(localizacao);

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.setConnection(connection);
                usuario = usuarioDAO.buscar(usuario);

                alocacao.setDesktop(desktop);
                alocacao.setLocalizacao(localizacao);
                alocacao.setUsuario(usuario);
                retorno.add(alocacao);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public Alocacao buscar(Alocacao alocacao) {
        String sql = "SELECT * FROM \"ALOCACAO\" WHERE \"ID_ALOCACAO\"=?";
        Alocacao retorno = new Alocacao();
        Desktop desktop = new Desktop();
        Localizacao localizacao = new Localizacao();
        Usuario usuario = new Usuario();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alocacao.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                retorno.setId(resultado.getInt("ID_ALOCACAO"));
                desktop.setId(resultado.getInt("ID_DESKTOP"));
                localizacao.setId(resultado.getInt("ID_LOCALIZACAO"));
                usuario.setId(resultado.getInt("ID_USUARIO"));
                retorno.setDesktop(desktop);
                retorno.setLocalizacao(localizacao);
                retorno.setUsuario(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public List<Alocacao> listarRelatorio() {
        String sql = "SELECT * FROM \"ALOCACAO\" INNER JOIN \"DESKTOP\" ON \"FK_DESKTOP_ALOCACAO\" = \"ID_DESKTOP\"\n"
                + "INNER JOIN \"USUARIO\" ON \"ID_USUARIO\" = \"FK_USUARIO_ALOCACAO\"\n"
                + "INNER JOIN \"LOCALIZACAO\" ON \"ID_LOCALIZACAO\" = \"FK_LOCALIZACAO_ALOCACAO\"";
        List<Alocacao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Alocacao alocacao = new Alocacao();
                Desktop desktop = new Desktop();
                Localizacao localizacao = new Localizacao();
                Usuario usuario = new Usuario();

                desktop.setId(resultado.getInt("FK_DESKTOP_ALOCACAO"));
                localizacao.setId(resultado.getInt("FK_LOCALIZACAO_ALOCACAO"));
                usuario.setId(resultado.getInt("FK_USUARIO_ALOCACAO"));

                DesktopDAO desktopDAO = new DesktopDAO();
                desktopDAO.setConnection(connection);
                desktop = desktopDAO.buscar(desktop);

                LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
                localizacaoDAO.setConnection(connection);
                localizacao = localizacaoDAO.buscar(localizacao);

                UsuarioDAO usuarioDAO = new UsuarioDAO();
                usuarioDAO.setConnection(connection);
                usuario = usuarioDAO.buscar(usuario);

                alocacao.setDesktop(desktop);
                alocacao.setLocalizacao(localizacao);
                alocacao.setUsuario(usuario);

                retorno.add(alocacao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    /*public List<Alocacao> listarAlocacaoGrafico() {
        String sql = "SELECT COUNT (\"FK_LOCALIZACAO_ALOCACAO\"), \"NOME_LOCALIZACAO\" FROM \"ALOCACAO\" INNER JOIN \"LOCALIZACAO\" \n" +
"	ON \"FK_LOCALIZACAO_ALOCACAO\" = \"ID_LOCALIZACAO\"\n" +
"		GROUP BY (\"FK_LOCALIZACAO_ALOCACAO\",\"NOME_LOCALIZACAO\");";
        List<Alocacao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                Localizacao localizacao = new Localizacao();
                Alocacao alocacao = new Alocacao();

                alocacao.setId(resultado.getInt("FK_LOCALIZACAO_ALOCACAO"));
                localizacao.setId(resultado.getInt("NOME_LOCALIZACAO"));

                LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
                localizacaoDAO.setConnection(connection);
                localizacao = localizacaoDAO.buscar(localizacao);
                System.out.println(localizacao.getNome());

                alocacao.setLocalizacao(localizacao);
                retorno.add(alocacao);
            }

        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }*/
    /*public Alocacao buscarQuantidade(Alocacao alocacao) {
        String sql = "SELECT COUNT (\"FK_LOCALIZACAO_ALOCACAO\") FROM \"ALOCACAO\" WHERE \"FK_LOCALIZACAO_ALOCACAO\"= ?";
        Alocacao retorno = new Alocacao();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, alocacao.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                Localizacao localizacao = new Localizacao();
                localizacao.setQuantidade(resultado.getInt("count"));

                alocacao.setLocalizacao(localizacao);
                retorno = alocacao;

            }
        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(retorno);
        return retorno;
    }*/

}
