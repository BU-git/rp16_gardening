package nl.intratuin.manager.contract;

public interface IAccessProvider {
    String getCredentials();
    String getSecretKey();
}
