package com.omnicrola.voxel.engine.entities.control;

import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import com.omnicrola.voxel.engine.entities.commands.IEntityCommand;

import java.util.PriorityQueue;

/**
 * Created by omnic on 1/16/2016.
 */
public class CommandQueueControl extends AbstractControl {

    private final PriorityQueue<IEntityCommand> commandQueue;

    public CommandQueueControl() {
        this.commandQueue = new PriorityQueue<>(10, new CommandComparator());
    }

    @Override
    protected void controlUpdate(float tpf) {
        evaluateCommands(tpf);
    }

    private void evaluateCommands(float tpf) {
        if (!this.commandQueue.isEmpty()) {
            final IEntityCommand command = this.commandQueue.peek();
            command.evaluate(getSpatial(), tpf);
            if (command.isFinished()) {
                this.commandQueue.poll();
            }
        }
    }


    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {

    }

    public void addCommand(IEntityCommand entityCommand) {
        this.commandQueue.add(entityCommand);
    }
}
