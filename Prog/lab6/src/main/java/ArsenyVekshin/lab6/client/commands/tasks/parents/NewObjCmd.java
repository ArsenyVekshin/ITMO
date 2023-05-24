package ArsenyVekshin.lab6.client.commands.tasks.parents;

import ArsenyVekshin.lab6.common.ui.InputHandler;
import ArsenyVekshin.lab6.common.ui.OutputHandler;
import ArsenyVekshin.lab6.client.utils.builder.ObjTree;

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
