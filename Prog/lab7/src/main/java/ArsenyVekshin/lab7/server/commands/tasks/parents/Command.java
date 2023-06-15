package ArsenyVekshin.lab7.server.commands.tasks.parents;

import ArsenyVekshin.lab7.common.CommandContainer;

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

}
