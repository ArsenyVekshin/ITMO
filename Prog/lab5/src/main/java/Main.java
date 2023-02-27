//Вариант 3116003

import collection.Storage;
import utils.builder.ObjTree;

public class Main {

    public static void main(String[] args) {
        try {
            ObjTree aaa = new ObjTree(Storage.class);

        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
}