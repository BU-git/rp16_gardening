package nl.intratuin.handlers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * Class {@code ErrorFragment} is used for creating error messages.
 *
 * @author Ivan
 * @see DialogFragment
 * @since 25.03.2016.
 */
//we don't need this class. or just for DialogFragment?
public class ErrorFragment extends DialogFragment {
    private String message = "";

    /**
     * New error error fragment.
     *
     * @param message the message
     * @return the error fragment
     */
    //why don't we just instantiating a new object?
    public static ErrorFragment newError(String message) {
        ErrorFragment ef = new ErrorFragment();
        ef.setMessage(message);
        return ef;
    }

    /**
     * Sets error message.
     *
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Creating dialog with error message
     *
     * @param savedInstanceState
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setMessage(message)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }
                )
                .create();

    }
}
