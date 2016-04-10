package com.omnicrola.voxel.server.main;

import com.jme3.app.SimpleApplication;
import com.jme3.system.JmeContext;
import com.omnicrola.voxel.engine.IActionQueue;
import com.omnicrola.voxel.server.network.ServerActivePlayState;
import com.omnicrola.voxel.server.network.VoxelNetworkServerFactory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 2/21/2016.
 */
public class VoxelServerEngine extends SimpleApplication implements IActionQueue {

    private static final Logger LOGGER = Logger.getLogger(VoxelServerEngine.class.getName());

    @Override
    public void simpleInitApp() {
        this.getStateManager().attach(new ServerLobbyState(new VoxelNetworkServerFactory()));
        this.getStateManager().attach(new ServerActivePlayState());
    }

    @Override
    public void start() {
        super.start(JmeContext.Type.Headless);
        LOGGER.log(Level.INFO, "Starting JME multiplayer server.");
    }
}
