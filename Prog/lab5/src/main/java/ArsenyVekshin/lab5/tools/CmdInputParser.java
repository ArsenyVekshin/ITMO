package ArsenyVekshin.lab5.tools;

public class CmdInputParser {
    static final String commentSeparator = "#";
    static final String cmdPartsSeparator = " ";

    public static String getCmdKey(String input){
        return input.substring(0, input.indexOf(" "));
    }

    public static String getCmdComment(String input){
        return input.substring(input.indexOf(commentSeparator));
    }

    public static String[] getCmdArgs(String input){
        return input.substring(input.indexOf(" "), input.indexOf(commentSeparator)).split(cmdPartsSeparator);
    }


}
