package nl.intratuin.dto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class ShowManagerImpl implements ShowManager{
    private Context context;

    public ShowManagerImpl (Context context) {
        this.context = context;
    }

    @Override
    public void showMessage(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Intratuin")
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
