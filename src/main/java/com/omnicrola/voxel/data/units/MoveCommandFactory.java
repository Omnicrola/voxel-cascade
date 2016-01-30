package com.omnicrola.voxel.data.units;

import com.omnicrola.voxel.engine.states.ICurrentLevelProvider;
import com.omnicrola.voxel.entities.commands.ICommand;
import com.omnicrola.voxel.entities.commands.MoveCommand;
import com.omnicrola.voxel.jme.wrappers.impl.GuiBuilder;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/30/2016.
 */
@XmlRootElement(name = "move-command")
public class MoveCommandFactory implements ICommandFactory {
    public ICommand build(ICurrentLevelProvider currentLevelProvider, GuiBuilder guiBuilder) {
        return new MoveCommand( );
    }
}
