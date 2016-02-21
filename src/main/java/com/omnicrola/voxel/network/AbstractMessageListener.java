package com.omnicrola.voxel.network;

import com.jme3.network.Message;
import com.jme3.network.MessageListener;

/**
 * Created by Eric on 2/21/2016.
 */
public abstract class AbstractMessageListener<T,S> implements MessageListener<S>{
    @Override
    public final void messageReceived(S s, Message message) {
        processMessage(s, (T) message);
    }

    protected abstract void processMessage(S connection, T message) ;
}
