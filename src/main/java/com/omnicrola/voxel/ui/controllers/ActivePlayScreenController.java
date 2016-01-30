package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.input.CommandGroup;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.ui.ISelectedUnit;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiCurrentSelectionObserver;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import com.omnicrola.voxel.ui.builders.UiConstants;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.elements.Element;

import java.util.List;

/**
 * Created by Eric on 1/25/2016.
 */
public class ActivePlayScreenController extends AbstractScreenController {
    private final UiCurrentSelectionObserver currentSelectionObserver;
    private UserActionGuiAdapter actionAdapter;
    private LevelState currentLevel;
    private SelectionGroup currentSelection;
    private List<CommandGroup> actionCommands;

    public ActivePlayScreenController(UserActionGuiAdapter actionAdapter) {
        this.actionAdapter = actionAdapter;
        this.currentSelectionObserver = new UiCurrentSelectionObserver(this);
    }

    @NiftyEventSubscriber(id = "ACTION_1")
    @SubscriberLink(UiToken.ACTION_1)
    public void triggerActionButton_1(String id, ButtonClickedEvent buttonClickedEvent) {
        this.actionAdapter.triggerMove();
        triggerCommandGroup(1);
    }

    private void triggerCommandGroup(int index) {
        if (this.actionCommands.size() >= index) {
            index = index - 1;
            CommandGroup commandGroup = this.actionCommands.get(index);
            commandGroup.execute();
        }
    }

    @NiftyEventSubscriber(id = "ACTION_2")
    @SubscriberLink(UiToken.ACTION_2)
    public void triggerActionButton_2(String id, ButtonClickedEvent buttonClickedEvent) {
        this.actionAdapter.triggerAttack();
    }

    @NiftyEventSubscriber(id = "ACTION_3")
    @SubscriberLink(UiToken.ACTION_3)
    public void triggerActionButton_3(String id, ButtonClickedEvent buttonClickedEvent) {
        this.actionAdapter.triggerStop();
    }

    public void setLevel(LevelState newLevel) {
        if (this.currentLevel != null) {
            this.currentLevel.getWorldCursor().removeSelectionObserver(this.currentSelectionObserver);
        }
        newLevel.getWorldCursor().addSelectionObserver(this.currentSelectionObserver);
        this.currentLevel = newLevel;
    }

    public void setCurrentSelection(SelectionGroup currentSelection) {
        this.currentSelection = currentSelection;
        Element selectionPanel = getScreen().findElementByName(UiToken.SELECTION_PANEL.toString());
        selectionPanel.getElements().forEach(e -> e.markForRemoval());
        selectionPanel.layoutElements();

        this.currentSelection.getSelections()
                .stream()
                .map(s -> makeUnitLabel(s, selectionPanel))
                .forEach(l -> selectionPanel.add(l));

        this.actionCommands = this.currentSelection.getAvailableCommands();
    }

    private Element makeUnitLabel(ISelectedUnit selectedUnit, Element parent) {
        return new LabelBuilder("unit-label-" + selectedUnit.hashCode(), selectedUnit.getDisplayName()) {{
            backgroundColor(UiConstants.Colors.LIGHT_RED);
            height(pixels(20));
        }}.build(getNifty(), getScreen(), parent);
    }
}
