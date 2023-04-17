package ArsenyVekshin.lab6.general;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
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
    private InetSocketAddress target;
    private InetSocketAddress source;

    public CommandContainer(){}

    /***
     * Abstract cmd container
     * @param raw raw cmd-string after filter
     */
    public CommandContainer(String raw){
        parse(raw);
    }

    public void parse(String raw){
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

    public void setNetSettings(InetSocketAddress source, InetSocketAddress target){
        this.source = source;
        this.target = target;
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

    public InetSocketAddress getSource() {
        return source;
    }

    public InetSocketAddress getTarget() {
        return target;
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

        if(returns != null) out += "\treturns = " + returns.toString() + "\n";
        if(errors != null) out += "\terrors = " + errors + "\n";

        out += "\tsource = " + source.getAddress() + " (" + source.getPort() + ")\n";
        out += "\ttarget = " + target.getAddress() + " (" + target.getPort() + ")\n";

        return out;
    }
}
