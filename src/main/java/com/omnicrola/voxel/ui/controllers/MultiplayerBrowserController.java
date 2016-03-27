package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Eric on 3/20/2016.
 */
public class MultiplayerBrowserController extends AbstractScreenController {
    private UiAdapter uiAdapter;
    private List<VoxelGameServer> servers;
    private VoxelGameServer currentlySelectedServer;

    public MultiplayerBrowserController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
        this.servers = new ArrayList<>();
    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_BROWSE_JOIN")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_BROWSE_JOIN)
    public void triggerJoin(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.joinServerLobby(this.currentlySelectedServer);
        this.uiAdapter.transitionTo(GlobalGameState.MULTIPLAYER_LOBBY);
    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_BROWSE_CANCEL")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_BROWSE_CANCEL)
    public void triggerCancel(String id, ButtonClickedEvent buttonClickedEvent) {
        uiAdapter.transitionTo(GlobalGameState.MAIN_MENU);
    }

    @SubscriberLink(UiToken.MULTIPLAYER_SERVER_LIST)
    @NiftyEventSubscriber(id = "MULTIPLAYER_SERVER_LIST")
    public void selectedServerChanged(String id, ListBoxSelectionChangedEvent event) {
        Optional<VoxelGameServer> selection = event.getSelection().stream().findFirst();
        if (selection.isPresent()) {
            this.currentlySelectedServer = selection.get();
            updateServerInformation();
        }
    }

    @Override
    protected void screenOpen() {
        ListBox<VoxelGameServer> serverDropdown = ui().getListBox(UiToken.MULTIPLAYER_SERVER_LIST);
        serverDropdown.removeAllItems(serverDropdown.getItems());
        serverDropdown.addItem(VoxelGameServer.EMPTY);
    }

    @Override
    protected void screenClose() {

    }

    private void updateServerInformation() {
        ui().getElement(UiToken.LABEL_SERVER_IP).setText("IP: " + this.currentlySelectedServer.getAddress());
        ui().getElement(UiToken.LABEL_SERVER_NAME).setText("Name: " + this.currentlySelectedServer.getName());
        ui().getElement(UiToken.LABEL_SERVER_PLAYERS).setText("Players: " + this.currentlySelectedServer.getPlayers());
    }

    public void updateServerList(List<VoxelGameServer> newServers) {
        ListBox<VoxelGameServer> serverDropdown = ui().getListBox(UiToken.MULTIPLAYER_SERVER_LIST);
        serverDropdown.removeAllItems(this.servers);
        serverDropdown.addAllItems(newServers);
        this.servers = newServers;
    }
}
