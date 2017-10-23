package hr.fer.tel.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class MeasurementManager {

    private static final String MEASUREMENT_FILE_PATH = "mjerenja.txt";
    private static final String ACTIVE_SECONDS = "Sensor has been active for: ";
    private static final String MEASUREMENT_INDEX = "Getting measurement number: ";

    private List<SensorMeasurement> sensorMeasurements;
    private long startingTime;

    public MeasurementManager() {
        startingTime = System.currentTimeMillis();
    }

    public void loadFile() {
        try {
            final List<String> fileLines = Files.readAllLines(Paths.get(MEASUREMENT_FILE_PATH));
            sensorMeasurements = MeasurementParser.parseMeasurements(fileLines);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public SensorMeasurement generateMeasurement() {
        final long activeSeconds = System.currentTimeMillis() - startingTime;
        System.out.println(ACTIVE_SECONDS + activeSeconds / 1000 + " seconds.");

        final int index = (int) (activeSeconds % 100 + 2);
        System.out.println(MEASUREMENT_INDEX + index);

        return sensorMeasurements.get(index);
    }

    public SensorMeasurement calculateAverageMeasurement(final SensorMeasurement localSensorMeasurement,
                                                         final SensorMeasurement neighbourSensorMeasurement) {
        final float averageTemperature = average(localSensorMeasurement.getTemperature(), neighbourSensorMeasurement.getTemperature());
        final float averagePressure = average(localSensorMeasurement.getPressure(), neighbourSensorMeasurement.getPressure());
        final float averageHumidity = average(localSensorMeasurement.getHumidity(), neighbourSensorMeasurement.getHumidity());
        final float averageCarbonDioxide = average(localSensorMeasurement.getCarbonDioxide(), neighbourSensorMeasurement.getCarbonDioxide());
        final float averageNitridDioxide = average(localSensorMeasurement.getNitridDioxide(), neighbourSensorMeasurement.getNitridDioxide());
        final float averageSulfurDioxide = average(localSensorMeasurement.getSulfurDioxide(), neighbourSensorMeasurement.getSulfurDioxide());

        return new SensorMeasurement(averageTemperature, averagePressure, averageHumidity,
                                     averageCarbonDioxide, averageNitridDioxide, averageSulfurDioxide);
    }

    private float average(final Float localValue, final Float neighbourValue) {
        return neighbourValue != 0 ? (localValue + neighbourValue) / 2 : localValue;
    }
}
