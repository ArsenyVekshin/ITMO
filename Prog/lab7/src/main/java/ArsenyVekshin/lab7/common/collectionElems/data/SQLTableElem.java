package ArsenyVekshin.lab7.common.collectionElems.data;

import java.util.HashMap;

public interface SQLTableElem {

    String genValuesLine();

    void parseValuesLine(HashMap<String, Object> values);
}
