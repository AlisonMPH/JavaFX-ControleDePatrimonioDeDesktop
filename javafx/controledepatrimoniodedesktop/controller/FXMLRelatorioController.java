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

public class FXMLRelatorioController implements Initializable {

    @FXML
    private ComboBox<Localizacao> comboBoxLocalizacao;

    @FXML
    private Label labelQuantidade;

    @FXML
    private Button buttonImprimir;

    @FXML
    private TableColumn<Desktop, String> tableColumDesktop;

    @FXML
    private TableColumn<Localizacao, String> tableColumLocalizacao;

    @FXML
    private TableColumn<Desktop, String> tableColumMac;

    @FXML
    private TableColumn<Desktop, String> tableColumModelo;

    @FXML
    private TableColumn<Desktop, String> tableColumServicetag;

    @FXML
    private TableColumn<Usuario, String> tableColumUsuario;

    @FXML
    private TableView<Alocacao> tableViewRelatorio;

    private List<Alocacao> listAlocacao;
    private ObservableList<Alocacao> observableListAlocacao;

    private List<Localizacao> listLocalizacao;
    private ObservableList<Localizacao> observableListLocalizacao;

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
        carregarTableViewAlocacao();

    }

    public void carregarTableViewAlocacao() {

        tableColumDesktop.setCellValueFactory(new PropertyValueFactory<>("desktop"));
        tableColumLocalizacao.setCellValueFactory(new PropertyValueFactory<>("localizacao"));
        tableColumUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        listAlocacao = alocacaoDAO.listarRelatorio();

        observableListAlocacao = FXCollections.observableArrayList(listAlocacao);
        tableViewRelatorio.setItems(observableListAlocacao);

    }

    public void handleImprimir() throws JRException {
        URL url = getClass().getResource("/javafx/controledepatrimoniodedesktop/relatorios/CONTROLE4.jasper");
        JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, connection);//null: caso não existam filtros
        JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);//false: não deixa fechar a aplicação principal
        jasperViewer.setVisible(true);
    }

    /*public void carregarComboBoxLocalizacao() {

        listLocalizacao = localizacaoDAO.listar();

        observableListLocalizacao = FXCollections.observableArrayList(listLocalizacao);
        comboBoxLocalizacao.setItems(observableListLocalizacao);
        comboBoxLocalizacao.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemComboBoxLocalizacao(newValue));

        //Como não será possível alterar uma Venda, não precisaremos implementar a seleção do cliente (caso seja uma alteração de venda)
    }*/

   
}
