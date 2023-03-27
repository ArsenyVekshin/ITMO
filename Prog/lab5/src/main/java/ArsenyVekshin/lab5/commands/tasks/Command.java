package ArsenyVekshin.lab5.commands.tasks;

public abstract class Command {
    private String name;

    private String description;

    public Command(String name, String description){
        this.name = name;
        this.description = description;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * execution call for cmd
     * @param args parsed cmd
     * @return
     */
    public abstract boolean execute(String[] args);

    /**
     * help-print sector
     */
    public abstract void help();

    protected boolean checkHelpFlag(String[] args){
        for (String arg : args){
            if(arg.contains("-h")) return true;
        }
        return false;
    }



}
