package com.omnicrola.voxel.network;

import java.net.InetAddress;

/**
 * Created by Eric on 3/27/2016.
 */
public class VoxelGameServer {
    private InetAddress address;

    public VoxelGameServer(InetAddress address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return address.getHostAddress() + " " + address.getHostName();
    }
}
