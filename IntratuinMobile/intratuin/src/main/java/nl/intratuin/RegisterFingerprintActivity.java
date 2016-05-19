package nl.intratuin;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ExecutionException;

import nl.intratuin.dto.Customer;
import nl.intratuin.dto.LoginData;
import nl.intratuin.net.RequestResponse;

@TargetApi(Build.VERSION_CODES.M)
public class RegisterFingerprintActivity extends AppCompatActivity {
    public static final String KEY_NAME = "fingerprint_key";
    private byte[] publicKey;

    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private KeyStore keyStore;
    private KeyPairGenerator keyPairGenerator;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (!keyguardManager.isKeyguardSecure()) {
            Toast.makeText(this, "Lock screen security not enabled in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Fingerprint authentication permission not enabled",
                    Toast.LENGTH_LONG).show();
            return;
        }

        if (!fingerprintManager.hasEnrolledFingerprints()) {
            Toast.makeText(this, "Register at least one fingerprint in Settings",
                    Toast.LENGTH_LONG).show();
            return;
        }

        generateAsymmetricKey();
        registerFingerprint(toByteArray(createPublicKey()));
    }

    private void generateAsymmetricKey() {
        try {
            keyPairGenerator = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
            keyPairGenerator.initialize(new KeyGenParameterSpec.Builder(KEY_NAME, KeyProperties.PURPOSE_SIGN)
                    .setDigests(KeyProperties.DIGEST_SHA256)
                    .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                    .setUserAuthenticationRequired(false)
                    .build());
        } catch (InvalidAlgorithmParameterException | NoSuchProviderException
                | NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to get KeyGenerator instance", e);
        }
        keyPairGenerator.generateKeyPair();
        Log.i("generate pair key", " +");
    }

    private PublicKey createPublicKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            PublicKey publicKey = keyStore.getCertificate(KEY_NAME).getPublicKey();
            KeyFactory factory = KeyFactory.getInstance(publicKey.getAlgorithm());
            X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKey.getEncoded());
            Log.i("createPublicKey", " +");
            return factory.generatePublic(spec);
        } catch (KeyStoreException | NoSuchAlgorithmException | IOException | CertificateException | InvalidKeySpecException e) {
            throw new RuntimeException("Failed to get public keys", e);
        }
    }

    public static  <T> byte[] toByteArray(T objects) {
        try (ByteArrayOutputStream outByte = new ByteArrayOutputStream();
             ObjectOutputStream outObject = new ObjectOutputStream(outByte)) {
            outObject.writeObject(objects);
            return outByte.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Serialization ERROR: ", e);
        }
    }

    public static <T> T toObject(byte[] data) {
        try (ByteArrayInputStream inByte = new ByteArrayInputStream(data);
             ObjectInputStream is = new ObjectInputStream(inByte);) {
            return (T) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Deserialization ERROR: ", e);
        }
    }

    private void registerFingerprint(byte[] publicKey) {
        URI updateURI = null;
        try {
            updateURI = new URI(BuildConfig.API_HOME + "customer/register/fingerprint");

            AsyncTask<byte[], Void, Boolean> jsonUpdateRespond =
                    new RequestResponse<byte[], Boolean>(updateURI, 3,
                            Boolean.class, App.getShowManager(), this).execute(publicKey);
            if (jsonUpdateRespond != null) {
                if (jsonUpdateRespond.get()) {
                    Toast.makeText(this, "you personal data is saved", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(this, SearchActivity.class));
                }
                else
                    App.getShowManager().showMessage("Sorry, error saving, try again", this);
            } else
                App.getShowManager().showMessage("Error! Null response!", this);
        } catch (URISyntaxException | InterruptedException | ExecutionException e) {
            App.getShowManager().showMessage("Exception!" + e.getMessage(), this);
        }

    }
}
