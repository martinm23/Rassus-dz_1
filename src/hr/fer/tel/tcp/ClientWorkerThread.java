package hr.fer.tel.tcp;

import hr.fer.tel.util.MeasurementManager;
import hr.fer.tel.util.SensorMeasurement;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientWorkerThread extends Thread {

    private static final String TEMPERATURE = "temperature";
    private static final String PRESSURE = "pressure";
    private static final String HUMIDITY = "humidity";
    private static final String CARBON_DIOXIDE = "CO2";
    private static final String NITRID_DIOXIDE = "NO2";
    private static final String SULFUR_DIOXIDE = "SO2";

    private static final byte REQUEST_BYTE = (byte) 23;
    private final ConnectionManager connectionManager;
    private final String ipAddress;
    private final int port;
    private final MeasurementManager measurementManager;
    private boolean isStopped;

    public ClientWorkerThread(final ConnectionManager connectionManager,
                              final String ipAddress, final int port) {
        this.connectionManager = connectionManager;
        this.ipAddress = ipAddress;
        this.port = port;
        this.measurementManager = new MeasurementManager();
        this.measurementManager.loadFile();
    }

    @Override
    public void run() {
        isStopped = false;

        try (Socket clientSocket = new Socket(ipAddress, port)) {
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());

            outputStream.write(REQUEST_BYTE);
            outputStream.writeUTF(connectionManager.getServerThread().getSensorName());
            outputStream.flush();

            while (!isStopped) {
                final float temperature = inputStream.readFloat();
                final float pressure = inputStream.readFloat();
                final float humidity = inputStream.readFloat();
                final float carbonDioxide = inputStream.readFloat();
                final float nitridDioxide = inputStream.readFloat();
                final float sulfurDioxide = inputStream.readFloat();

                final SensorMeasurement neighbourMeasurement = new SensorMeasurement(temperature, pressure, humidity,
                                                                                     carbonDioxide, nitridDioxide, sulfurDioxide);
                System.out.println("Neighbour sensor measured: " + neighbourMeasurement.toString());
                final SensorMeasurement localMeasurement = measurementManager.generateMeasurement();
                final SensorMeasurement averageMeasurement = measurementManager.calculateAverageMeasurement(localMeasurement,
                                                                                                            neighbourMeasurement);
                final ServerWorkerThread serverWorkerThread = connectionManager.getServerThread();

                serverWorkerThread.sendMeasurement(TEMPERATURE, averageMeasurement.getTemperature());
                serverWorkerThread.sendMeasurement(PRESSURE, averageMeasurement.getPressure());
                serverWorkerThread.sendMeasurement(HUMIDITY, averageMeasurement.getHumidity());
                serverWorkerThread.sendMeasurement(CARBON_DIOXIDE, averageMeasurement.getCarbonDioxide());
                serverWorkerThread.sendMeasurement(NITRID_DIOXIDE, averageMeasurement.getNitridDioxide());
                serverWorkerThread.sendMeasurement(SULFUR_DIOXIDE, averageMeasurement.getSulfurDioxide());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
