package javafx.controledepatrimoniodedesktop.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

public class FXMLMenuController implements Initializable {

    @FXML
    private MenuItem menuCadastrosDesktop;

    @FXML
    private MenuItem menuCadastrosAlocar;

    @FXML
    private MenuItem menuMostrarGrafico;

    @FXML
    private MenuItem menuMostrarRelatorio;

    @FXML
    private MenuItem menuMostrarRelatorioQuantidade;

    @FXML
    private AnchorPane anchorPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void showFXMLMenuCadastrosDesktop() throws IOException {
        System.out.println("\nCarregando Tela FXMLMenuCadastrosDesktop\n");
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/controledepatrimoniodedesktop/view/FXMLMenuCadastrosDesktop.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void showFXMLMenuAlocarDesktop() throws IOException {
        System.out.println("\nCarregando Tela FXMLMenuAlocarDesktop\n");
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/controledepatrimoniodedesktop/view/FXMLMenuAlocarDesktop.fxml"));
        anchorPane.getChildren().setAll(a);
    }

    @FXML
    public void showFXMLMenuRelatorio() throws IOException {
        System.out.println("\nCarregando Tela FXMLMenuRelatorio\n");
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/controledepatrimoniodedesktop/view/FXMLRelatorio.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    public void showFXMLMenuRelatorioQuantidade() throws IOException {
        System.out.println("\nCarregando Tela FXMLMenuRelatorioQuantidade\n");
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/controledepatrimoniodedesktop/view/FXMLRelatorioQuantidade.fxml"));
        anchorPane.getChildren().setAll(a);
    }
    @FXML
    public void showFXMLMenuGraficoAlocacao() throws IOException {
        System.out.println("\nCarregando Tela FXMLMenuGraficoAlocacao\n");
        AnchorPane a = (AnchorPane) FXMLLoader.load(getClass().getResource("/javafx/controledepatrimoniodedesktop/view/FXMLGraficoAlocacao.fxml"));
        anchorPane.getChildren().setAll(a);
    }

}
