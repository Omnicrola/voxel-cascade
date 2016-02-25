package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.world.MessagePackage;

/**
 * Created by Eric on 2/24/2016.
 */
public interface ILocalCommand {

    public void execute(MessagePackage messagePackage);
}
