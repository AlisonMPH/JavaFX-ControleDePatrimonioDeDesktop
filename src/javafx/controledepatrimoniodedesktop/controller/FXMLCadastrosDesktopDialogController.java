
package javafx.controledepatrimoniodedesktop.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.controledepatrimoniodedesktop.desktop.Desktop;



public class FXMLCadastrosDesktopDialogController {
    
    @FXML
    private Button buttonConfirmar;

    @FXML
    private Button buttonCancelar;

    @FXML
    private TextField textDesktopNome;

    @FXML
    private TextField textDesktopModelo;

    @FXML
    private TextField textDesktopMac;

    @FXML
    private TextField textDesktopFabricante;

    @FXML
    private TextField textDesktopServiceTag;
    
    private Stage dialogStage;
    private boolean buttonConfirmarClicked = false;
    private Desktop desktop;
    
    public void initialize(URL url, ResourceBundle rb) {
       
    }

    public Stage getDialogStage() {
        return dialogStage;
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public Desktop getdesktop() {
        return this.desktop;
    }

    public void setDesktop(Desktop desktop) {
        this.desktop = desktop;
        this.textDesktopNome.setText(desktop.getNome());
        this.textDesktopFabricante.setText(desktop.getFabricante());
        this.textDesktopServiceTag.setText(desktop.getServicetag());
        this.textDesktopModelo.setText(desktop.getModelo());
        this.textDesktopMac.setText(desktop.getMac());
    }

    public boolean isButtonConfirmarClicked() {
        return buttonConfirmarClicked;
    }

    @FXML
    public void handleButtonConfirmar() {
        if (validarEntradaDeDados()) {
            desktop.setNome(textDesktopNome.getText());
            desktop.setFabricante(textDesktopFabricante.getText());
            desktop.setServicetag(textDesktopServiceTag.getText());
            desktop.setModelo(textDesktopModelo.getText());
            desktop.setMac(textDesktopMac.getText());

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
        String errorMessage = "";

        if (textDesktopNome.getText() == null || textDesktopNome.getText().length() == 0) {
            errorMessage += "Nome inválido!\n";
        }
        if (textDesktopFabricante.getText() == null || textDesktopFabricante.getText().length() == 0) {
            errorMessage += "Fabricante inválido!\n";
        }
        if (textDesktopServiceTag.getText() == null || textDesktopServiceTag.getText().length() == 0) {
            errorMessage += "Service Tag inválido!\n";
        }
        if (textDesktopModelo.getText() == null || textDesktopModelo.getText().length() == 0) {
            errorMessage += "Modelo inválido!\n";
        }
        if (textDesktopMac.getText() == null || textDesktopMac.getText().length() == 0) {
            errorMessage += "Mac inválido!\n";
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Mostrando a mensagem de erro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro no cadastro");
            alert.setHeaderText("Campos inválidos, por favor, corrija...");
            alert.setContentText(errorMessage);
            alert.show();
            return false;
        }
    }

    
}
