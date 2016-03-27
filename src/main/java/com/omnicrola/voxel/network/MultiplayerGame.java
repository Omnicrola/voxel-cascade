package com.omnicrola.voxel.network;

/**
 * Created by Eric on 3/27/2016.
 */
public class MultiplayerGame {
    private String serverIp;
    private String serverName;
    private String playerCount;

    public MultiplayerGame(String serverIp, String serverName, String playerCount) {
        this.serverIp = serverIp;
        this.serverName = serverName;
        this.playerCount = playerCount;
    }

    public String getServerIp() {
        return serverIp;
    }

    public String getServerName() {
        return serverName;
    }


    public String getPlayerCount() {
        return playerCount;
    }
}
