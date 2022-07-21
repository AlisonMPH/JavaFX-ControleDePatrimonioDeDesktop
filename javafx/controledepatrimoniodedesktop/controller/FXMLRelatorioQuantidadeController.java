package javafx.controledepatrimoniodedesktop.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.controledepatrimoniodedesktop.desktop.Alocacao;
import javafx.controledepatrimoniodedesktop.desktop.Desktop;
import javafx.controledepatrimoniodedesktop.desktop.Localizacao;
import javafx.controledepatrimoniodedesktop.desktop.Usuario;
import javafx.controledepatrimoniodedesktop.model.dao.AlocacaoDAO;
import javafx.controledepatrimoniodedesktop.model.dao.DesktopDAO;
import javafx.controledepatrimoniodedesktop.model.dao.LocalizacaoDAO;
import javafx.controledepatrimoniodedesktop.model.dao.UsuarioDAO;
import javafx.controledepatrimoniodedesktop.model.database.Database;
import javafx.controledepatrimoniodedesktop.model.database.DatabaseFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class FXMLRelatorioQuantidadeController implements Initializable {

    @FXML
    private TableView<Localizacao> tableViewRelatorioQuantidade;

    @FXML
    private TableColumn<Localizacao, String> tableColumLocalizacao;

    @FXML
    private TableColumn<Localizacao, String> tableColumQuantidade;

    @FXML
    private TableColumn<Localizacao, Integer> tableColumCapacidade;

    @FXML
    private Button buttonImprimir;

    private List<Localizacao> listLocalizacaoQuantidade;
    private ObservableList<Localizacao> observableListLocalizacaoQuantidade;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final AlocacaoDAO alocacaoDAO = new AlocacaoDAO();
    private final LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final DesktopDAO desktopDAO = new DesktopDAO();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        alocacaoDAO.setConnection(connection);
        localizacaoDAO.setConnection(connection);
        usuarioDAO.setConnection(connection);
        desktopDAO.setConnection(connection);
        carregarTableViewAlocacaoQuantidade();

    }

    public void carregarTableViewAlocacaoQuantidade() {
        
        tableColumQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        tableColumLocalizacao.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumCapacidade.setCellValueFactory(new PropertyValueFactory<>("capacidade"));

        listLocalizacaoQuantidade = localizacaoDAO.listarRelatorioQuantidade();

        observableListLocalizacaoQuantidade = FXCollections.observableArrayList(listLocalizacaoQuantidade);
        tableViewRelatorioQuantidade.setItems(observableListLocalizacaoQuantidade);
    }

    public void handleImprimir() throws JRException {
        URL url = getClass().getResource("/javafx/controledepatrimoniodedesktop/relatorios/QUANTIDADE1.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }

}
