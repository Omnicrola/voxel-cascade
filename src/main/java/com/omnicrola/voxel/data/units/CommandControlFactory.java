package com.omnicrola.voxel.data.units;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.commands.IEntityCommand;
import com.omnicrola.voxel.entities.control.old.EntityCommandController;
import com.omnicrola.voxel.entities.control.old.IControlFactory;

import java.util.List;

/**
 * Created by omnic on 1/30/2016.
 */
public class CommandControlFactory implements IControlFactory {
    private List<IEntityCommand> commands;
    private List<IEntityCommand> buildCommands;

    public CommandControlFactory(List<IEntityCommand> commands, List<IEntityCommand> buildCommands) {
        this.commands = commands;
        this.buildCommands = buildCommands;
    }

    @Override
    public void build(Spatial spatial, UnitDefinitionRepository unitDefinitionRepository) {
        EntityCommandController commandController = new EntityCommandController(this.commands, buildCommands);
        spatial.addControl(commandController);
    }
}
