package hr.fer.tel.rassus.dz_1.clients;

public class UserAddress {

    private String username;
    private String ipAddress;
    private int port;
    private double latitude;
    private double longitude;

    public UserAddress() {
    }

    public UserAddress(String username, String ipAddress, int port, double latitude, double longitude) {
        this.username = username;
        this.ipAddress = ipAddress;
        this.port = port;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        return username != null ? username.hashCode() : 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UserAddress that = (UserAddress) o;

        return username != null ? username.equals(that.username) : that.username == null;
    }
}
