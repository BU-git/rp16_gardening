package nl.intratuin.testmarket.dto;

public class DTOFingerprint {
    private int idCustomer;
    private byte[] fingerprint;

    public DTOFingerprint(){}

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public byte[] getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(byte[] fingerprint) {
        this.fingerprint = fingerprint;
    }
}
