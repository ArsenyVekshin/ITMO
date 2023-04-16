package ArsenyVekshin.lab6.client.commands.tasks;

import ArsenyVekshin.lab6.client.commands.CommandContainer;

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
     * @param cmd parsed cmd
     * @return
     */
    public abstract boolean execute(CommandContainer cmd);

    /**
     * help-print sector
     */
    public abstract void help();
}
