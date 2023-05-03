package ArsenyVekshin.lab6.common.net.exeptions;

import java.io.IOException;

/**
 * Access rights for file/dir not enough for work
 */
public class ServerNotAvailable extends IOException {
    public ServerNotAvailable(String arg) { super("адресат " + arg + " недоступен, повторите попытку позднее"); }
}