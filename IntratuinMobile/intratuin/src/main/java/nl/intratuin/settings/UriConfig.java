package nl.intratuin.settings;

import java.net.URI;

/**
 * Created by Иван on 17.05.2016.
 */
public class UriConfig {
    private URI login;
    private URI registration;
    private URI twitterLogin;
    private URI facebookLogin;
    private URI categoryList;
    private URI productSearch;
    private URI userInfo;
    private URI search;
    private URI productsInCategory;
    private URI barcode;
    private URI customerByToken;
    private URI customerPersonal;
    private URI registerFingerprint;
    private URI categoryName;

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

    public URI getTwitterLogin() {
        return twitterLogin;
    }

    public void setTwitterLogin(URI twitterLogin) {
        this.twitterLogin = twitterLogin;
    }

    public URI getFacebookLogin() {
        return facebookLogin;
    }

    public void setFacebookLogin(URI facebookLogin) {
        this.facebookLogin = facebookLogin;
    }

    public URI getCategoryList() {
        return categoryList;
    }

    public void setCategoryList(URI categoryList) {
        this.categoryList = categoryList;
    }

    public URI getProductSearch() {
        return productSearch;
    }

    public void setProductSearch(URI productSearch) {
        this.productSearch = productSearch;
    }

    public URI getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(URI userInfo) {
        this.userInfo = userInfo;
    }

    public URI getSearch() {
        return search;
    }

    public void setSearch(URI search) {
        this.search = search;
    }

    public URI getProductsInCategory() {
        return productsInCategory;
    }

    public void setProductsInCategory(URI productsInCategory) {
        this.productsInCategory = productsInCategory;
    }

    public URI getBarcode() {
        return barcode;
    }

    public void setBarcode(URI barcode) {
        this.barcode = barcode;
    }

    public URI getCustomerByToken() {
        return customerByToken;
    }

    public void setCustomerByToken(URI customerByToken) {
        this.customerByToken = customerByToken;
    }

    public URI getCustomerPersonal() {
        return customerPersonal;
    }

    public void setCustomerPersonal(URI customerPersonal) {
        this.customerPersonal = customerPersonal;
    }

    public URI getRegisterFingerprint() {
        return registerFingerprint;
    }

    public void setRegisterFingerprint(URI registerFingerprint) {
        this.registerFingerprint = registerFingerprint;
    }

    public URI getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(URI categoryName) {
        this.categoryName = categoryName;
    }
}
