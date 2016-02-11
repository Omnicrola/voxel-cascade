package com.omnicrola.voxel.entities.commands;

import com.omnicrola.voxel.input.CommandGroup;
import com.omnicrola.voxel.input.CursorCommandDelegator;
import com.omnicrola.voxel.input.SelectionGroup;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by omnic on 1/30/2016.
 */

@XmlRootElement(name = "command")
public enum EntityCommand implements IEntityCommand {
    NONE(99) {
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandDelegator cursorCommandDelegator) {
            return null;
        }
    },
    MOVE(1) {
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandDelegator cursorCommandDelegator) {
            cursorCommandDelegator.setMoveStrategy();
            return null;
        }
    },
    ATTACK(2) {
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandDelegator cursorCommandDelegator) {
            cursorCommandDelegator.setAttackStrategy();
            return null;
        }
    },
    STOP(3) {
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandDelegator cursorCommandDelegator) {
            selectionGroup.orderStop();
            return null;
        }
    },
    BUILD(4) {
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandDelegator cursorCommandDelegator) {
            cursorCommandDelegator.setBuildStrategy(selectionGroup);
            return selectionGroup.getBuildCommands();
        }
    },
    HARVEST(5){
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandDelegator cursorCommandDelegator) {
            cursorCommandDelegator.setHarvestStrategy(selectionGroup);
            return null;
        }
    };

    private int priority;

    private EntityCommand(int priority) {
        this.priority = priority;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return this.toString();
    }

    @Override
    public int priority() {
        return this.priority;
    }

}
