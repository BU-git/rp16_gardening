package nl.intratuin.testmarket;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.SignatureException;

/**
 * Created by Иван on 25.03.2016.
 */
public class Settings {
    // Note: Parameters should be obfuscated in your source code before shipping.
    private static final String twitter_par1 = "CpdWZOr27su9Vduhipt3XVUgD";
    private static final String twitter_par2 = "pcOrskD4hWcUGxi5n3nMvtL3bArhIuyj2pmiMCQ7E9jj71wH5w";

    public static String getEncryptedTwitterKey(String key) throws SignatureException {
        String value=twitter_par1+twitter_par2;
        String result;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // base64-encode the hmac
            byte[] encoded=java.util.Base64.getEncoder().encode(rawHmac);
            result=new String(encoded);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result;
    }
}
