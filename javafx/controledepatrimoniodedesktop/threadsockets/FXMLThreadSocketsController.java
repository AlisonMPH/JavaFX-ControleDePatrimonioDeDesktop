
package javafx.controledepatrimoniodedesktop.threadsockets;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLThreadSocketsController implements Initializable {

    @FXML
    private Label labelConexoes;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Thread whileThread = new Thread(() -> {
            while (true) {
                try {
                    Thread conexao = new Thread(new RunnableConexoesClientes(labelConexoes));
                    conexao.start();
                    Thread.sleep(5000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLThreadSocketsController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        whileThread.start();
    }
}