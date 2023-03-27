package ArsenyVekshin.lab5.tools;

import com.sun.jdi.Type;

/**
 * Static multi-type comparators
 */
public class Comparators {

    /**
     * Custom string comparator
     * @param s1 string 1
     * @param s2 string 2
     * @return
     */
    private static int compareStrings(String s1, String s2) {
        if (s1 == s2) return 0;
        for (int i = 0; i < s1.length() && i < s2.length(); i++) {
            if (s1.charAt(i) > s2.charAt(i)) return 1;
            if (s1.charAt(i) < s2.charAt(i)) return -1;
        }
        return 0;
    }

    /**
     * Comparator for any primitive
     * @param f1 obj 1
     * @param f2 obj 2
     * @return
     */
    public static int compareFields(Object f1, Object f2) {
        if (f1.getClass().equals(String.class)) {
            String a = (String) f1;
            String b = (String) f2;
            return compareStrings(a, b);
        }
        if (f1.getClass() == Number.class) {
            if(f1.getClass().equals(Float.class) ||
                    f1.getClass().equals(Double.class)){
                float a = (float) f1;
                float b = (float) f2;

                if (a > b) return 1;
                if (a < b) return -1;
            }  else {
                long a = (long) f1;
                long b = (long) f2;

                if (a > b) return 1;
                if (a < b) return -1;
            }
        }
        return 0;
    }
}
