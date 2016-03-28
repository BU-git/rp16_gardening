package nl.intratuin.dto;

public class TransferAccessToken {
    private String accessToken;

    public TransferAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public TransferAccessToken(){}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}