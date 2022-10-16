package controller;

import business.ClientHandler;
import business.ServerThread;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ChatBoxController {


    private String username;
    private ClientHandler cs;
    @FXML
    private TextArea txtContent;
    @FXML
    private Button btnSend;
    @FXML
    private TextField txtMessage;
    @FXML
    private AnchorPane chatBox;

    private Stage stage;

    public void initialize() {
        btnSend.setOnAction(actionEvent -> {
            btnSendActionPerformed(actionEvent);
        });


    }

    private void btnSendActionPerformed(ActionEvent actionEvent) {

        String text = txtMessage.getText();
        if (text != null) {
            try {
                cs.send(text);
                txtMessage.setText(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void setUserName(String clientName) {


        this.username = clientName;
        cs = ServerThread.clients.get(username);
        cs.setTxtContent(txtContent);
        cs.setIsUsingbyThread(true);


        Thread t = new Thread(cs);
        t.setName(clientName);
        t.start();

        stage = (Stage) chatBox.getScene().getWindow();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
//                cs.setIsRunning(false);
                cs.setIsUsingbyThread(false);
//                t.interrupt();
            }
        });


    }


}
