package ics4x.isp;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

public class Packet implements Serializable {

    private String sendingID;
    private String textContent = "";
    private File file = new File("");
    private ArrayList<String> activePeers = new ArrayList<>();

    private int contentType = 1;
    /*
    Content Type:
    0 : id
    1 : text (default)
    2 : file
    3 : image
    4 : active peers
     */

    public Packet(String sendingID) {
        this.sendingID = sendingID;
        this.contentType = 0;
    }

    public Packet(String sendingID, String textContent) {
        this.sendingID = sendingID;
        this.textContent = textContent;
        this.contentType = 1;
    }

    public Packet(String sendingID, File file) {
        this.sendingID = sendingID;
        this.file = file;
        this.contentType = 2;
    }

    public Packet(String sendingID, ArrayList<String> activePeers) {
        this.sendingID = sendingID;
        this.activePeers.addAll(activePeers);
        this.contentType = 4;
    }

    public String getSendingID() {
        return sendingID;
    }

    public String getTextContent() {
        return textContent;
    }

    public File getFile() {
        return file;
    }

    public int getContentType() {
        return contentType;
    }

    public ArrayList<String> getActivePeers() {
        return activePeers;
    }

}
