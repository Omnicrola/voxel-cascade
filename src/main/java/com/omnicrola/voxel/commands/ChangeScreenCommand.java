package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 3/27/2016.
 */
public class ChangeScreenCommand extends AbstractWorldCommand {

    private UiScreen screen;

    public ChangeScreenCommand(UiScreen screen) {
        this.screen = screen;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        commandPackage.getUiManager().changeScreen(this.screen);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
