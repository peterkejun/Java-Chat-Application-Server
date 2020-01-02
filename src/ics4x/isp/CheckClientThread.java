package ics4x.isp;

import java.io.IOException;
import java.util.ArrayList;

public class CheckClientThread extends Thread {

    private Server server;

    private ArrayList<String> activeClientsIDs;

    public CheckClientThread(Server server) {
        this.server = server;
        activeClientsIDs = new ArrayList<>();
    }

    @Override
    public void run() {
        while (true) {
            activeClientsIDs.clear();
            for (ClientThread ct : server.getClientThreads()) {
                activeClientsIDs.add(ct.getClientID());
            }

            Packet packet = new Packet("Server", activeClientsIDs);

            ArrayList<String> clientsToRemove = new ArrayList<>();
            for (ClientThread et : server.getClientThreads()) {
                try {
                    et.sendPacketToClient(packet);
                } catch (IOException e) {
                    clientsToRemove.add(et.getClientID());
                }
            }

            server.removeClients(clientsToRemove);

            server.updateActiveClients();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
