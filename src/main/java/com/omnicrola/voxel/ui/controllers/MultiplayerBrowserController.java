package com.omnicrola.voxel.ui.controllers;

import com.google.common.eventbus.Subscribe;
import com.omnicrola.voxel.commands.JoinLobbyCommand;
import com.omnicrola.voxel.commands.StopBrowsingForMultiplayerGameCommand;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.JoinLobbyEvent;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
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

    @NiftyEventSubscriber(id = "button-join")
    public void triggerJoin(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new JoinLobbyCommand(this.currentlySelectedServer));
    }

    @NiftyEventSubscriber(id = "button-cancel")
    public void triggerCancel(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.sendCommand(new StopBrowsingForMultiplayerGameCommand());
    }

    @NiftyEventSubscriber(id = "combobox-server-list")
    public void selectedServerChanged(String id, ListBoxSelectionChangedEvent event) {
        Optional<VoxelGameServer> selection = event.getSelection().stream().findFirst();
        if (selection.isPresent()) {
            this.currentlySelectedServer = selection.get();
            updateServerInformation();
        }
    }

    @Subscribe
    public void handleLobbyJoinedResult(JoinLobbyEvent event) {
        if (event.succeeded()) {
            this.uiAdapter.gotoScreen(UiScreen.MULTIPLAYER_LOBBY);
        } else {
            showFailDialog();
        }
    }

    private void showFailDialog() {
        System.out.println("FAILED TO JOIN LOBBY");
    }

    @Override
    protected void screenOpen() {
        ListBox<VoxelGameServer> serverDropdown = ui().getListBox(UiToken.Multiplayer.Browse.MULTIPLAYER_SERVER_LIST);
        serverDropdown.removeAllItems(serverDropdown.getItems());
        serverDropdown.addItem(VoxelGameServer.EMPTY);
        VoxelEventBus.INSTANCE().register(this);
    }

    @Override
    protected void screenClose() {
        VoxelEventBus.INSTANCE().unregister(this);
    }

    private void updateServerInformation() {
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_IP).setText("IP: " + this.currentlySelectedServer.getAddress());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_NAME).setText("Name: " + this.currentlySelectedServer.getName());
        ui().getElement(UiToken.Multiplayer.Browse.LABEL_SERVER_PLAYERS).setText("Players: " + this.currentlySelectedServer.getPlayers());
    }

    public void updateServerList(List<VoxelGameServer> newServers) {
        ListBox<VoxelGameServer> serverDropdown = ui().getListBox(UiToken.Multiplayer.Browse.MULTIPLAYER_SERVER_LIST);
        serverDropdown.removeAllItems(this.servers);
        serverDropdown.addAllItems(newServers);
        this.servers = newServers;
    }
}
