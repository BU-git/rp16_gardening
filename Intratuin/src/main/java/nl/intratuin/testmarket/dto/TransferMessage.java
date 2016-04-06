package nl.intratuin.testmarket.dto;

public class TransferMessage {
    private String message;

    public TransferMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}