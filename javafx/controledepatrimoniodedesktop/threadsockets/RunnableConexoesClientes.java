
package javafx.controledepatrimoniodedesktop.threadsockets;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;


public class RunnableConexoesClientes implements Runnable {

    @FXML
    private final Label labelConexoes;

    public RunnableConexoesClientes(Label labelConexoes) {
        this.labelConexoes = labelConexoes;
    }

    @Override
    public void run() {

        final String ip = "34.125.13.172";
        final Integer port = 12345;
        
        try {
            Socket clientSocket = new Socket(ip, port);
            ObjectInputStream input = new ObjectInputStream(clientSocket.getInputStream());
            Integer atualizar = (Integer) input.readObject();
            Platform.runLater(() -> labelConexoes.setText(String.format("%d", atualizar)));
            System.out.println("Conexoes added");
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(FXMLThreadSocketsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
