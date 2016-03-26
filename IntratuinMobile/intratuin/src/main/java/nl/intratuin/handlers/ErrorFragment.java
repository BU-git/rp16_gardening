package nl.intratuin.handlers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Created by Иван on 25.03.2016.
 */
    public class ErrorFragment extends DialogFragment {
        private String message="";

        public static ErrorFragment newError(String message){
            ErrorFragment ef=new ErrorFragment();
            ef.setMessage(message);
            return ef;
        }
        public void setMessage(String message) { this.message=message; }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) { }
                        }
                )
                .create();

    }
}
