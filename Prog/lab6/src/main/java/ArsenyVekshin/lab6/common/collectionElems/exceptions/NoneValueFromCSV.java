package ArsenyVekshin.lab6.common.collectionElems.exceptions;

import java.io.IOException;

/**
 * error while line csv-importing exception
 */
public class NoneValueFromCSV extends IOException {
    public NoneValueFromCSV(String param) { super("csv import error, received string:" + param); }
}
