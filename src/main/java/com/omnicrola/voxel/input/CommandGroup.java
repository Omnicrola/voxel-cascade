package com.omnicrola.voxel.input;

import com.omnicrola.voxel.entities.commands.IEntityCommand;

import java.util.List;

/**
 * Created by omnic on 1/30/2016.
 */
public class CommandGroup {
    private SelectionGroup selectionGroup;
    private CursorCommandDelegator cursorCommandDelegator;
    private IEntityCommand command;

    public CommandGroup(SelectionGroup selectionGroup, CursorCommandDelegator cursorCommandDelegator, IEntityCommand command) {
        this.selectionGroup = selectionGroup;
        this.cursorCommandDelegator = cursorCommandDelegator;
        this.command = command;
    }

    public List<CommandGroup> execute() {
        return this.command.execute(this.selectionGroup, this.cursorCommandDelegator);
    }

    public int priority() {
        return this.command.priority();
    }

    public String getName() {
        return this.command.getName();
    }
}
