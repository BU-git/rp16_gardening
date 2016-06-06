package nl.intratuin.handlers;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import nl.intratuin.App;
import nl.intratuin.FingerprintActivity;
import nl.intratuin.LoginActivity;
import nl.intratuin.R;
import nl.intratuin.RegisterActivity;
import nl.intratuin.WebActivity;

public class FingerprintAuthenticationDialog {
    public static final String Fingerprint_FILENAME = "fingerprint";
    private Context context;

    public FingerprintAuthenticationDialog(Context context) {
        this.context = context;
    }

    public void setLockScreenSecurity() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Lock screen security not enabled in Settings")
                .setMessage("To set Lock screen security go to Settings -> (Personal) Security ->")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(dialogIntent);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                context.startActivity(new Intent(context, WebActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, RegisterActivity.responseAccessToken));
                            }
                        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    public void registerFingerprint() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setTitle("Register at least one fingerprint in Settings")
                .setMessage("go to Settings -> (Personal) Security ->")
                .setPositiveButton("Settings", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent dialogIntent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                        dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(dialogIntent);
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                context.startActivity(new Intent(context, WebActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, RegisterActivity.responseAccessToken));
                            }
                        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void confirmToSaveFingerprint() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);
        builder.setView(R.layout.confirm_fingerprint)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        App.getAuthManagerOfFingerprint().loginAndCache(FingerprintActivity.secretKey, LoginActivity.credentials);
                        context.startActivity(new Intent(context, WebActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, RegisterActivity.responseAccessToken));

                        Toast.makeText(context, "login with fingerprint is activated", Toast.LENGTH_LONG).show();
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                context.startActivity(new Intent(context, WebActivity.class).putExtra(LoginActivity.ACCESS_TOKEN, RegisterActivity.responseAccessToken));
                            }
                        });
        android.app.AlertDialog alert = builder.create();
        alert.show();
    }
}
