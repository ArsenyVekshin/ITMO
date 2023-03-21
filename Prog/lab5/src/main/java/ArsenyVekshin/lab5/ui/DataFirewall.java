package ArsenyVekshin.lab5.ui;

import ArsenyVekshin.lab5.ui.exeptions.IncorrectLengthException;
import ArsenyVekshin.lab5.ui.exeptions.NotNullException;
import ArsenyVekshin.lab5.ui.exeptions.UnexpectedSymbolsFounded;
import java.util.regex.Pattern;

public abstract class DataFirewall {

    private final int MAX_STR_LENGTH = 100;
    private Pattern patternNumber = Pattern.compile("-?\\d+(\\.\\d+)?");
    private Pattern patternSymbols = Pattern.compile("^[A-Z][a-z]*(\\\\s(([a-z]{1,3})|(([a-z]+\\\\')?[A-Z][a-z]*)))*$");



    public String readString(String input){
        return getPattern(input, patternSymbols);
    }

    public String readNumeric(String input){
        return getPattern(input, patternNumber);
    }

    private String getPattern(String input, Pattern patternNumber) {
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
}
