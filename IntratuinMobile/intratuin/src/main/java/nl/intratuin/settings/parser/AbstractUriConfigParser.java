package nl.intratuin.settings.parser;

import android.content.Context;

/**
 * Created by Иван on 17.05.2016.
 */
public abstract class AbstractUriConfigParser {
    protected Context context;

    public AbstractUriConfigParser(Context context) {
        this.context = context;
    }

    public abstract void parseUriConfiguration();
}
