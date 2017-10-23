package hr.fer.tel.tcp;

import hr.fer.tel.web_service.SensorServer;
import hr.fer.tel.web_service.SensorServer_Service;
import hr.fer.tel.web_service.UserAddress;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerWorkerThread extends Thread {

    private final ConnectionManager connectionManager;
    private final SensorServer server;
    private ServerSocket serverSocket;
    private boolean isStopped;
    private String sensorName;
    private UserAddress neighbourSensor;
    private ClientWorkerThread clientWorkerThread;
    private List<Client> connectedClients;

    public ServerWorkerThread(final ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
        this.server = new SensorServer_Service().getSensorServer();
        this.connectedClients = new ArrayList<>();
    }

    @Override
    public void run() {
        isStopped = false;
        try {
            serverSocket = new ServerSocket(0);
            final Location location = connectionManager.getLocation();
            if (register(sensorName, serverSocket.getInetAddress().getHostAddress(), serverSocket.getLocalPort(),
                         location.getLatitude(), location.getLongitude())) {
                System.out.println(sensorName + " successfully registered!");
            } else {
                System.out.println(sensorName + " already registered");
                System.exit(-1);
            }

            neighbourSensor = searchNeighbour(sensorName);
            if (neighbourSensor != null) {
                clientWorkerThread = new ClientWorkerThread(connectionManager, neighbourSensor.getIpAddress(), neighbourSensor.getPort());
                clientWorkerThread.start();
            }
        } catch (IOException e) {
            System.out.println("Failed to open the server socket.");
        }

        System.out.println("Started sensor server on port " + serverSocket.getLocalPort());

        while (!isStopped) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                final Client client = new Client(clientSocket);
                connectedClients.add(client);
            } catch (IOException e) {
                try {
                    clientSocket.close();
                } catch (NullPointerException | IOException e1) {
                    e1.printStackTrace();
                }
                if (isStopped) {
                    break;
                }
                e.printStackTrace();
            }
        }
    }

    public boolean register(final String username, final String ipAddress, final int port,
                            final double latitude, final double longitude) {
        return server.register(username, latitude, longitude, ipAddress, port);
    }

    public UserAddress searchNeighbour(final String name) {
        final UserAddress userAddress = server.searchNeighbour(name);
        if (userAddress != null) {
            System.out.println("Got user address " + userAddress.getUsername() + ": " + userAddress.getIpAddress() + ":" + userAddress.getPort());
        }
        return userAddress;
    }

    public void setSensorName(final String name) {
        sensorName = name;
    }

    public void sendMeasurement(final String parameter, final float value) {
        server.storeMeasurement(sensorName, parameter, value);
    }

    public void exitAllConnections() {
        for (final Client client : connectedClients) {
            System.out.println("Closing connection to client " + client.getUsername());
            client.close();
        }
    }

    public String getSensorName() {
        return sensorName;
    }
}
