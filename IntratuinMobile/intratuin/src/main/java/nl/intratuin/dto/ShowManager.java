package nl.intratuin.dto;

import android.content.Context;

/**
 * Interface {@code ShowManager} is a messaging service
 */
public interface ShowManager {
    /**
     * Show an alert message.
     *
     * @param message alert message
     * @param context application context
     */
    void showMessage(String message, Context context);
}
