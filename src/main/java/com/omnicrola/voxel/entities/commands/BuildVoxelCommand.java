package com.omnicrola.voxel.entities.commands;

import com.omnicrola.voxel.input.CommandGroup;
import com.omnicrola.voxel.input.CursorCommandAdaptor;
import com.omnicrola.voxel.input.SelectionGroup;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by omnic on 1/31/2016.
 */
@XmlRootElement(name = "voxel")
public class BuildVoxelCommand implements IEntityCommand {

    @XmlAttribute(name = "type")
    private byte voxelType;

    public BuildVoxelCommand() {
    }

    public BuildVoxelCommand(byte voxelType) {
        this.voxelType = voxelType;
    }

    @Override
    public String getIcon() {
        return null;
    }

    @Override
    public String getName() {
        return "Voxel " + this.voxelType;
    }

    @Override
    public int priority() {
        return voxelType;
    }

    @Override
    public List<CommandGroup> execute(SelectionGroup selectionGroup, CursorCommandAdaptor cursorCommandAdaptor) {
        cursorCommandAdaptor.setBuildVoxelStrategy(this.voxelType);
        return null;
    }
}
