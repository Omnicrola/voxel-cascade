package com.omnicrola.voxel.data.units;

import com.omnicrola.voxel.engine.states.ICurrentLevelProvider;
import com.omnicrola.voxel.entities.commands.ICommand;
import com.omnicrola.voxel.jme.wrappers.impl.GuiBuilder;

/**
 * Created by omnic on 1/30/2016.
 */
public interface ICommandFactory {

    public ICommand build(ICurrentLevelProvider currentLevelProvider, GuiBuilder guiBuilder);
}
