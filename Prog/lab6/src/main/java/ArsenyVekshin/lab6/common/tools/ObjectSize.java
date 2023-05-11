package ArsenyVekshin.lab6.common.tools;

import java.lang.instrument.Instrumentation;

public class ObjectSize {
    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object obj) {
        return instrumentation.getObjectSize(obj);
    }
}
