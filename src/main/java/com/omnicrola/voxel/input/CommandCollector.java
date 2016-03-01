package com.omnicrola.voxel.input;

import com.omnicrola.voxel.entities.commands.IEntityCommand;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by omnic on 1/30/2016.
 */
public class CommandCollector {
    private final HashMap<IEntityCommand, Integer> commandCounters;
    private int totalUnitsSelected;

    public CommandCollector(int totalUnitsSelected) {
        this.totalUnitsSelected = totalUnitsSelected;
        this.commandCounters = new HashMap<>();
    }

    public void collect(IEntityCommand command) {
        Integer count = this.commandCounters.get(command);
        if (count == null) {
            count = 0;
        }
        count++;
        this.commandCounters.put(command, count);
    }

    public List<CommandGroup> getCommandsCommonToAllEntities(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
        return this.commandCounters.entrySet()
                .stream()
                .filter(e -> e.getValue() == this.totalUnitsSelected)
                .map(e -> new CommandGroup(selectionGroup, cursorCommandAdaptor, e.getKey()))
                .sorted(new CommandSorter())
                .collect(Collectors.toList());
    }
}
