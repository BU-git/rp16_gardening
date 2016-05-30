package nl.intratuin.handlers;


import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.CancellationSignal;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import nl.intratuin.App;
import nl.intratuin.LoginActivity;

@TargetApi(Build.VERSION_CODES.M)
public class FingerprintHandlerLogin extends FingerprintManager.AuthenticationCallback {
    public static String emailByFingerprint;
    public static String passwordByFingerprint;

    private CancellationSignal cancellationSignal;
    private Context appContext;

    public FingerprintHandlerLogin(Context context) {
        appContext = context;
    }

    public void startAuth(FingerprintManager manager,
                          FingerprintManager.CryptoObject cryptoObject) {
        cancellationSignal = new CancellationSignal();

        if (ActivityCompat.checkSelfPermission(appContext,
                Manifest.permission.USE_FINGERPRINT) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationError(int errMsgId, CharSequence errString) {
        Toast.makeText(appContext, "Authentication error\n" + errString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
        Toast.makeText(appContext, "Authentication help\n" + helpString,
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(appContext, "Authentication failed.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        String valueOfFingerprint =  App.getAuthManagerOfFingerprint().getValuesOfFingerprint(LoginActivity.secretKey);
        if (valueOfFingerprint != null && valueOfFingerprint.length() > 0) {
            String[] arrValueOfFingerprint = valueOfFingerprint.split(":");
            emailByFingerprint = arrValueOfFingerprint[0];
            passwordByFingerprint = arrValueOfFingerprint[1];
            ((LoginActivity)appContext).login(appContext, emailByFingerprint, passwordByFingerprint);
        }
    }
}
