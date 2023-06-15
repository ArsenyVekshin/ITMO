package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;

import java.util.ArrayList;

import static ArsenyVekshin.lab7.common.net.UdpManager.addGroupFlag;

public class ShowCollectionCmd extends DataCmd {
    private final int partSize = 10;
    private ArrayList<CommandContainer> queue;
    public ShowCollectionCmd(Storage collection, ArrayList<CommandContainer> queue) {
        super("show", "print collection content", collection);
        this.queue = queue;
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(collection.getElementsCount()==0) cmd.setReturns("collection is empty");
        else{
            int numOfParts = (int)Math.ceil(collection.getElementsCount()/partSize);
            if(numOfParts>1) addGroupFlag(cmd.getSource(), numOfParts);
            for(int i=0; i<numOfParts; i++){
                CommandContainer newCmd = cmd.clone();
                newCmd.setReturns(collection.showPart(i, partSize));
                queue.add(newCmd);
            }
            cmd.setReturns("finished");

        }

        return true;
    }
}
