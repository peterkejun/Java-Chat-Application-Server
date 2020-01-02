package ics4x.isp;

import ics4x.isp.GUI.ServerFrame;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    public static void main(String[] args) {
        // write your code here
        Server server = new Server();
        server.startRunning();
    }

    private Socket socket;
    private ServerSocket serverSocket;
    private ArrayList<ClientThread> clientThreads;
    private int totalClients = 100;
    private int port = 6789;

    private CheckClientThread checkClientThread;

    private ServerFrame serverFrame;

    public Server() {
        //user interface
        serverFrame = new ServerFrame(this);

        //client threads
        clientThreads = new ArrayList<>();
        checkClientThread = new CheckClientThread(this);
        checkClientThread.start();
    }

    synchronized public void sendMessageInPacket(String message) {
        Packet packet = new Packet("Server", message);
        sendPacket(packet);
    }

    synchronized public void sendPacket(Packet packet) {
        if (packet.getContentType() == 1) {
            serverFrame.getChatWindow().addToChatArea(packet.getSendingID(), packet.getTextContent());
        } else if (packet.getContentType() == 2) {
            serverFrame.getChatWindow().addToChatArea(packet.getSendingID(), "sent a file: " + packet.getFile().getName());
        }

        for (ClientThread ct : getClientThreads()) {
            if (ct.getClientID().equals(packet.getSendingID()))
                continue;
            try {
                ct.sendPacketToClient(packet);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }
    }

    public void startRunning()
    {
        try
        {
            serverSocket = new ServerSocket(port, totalClients);
            System.out.println("serverSocket initiated");
            while(true)
            {
                try
                {
                    serverFrame.getChatWindow().setStatus("Waiting for Someone to Connect...");
                    socket = serverSocket.accept();
                    serverFrame.getChatWindow().setStatus(" Now Connected to "+ socket.getInetAddress().getHostName());

                    ClientThread et = new ClientThread(socket, this);
                    getClientThreads().add(et);
                    et.start();
                    System.out.println("one client joined");

                } catch (EOFException eofException) {

                }
            }
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
    }

    synchronized public ArrayList<ClientThread> getClientThreads() {
        return clientThreads;
    }

    public void removeClients(ArrayList<String> clientIDs) {
        for (String clientID : clientIDs) {
            for (ClientThread et : getClientThreads()) {
                if (et.getClientID().equals(clientID)) {
                    System.out.println(clientID + " is off");
                    getClientThreads().remove(et);
                    et.setShouldRun(false);
                    break;
                }
            }
        }
    }

    public void updateActiveClients() {
        ArrayList<String> clientIDs = new ArrayList<>();
        for (ClientThread ct : getClientThreads()) {
            clientIDs.add(ct.getClientID());
        }
        serverFrame.getClientStatusWindow().updateTable(clientIDs);
    }

    public ClientThread getThreadForClientID(String clientID) {
        for (ClientThread ct : getClientThreads()) {
            if (ct.getClientID().equals(clientID)) {
                return ct;
            }
        }
        return null;
    }

}

