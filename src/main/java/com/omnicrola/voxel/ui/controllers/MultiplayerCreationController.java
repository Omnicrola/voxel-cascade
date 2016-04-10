package com.omnicrola.voxel.ui.controllers;

import com.google.common.eventbus.Subscribe;
import com.omnicrola.voxel.commands.CancelMultiplayerCreationCommand;
import com.omnicrola.voxel.commands.StartMultiplayerGameCommand;
import com.omnicrola.voxel.data.level.LevelDefinition;
import com.omnicrola.voxel.data.level.LevelGeneratorTool;
import com.omnicrola.voxel.eventBus.events.UpdateAvailableLevelsEvent;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.data.LevelWrapper;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.ListBox;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eric on 2/21/2016.
 */
public class MultiplayerCreationController extends MultiplayerLobbyScreenController {

    private List<LevelDefinition> levels = new ArrayList<>();

    public MultiplayerCreationController(UiAdapter uiAdapter) {
        super(uiAdapter);
    }

    @NiftyEventSubscriber(id = "button-cancel")
    public void quitLobby(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new CancelMultiplayerCreationCommand());
    }

    @NiftyEventSubscriber(id = "button-start")
    public void startGame(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new StartMultiplayerGameCommand(LevelGeneratorTool.BASIC_LEVEL_UUID));
    }

    @Subscribe
    public void setListOfLevels(UpdateAvailableLevelsEvent event) {
        levels = event.getLevels();
        updateListOfLevels();
    }

    private void updateListOfLevels() {
        ListBox<LevelWrapper> levelList = ui().getListBox(UiToken.Multiplayer.Lobby.LEVEL_LISTBOX);
        if (levelList != null) {
            levelList.removeAllItems(levelList.getItems());
            levels.forEach(l -> levelList.addItem(new LevelWrapper(l)));
        }
    }

    @Override
    protected void screenOpen() {
        super.screenOpen();
        ui().getElement(UiToken.Multiplayer.Lobby.LEVEL_LISTBOX).setVisible(true);
        updateListOfLevels();
    }
}
