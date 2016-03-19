package com.omnicrola.voxel.network.listeners;

import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.commands.IWorldCommand;
import com.omnicrola.voxel.engine.IActionQueue;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Eric on 2/22/2016.
 */
public class ClientCommandListener implements MessageListener<Client> {

    private static final Logger LOGGER = Logger.getLogger(ClientCommandListener.class.getName());

    private IActionQueue actionQueue;
    private ICommandProcessor commandProcessor;

    public ClientCommandListener(IActionQueue actionQueue, ICommandProcessor commandProcessor) {
        this.actionQueue = actionQueue;
        this.commandProcessor = commandProcessor;
    }

    @Override
    public void messageReceived(Client client, Message message) {
        if (message instanceof IWorldCommand) {
            this.actionQueue.enqueue(() -> addCommand((IWorldCommand) message));
        }
    }

    private Object addCommand(IWorldCommand command) {
        LOGGER.log(Level.FINE, "C <= recieved message: " + command);
        this.commandProcessor.addCommand(command);
        return null;
    }
}
