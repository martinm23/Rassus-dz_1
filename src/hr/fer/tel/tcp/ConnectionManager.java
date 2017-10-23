package hr.fer.tel.tcp;

public class ConnectionManager {

    private final Location location;
    private final ServerWorkerThread serverWorkerThread;

    public ConnectionManager() {
        this.location = new Location();
        this.serverWorkerThread = new ServerWorkerThread(this);
    }

    public void start(final String name) {
        this.serverWorkerThread.setSensorName(name);
        this.serverWorkerThread.run();
    }

    public Location getLocation() {
        return location;
    }

    public ServerWorkerThread getServerThread() {
        return serverWorkerThread;
    }

    public void stop() {
        serverWorkerThread.exitAllConnections();
    }
}
