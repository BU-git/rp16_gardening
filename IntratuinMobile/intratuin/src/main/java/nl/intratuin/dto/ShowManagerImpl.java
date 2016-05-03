package nl.intratuin.dto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import nl.intratuin.R;

/**
 * The standard ShowManager implementation with "Intratuin" title
 *
 * @see ShowManager
 */
public class ShowManagerImpl implements ShowManager {

    /**
     * Show an alert message.
     *
     * @param message
     * @param context
     * @see ShowManager
     */
    @Override
    public void showMessage(String message, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.app_name)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
