package com.omnicrola.voxel.input;

import com.omnicrola.voxel.entities.commands.IEntityCommand;

import java.util.List;

/**
 * Created by omnic on 1/30/2016.
 */
public class CommandGroup {
    private SelectionGroup selectionGroup;
    private CursorCommandAdaptor cursorCommandAdaptor;
    private IEntityCommand command;

    public CommandGroup(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor, IEntityCommand command) {
        this.selectionGroup = selectionGroup;
        this.cursorCommandAdaptor = cursorCommandAdaptor;
        this.command = command;
    }

    public List<CommandGroup> execute() {
        return this.command.execute(this.selectionGroup, this.cursorCommandAdaptor);
    }

    public int priority() {
        return this.command.priority();
    }

    public String getName() {
        return this.command.getName();
    }
}
