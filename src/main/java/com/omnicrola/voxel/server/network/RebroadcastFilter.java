package com.omnicrola.voxel.server.network;

import com.jme3.network.Filter;
import com.jme3.network.HostedConnection;

/**
 * Created by Eric on 2/21/2016.
 */
public class RebroadcastFilter implements Filter<HostedConnection> {
    private HostedConnection connectionToSkip;

    public RebroadcastFilter(HostedConnection connection) {
        this.connectionToSkip = connection;
    }

    @Override
    public boolean apply(HostedConnection hostedConnection) {
        return this.connectionToSkip != hostedConnection;
    }
}
