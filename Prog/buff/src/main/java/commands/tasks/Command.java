package commands.tasks;

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

    public abstract boolean execute(String arg);

    public abstract void help();

    protected boolean checkHelpFlag(String arg){
        return arg.contains("-h");
    }



}
