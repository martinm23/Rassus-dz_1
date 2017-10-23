package hr.fer.tel.tcp;

import java.util.Random;

public class Location {

    private static final double LATITUDE_LEFT_BOUNDARY = 45.75;
    private static final double LATITUDE_RIGHT_BOUNDARY = 45.85;

    private static final double LONGITUDE_LEFT_BOUNDARY = 15.87;
    private static final double LONGITUDE_RIGHT_BOUNDARY = 16;

    private Random random = new Random();

    private double latitude;
    private double longitude;

    public Location() {
        this.latitude = (random.nextDouble() * (LATITUDE_RIGHT_BOUNDARY - LATITUDE_LEFT_BOUNDARY) + LATITUDE_LEFT_BOUNDARY);
        this.longitude = (random.nextDouble() * (LONGITUDE_RIGHT_BOUNDARY - LONGITUDE_LEFT_BOUNDARY) + LONGITUDE_LEFT_BOUNDARY);
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
