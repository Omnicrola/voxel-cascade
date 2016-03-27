package com.omnicrola.voxel.network;

import java.util.List;

/**
 * Created by Eric on 3/20/2016.
 */
public interface INetworkObserver {

    public void availableServersChanged(List<VoxelGameServer> servers);

}
