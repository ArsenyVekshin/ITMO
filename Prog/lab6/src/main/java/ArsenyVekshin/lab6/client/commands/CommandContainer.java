package ArsenyVekshin.lab6.client.commands;

import java.io.Serializable;
import java.util.ArrayList;

/***
 * Abstract cmd container class
 */
public class CommandContainer implements Serializable {
    private String type = null;
    private ArrayList<String> args = null;
    private ArrayList<String> keys = null;
    private Object returns = null;
    private String errors = null;

    public CommandContainer(){}

    /***
     * Abstract cmd container
     * @param raw raw cmd-string after filter
     */
    public CommandContainer(String raw){
        if(raw.isEmpty()) return;
        String data[] = raw.split(" ");
        type = data[0];
        for(String s: data){
            if(s.isBlank()) continue;
            if(s.charAt(0) == '-')
                for(byte i=1; i<s.length(); i++) keys.add(String.valueOf(s.charAt(i)));
            else args.add(s);
        }
    }

    public String getType() {
        return type;
    }

    public ArrayList<String> getArgs() {
        return args;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public Object getReturns() {
        return returns;
    }

    public void setReturns(Object returns) {
        this.returns = returns;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        String out = "cmd type = " + type +"\n";

        out += "\tkeys = [";
        for(String s: keys) out += " " + s;
        out += "]\n";

        out += "\targs = [";
        for(String s: args) out += " " + s;
        out += "]\n";

        if(returns != null) out += "\treturns = " + returns.toString();
        if(errors != null) out += "\terrors = " + errors;

        return out;
    }
}
