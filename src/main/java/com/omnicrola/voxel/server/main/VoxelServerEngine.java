package com.omnicrola.voxel.server.main;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import com.omnicrola.voxel.server.network.ServerNetworkState;

/**
 * Created by Eric on 2/21/2016.
 */
public class VoxelServerEngine extends SimpleApplication {

    @Override
    public void simpleInitApp() {
        ServerNetworkState serverNetworkState = new ServerNetworkState();
        this.getStateManager().attach(serverNetworkState);
    }

    @Override
    public void start() {
        super.start(JmeContext.Type.Headless);
    }
}
