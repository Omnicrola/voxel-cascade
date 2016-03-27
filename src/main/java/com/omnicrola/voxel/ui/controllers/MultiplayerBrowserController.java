package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.network.VoxelGameServer;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.DropDownSelectionChangedEvent;

import java.util.List;

/**
 * Created by Eric on 3/20/2016.
 */
public class MultiplayerBrowserController extends AbstractScreenController {
    private UiAdapter uiAdapter;
    private List<VoxelGameServer> servers;
    private VoxelGameServer currentlySelectedServer;

    public MultiplayerBrowserController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_BROWSE_JOIN")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_BROWSE_JOIN)
    public void triggerJoin(String id, ButtonClickedEvent buttonClickedEvent) {
        System.out.println("Joining! " + this.currentlySelectedServer.getIp() + " " + this.currentlySelectedServer.getServerName());
    }

    @NiftyEventSubscriber(id = "BUTTON_MULTIPLAYER_BROWSE_CANCEL")
    @SubscriberLink(UiToken.BUTTON_MULTIPLAYER_BROWSE_CANCEL)
    public void triggerCancel(String id, ButtonClickedEvent buttonClickedEvent) {
        uiAdapter.transitionTo(GlobalGameState.MAIN_MENU);
    }

    @SubscriberLink(UiToken.MULTIPLAYER_SERVER_LIST)
    @NiftyEventSubscriber(id = "MULTIPLAYER_SERVER_LIST")
    public void selectedServerChanged(String id, DropDownSelectionChangedEvent event) {
        this.currentlySelectedServer = (VoxelGameServer) event.getSelection();
    }


    public void updateServerList(List<VoxelGameServer> newServers) {
        DropDown<VoxelGameServer> serverDropdown = ui().getDropdown(UiToken.MULTIPLAYER_SERVER_LIST);
        serverDropdown.removeAllItems(this.servers);
        serverDropdown.addAllItems(newServers);
        this.servers = newServers;
    }

}
