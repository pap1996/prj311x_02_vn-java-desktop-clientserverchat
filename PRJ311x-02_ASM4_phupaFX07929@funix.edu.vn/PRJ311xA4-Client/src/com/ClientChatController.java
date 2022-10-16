package com;

import business.ClientThread;
import com.entity.Client;
import com.entity.Server;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.ConnectException;
import java.net.Socket;

public class ClientChatController {

    @FXML
    private TextArea txtContent;
    @FXML
    private TextField txtUsername, txtHostIP, txtPort, txtMessage;

    private ClientThread clientThread = null;

    @FXML
    private Button btnConnect, btnSend;

    public void initialize() {

        /** NOTES: Disable button btnSend until connection succeeds*/
        btnSend.setDisable(true);
    }


    public void btnConnectActionPerformed(ActionEvent actionEvent) {

//        if (clientThread == null) {
        try {
            Client c = new Client(txtUsername.getText(), "");
            Server server = new Server(txtHostIP.getText(), Integer.valueOf(txtPort.getText()));


            /** NOTES: try-with-resources with socket to check if server avails
             * -> If not avail, catch ConnectionException and notify user*/
            try (Socket socket = new Socket(server.getHost(), server.getPort())) {
                clientThread = new ClientThread(server, txtContent, btnConnect, btnSend);
                Thread t = new Thread(clientThread);
                t.start();
                clientThread.send(":" + c.getUsername());
                txtContent.appendText("===== Connected to server");
                btnConnect.setDisable(true);
                btnSend.setDisable(false);
            } catch (ConnectException e) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Server Disconnected");
                alert.setContentText("Please check the availability of server");
                alert.setHeaderText(null);
                alert.showAndWait();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        }
    }

    public void btnSendActionPerformed(ActionEvent actionEvent) {

        /** NOTES: if conditionals needed to avoid NullPointerException when sending null*/
        if (txtMessage.getText() != null) {
            try {
                clientThread.send(txtMessage.getText());
                txtMessage.setText(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


}
