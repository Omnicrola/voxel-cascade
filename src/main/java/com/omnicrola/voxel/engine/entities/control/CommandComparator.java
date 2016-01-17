package com.omnicrola.voxel.engine.entities.control;

import com.omnicrola.voxel.engine.entities.commands.IEntityCommand;

import java.util.Comparator;

/**
 * Created by omnic on 1/16/2016.
 */
public class CommandComparator implements Comparator<IEntityCommand> {

    @Override
    public int compare(IEntityCommand command1, IEntityCommand command2) {
        final int priority1 = command1.priority();
        final int priority2 = command2.priority();
        if (priority1 > priority2) {
            return 1;
        }
        if (priority1 < priority2) {
            return -1;
        }
        return 0;
    }

}
