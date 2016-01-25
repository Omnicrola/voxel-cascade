package com.omnicrola.voxel.ui;

import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

/**
 * Created by Eric on 1/24/2016.
 */
public class MainMenuScreenController extends AbstractScreenController {


    @NiftyEventSubscriber(id = "BUTTON_START")
    @SubscriberLink(UiToken.BUTTON_START)
    public void start(String id, ButtonClickedEvent buttonClickedEvent) {
        System.out.println("hello");
    }


}
