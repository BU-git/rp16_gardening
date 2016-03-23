package nl.intratuin.settings;

/**
 * Created by Иван on 19.03.2016.
 */
public class Settings {
    private static boolean useDeployed = false;

    private static String hostLocal="192.168.10.210";//local -- depends on your computer's inner ip
    private static String hostBionic="128.0.169.5";//bionic -- do not change

    public static boolean usingDeployed() { return useDeployed; }
    public static String getHost(){ return useDeployed?hostBionic:hostLocal;}
}