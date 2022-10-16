/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business;

import com.entity.Server;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.net.SocketException;

public class ClientThread implements Runnable, Serializable {

    //for I/O
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket socket;
    private Server server;
    private TextArea txtContent;

    /**
     * NOTES: isAvail to check connection established or not -> read/write operation will follow this boolean var
     */
    private Boolean isAvail;

    /**
     * NOTES: btnSend and btnConnect also send to this class for the case that SocketException happens
     * btnSend is disabled and btnConnect is clickable as beginning
     */
    private Button btnSend, btnConnect;


    /*provide setter and getter here*/

    public ClientThread(Server server, TextArea txtContent, Button btnConnect, Button btnSend) {
        /**connect to server and get input/output stream here*/

        try {
            this.txtContent = txtContent;
            this.server = server;
            this.btnConnect = btnConnect;
            this.btnSend = btnSend;
            socket = new Socket(server.getHost(), server.getPort());
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());

            this.isAvail = true;


        } catch (SocketException e) {
            System.out.println("Loi Server");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void run() {
        /*receive message from server and output to txtContent*/
//        try (DataInputStream dis = new DataInputStream(socket.getInputStream());
//        DataOutputStream dos = new DataOutputStream(socket.getOutputStream())) {
////            dis = new DataInputStream(socket.getInputStream());
////            dos = new DataOutputStream(socket.getOutputStream());
//
//            this.dis = dis;
//            this.dos = dos;

        try {

            while (isAvail) {

                Object line = dis.readUTF();
                if (line != null) {
                    txtContent.appendText("\n" + server.getHost() + ": " + line);
                }

            }
        } catch (SocketException r) {

            /** NOTES: catch SocketException to disconnect and close socket
             * return enabled btnConnect and disabled btnSend for next connection */

            txtContent.appendText("\n===== Disconnect from the server, please check connection \n");
            try {
                socket.close();
                this.btnConnect.setDisable(false);
                this.btnSend.setDisable(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void send(Object line) throws Exception {
        /*send a message line to server*/
        if (isAvail) {
            dos.writeUTF(line.toString());
            if (!line.toString().startsWith(":")) {
                txtContent.appendText("\nMe: " + line);
            }
        }

    }


}
