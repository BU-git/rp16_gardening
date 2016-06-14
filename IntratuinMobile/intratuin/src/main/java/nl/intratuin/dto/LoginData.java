package nl.intratuin.dto;

import java.util.UUID;

public class LoginData {
    private UUID valueRandom;
    private byte[] publicKey;

    public LoginData(byte[] publicKey) {
        valueRandom = UUID.randomUUID();
        this.publicKey = publicKey;
    }

    public UUID getValueRandom() {
        return valueRandom;
    }

    public void setValueRandom(UUID valueRandom) {
        this.valueRandom = valueRandom;
    }

    public byte[] getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(byte[] publicKey) {
        this.publicKey = publicKey;
    }
}
