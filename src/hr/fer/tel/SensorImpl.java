package hr.fer.tel;

import hr.fer.tel.tcp.ConnectionManager;

public class SensorImpl implements Sensor {

    private final ConnectionManager connectionManager;

    public SensorImpl() {
        connectionManager = new ConnectionManager();
    }

    @Override
    public void start(final String name) {
        connectionManager.start(name);
    }

    @Override
    public void stop() {
        connectionManager.stop();
    }
}
