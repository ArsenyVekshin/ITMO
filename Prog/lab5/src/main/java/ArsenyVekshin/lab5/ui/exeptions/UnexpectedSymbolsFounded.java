package ArsenyVekshin.lab5.ui.exeptions;

import java.io.IOException;

/**
 * on target string founded symbols from stop-list
 */
public class UnexpectedSymbolsFounded extends IOException {
    public UnexpectedSymbolsFounded(String mes) { super("Founded unexpected symbols on: " + mes); }
}
