package javafx.controledepatrimoniodedesktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.controledepatrimoniodedesktop.desktop.Localizacao;
import javafx.controledepatrimoniodedesktop.model.dao.LocalizacaoDAO;
import javafx.controledepatrimoniodedesktop.model.database.Database;
import javafx.controledepatrimoniodedesktop.model.database.DatabaseFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class FXMLGraficoAlocacaoController implements Initializable {

    @FXML
    private BarChart<String, Integer> barChart;

    @FXML
    private CategoryAxis categoryAxis;

    @FXML
    private NumberAxis numberAxis;

    private List<Localizacao> listQuantidade;
    private ObservableList<Localizacao> observableListQuantidade;

    private List<Localizacao> listCapacidade;
    private ObservableList<Localizacao> observableListCapacidade;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();

    private final LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();

    //private ObservableList<Localizacao> observableListLocalizacaoQuantidade = FXCollections.observableArrayList();
    //private ObservableList<Localizacao> observableListLocalizacaoCapacidade = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        localizacaoDAO.setConnection(connection);
        List<Localizacao> list = localizacaoDAO.listarRelatorioQuantidade();

        XYChart.Series<String, Integer> series = new XYChart.Series<>();

        for (Localizacao l : list) {
            series.getData().add(new XYChart.Data<>(l.getNome(), l.getQuantidade()));

        }
        barChart.getData().add(series);
    }

}
