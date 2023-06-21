package ArsenyVekshin.lab7.server.commands.tasks;

import ArsenyVekshin.lab7.common.CommandContainer;
import ArsenyVekshin.lab7.common.net.UdpManager;
import ArsenyVekshin.lab7.server.collection.Storage;
import ArsenyVekshin.lab7.server.commands.tasks.parents.DataCmd;

import java.util.ArrayList;

import static ArsenyVekshin.lab7.common.net.UdpManager.addGroupFlag;
import static ArsenyVekshin.lab7.common.tools.DebugPrints.debugPrintln;

public class ShowCollectionCmd extends DataCmd {
    private final int partSize = 10;
    private UdpManager udpManager;
    public ShowCollectionCmd(Storage collection, UdpManager udpManager) {
        super("show", "print collection content", collection);
        this.udpManager = udpManager;
    }

    @Override
    public boolean execute(CommandContainer cmd) {
        if(collection.getElementsCount()==0) cmd.setReturns("collection is empty");
        else{
            int numOfParts = (int)Math.ceil((float)collection.getElementsCount()/partSize);
            //if(numOfParts>1) addGroupFlag(cmd.getSource(), numOfParts);
            for(int i=0; i<numOfParts; i++){
                CommandContainer newCmd = cmd.clone();
                newCmd.setReturns(collection.showPart(i, partSize));
                debugPrintln("send " + newCmd.toString());
                udpManager.addCallBack(newCmd);
            }
            cmd.setReturns("finished");

        }

        return true;
    }
}
