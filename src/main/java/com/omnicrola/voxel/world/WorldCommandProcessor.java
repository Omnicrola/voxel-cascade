package com.omnicrola.voxel.world;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldCommandProcessor {

    private final ArrayList<IWorldCommand> commands;
    private CommandPackage commandPackage;

    public WorldCommandProcessor(CommandPackage commandPackage) {
        this.commandPackage = commandPackage;
        this.commands = new ArrayList<>();
    }

    public void addCommand(IWorldCommand worldCommand) {
        this.commands.add(worldCommand);
    }

    public void execute(long tick) {
        Iterator<IWorldCommand> iterator = this.commands.iterator();
        while (iterator.hasNext()) {
            IWorldCommand command = iterator.next();
            if (command.getTargetTic() <= tick) {
                command.execute(commandPackage);
                iterator.remove();
            }
        }
    }
}
