package ArsenyVekshin.lab7.common.ui.exeptions;

import java.io.IOException;

/**
 * Access rights for file/dir not enough for work
 */
public class FileAccessRightsException extends IOException {
    public FileAccessRightsException(String arg) { super("Выбранный файл недоступен для " + arg + " - проверьте настройку прав"); }
}