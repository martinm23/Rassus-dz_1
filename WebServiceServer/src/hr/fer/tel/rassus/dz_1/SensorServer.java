package hr.fer.tel.rassus.dz_1;

import hr.fer.tel.rassus.dz_1.blockchain.BlockchainManager;
import hr.fer.tel.rassus.dz_1.blockchain.ScheduledStateTask;
import hr.fer.tel.rassus.dz_1.clients.SensorManager;
import hr.fer.tel.rassus.dz_1.clients.UserAddress;

import java.util.Timer;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService(name = "SensorServer")
public class SensorServer {

    private static final int PERIOD = 10000;
    private static SensorManager sensorManager;
    private static BlockchainManager blockchainManager;

    public static void main(String[] argv) {
        final Object implementor = new SensorServer();
        final String address = "http://localhost:9000/SensorServer";

        sensorManager = new SensorManager();
        blockchainManager = new BlockchainManager();

        ScheduledStateTask scheduledStateTask = new ScheduledStateTask(blockchainManager);
        final Timer timer = new Timer();
        timer.schedule(scheduledStateTask, PERIOD);

        Endpoint.publish(address, implementor);
    }

    @WebMethod(operationName = "register")
    public boolean register(final String username, final double latitude, final double longitude,
                            final String ipAddress, final int port) {
        final UserAddress userAddress = new UserAddress(username, ipAddress, port, latitude, longitude);
        System.out.println("> Trying to register " + username + " which is situated on location: [" + latitude + "," + longitude +
                                "] and his TCP credentials are: " + ipAddress + ":" + port);
        return sensorManager.addSensor(userAddress);
    }

    @WebMethod(operationName = "searchNeighbour")
    public UserAddress searchNeighbour(final String username) {
        final UserAddress userAddress = sensorManager.findNeighbour(username);
        if (userAddress != null) {
            System.out.println("Got user address " + userAddress.getUsername() + ": " + userAddress.getIpAddress() + ":" + userAddress.getPort());
        }
        return userAddress;
    }

    @WebMethod(operationName = "storeMeasurement")
    public boolean storeMeasurement(final String username, final String parameter, final float averageValue) {
        return blockchainManager.append(username, parameter, averageValue);
    }

    @WebMethod(operationName = "unregister")
    public void unregister(final String username) {
        sensorManager.removeSensor(username);
    }
}