package nl.intratuin.testmarket.util;

import nl.intratuin.testmarket.Credentials;
import nl.intratuin.testmarket.Message;
import nl.intratuin.testmarket.Settings;
import nl.intratuin.testmarket.TransferAccessToken;
import nl.intratuin.testmarket.entity.Customer;
import nl.intratuin.testmarket.service.contract.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.time.LocalDate;

@Component
public class SocialNetworkUtil {

    @Autowired
    CustomerService customerService;

    public Message loginWithTwitter(Credentials credentials) {
        String twitterKey = credentials.getPassword();
        String emailToLogin = credentials.getEmail();
        try {
            if (!twitterKey.equals(Settings.getEncryptedTwitterKey(emailToLogin)))
                return new Message("Wrong Twitter key.");
        } catch(SignatureException e){
            return new Message("Server encryption error");
        }
        Integer foundCustomerId = customerService.findByEmail(emailToLogin);
        if (foundCustomerId != null) {
            return new Message("Login is successful");
        } else {
            Customer newCustomer = new Customer();
            newCustomer.setEmail(emailToLogin);
            customerService.save(newCustomer);
            return new Message("Registration and login is successful.");
        }
    }

    public Message loginWithFacebook(TransferAccessToken accessToken) {
        Facebook facebook = new FacebookTemplate(accessToken.getAccessToken(), "IntratuinMobile", "1720162671574425");
        User profile = facebook.userOperations().getUserProfile();
        String emailToLoginWithFacebook = profile.getEmail();
        if(emailToLoginWithFacebook != null) {
            Integer existedCustomerId = customerService.findByEmail(emailToLoginWithFacebook);

            return existedCustomerId != null
                    ? new Message("Login is successful")
                    : addWithFacebook(profile);
        } else {
            return new Message("We need your email to login you successfully.");
        }
    }

    private Message addWithFacebook(User profile) {
        Customer customer = new Customer();

        customer.setFirstName(profile.getFirstName());
        customer.setLastName(profile.getLastName());
        customer.setEmail(profile.getEmail());
        if(profile.getLocation() != null) {
            customer.setCity( profile.getLocation().getName().toString());
        }

        String birthdayFromFacebook = profile.getBirthday();
        if(birthdayFromFacebook != null) {
            String[] arrForBirthday = birthdayFromFacebook.split("/");
            LocalDate birthdayLocalDate = LocalDate.of(Integer.parseInt(arrForBirthday[2]),
                    Integer.parseInt(arrForBirthday[0]), Integer.parseInt(arrForBirthday[1]));
            java.sql.Date birthday = java.sql.Date.valueOf(birthdayLocalDate);
            customer.setBirthday(birthday);
        }

        String genderFromFacebook = profile.getGender();
        if(genderFromFacebook != null) {
            customer.setGender(genderFromFacebook.equals("male") ? 1 : 0);
        }
        customerService.save(customer);
        return new Message("Registration with Facebook is successful");
    }

}
