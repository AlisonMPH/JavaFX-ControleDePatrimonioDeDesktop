
package javafx.controledepatrimoniodedesktop.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.controledepatrimoniodedesktop.desktop.Desktop;
import javafx.controledepatrimoniodedesktop.model.dao.DesktopDAO;
import javafx.controledepatrimoniodedesktop.model.database.Database;
import javafx.controledepatrimoniodedesktop.model.database.DatabaseFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class FXMLMenuCadastrosDesktopController implements Initializable {
    
    @FXML
    private Button buttonAlterar;

    @FXML
    private Button buttonInserir;

    @FXML
    private Button buttonRemover;

    @FXML
    private Label labelFabricante;

    @FXML
    private Label labelMac;

    @FXML
    private Label labelModelo;

    @FXML
    private Label labelNome;

    @FXML
    private Label labelServiceTag;
    
    @FXML
    private Label labelId;

    @FXML
    private TableColumn<Desktop, Integer> tableColumId;

    @FXML
    private TableColumn<Desktop, String> tableColumModelo;

    @FXML
    private TableColumn<Desktop, String> tableColumNome;

    @FXML
    private TableColumn<Desktop, String> tableColumServicetag;

    @FXML
    private TableView<Desktop> tableView;

    private List<Desktop> listDesktop;
    private ObservableList<Desktop> observableListDesktop;

    private final Database database = DatabaseFactory.getDatabase("postgresql");
    private final Connection connection = database.conectar();
    private final DesktopDAO desktopDAO = new DesktopDAO();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        desktopDAO.setConnection (connection);
        carregarTableViewDesktop();
        selecionarItemTableViewDesktop(null);
        tableView.getSelectionModel().selectedItemProperty().addListener(
        (observable, oldValue, newValue) -> selecionarItemTableViewDesktop(newValue));
    }    
    
    /*@FXML
    public void showFXMLInserirDesktop() throws IOException{
        System.out.println("\nCarregando Tela FXMLInserirDesktop\n");
        Button a = (Button) FXMLLoader.load(getClass().getResource("FXMLAlocarDesktopDialog.fxml"));
        
    }*/
    public void carregarTableViewDesktop() {
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tableColumModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tableColumServicetag.setCellValueFactory(new PropertyValueFactory<>("servicetag"));
        
        listDesktop = desktopDAO.listar();

        observableListDesktop = FXCollections.observableArrayList(listDesktop);
        tableView.setItems(observableListDesktop);
    }
    
    public void selecionarItemTableViewDesktop(Desktop desktop) {
        if (desktop != null) {
            labelId.setText(String.valueOf(desktop.getId()));
            labelNome.setText(desktop.getNome());
            labelModelo.setText(desktop.getModelo());
            labelFabricante.setText(desktop.getFabricante());
            labelServiceTag.setText(desktop.getServicetag());
            labelMac.setText(desktop.getMac());
        } else {
            labelId.setText("");
            labelNome.setText("");
            labelModelo.setText("");
            labelFabricante.setText("");
            labelServiceTag.setText("");
            labelMac.setText("");
        }
    }
    @FXML
    public void handleButtonInserir() throws IOException {
        Desktop desktop = new Desktop();
        boolean buttonConfirmarClicked = showFXMLCadastrosDesktopDialog(desktop);
        if (buttonConfirmarClicked) {
            desktopDAO.inserir(desktop);
            carregarTableViewDesktop();
        }
    }

    @FXML
    public void handleButtonAlterar() throws IOException {
        Desktop desktop = tableView.getSelectionModel().getSelectedItem();//obtendo o desktop selecionado
        if (desktop != null) {
            boolean buttonConfirmarClicked = showFXMLCadastrosDesktopDialog(desktop);
            if (buttonConfirmarClicked) {
                desktopDAO.alterar(desktop);
                carregarTableViewDesktop();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha um desktop na Tabela!");
            alert.show();
        }
    }

    @FXML
    public void handleButtonRemover() throws IOException {
        Desktop desktop = tableView.getSelectionModel().getSelectedItem();
        if (desktop != null) {
            desktopDAO.remover(desktop);
            carregarTableViewDesktop();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Escolha um desktop na Tabela!");
            alert.show();
        }
    }
    
    public boolean showFXMLCadastrosDesktopDialog(Desktop desktop) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(FXMLCadastrosDesktopDialogController.class.getResource("/javafx/controledepatrimoniodedesktop/view/FXMLCadastrosDesktopDialog.fxml"));
        AnchorPane page = (AnchorPane) loader.load();
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Cadastro de Desktop");
        //Especifica a modalidade para esta fase . Isso deve ser feito antes de fazer o estágio visível. A modalidade pode ser: Modality.NONE , Modality.WINDOW_MODAL , ou Modality.APPLICATION_MODAL 
        //dialogStage.initModality(Modality.WINDOW_MODAL);//WINDOW_MODAL (possibilita minimizar)
        
        //Especifica a janela do proprietário para esta página, ou null para um nível superior.
        //dialogStage.initOwner(null); //null deixa a Tela Principal livre para ser movida
        //dialogStage.initOwner(this.tableViewClientes.getScene().getWindow()); //deixa a tela de Preenchimento dos dados como prioritária
        
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);

        //Setando o cliente no Controller.
        FXMLCadastrosDesktopDialogController controller = loader.getController();
        controller.setDialogStage(dialogStage);
        controller.setDesktop(desktop);

        //Mostra o Dialog e espera até que o usuário o feche
        dialogStage.setFocused(true);
        
        dialogStage.showAndWait();
        

        return controller.isButtonConfirmarClicked();
   

    }

}


