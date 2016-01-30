package com.omnicrola.voxel.data.units;

import com.jme3.scene.Spatial;
import com.omnicrola.voxel.entities.control.EntityCommandController;
import com.omnicrola.voxel.entities.control.IControlFactory;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.jme.wrappers.impl.GuiBuilder;

import java.util.List;

/**
 * Created by omnic on 1/30/2016.
 */
public class CommandControlFactory implements IControlFactory {
    private IGameContainer gameWorld;
    private List<ICommandFactory> commandFactories;

    public CommandControlFactory(IGameContainer gameWorld, List<ICommandFactory> commandFactories) {
        this.gameWorld = gameWorld;
        this.commandFactories = commandFactories;
    }

    @Override
    public void build(Spatial spatial) {
        EntityCommandController commandController = new EntityCommandController();
        GuiBuilder guiBuilder = this.gameWorld.gui().build();
        this.commandFactories.forEach(f -> commandController.addCommand(f.build(null, guiBuilder)));
    }
}
