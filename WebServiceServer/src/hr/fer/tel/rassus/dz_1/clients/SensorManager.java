package hr.fer.tel.rassus.dz_1.clients;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SensorManager {

    private static final int R = 6371;

    private List<UserAddress> sensors;

    public SensorManager() {
        this.sensors = new ArrayList<>();
    }

    public boolean addSensor(final UserAddress userAddress) {
        if (sensors.contains(userAddress)) {
            System.err.println("> " + userAddress.getUsername() + " is already registered!");
            return false;
        } else {
            sensors.add(userAddress);
            System.out.println("> " + userAddress.getUsername() + " is successfully registered!");
            return true;
        }
    }

    public void removeSensor(final String username) {
        final Optional<UserAddress> removableUserAddress = getOptionalUserAddress(username);

        if (removableUserAddress.isPresent()) {
            final UserAddress userAddress = removableUserAddress.get();
            sensors.remove(userAddress);
        }
    }

    public UserAddress findNeighbour(final String username) {
        final Optional<UserAddress> currentSensor = getOptionalUserAddress(username);

        return currentSensor.isPresent() ? calculateClosestNeighbour(currentSensor.get()) : null;
    }

    private UserAddress calculateClosestNeighbour(final UserAddress currentUserAddress) {
        final int sensorsCount = sensors.size();

        if (sensorsCount == 1) {
            return null;
        }

        double minDifference = calculateDifference(currentUserAddress, sensors.get(0));
        int indexOfMinDifference = 0;

        for (int i = 0; i < sensorsCount; i++) {
            if (!sensors.get(i).equals(currentUserAddress)) {
                double currentDifference = calculateDifference(currentUserAddress, sensors.get(i));

                if (currentDifference < minDifference) {
                    minDifference = currentDifference;
                    indexOfMinDifference = i;
                }
            }
        }

        return sensors.get(indexOfMinDifference);
    }

    private double calculateDifference(final UserAddress currentUserAddress, final UserAddress neighbourUserAddress) {
        final double currentSensorLatitude = currentUserAddress.getLatitude();
        final double currentSensorLongitude = currentUserAddress.getLongitude();

        final double neighbourSensorLatitude = neighbourUserAddress.getLatitude();
        final double neighbourSensorLongitude = neighbourUserAddress.getLongitude();


        final double diffLongitude = Math.abs(currentSensorLongitude - neighbourSensorLongitude);
        final double diffLatitude = Math.abs(currentSensorLatitude - neighbourSensorLatitude);

        final double a = Math.pow(Math.sin(Math.toRadians(diffLatitude / 2)), 2) + Math.cos(Math.toRadians(currentSensorLatitude)) *
            Math.cos(Math.toRadians(neighbourSensorLatitude)) * Math.pow(Math.sin(Math.toRadians(diffLongitude / 2)), 2);

        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return R * c;
    }

    public Optional<UserAddress> getOptionalUserAddress(final String username) {
        return sensors.stream()
            .filter(userAddress -> userAddress.getUsername().equals(username))
            .findFirst();
    }


}
