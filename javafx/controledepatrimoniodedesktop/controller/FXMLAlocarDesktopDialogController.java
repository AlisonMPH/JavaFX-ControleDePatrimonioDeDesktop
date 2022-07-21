package javafx.controledepatrimoniodedesktop.controller;

import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
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

public class FXMLAlocarDesktopDialogController implements Initializable {

    @FXML
    private Button buttonConfirmar;

    @FXML
    private Button buttonCancelar;

    @FXML
    private ComboBox<Localizacao> comboBoxLocalizacao;

    @FXML
    private ComboBox<Usuario> comboBoxUsuario;

    @FXML
    private ComboBox<Desktop> comboBoxDesktop;

    public static String roubo;

    //Atributos para manipulação de Banco de Dados
    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final LocalizacaoDAO localizacaoDAO = new LocalizacaoDAO();
    private final DesktopDAO desktopDAO = new DesktopDAO();
    private final AlocacaoDAO alocacaoDAO = new AlocacaoDAO();

    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Alocacao alocacao;

    private List<Alocacao> listAlocacao = new ArrayList<>();
    private ObservableList<Alocacao> observableListAlocacao;

    private List<Desktop> listDesktop = new ArrayList<>();
    private ObservableList<Desktop> observableListDesktop;

    private List<Usuario> listUsuario = new ArrayList<>();
    private ObservableList<Usuario> observableListUsuario;

    private List<Localizacao> listLocalizacao = new ArrayList<>();
    private ObservableList<Localizacao> observableListLocalizacao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        alocacaoDAO.setConnection(connection);
        desktopDAO.setConnection(connection);
        usuarioDAO.setConnection(connection);
        localizacaoDAO.setConnection(connection);
        carregarComboBoxDesktop();
        carregarComboBoxLocalizacao();
        carregarComboBoxUsuario();

    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Alocacao getAlocacao() {
        return this.alocacao;
    }

    public void setAlocacao(Alocacao alocacao) {
        this.alocacao = alocacao;
        comboBoxLocalizacao.getSelectionModel().select(alocacao.getLocalizacao());
        comboBoxUsuario.getSelectionModel().select(alocacao.getUsuario());
        comboBoxDesktop.getSelectionModel().select(alocacao.getDesktop());

    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {

        if (validarEntradaDeDados()) {
            alocacao.setDesktop((Desktop) comboBoxDesktop.getSelectionModel().getSelectedItem());
            alocacao.setLocalizacao((Localizacao) comboBoxLocalizacao.getSelectionModel().getSelectedItem());
            alocacao.setUsuario((Usuario) comboBoxUsuario.getSelectionModel().getSelectedItem());
            buttonConfirmarClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    public void handleButtonCancelar() {
        getDialogStage().close();
    }

    //Validar entrada de dados para o cadastro
    private boolean validarEntradaDeDados() {
        boolean val = true;
        String errorMessage = "";
        
        Localizacao localizacao = new Localizacao();
        if (comboBoxDesktop.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione um Desktop!\n";
        }
        if (comboBoxUsuario.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione um Usuario!\n";
        }
        if (comboBoxLocalizacao.getSelectionModel().getSelectedItem() == null) {
            errorMessage += "Selecione uma Localizacao!\n";
        }

        /*if (comboBoxLocalizacao.getSelectionModel().getSelectedItem() < localizacaoDAO.listarRelatorioQuantidade(localizacao.getCapacidade)) {
            errorMessage += "Selecione uma Localizacao!\n";
        }*/
        /*if (validarQuantidadeCapacidade() == false){
            errorMessage += "Capacidade maxima Atingida!\n";
        }*/
 
        if (val == false) {
            if (localizacao.getQuantidade() < localizacao.getCapacidade()) {
                val = true;
            } else {
                System.out.println("Limite de quantidade alcaçada");
                val = false;
            }
        }
        System.out.println(localizacao.getCapacidade());
        System.out.println(localizacao.getQuantidade());
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro na Alocação");
            alert.setHeaderText("Campo(s) não selecionado(s), por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    public void carregarComboBoxDesktop() {
        listDesktop = desktopDAO.listar();

        observableListDesktop = FXCollections.observableArrayList(listDesktop);
        comboBoxDesktop.setItems(observableListDesktop);
    }

    public void carregarComboBoxUsuario() {
        listUsuario = usuarioDAO.listar();

        observableListUsuario = FXCollections.observableArrayList(listUsuario);
        comboBoxUsuario.setItems(observableListUsuario);
    }

    public void carregarComboBoxLocalizacao() {
        listLocalizacao = localizacaoDAO.listar();
        observableListLocalizacao = FXCollections.observableArrayList(listLocalizacao);
        comboBoxLocalizacao.setItems(observableListLocalizacao);

    }

    /*private boolean validarQuantidadeCapacidade() {
        Localizacao localizacao = new Localizacao();

        boolean val;
        if (localizacao.getQuantidade() < localizacao.getCapacidade()) {
            val = true;
        } else {
            System.out.println("Limite de quantidade alcaçada");
            val = false;
        }
        System.out.println(localizacao.getCapacidade());
        System.out.println(localizacao.getQuantidade());

        return val;
    }*/
    private Localizacao validarQuantidadeCapacidade(Localizacao localizacao) {
        
        localizacaoDAO.setConnection(connection);
        List<Localizacao> list = localizacaoDAO.listarRelatorioQuantidade();
                 
        System.out.println(localizacao.getCapacidade());
        System.out.println(localizacao.getQuantidade());

        return localizacao;
    }

}
