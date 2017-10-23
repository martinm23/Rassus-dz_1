package hr.fer.tel.tcp;

import java.io.DataInputStream;
import java.io.IOException;

public class ClientListenerThread extends Thread {

    private static final byte REQUEST_BYTE = (byte) 23;
    private final Client client;
    private DataInputStream inputStream;

    public ClientListenerThread(final Client client) {
        this.client = client;

        try {
            inputStream = new DataInputStream(client.getSocket().getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        try {
            byte packetType = inputStream.readByte();
            System.out.println("Accepted request from another sensor.");
            if (packetType == REQUEST_BYTE) {
                final String username = inputStream.readUTF();
                client.setUsername(username);
                client.startGeneratingMeasurements();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
