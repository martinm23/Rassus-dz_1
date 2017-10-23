package hr.fer.tel.tcp;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Client {

    private static final int PERIOD = 5000;
    private final Socket socket;
    private final ClientListenerThread clientListenerThread;
    private DataOutputStream outputStream;
    private String username;

    public Client(final Socket clientSocket) throws IOException {
        this.socket = clientSocket;
        this.outputStream = new DataOutputStream(socket.getOutputStream());
        this.clientListenerThread = new ClientListenerThread(this);
        this.clientListenerThread.start();
    }

    public Socket getSocket() {
        return socket;
    }

    public void startGeneratingMeasurements() {
        final MeasurementTask measurementTask = new MeasurementTask(this);
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(measurementTask, 1, 10, TimeUnit.SECONDS);
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
