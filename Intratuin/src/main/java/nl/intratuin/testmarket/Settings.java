package nl.intratuin.testmarket;

/**
 * Created by Иван on 25.03.2016.
 */
public class Settings {
    // Note: Parameters should be obfuscated in your source code before shipping.
    private static final String twitter_par1 = "CpdWZOr27su9Vduhipt3XVUgD";
    private static final String twitter_par2 = "pcOrskD4hWcUGxi5n3nMvtL3bArhIuyj2pmiMCQ7E9jj71wH5w";

    public static String getTwitterKey(){
        return twitter_par1+twitter_par2;
    }
}
