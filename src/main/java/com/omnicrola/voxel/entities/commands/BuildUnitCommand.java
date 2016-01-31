package com.omnicrola.voxel.entities.commands;

import com.omnicrola.voxel.input.CommandGroup;
import com.omnicrola.voxel.input.CursorCommandDelegator;
import com.omnicrola.voxel.input.SelectionGroup;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by Eric on 1/30/2016.
 */
@XmlRootElement(name = "unit")
public class BuildUnitCommand implements IEntityCommand {

    @XmlAttribute(name = "unit-id", required = true)
    private int unitId;

    @XmlAttribute(name = "priority")
    private int priority = 1;

    public BuildUnitCommand() {
    }

    public BuildUnitCommand(int unitId) {
        this.unitId = unitId;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "Build " + this.unitId;
    }

    @Override
    public int priority() {
        return this.priority;
    }

    @Override
    public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandDelegator cursorCommandDelegator) {
        cursorCommandDelegator.setBuildUnitStrategy(this.unitId);
        return null;
    }
}
