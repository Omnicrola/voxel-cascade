package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.settings.DisplayModePackage;
import com.omnicrola.voxel.settings.DisplaySettingsHandler;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 4/3/2016.
 */
public class ChangeDisplaySettingsCommand extends AbstractWorldCommand {
    private final DisplaySettingsHandler displaySettingsHandler;
    private final DisplayModePackage selectedMode;

    public ChangeDisplaySettingsCommand(DisplaySettingsHandler displaySettingsHandler, DisplayModePackage selectedMode) {
        this.displaySettingsHandler = displaySettingsHandler;
        this.selectedMode = selectedMode;
    }


    @Override
    public void execute(CommandPackage commandPackage) {
        this.displaySettingsHandler.setDisplayMode(this.selectedMode);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
