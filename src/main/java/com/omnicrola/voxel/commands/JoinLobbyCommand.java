package com.omnicrola.voxel.commands;

import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.world.CommandPackage;

/**
 * Created by Eric on 3/27/2016.
 */
public class JoinLobbyCommand extends AbstractWorldCommand {
    private VoxelGameServer serverToJoin;

    public JoinLobbyCommand(VoxelGameServer serverToJoin) {
        this.serverToJoin = serverToJoin;
    }

    @Override
    public void execute(CommandPackage commandPackage) {
        commandPackage.getNetworkManager().joinLobby(this.serverToJoin);
        commandPackage.getUiManager().changeScreen(UiScreen.MULTIPLAYER_LOBBY);
    }

    @Override
    public boolean isLocal() {
        return true;
    }
}
