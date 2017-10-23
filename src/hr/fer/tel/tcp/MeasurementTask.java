package hr.fer.tel.tcp;

import hr.fer.tel.util.MeasurementManager;
import hr.fer.tel.util.SensorMeasurement;

import java.io.DataOutputStream;
import java.io.IOException;

public class MeasurementTask implements Runnable {

    private final Client client;
    private final MeasurementManager measurementManager;

    public MeasurementTask(final Client client) {
        this.client = client;
        this.measurementManager = new MeasurementManager();
        this.measurementManager.loadFile();
    }

    @Override
    public void run() {
        if (client.getSocket().isConnected()) {

            try {
                final DataOutputStream outputStream = client.getOutputStream();
                final SensorMeasurement sensorMeasurement = measurementManager.generateMeasurement();

                System.out.println("Generated measurements: " + sensorMeasurement.toString());
                System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------");
                System.out.println();

                outputStream.writeFloat(sensorMeasurement.getTemperature());
                outputStream.writeFloat(sensorMeasurement.getPressure());
                outputStream.writeFloat(sensorMeasurement.getHumidity());
                outputStream.writeFloat(sensorMeasurement.getCarbonDioxide());
                outputStream.writeFloat(sensorMeasurement.getNitridDioxide());
                outputStream.writeFloat(sensorMeasurement.getSulfurDioxide());
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
