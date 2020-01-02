package ics4x.isp;

import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {

    private String clientID = "Client";
    private Socket socket = null;
    private Server server = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private ObjectInputStream inputStream = null;
    private ObjectOutputStream outputStream = null;

    private volatile boolean shouldRun = true;

    public ClientThread(Socket clientSocket, Server server) {
        this.socket = clientSocket;
        this.server = server;
    }

    /*
    Message Formats
    Server to Client:
    1. "TEXT - Sending Client ID - Content"
    Client to Server:
    1. "TEXT -
     */


    @Override
    public void run() {

        try {

            outputStream = new ObjectOutputStream(getSocket().getOutputStream());
            outputStream.flush();
            inputStream = new ObjectInputStream(getSocket().getInputStream());
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        while (shouldRun) {
            try {
                Packet packet = (Packet) inputStream.readObject();
                if (packet.getContentType() == 0) {
                    clientID = packet.getSendingID();
                } else {
                    server.sendPacket(packet);
                }
            } catch (IOException | ClassNotFoundException exc) {

            }
        }

        try {
            getSocket().close();
            inputStream.close();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }

    synchronized public void sendPacketToClient(Packet packet) throws IOException {
        outputStream.writeObject(packet);
    }

    synchronized public String getClientID() {
        return clientID;
    }

    synchronized public Socket getSocket() {
        return socket;
    }

    public void setShouldRun(boolean shouldRun) {
        this.shouldRun = shouldRun;
    }

}
