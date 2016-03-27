package com.omnicrola.voxel.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Eric on 3/27/2016.
 */
public class VoxelGameServer {

    public static final VoxelGameServer EMPTY = emptyServer();

    private static VoxelGameServer emptyServer() {
        try {
            return new VoxelGameServer(InetAddress.getByName("localhost"), 0);
        } catch (UnknownHostException e) {
            return null;
        }
    }

    private InetAddress address;
    private int playerCount;

    public VoxelGameServer(InetAddress address, int playerCount) {
        this.address = address;
        this.playerCount = playerCount;
    }

    @Override
    public String toString() {
        return address.getHostAddress() + " " + address.getHostName();
    }

    public String getAddress() {
        return address.getHostAddress();
    }

    public String getName() {
        return this.address.getHostName();
    }

    public int getPlayers() {
        return this.playerCount;
    }
}
