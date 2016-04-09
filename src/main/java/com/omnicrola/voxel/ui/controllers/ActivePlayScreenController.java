package com.omnicrola.voxel.ui.controllers;

import com.google.common.eventbus.Subscribe;
import com.omnicrola.voxel.data.TeamId;
import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.eventBus.VoxelEventBus;
import com.omnicrola.voxel.eventBus.events.LevelStatisticChangeEvent;
import com.omnicrola.voxel.eventBus.events.SelectionChangeEvent;
import com.omnicrola.voxel.eventBus.events.SelectionUpdateEvent;
import com.omnicrola.voxel.input.CommandGroup;
import com.omnicrola.voxel.input.SelectionGroup;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import com.omnicrola.voxel.ui.builders.UiConstants;
import com.omnicrola.voxel.ui.decorations.ISpatialDecorator;
import com.omnicrola.voxel.ui.nifty.IUiButton;
import com.omnicrola.voxel.ui.nifty.IUiElement;
import com.omnicrola.voxel.ui.select.ISelectedUnit;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.ButtonClickedEvent;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.screen.Screen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Eric on 1/25/2016.
 */
public class ActivePlayScreenController extends AbstractScreenController {
    private SelectionGroup currentSelection;
    private List<CommandGroup> actionCommands;
    private UiAdapter uiAdapter;

    public ActivePlayScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @Override
    public void bind(Nifty nifty, Screen screen) {
        super.bind(nifty, screen);
    }

    @Override
    protected void screenOpen() {
        VoxelEventBus.INSTANCE().register(this);
    }

    @Override
    protected void screenClose() {
        VoxelEventBus.INSTANCE().unregister(this);
    }

    @NiftyEventSubscriber(id = "unit-action-1")
    public void triggerActionButton_1(String id, ButtonClickedEvent buttonClickedEvent) {
        triggerCommandGroup(1);
    }

    @NiftyEventSubscriber(id = "unit-action-2")
    public void triggerActionButton_2(String id, ButtonClickedEvent buttonClickedEvent) {
        triggerCommandGroup(2);
    }

    @NiftyEventSubscriber(id = "unit-action-3")
    public void triggerActionButton_3(String id, ButtonClickedEvent buttonClickedEvent) {
        triggerCommandGroup(3);
    }

    @NiftyEventSubscriber(id = "unit-action-4")
    public void triggerActionButton_4(String id, ButtonClickedEvent buttonClickedEvent) {
        triggerCommandGroup(4);
    }

    @NiftyEventSubscriber(id = "unit-action-5")
    public void triggerActionButton_5(String id, ButtonClickedEvent buttonClickedEvent) {
        triggerCommandGroup(5);
    }

    @NiftyEventSubscriber(id = "unit-action-6")
    public void triggerActionButton_6(String id, ButtonClickedEvent buttonClickedEvent) {
        triggerCommandGroup(6);
    }


    @Subscribe
    public void playerSelectionChanged(SelectionChangeEvent event) {
        SelectionGroup currentSelection = event.getSelection();
        removeHealthBarsFromSelection();
        this.currentSelection = currentSelection;
        updateSelectionList();
        addHealthbarsToSelection();
        setCommandLabels(this.currentSelection.getAvailableCommands());
    }

    @Subscribe
    public void updateStatistics(LevelStatisticChangeEvent event) {
        TeamId playerTeam = event.getPlayerTeam();
        float resources = event.getResources(playerTeam);
        IUiElement resourceLabel = ui().getElement(UiToken.Play.LABEL_RESOURCE_AMOUNT);
        resourceLabel.setText(String.valueOf((int) resources));
    }

    @Subscribe
    public void currentSelectionUpdated(SelectionUpdateEvent event) {
        updateSelectionList();
        ISpatialDecorator decorator = this.uiAdapter.getSpatialDecorator();
        event.getRemovedUnits().forEach(decorator::removeSelectionDecorations);
        setCommandLabels(this.currentSelection.getAvailableCommands());
    }

    @Deprecated
    public void selectionHasUpdated(List<ISelectedUnit> removedUnits) {
        updateSelectionList();
        ISpatialDecorator decorator = this.uiAdapter.getSpatialDecorator();
        removedUnits.forEach(decorator::removeSelectionDecorations);
        setCommandLabels(this.currentSelection.getAvailableCommands());
    }

    @Deprecated
    public void setCurrentSelection(SelectionGroup currentSelection) {
        removeHealthBarsFromSelection();
        this.currentSelection = currentSelection;
        updateSelectionList();
        addHealthbarsToSelection();
        setCommandLabels(this.currentSelection.getAvailableCommands());
    }


    private void removeHealthBarsFromSelection() {
        if (this.currentSelection != null) {
            ISpatialDecorator decorator = this.uiAdapter.getSpatialDecorator();
            this.currentSelection.getSelections().forEach(u -> decorator.removeSelectionDecorations(u));
        }
    }

    private void addHealthbarsToSelection() {
        ISpatialDecorator decorator = this.uiAdapter.getSpatialDecorator();
        this.currentSelection.getSelections().forEach(u -> decorator.addSelectionDecorations(u));
    }

    @Deprecated
    public void updateStats(LevelState currentLevel) {
        TeamId playerTeam = currentLevel.getPlayerTeam();
        float resources = currentLevel.getResources(playerTeam);
        IUiElement resourceLabel = ui().getElement(UiToken.Play.LABEL_RESOURCE_AMOUNT);
        resourceLabel.setText(String.valueOf((int) resources));
    }

    private void setCommandLabels(List<CommandGroup> newCommands) {
        this.actionCommands = newCommands;
        Iterator<IUiButton> buttonIterator = getCommandButtons().iterator();
        this.actionCommands.forEach(c -> buttonIterator.next().setText(c.getName()));
        while (buttonIterator.hasNext()) {
            buttonIterator.next().setText("");
        }
    }

    private ArrayList<IUiButton> getCommandButtons() {
        ArrayList<IUiButton> buttons = new ArrayList<>();
        buttons.add(ui().getButton(UiToken.Play.ACTION_1));
        buttons.add(ui().getButton(UiToken.Play.ACTION_2));
        buttons.add(ui().getButton(UiToken.Play.ACTION_3));
        buttons.add(ui().getButton(UiToken.Play.ACTION_4));
        buttons.add(ui().getButton(UiToken.Play.ACTION_5));
        buttons.add(ui().getButton(UiToken.Play.ACTION_6));
        buttons.add(ui().getButton(UiToken.Play.ACTION_7));
        buttons.add(ui().getButton(UiToken.Play.ACTION_8));
        buttons.add(ui().getButton(UiToken.Play.ACTION_9));
        return buttons;
    }

    private void updateSelectionList() {
        IUiElement selectionPanel = ui().getElement(UiToken.SELECTION_PANEL);
        selectionPanel.removeAllChildren();

        this.currentSelection.getSelections()
                .forEach(l -> selectionPanel.addElement(makeElement(l)));
    }

    private LabelBuilder makeElement(ISelectedUnit selectedUnit) {
        String id = "unit-label-" + selectedUnit.hashCode();
        return new LabelBuilder(id, selectedUnit.getDisplayName()) {{
            backgroundColor(UiConstants.Colors.LIGHT_RED);
            height(pixels(20));
        }};
    }

    private void triggerCommandGroup(int index) {
        if (this.actionCommands.size() >= index) {
            index = index - 1;
            CommandGroup commandGroup = this.actionCommands.get(index);
            List<CommandGroup> newCommands = commandGroup.execute();
            if (newCommands != null) {
                setCommandLabels(newCommands);
            }
        }
    }

}
