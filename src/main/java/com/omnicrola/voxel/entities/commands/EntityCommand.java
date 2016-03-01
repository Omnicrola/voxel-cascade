package com.omnicrola.voxel.entities.commands;

import com.omnicrola.voxel.input.CommandGroup;
import com.omnicrola.voxel.input.CursorCommandAdaptor;
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
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
            return null;
        }
    },
    MOVE(1) {
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
            cursorCommandAdaptor.setMoveStrategy();
            return null;
        }
    },
    ATTACK(2) {
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
            cursorCommandAdaptor.setAttackStrategy();
            return null;
        }
    },
    STOP(3) {
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
            selectionGroup.orderStop();
            return null;
        }
    },
    BUILD(4) {
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
            cursorCommandAdaptor.setBuildStrategy(selectionGroup);
            return selectionGroup.getBuildCommands();
        }
    },
    HARVEST(5){
        @Override
        public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
            cursorCommandAdaptor.setHarvestStrategy();
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
