package business;

import com.entity.Client;
import com.entity.Server;
import controller.ServerBoxController;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class ServerThread implements Runnable, Serializable {

    private Server chatServer;
    private ServerSocket server;
    private Socket socket;
    public static HashMap<String, ClientHandler> clients = new HashMap<>();

    public ServerThread(Server chatServer) {

        this.chatServer = chatServer;
        try {
            server = new ServerSocket(chatServer.getPort());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        while (true) {
            try {
                socket = server.accept();
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                String username = dis.readUTF();
                Client c = new Client();

                if (username != null) {
                    String x = username.substring(username.indexOf(":") + 1);
                    c.setUsername(x);
                    c.setSocket(socket);


                    ClientHandler ch = new ClientHandler(socket, c);

                    /** NOTES: check and update the list view -> must use Platform.runLater due to the change of
                     * listClients affects UI*/
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (ServerBoxController.listClients.indexOf(c.getUsername()) == -1) {
                                ServerBoxController.listClients.add(c.getUsername());
                            }

                        }
                    });

                    clients.put(x, ch);

                }

            } catch (EOFException e) {
//                System.out.println("End of message");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
