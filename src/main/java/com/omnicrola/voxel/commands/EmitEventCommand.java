package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 4/10/2016.
 */
public class EmitEventCommand extends AbstractWorldCommand {
    private Object event;

    public EmitEventCommand(Object event) {
        this.event = event;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        System.out.println("POST EVENT");
        VoxelEventBus.INSTANCE().post(this.event);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
