package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.data.level.LevelState;
import com.omnicrola.voxel.engine.GlobalGameState;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import com.omnicrola.voxel.ui.builders.UiConstants;
import com.omnicrola.voxel.ui.data.TeamStatistics;
import com.omnicrola.voxel.ui.nifty.IUiElement;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.ButtonClickedEvent;

import java.util.List;

/**
 * Created by Eric on 2/6/2016.
 */
public class GameOverScreenController extends AbstractScreenController {

    private UiAdapter uiAdapter;

    public GameOverScreenController(UiAdapter uiAdapter) {
        this.uiAdapter = uiAdapter;
    }

    @NiftyEventSubscriber(id = "BUTTON_MAIN_MENU")
    @SubscriberLink(UiToken.BUTTON_MAIN_MENU)
    public void triggerMainMenuButton(String id, ButtonClickedEvent buttonClickedEvent) {
        this.uiAdapter.transitionTo(GlobalGameState.MAIN_MENU);
    }


    @Override
    protected void screenOpen() {
        IUiElement resultsPanel = ui().getElement(UiToken.TEAM_RESULTS_PANEL);
        IUiElement resultsContainer = ui().getElement(UiToken.TEAM_RESULTS_CONTAINER);
        IUiElement elapsedTimeLabel = ui().getElement(UiToken.ELAPSED_TIME);
        resultsPanel.removeAllChildren();
        resultsPanel.setWidth(resultsContainer.getWidth());

        LevelState levelState = this.uiAdapter.getCurrentLevel();
        elapsedTimeLabel.setText(formatTime(levelState.getTimeElapsed()));
        List<TeamStatistics> teamStatistics = levelState.getTeamStatistics();
        resultsPanel.addElement(teamRow("Team", "Units Built", "Units Lost", "Structures Built", "Structures Lost", ""));
        teamStatistics.forEach(s -> addStats(resultsPanel, s));
    }

    @Override
    protected void screenClose() {

    }

    private void addStats(IUiElement resultsPanel, TeamStatistics teamStatistics) {
        resultsPanel.addElement(teamRow(
                teamStatistics.getName(),
                teamStatistics.getUnitsBuilt(),
                teamStatistics.getUnitsLost(),
                teamStatistics.getStructuresBuilt(),
                teamStatistics.getStructuresLost(),
                teamStatistics.isWinner()
        ));
    }

    private PanelBuilder teamRow(String teamName,
                                 String unitCount, String unitLost,
                                 String structureCount, String structureLost,
                                 String isWinner) {
        return new PanelBuilder() {
            {
                width(percentage(100));
                height(pixels(30));
                childLayoutHorizontal();

                text(cell(10, teamName));
                text(cell(15, unitCount));
                text(cell(15, unitLost));
                text(cell(15, structureCount));
                text(cell(15, structureLost));
                text(cell(15, isWinner));
            }
        };
    }

    private TextBuilder cell(int percentWidth, String text) {
        return new TextBuilder() {{
            text(text);
            font(UiConstants.DEFAULT_FONT);
            width(percentage(percentWidth));
        }};
    }

    private String formatTime(float timeElapsed) {
        int hourInSeconds = 60 * 60;
        int minuteInSeconds = 60;
        int hours = (int) Math.floor(timeElapsed / hourInSeconds);
        timeElapsed = timeElapsed - (hours * hourInSeconds);
        int minutes = (int) Math.floor(timeElapsed / minuteInSeconds);
        timeElapsed = timeElapsed - (minutes * minuteInSeconds);
        int seconds = (int) Math.floor(timeElapsed);
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        return time;
    }
}
