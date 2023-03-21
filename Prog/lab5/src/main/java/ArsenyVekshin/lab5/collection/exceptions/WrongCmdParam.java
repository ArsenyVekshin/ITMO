package ArsenyVekshin.lab5.collection.exceptions;

import java.io.IOException;

public class WrongCmdParam extends IOException {
    public WrongCmdParam(String data) { super("Неправильный формат ввода команды: " + data + "попробуйте ввести команду с ключом -h"); }
}
