package com.omnicrola.voxel.entities.commands;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/30/2016.
 */

@XmlRootElement(name = "command")
public enum EntityCommand {
    NONE(99),
    MOVE(1),
    ATTACK(2),
    STOP(3),
    BUILD(4);

    private int priority;

    private EntityCommand(int priority) {
        this.priority = priority;
    }

    public String getIcon() {
        return null;
    }

    public String getName() {
        return null;
    }

    public int priority() {
        return this.priority;
    }

}
