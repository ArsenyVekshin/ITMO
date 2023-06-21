package ArsenyVekshin.lab7.client.commands.tasks.parents;

import ArsenyVekshin.lab7.common.builder.ObjTree;
import ArsenyVekshin.lab7.common.ui.InputHandler;
import ArsenyVekshin.lab7.common.ui.OutputHandler;

public abstract class NewObjCmd extends DialogueCmd {

    protected ObjTree tree;
    public NewObjCmd(String name, String description, OutputHandler outputHandler, InputHandler inputHandler, ObjTree tree) {
        super(name, description, outputHandler, inputHandler);
        this.tree = tree;
    }

    public void setTree(ObjTree tree) {
        this.tree = tree;
    }
}
