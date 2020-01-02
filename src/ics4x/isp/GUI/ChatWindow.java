package ics4x.isp.GUI;

import ics4x.isp.Server;

import javax.swing.*;
import java.awt.*;

public class ChatWindow extends JPanel {

    private Server server;

    private JTextArea chatArea;
    private JTextField inputTextField;
    private JLabel statusLabel;

    public ChatWindow(Server server) {
        this.server = server;
        initComponents();
        this.setVisible(false);
        statusLabel.setVisible(true);
    }

    private void initComponents() {


        JPanel panel = new JPanel();
        JScrollPane scrollPane = new JScrollPane();
        chatArea = new JTextArea();
        inputTextField = new JTextField();
        JButton sendButton = new JButton();
        statusLabel = new JLabel();
        JLabel promptLabel = new JLabel();

        panel.setLayout(null);

        chatArea.setColumns(20);
        chatArea.setRows(5);
        scrollPane.setViewportView(chatArea);

        panel.add(scrollPane);
        scrollPane.setBounds(30, 110, 360, 270);

        inputTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                server.sendMessageInPacket(inputTextField.getText());
                inputTextField.setText("");
            }
        });
        panel.add(inputTextField);
        inputTextField.setBounds(30, 50, 270, 30);

        sendButton.setText("Send");
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                server.sendMessageInPacket(inputTextField.getText());
                inputTextField.setText("");
            }
        });
        panel.add(sendButton);
        sendButton.setBounds(310, 50, 80, 30);

        statusLabel.setText("...");
        panel.add(statusLabel);
        statusLabel.setBounds(30, 80, 300, 40);

        promptLabel.setText("Write your text here");
        panel.add(promptLabel);
        promptLabel.setBounds(30, 30, 150, 20);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(417, 425));
    }

    public void setStatus(String status) {
        statusLabel.setText(status);
    }

    public void addToChatArea(String sendingID, String message) {
        chatArea.append(sendingID + " - " + message + "\n");
    }

}
