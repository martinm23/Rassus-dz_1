package hr.fer.tel.util;

import java.util.ArrayList;
import java.util.List;

public class MeasurementParser {

    private static final String LINE_SEPARATOR = ",";

    public static List<SensorMeasurement> parseMeasurements(final List<String> fileLines) {
        final List<SensorMeasurement> sensorMeasurements = new ArrayList<>();
        fileLines.remove(0);

        fileLines.forEach(line -> {
            final String[] splittedMeasurement = line.split(LINE_SEPARATOR);
            SensorMeasurement sensorMeasurement = null;
            if (splittedMeasurement.length == 6) {
                sensorMeasurement = new SensorMeasurement(castToNumber(splittedMeasurement[0]), castToNumber(splittedMeasurement[1]),
                                                                                  castToNumber(splittedMeasurement[2]), castToNumber(splittedMeasurement[3]),
                                                                                  castToNumber(splittedMeasurement[4]), castToNumber(splittedMeasurement[5]));
            } else {
                sensorMeasurement = new SensorMeasurement(castToNumber(splittedMeasurement[0]), castToNumber(splittedMeasurement[1]),
                                                          castToNumber(splittedMeasurement[2]), castToNumber(splittedMeasurement[3]),
                                                          castToNumber(splittedMeasurement[4]), null);
            }
            sensorMeasurements.add(sensorMeasurement);
        });

        return sensorMeasurements;
    }

    private static Float castToNumber(String s) {
        return s.equals("") ? null : Float.parseFloat(s);
    }
}
