package com.omnicrola.voxel.entities.commands;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by omnic on 1/30/2016.
 */

@XmlRootElement(name="command")
public enum EntityCommand   {
    NONE, MOVE, ATTACK, STOP;

    private EntityCommand() {
    }

    public String getIcon() {
        return null;
    }

    public String getName() {
        return null;
    }

}
