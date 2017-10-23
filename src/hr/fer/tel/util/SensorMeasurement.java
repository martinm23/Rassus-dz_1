package hr.fer.tel.util;

public class SensorMeasurement {

    private Float temperature;
    private Float pressure;
    private Float humidity;
    private Float carbonDioxide;
    private Float nitridDioxide;
    private Float sulfurDioxide;

    public SensorMeasurement(Float temperature, Float pressure, Float humidity, Float carbonDioxide, Float nitridDioxide, Float sulfurDioxide) {
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.carbonDioxide = carbonDioxide;
        this.nitridDioxide = nitridDioxide;
        this.sulfurDioxide = sulfurDioxide;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getPressure() {
        return pressure;
    }

    public void setPressure(Float pressure) {
        this.pressure = pressure;
    }

    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getCarbonDioxide() {
        return carbonDioxide != null ? carbonDioxide : 0f;
    }

    public void setCarbonDioxide(Float carbonDioxide) {
        this.carbonDioxide = carbonDioxide;
    }

    public Float getNitridDioxide() {
        return nitridDioxide != null ? nitridDioxide : 0f;
    }

    public void setNitridDioxide(Float nitridDioxide) {
        this.nitridDioxide = nitridDioxide;
    }

    public Float getSulfurDioxide() {
        return sulfurDioxide != null ? sulfurDioxide : 0f;
    }

    public void setSulfurDioxide(Float sulfurDioxide) {
        this.sulfurDioxide = sulfurDioxide;
    }

    @Override
    public String toString() {
        return "| Temperature: " + temperature + " |   | Pressure: " + pressure + " |   | Humidity: " + humidity + " |   | Carbon dioxide: " + getCarbonDioxide() +
            " |   | Nitrid dioxide: " + getNitridDioxide() + " |   | Sulfur dioxide: " + getSulfurDioxide() + " |";
    }
}
