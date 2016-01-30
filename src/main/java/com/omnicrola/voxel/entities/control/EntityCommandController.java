package com.omnicrola.voxel.entities.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.entities.commands.EntityCommand;
import com.omnicrola.voxel.input.CommandCollector;

import java.util.List;

/**
 * Created by omnic on 1/30/2016.
 */
public class EntityCommandController extends AbstractControl {

    private final List<EntityCommand> commands;

    public EntityCommandController(List<EntityCommand> commands) {
        this.commands = commands;
    }

    @Override
    protected void controlUpdate(float tpf) {

    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public void collect(CommandCollector commandCollector) {
        this.commands.stream().forEach(c -> commandCollector.collect(c));
    }
}
