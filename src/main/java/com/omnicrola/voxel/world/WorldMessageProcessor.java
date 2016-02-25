package com.omnicrola.voxel.world;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Eric on 2/22/2016.
 */
public class WorldMessageProcessor {

    private final ArrayList<IWorldMessage> messages;
    private MessagePackage messagePackage;

    public WorldMessageProcessor(MessagePackage messagePackage) {
        this.messagePackage = messagePackage;
        this.messages = new ArrayList<>();
    }

    public void addMessage(IWorldMessage worldMessage) {
        this.messages.add(worldMessage);
    }

    public void handleMessages(long tick) {
        Iterator<IWorldMessage> iterator = this.messages.iterator();
        while (iterator.hasNext()) {
            IWorldMessage message = iterator.next();
            if (message.getTargetTic() <= tick) {
                System.out.println("Handling message: "+message);
                message.execute(messagePackage);
                iterator.remove();
            }
        }
    }
}
