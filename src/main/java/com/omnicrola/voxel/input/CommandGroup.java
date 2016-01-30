package com.omnicrola.voxel.input;

import com.omnicrola.voxel.entities.commands.EntityCommand;

/**
 * Created by omnic on 1/30/2016.
 */
public class CommandGroup {
    private SelectionGroup selectionGroup;
    private CursorStrategySetter cursorStrategySetter;
    private EntityCommand command;

    public CommandGroup(SelectionGroup selectionGroup, CursorStrategySetter cursorStrategySetter, EntityCommand command) {
        this.selectionGroup = selectionGroup;
        this.cursorStrategySetter = cursorStrategySetter;
        this.command = command;
    }

    public void execute() {
        switch (this.command) {
            case NONE:
                break;
            case MOVE:
                setMoveStrategy();
                break;
            case ATTACK:
                setAttackStrategy();
                break;
            case STOP:
                this.selectionGroup.orderStop();
                break;
        }
    }

    private void setAttackStrategy() {
        this.cursorStrategySetter.setAttackStrategy();
    }

    private void setMoveStrategy() {
        this.cursorStrategySetter.setMoveStrategy();
    }
}
