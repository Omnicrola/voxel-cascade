package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.commands.ICommandProcessor;
import com.omnicrola.voxel.commands.IMessageProcessor;
import com.omnicrola.voxel.commands.JoinMultiplayerCommand;
import com.omnicrola.voxel.commands.StartMultiplayerServerCommand;
import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.jme.wrappers.IGameContainer;
import com.omnicrola.voxel.network.messages.LoadLevelMessage;
import com.omnicrola.voxel.server.main.init.ServerBootstrapper;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 2/21/2016.
 */
public class MultiplayerScreenController extends AbstractScreenController {


    private IGameContainer gameContainer;
    private ICommandProcessor commandProcessor;
    private IMessageProcessor messageProcessor;

    public MultiplayerScreenController(IGameContainer gameContainer,
                                       ICommandProcessor commandProcessor,
                                       IMessageProcessor messageProcessor) {
        this.gameContainer = gameContainer;
        this.commandProcessor = commandProcessor;
        this.messageProcessor = messageProcessor;
    }

    @NiftyEventSubscriber(id = "BUTTON_ABORT_SERVER_CONNECT")
    @SubscriberLink(UiToken.BUTTON_ABORT_SERVER_CONNECT)
    public void quitGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.gameContainer.network().closeConnection();
        this.gameContainer.gui().changeScreens(UiScreen.MAIN_MENU);
    }

    @NiftyEventSubscriber(id = "BUTTON_START_MULTIPLAYER")
    @SubscriberLink(UiToken.BUTTON_START_MULTIPLAYER)
    public void startGame(String id, ButtonClickedEvent buttonClickedEvent) {
        StartMultiplayerServerCommand startMultiplayerServerCommand = new StartMultiplayerServerCommand(ServerBootstrapper.bootstrap());
        this.commandProcessor.executeCommand(startMultiplayerServerCommand);
        JoinMultiplayerCommand joinMultiplayerCommand = new JoinMultiplayerCommand("localhost");
        this.commandProcessor.executeCommand(joinMultiplayerCommand);

        LoadLevelMessage loadLevelMessage = new LoadLevelMessage(LevelGeneratorTool.BASIC_LEVEL_UUID.toString());
        this.messageProcessor.sendLocal(loadLevelMessage);
    }

}
