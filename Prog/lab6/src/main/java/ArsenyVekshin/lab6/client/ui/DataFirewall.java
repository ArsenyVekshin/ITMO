package ArsenyVekshin.lab6.client.ui;

import ArsenyVekshin.lab6.client.ui.exeptions.IncorrectLengthException;
import ArsenyVekshin.lab6.client.ui.exeptions.NotNullException;
import ArsenyVekshin.lab6.client.ui.exeptions.UnexpectedSymbolsFounded;

import java.util.regex.Pattern;

/**
 * input-strings filter
 */
public class DataFirewall {

    private static final int MAX_STR_LENGTH = 300;
    private static Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static Pattern patternSymbols = Pattern.compile("^[A-Z][a-z]*(\\\\s(([a-z]{1,3})|(([a-z]+\\\\')?[A-Z][a-z]*)))*$");


    /**
     * Get string by default string-pattern
     * @param input raw string
     * @return filtered string
     */
    public static String readString(String input){
        return getPattern(input, patternSymbols);
    }

    /**
     * Get string by default numeric-pattern
     * @param input raw string
     * @return filtered string
     */
    public static String readNumeric(String input){
        return getPattern(input, patternNumber);
    }

    /**
     * Get string by pattern
     * @param input raw string
     * @param patternNumber pattern
     * @return  filtered string
     */
    private static String getPattern(String input, Pattern patternNumber) {
        try {
            if (input.equals("")) throw new NotNullException();
            if (input.length() >= MAX_STR_LENGTH) throw new IncorrectLengthException();
            if (!patternNumber.matcher(input).matches()) throw new UnexpectedSymbolsFounded(input);
            return input.trim();
        } catch (NotNullException | IncorrectLengthException | UnexpectedSymbolsFounded e) {
            System.out.println(e.getMessage());//e.printStackTrace();
        }
        return "";
    }

    /**
     * get string after default-filter
     * @param data raw string
     * @return filtered string
     */
    public static String filterInputString(String data){
        if(data.length() >= MAX_STR_LENGTH) return "";
        data = data.trim();
        for (byte i=0; i<data.length(); i++){
            if((int)data.charAt(i) < 32 || (int)data.charAt(i) >= 126) return "";
        }
        return data;
    }
}
