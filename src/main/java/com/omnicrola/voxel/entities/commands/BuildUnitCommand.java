package com.omnicrola.voxel.entities.commands;

import com.omnicrola.voxel.input.CommandGroup;
import com.omnicrola.voxel.input.CursorCommandAdaptor;
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

    @XmlAttribute(name = "label", required = true)
    private String label;

    @XmlAttribute(name = "build-range")
    private float buildRadius = 1.0f;

    @XmlAttribute(name = "priority")
    private int priority = 1;


    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return label;
    }

    @Override
    public int priority() {
        return this.priority;
    }

    public BuildUnitCommand() {
    }

    public BuildUnitCommand(String label, int unitId, float buildRadius) {
        this.label = label;
        this.unitId = unitId;
        this.buildRadius = buildRadius;
    }

    @Override
    public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
        cursorCommandAdaptor.setBuildUnitStrategy(this.unitId, this.buildRadius, selectionGroup);
        return null;
    }
}
