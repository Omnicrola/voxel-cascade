package com.omnicrola.voxel.data.units;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.commands.EntityCommand;
import com.omnicrola.voxel.entities.control.EntityCommandController;
import com.omnicrola.voxel.entities.control.IControlFactory;

import java.util.List;

/**
 * Created by omnic on 1/30/2016.
 */
public class CommandControlFactory implements IControlFactory {
    private List<EntityCommand> commands;

    public CommandControlFactory(List<EntityCommand> commands) {
        this.commands = commands;
    }

    @Override
    public void build(Spatial spatial) {
        EntityCommandController commandController = new EntityCommandController(this.commands);
        spatial.addControl(commandController);
    }
}
