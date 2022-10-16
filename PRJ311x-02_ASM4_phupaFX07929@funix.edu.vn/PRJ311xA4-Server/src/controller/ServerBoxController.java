package controller;

import business.ClientHandler;
import business.ServerThread;
import com.entity.Client;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class ServerBoxController {

    @FXML
    private ListView<String> clients;

    public static ObservableList<String> listClients = FXCollections.observableArrayList();




    public void initialize() {

        clients.setItems(listClients);

        clients.setOnMouseClicked(mouseEvent -> {
            listClientsMouseClick(mouseEvent);
        });
    }

    private void listClientsMouseClick(MouseEvent evt) {

        /** NOTES: code to handle double to open new window "Chat with ABX"
         * Use if-else control flow with condition isUsingByThread() in order to avoid opening another window
         * for the same client while a "Chat with ABX" window on stage*/
        if (evt.getClickCount() == 2 && clients.getSelectionModel().getSelectedItem() != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/ChatBox.fxml"));
            String clientName = clients.getSelectionModel().getSelectedItem();
            try {

                if (!ServerThread.clients.get(clientName).isUsingByThread()) {
                    Parent root = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Chat with " + clientName);
                    stage.show();

                    ChatBoxController controller = loader.getController();
                    controller.setUserName(clientName);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Not for current");
                    alert.setHeaderText(null);
                    alert.setContentText("This client is in conversation, wait for ending to re-chat");
                    alert.showAndWait();

//                    stage.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
