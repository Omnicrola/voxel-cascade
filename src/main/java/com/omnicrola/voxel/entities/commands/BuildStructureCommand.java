package com.omnicrola.voxel.entities.commands;

import com.omnicrola.voxel.input.CommandGroup;
import com.omnicrola.voxel.input.CursorCommandAdaptor;
import com.omnicrola.voxel.input.SelectionGroup;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by omnic on 3/12/2016.
 */
@XmlRootElement(name = "structure")
public class BuildStructureCommand implements IEntityCommand {
    @XmlAttribute(name = "structure-id", required = true)
    private int globalId;

    @XmlAttribute(name = "label", required = true)
    private String label;

    @XmlAttribute(name = "build-radius")
    private float buildRadius;

    @XmlAttribute(name = "priority")
    private int priority = 1;

    public BuildStructureCommand() {
    }

    public BuildStructureCommand(String label, int globalId, float buildRadius) {
        this.label = label;
        this.globalId = globalId;
        this.buildRadius = buildRadius;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return this.label;
    }

    @Override
    public int priority() {
        return this.priority;
    }

    @Override
    public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
        cursorCommandAdaptor.setBuildStructureStrategy(this.globalId, this.buildRadius, selectionGroup);
        return null;
    }
}
