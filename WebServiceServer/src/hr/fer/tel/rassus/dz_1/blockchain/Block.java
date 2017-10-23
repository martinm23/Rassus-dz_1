package hr.fer.tel.rassus.dz_1.blockchain;

public class Block {

    private final long timeInMillis;
    private final int lastBlockHash;
    private final String username;
    private final String parameter;
    private final float averageValue;

    public Block(final int hash, final String username, final String parameter, final float averageValue) {
        this.timeInMillis = System.currentTimeMillis();
        this.lastBlockHash = hash;
        this.username = username;
        this.parameter = parameter;
        this.averageValue = averageValue;
    }

    public int getLastBlockHash() {
        return lastBlockHash;
    }

    public String getUsername() {
        return username;
    }

    public String getParameter() {
        return parameter;
    }

    public float getAverageValue() {
        return averageValue;
    }
}
