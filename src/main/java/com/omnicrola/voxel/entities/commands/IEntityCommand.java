package com.omnicrola.voxel.entities.commands;

import com.omnicrola.voxel.input.CommandGroup;
import com.omnicrola.voxel.input.CursorStrategySetter;
import com.omnicrola.voxel.input.SelectionGroup;

import java.util.List;

/**
 * Created by Eric on 1/30/2016.
 */
public interface IEntityCommand {
    String getIcon();

    String getName();

    int priority();

    List<CommandGroup> execute(SelectionGroup selectionGroup, CursorStrategySetter cursorStrategySetter);
}
