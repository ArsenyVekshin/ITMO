package ArsenyVekshin.lab5.ui;

import ArsenyVekshin.lab5.ui.exeptions.IncorrectLengthException;
import ArsenyVekshin.lab5.ui.exeptions.NotNullException;
import ArsenyVekshin.lab5.ui.exeptions.UnexpectedSymbolsFounded;

import java.util.regex.Pattern;

public class DataFirewall {

    private static final int MAX_STR_LENGTH = 100;
    private static Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");
    private static Pattern patternSymbols = Pattern.compile("^[A-Z][a-z]*(\\\\s(([a-z]{1,3})|(([a-z]+\\\\')?[A-Z][a-z]*)))*$");



    public static String readString(String input){
        return getPattern(input, patternSymbols);
    }

    public static String readNumeric(String input){
        return getPattern(input, patternNumber);
    }

    private static String getPattern(String input, Pattern patternNumber) {
        try {
            if (input.equals("")) throw new NotNullException();
            if (input.length() >= MAX_STR_LENGTH) throw new IncorrectLengthException();
            if (!patternNumber.matcher(input).matches()) throw new UnexpectedSymbolsFounded(input);
            return input.trim();
        } catch (NotNullException | IncorrectLengthException | UnexpectedSymbolsFounded e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String filterInputString(String data){
        if(data.length() >= MAX_STR_LENGTH) return "";
        data = data.trim();
        for (byte i=0; i<data.length(); i++){
            if((int)data.charAt(i) < 32 || (int)data.charAt(i) >= 126) return "";
        }
        return data;
    }
}
