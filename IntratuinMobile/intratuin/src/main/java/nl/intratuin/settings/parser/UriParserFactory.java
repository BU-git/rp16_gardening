package nl.intratuin.settings.parser;

import android.content.Context;

import nl.intratuin.settings.BuildType;
import nl.intratuin.settings.parser.impl.*;

/**
 * Created by Иван on 17.05.2016.
 */
public class UriParserFactory {
    public static AbstractUriConfigParser getParserImplementation(BuildType buildType, Context context){
        switch (buildType){
            case DEPLOYED: {return new UriConfigParserDeployedImpl(context);}
            case DEMOAPI:
            case API:{return new UriConfigParserApiImpl(context);}
            default: return new UriConfigParserLocalImpl(context);
        }
    }
}
