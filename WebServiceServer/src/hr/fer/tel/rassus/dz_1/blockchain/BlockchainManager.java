package hr.fer.tel.rassus.dz_1.blockchain;

import hr.fer.tel.rassus.dz_1.clients.Measurement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BlockchainManager {

    private List<Block> blockchain;

    public BlockchainManager() {
        blockchain = new ArrayList<>();
    }

    public boolean append(final String username, final String parameter, final float averageValue) {
        Block newBlock = null;
        if (blockchain.size() != 0) {
            final Block lastBlock = peekLast();
            newBlock = new Block(lastBlock.hashCode(), username, parameter, averageValue);
        } else {
            newBlock = new Block(0, username, parameter, averageValue);
        }

        blockchain.add(newBlock);
        System.out.println("Sensor " + newBlock.getUsername() + " measured " + newBlock.getParameter() + " with value " +
                               newBlock.getAverageValue() + ".");
        return true;
    }

    public Block peekLast() {
        return blockchain.get(blockchain.size() - 1);
    }

    public Set<Measurement> getState() {
        final Set<Measurement> measurements = new HashSet<>();
        for (final Block block : blockchain) {
            if (!measurementExists(block, measurements)) {
                final Measurement measurement = new Measurement(block.getUsername());
                measurement.addParameter(block.getParameter(), block.getAverageValue());
                measurements.add(measurement);
            }
        }
        return measurements;
    }

    private boolean measurementExists(final Block block, final Set<Measurement> measurements) {
        for (final Measurement measurement : measurements) {
            final String measurementSensor = measurement.getUsername();

            if (block.getUsername().equals(measurementSensor)) {
                measurement.addParameter(block.getParameter(), block.getAverageValue());
                return true;
            }
        }
        return false;
    }
}
