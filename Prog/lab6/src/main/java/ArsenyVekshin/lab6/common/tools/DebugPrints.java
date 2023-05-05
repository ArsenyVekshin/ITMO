package ArsenyVekshin.lab6.common.tools;

/**
 * Static multi-type comparators
 */
public class DebugPrints {
    static boolean DEBUGMODE = false;

    public static void debugPrint(String s){
        if(!DEBUGMODE) return;
        System.out.print("DEBUG: ");
        System.out.print(s);
    }
    public static void debugPrint0(String s){
        if(!DEBUGMODE) return;
        System.out.print(s);
    }
    public static void debugPrintln(String s){
        if(!DEBUGMODE) return;
        debugPrint(s);
        System.out.println();
    }
    public static void debugPrintln0(String s){
        if(!DEBUGMODE) return;
        debugPrint0(s);
        System.out.println();
    }
}
