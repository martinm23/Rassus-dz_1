package hr.fer.tel.rassus.dz_1.clients;

import java.util.HashMap;
import java.util.Map;

public class Measurement {

    private final String username;
    private final Map<String, Double> parameterMap;

    public Measurement(final String username) {
        this.username = username;
        this.parameterMap = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public Map<String, Double> getParameterMap() {
        return parameterMap;
    }

    public void addParameter(final String parameter, final double averageValue) {
        parameterMap.put(parameter, averageValue);
    }
}
