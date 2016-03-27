package nl.intratuin.settings;

import android.util.Base64;

import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.security.SignatureException;

import javax.crypto.spec.SecretKeySpec;
import javax.crypto.Mac;

/**
 * Created by Иван on 19.03.2016.
 */
public class Settings {
    // Note: Parameters should be obfuscated in your source code before shipping.
    private static final String twitter_par1 = "CpdWZOr27su9Vduhipt3XVUgD";
    private static final String twitter_par2 = "pcOrskD4hWcUGxi5n3nMvtL3bArhIuyj2pmiMCQ7E9jj71wH5w";

    private static boolean useDeployed = false;

    private static int connectionTimeout=3000;

    private static String hostLocal="192.168.1.23";//local -- depends on your computer's inner ip
    private static String hostBionic="128.0.169.5";//bionic -- do not change

    public static boolean usingDeployed() { return useDeployed; }
    public static String getHost(){ return useDeployed?hostBionic:hostLocal;}
    public static TwitterAuthConfig getTwitterConfig() {
        return new TwitterAuthConfig(twitter_par1,twitter_par2);
    }
    public static String getEncryptedTwitterKey(String key) throws SignatureException{
        String value=twitter_par1+twitter_par2;
        String result="";
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), "HmacSHA1");

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // base64-encode the hmac
            result = Base64.encodeToString(rawHmac,Base64.DEFAULT);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
        return result.substring(0,result.length()-1);
    }

    public static int getConnectionTimeout() { return connectionTimeout; }
}