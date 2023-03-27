package ArsenyVekshin.lab5.ui.exeptions;

import java.io.IOException;

/**
 * target stream isn't available
 */
public class StreamBrooked extends IOException {
    public StreamBrooked() { super("Ошибка вывода в поток: поток не существует"); }
}
