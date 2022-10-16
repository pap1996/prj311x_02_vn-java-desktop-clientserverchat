package business;

import com.entity.Client;
import javafx.scene.control.TextArea;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private Client client;
    private DataInputStream dis;
    private DataOutputStream dos;
    private TextArea txtContent;

    /** NOTES: isUsingByThread is to check whether clienthandler is used in Chat with ABX window
     * If yes -> avoid opening new Chat with ABX window for the same client
     * If no -> allow to open a new Chat with ABX window */
    private boolean isUsingByThread = false;


    public ClientHandler(Socket socket, Client c) {
        this.socket = socket;
        this.client = c;

        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            /**/
        }

    }

    public boolean isUsingByThread() {
        return this.isUsingByThread;
    }


    @Override
    public void run() {


        /** Synchronise DataInputStream to ensure that when closing and re-open Chat with ABX window, server can send data
         * to client on the same stream (thread still hold lock of the DIS dis)
         * */
        synchronized (dis) {
            try {

//                while (true && isUsingByThread) {
//                    System.out.println(isUsingByThread);
//                    Object line = dis.readUTF();
//                    System.out.println(line);
//
//                    if (line != null) {
//                        txtContent.appendText("\n" + client.getUsername() + ": " + line);
//                    }
//                }
                do {
                    System.out.println("Before - " + isUsingByThread);
                    Object line = dis.readUTF();
                    System.out.println(line);
                    System.out.println("After - " + isUsingByThread);


                    if (line != null) {
                        txtContent.appendText("\n" + client.getUsername() + ": " + line);
                    }
                } while (isUsingByThread);
                /** NOTES: isUsingByThread in the while loop to make sure that when client types something
                 * while chatbox in server close, they will not
                 * be appended to txtContent and be unavailable later - when open chatbox */
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }


    public void send(Object line ) throws Exception {

            dos.writeUTF(line.toString());

        txtContent.appendText("\nMe: " + line);
    }

    public void setTxtContent(TextArea txtContent) {
        this.txtContent= txtContent;
    }

    public void setIsUsingbyThread(boolean b) {
        this.isUsingByThread = b;
    }



}
