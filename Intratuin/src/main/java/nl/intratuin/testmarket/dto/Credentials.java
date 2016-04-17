package nl.intratuin.testmarket.dto;

public class Credentials {
    private String password;
    private String email;
    private boolean flagToCache;

    public Credentials() {
    }

    public boolean getFlagToCache() {
        return flagToCache;
    }

    public void setFlagToCache(boolean flagToCache) {
        this.flagToCache = flagToCache;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
