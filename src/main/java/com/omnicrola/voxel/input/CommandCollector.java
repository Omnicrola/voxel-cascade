package com.omnicrola.voxel.input;

import com.omnicrola.voxel.entities.commands.EntityCommand;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by omnic on 1/30/2016.
 */
public class CommandCollector {
    private final HashMap<EntityCommand, Integer> commandCounters;
    private int totalUnitsSelected;

    public CommandCollector(int totalUnitsSelected) {
        this.totalUnitsSelected = totalUnitsSelected;
        this.commandCounters = new HashMap<>();
    }

    public void collect(EntityCommand command) {
        Integer count = this.commandCounters.get(command);
        if (count == null) {
            count = 0;
        }
        count++;
        this.commandCounters.put(command, count);
    }

    public List<CommandGroup> getCommandsCommonToAllEntities(SelectionGroup selectionGroup, CursorStrategySetter cursorStrategySetter) {
        return this.commandCounters.entrySet()
                .stream()
                .filter(e -> e.getValue() == this.totalUnitsSelected)
                .map(e -> new CommandGroup(selectionGroup, cursorStrategySetter, e.getKey()))
                .sorted(new CommandSorter())
                .collect(Collectors.toList());
    }
}
