package hr.fer.tel.rassus.dz_1.blockchain;

import hr.fer.tel.rassus.dz_1.clients.Measurement;

import java.util.Set;
import java.util.TimerTask;

public class ScheduledStateTask extends TimerTask {

    private final BlockchainManager blockchainManager;

    public ScheduledStateTask(final BlockchainManager blockchainManager) {
        this.blockchainManager = blockchainManager;
    }

    @Override
    public void run() {
        final Set<Measurement> state = blockchainManager.getState();
        for (final Measurement measurement : state) {
            System.out.println("");
            System.out.println("-----------------------------------------------------------------");
            System.out.println("Sensor " + measurement.getUsername() + " latest data:");
            measurement.getParameterMap().forEach((s, aDouble) -> System.out.println("| " + s + " : " + aDouble + " |"));
        }
    }
}
