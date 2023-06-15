package ArsenyVekshin.lab7.server.commands.exeptions;

import java.io.IOException;

public class WrongCommandArgument extends IOException {
    public WrongCommandArgument(String mes) { super(mes); }
}
