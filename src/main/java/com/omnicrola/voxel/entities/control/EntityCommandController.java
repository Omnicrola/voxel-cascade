package com.omnicrola.voxel.entities.control;

import com.omnicrola.voxel.entities.commands.ICommand;

import java.util.ArrayList;

/**
 * Created by omnic on 1/30/2016.
 */
public class EntityCommandController {

    private final ArrayList<ICommand> commands;

    public EntityCommandController() {
        this.commands = new ArrayList<>();
    }

    public void addCommand(ICommand command) {
        this.commands.add(command);
    }
}
