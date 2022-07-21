package javafx.controledepatrimoniodedesktop.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.controledepatrimoniodedesktop.desktop.Localizacao;
import static javax.swing.UIManager.getInt;

public class LocalizacaoDAO {

    private Connection connection;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public List<Localizacao> listar() {
        String sql = "SELECT * FROM \"LOCALIZACAO\"";
        List<Localizacao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                Localizacao localizacao = new Localizacao();
                localizacao.setId(resultado.getInt("ID_LOCALIZACAO"));
                localizacao.setNome(resultado.getString("NOME_LOCALIZACAO"));
                localizacao.setCapacidade(resultado.getInt("CAPACIDADE_LOCALIZACAO"));
                retorno.add(localizacao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocalizacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    /*public List<Localizacao> buscarCapacidade(Localizacao localizacao) {
        String sql = "SELECT \"CAPACIDADE_LOCALIZACAO\" FROM \"LOCALIZACAO\" WHERE \"NOME_LOCALIZACAO\"='?'";
        List<Localizacao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, localizacao.getNome());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                localizacao.setCapacidade(getInt("CAPACIDADE_LOCALIZACAO"));
                retorno.add(localizacao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocalizacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return retorno;
    }

    public List<Localizacao> buscarQuantidade(Localizacao localizacao) {
        String sql = "SELECT COUNT (\"FK_LOCALIZACAO_ALOCACAO\") FROM \"ALOCACAO\" WHERE \"FK_LOCALIZACAO_ALOCACAO\"= ?";
        List<Localizacao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, localizacao.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                localizacao.setQuantidade(resultado.getInt("count"));
                retorno.add(localizacao);

            }
        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(retorno);
        return retorno;
    }*/

    public List<Localizacao> listarRelatorioQuantidade() {
        String sql = "SELECT COUNT (\"FK_LOCALIZACAO_ALOCACAO\"), \"NOME_LOCALIZACAO\", \"CAPACIDADE_LOCALIZACAO\" FROM \"ALOCACAO\" INNER JOIN \"LOCALIZACAO\" \n"
                + "	ON \"FK_LOCALIZACAO_ALOCACAO\" = \"ID_LOCALIZACAO\"\n"
                + "		GROUP BY (\"FK_LOCALIZACAO_ALOCACAO\",\"NOME_LOCALIZACAO\",\"CAPACIDADE_LOCALIZACAO\");";
        List<Localizacao> retorno = new ArrayList<>();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();
            while (resultado.next()) {
                //Alocacao alocacao = new Alocacao();
                Localizacao localizacao = new Localizacao();

                localizacao.setQuantidade(resultado.getInt("count"));
                localizacao.setNome(resultado.getString("NOME_LOCALIZACAO"));
                localizacao.setCapacidade(resultado.getInt("CAPACIDADE_LOCALIZACAO"));

                retorno.add(localizacao);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlocacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

    public Map<Integer, ArrayList> listarGraficoQuantidade() {
        String sql = "SELECT COUNT (\"FK_LOCALIZACAO_ALOCACAO\"), \"NOME_LOCALIZACAO\", \"CAPACIDADE_LOCALIZACAO\" FROM \"ALOCACAO\" INNER JOIN \"LOCALIZACAO\" \n"
                + "	ON \"FK_LOCALIZACAO_ALOCACAO\" = \"ID_LOCALIZACAO\"\n"
                + "		GROUP BY (\"FK_LOCALIZACAO_ALOCACAO\",\"NOME_LOCALIZACAO\",\"CAPACIDADE_LOCALIZACAO\");";
        Map<Integer, ArrayList> retorno = new HashMap();

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultado = stmt.executeQuery();

            while (resultado.next()) {
                ArrayList linha = new ArrayList();
                linha.add(resultado.getString("NOME_LOCALIZACAO"));
                retorno.put(resultado.getInt("count"), linha);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocalizacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;

    }

    public Localizacao buscar(Localizacao localizacao) {
        String sql = "SELECT * FROM \"LOCALIZACAO\" WHERE \"ID_LOCALIZACAO\"=?";
        Localizacao retorno = new Localizacao();
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, localizacao.getId());
            ResultSet resultado = stmt.executeQuery();
            if (resultado.next()) {
                localizacao.setId(resultado.getInt("ID_LOCALIZACAO"));
                localizacao.setNome(resultado.getString("NOME_LOCALIZACAO"));
                localizacao.setCapacidade(resultado.getInt("CAPACIDADE_LOCALIZACAO"));

                retorno = localizacao;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LocalizacaoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return retorno;
    }

}
