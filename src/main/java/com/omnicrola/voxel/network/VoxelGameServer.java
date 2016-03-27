package com.omnicrola.voxel.network;

/**
 * Created by Eric on 3/27/2016.
 */
public class VoxelGameServer {
    private String ip;
    private String serverName;

    public VoxelGameServer(String ip, String serverName) {
        this.ip = ip;
        this.serverName = serverName;
    }

    public String getIp() {
        return ip;
    }

    public String getServerName() {
        return serverName;
    }
}
