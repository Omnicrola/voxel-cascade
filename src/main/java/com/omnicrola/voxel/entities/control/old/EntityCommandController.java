package com.omnicrola.voxel.entities.control.old;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.entities.commands.IEntityCommand;
import com.omnicrola.voxel.input.CommandCollector;

import java.util.List;

/**
 * Created by omnic on 1/30/2016.
 */
public class EntityCommandController extends AbstractControl {

    private final List<IEntityCommand> commands;
    private List<IEntityCommand> buildCommands;

    public EntityCommandController(List<IEntityCommand> commands, List<IEntityCommand> buildCommands) {
        this.commands = commands;
        this.buildCommands = buildCommands;
    }

    @Override
    protected void controlUpdate(float tpf) {

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public void collectCommands(CommandCollector commandCollector) {
        this.commands.stream().forEach(c -> commandCollector.collect(c));
    }


    public void collectBuildCommands(CommandCollector commandCollector) {
        this.buildCommands.stream()
                .forEach(c -> commandCollector.collect(c));
    }
}
