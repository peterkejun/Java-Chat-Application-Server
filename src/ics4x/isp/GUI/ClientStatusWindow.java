package ics4x.isp.GUI;

import ics4x.isp.Server;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ClientStatusWindow extends JPanel {

    private Server server;

    private JTable table;

    public ClientStatusWindow(Server server) {
        this.server = server;
        initComponents();
        this.setVisible(false);
    }

    private void initComponents() {

        this.setLayout(null);

        JLabel clientStatusLabel = new JLabel("Client Status");
        clientStatusLabel.setForeground(Color.black);
        clientStatusLabel.setBounds(10, 10, 200, 30);
        this.add(clientStatusLabel);

        table = new JTable();
        table.setPreferredScrollableViewportSize(new Dimension(200, 300));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 50, 250, 350);
        this.add(scrollPane);

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(300, 425);
    }

    public void updateTable(ArrayList<String> clientIDs) {
        DefaultTableModel dtm = new DefaultTableModel(0, 1);
        for (String clientID : clientIDs) {
            if (clientID.equals("Client")) {
                continue;
            }
            dtm.addRow(new Object[] {clientID});
        }
        table.setModel(dtm);
    }

}
