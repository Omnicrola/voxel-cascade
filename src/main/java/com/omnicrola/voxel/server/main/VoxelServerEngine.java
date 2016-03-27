package com.omnicrola.voxel.server.main;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.server.network.ServerActivePlayState;
import com.omnicrola.voxel.server.network.VoxelNetworkServerFactory;

/**
 * Created by Eric on 2/21/2016.
 */
public class VoxelServerEngine extends SimpleApplication implements IActionQueue{

    @Override
    public void simpleInitApp() {
        this.getStateManager().attach(new ServerLobbyState(new VoxelNetworkServerFactory()));
        this.getStateManager().attach(new ServerActivePlayState());
    }

    @Override
    public void start() {
        super.start(JmeContext.Type.Headless);
    }
}
