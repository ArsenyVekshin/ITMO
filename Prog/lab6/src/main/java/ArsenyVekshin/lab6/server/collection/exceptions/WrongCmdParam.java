package ArsenyVekshin.lab6.server.collection.exceptions;

import java.io.IOException;

/**
 * cmd arg no valid exception
 */
public class WrongCmdParam extends IOException {
    public WrongCmdParam(String data) { super("Неправильный формат ввода команды: " + data + "попробуйте ввести команду с ключом -h"); }
}
