package nl.intratuin.settings;

import java.net.URI;

/**
 * Created by Иван on 17.05.2016.
 */
public class UriConfig {
    private URI login;
    private URI registration;
    private URI userInfo;
    private URI barcode;

    public URI getLogin() {
        return login;
    }

    public void setLogin(URI login) {
        this.login = login;
    }

    public URI getRegistration() {
        return registration;
    }

    public void setRegistration(URI registration) {
        this.registration = registration;
    }

    public URI getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(URI userInfo) {
        this.userInfo = userInfo;
    }

    public URI getBarcode() {
        return barcode;
    }

    public void setBarcode(URI barcode) {
        this.barcode = barcode;
    }
}
