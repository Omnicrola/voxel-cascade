package com.omnicrola.voxel.input;

import java.util.Comparator;

/**
 * Created by omnic on 1/30/2016.
 */
public class CommandSorter implements Comparator<CommandGroup> {

    @Override
    public int compare(CommandGroup o1, CommandGroup o2) {
        if (o1.priority() > o2.priority()) {
            return 1;
        }
        if (o2.priority() > o1.priority()) {
            return -1;
        }
        return 0;
    }
}
