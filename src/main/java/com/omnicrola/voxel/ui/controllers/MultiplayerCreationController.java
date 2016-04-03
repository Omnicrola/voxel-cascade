package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.commands.CancelMultiplayerCreationCommand;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 2/21/2016.
 */
public class MultiplayerCreationController extends AbstractScreenController {

    private UiAdapter uiAdapter;

    public MultiplayerCreationController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @NiftyEventSubscriber(id = "button-cancel")
    public void quitLobby(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new CancelMultiplayerCreationCommand());
    }

    @NiftyEventSubscriber(id = "button-start")
    public void startGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new StartMultiplayerGameCommand(LevelGeneratorTool.BASIC_LEVEL_UUID));
    }

    @Override
    protected void screenOpen() {
        VoxelGameServer server = this.uiAdapter.getCurrentServer();
        if (server != null) {
            updateLabels(server);
        }
    }

    private void updateLabels(VoxelGameServer server) {
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_IP).setText("IP: " + server.getAddress());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_NAME).setText("Name: " + server.getName());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_PLAYERS).setText("Players: " + server.getPlayers());
    }

    @Override
    protected void screenClose() {

    }
}
