package ics4x.isp.GUI;

import ics4x.isp.Server;

import javax.swing.*;
import java.awt.*;

public class ServerFrame extends JFrame {

    private Server server;

    private ChatWindow chatWindow;

    private ClientStatusWindow clientStatusWindow;

    public ServerFrame(Server server) {
        this.server = server;

        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        chatWindow = new ChatWindow(server);
        chatWindow.setVisible(true);
        this.add(chatWindow, BorderLayout.EAST);

        clientStatusWindow = new ClientStatusWindow(server);
        clientStatusWindow.setVisible(true);
        this.add(clientStatusWindow, BorderLayout.WEST);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
    }

    public ChatWindow getChatWindow() {
        return chatWindow;
    }

    public ClientStatusWindow getClientStatusWindow() {
        return clientStatusWindow;
    }

}
