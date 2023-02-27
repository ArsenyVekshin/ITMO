package collection.exceptions;

import java.io.IOException;

public class CSVFileBroken extends IOException {
    public CSVFileBroken(String param) { super("CSV database file broken, column with NotNull-tag not exist. file: " + param); }
}
