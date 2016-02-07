package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.engine.states.IGameStatisticProvider;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.builders.AbstractScreenController;
import com.omnicrola.voxel.ui.builders.UiConstants;
import com.omnicrola.voxel.ui.data.TeamStatistics;
import com.omnicrola.voxel.ui.nifty.IUiElement;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;

import java.util.List;

/**
 * Created by Eric on 2/6/2016.
 */
public class GameOverScreenController extends AbstractScreenController {

    private IGameStatisticProvider statisticProvider;

    public GameOverScreenController(IGameStatisticProvider statisticProvider) {
        this.statisticProvider = statisticProvider;
    }

    @Override
    public void onStartScreen() {
        IUiElement resultsPanel = ui().getElement(UiToken.TEAM_RESULTS_PANEL);
        resultsPanel.removeAllChildren();

        List<TeamStatistics> teamStatistics = this.statisticProvider.getTeamStatistics();
        resultsPanel.addElement(teamRow("Team", "Units", "Structures", ""));
        teamStatistics.forEach(s -> addStats(resultsPanel, s));
    }

    private void addStats(IUiElement resultsPanel, TeamStatistics teamStatistics) {
        resultsPanel.addElement(teamRow(
                teamStatistics.getName(),
                teamStatistics.getUnitsBuilt(),
                teamStatistics.getStructuresBuilt(),
                teamStatistics.isWinner()
        ));
    }

    private PanelBuilder teamRow(String teamName, String unitCount, String structureCount, String isWinner) {
        return new PanelBuilder() {
            {
                width(percentage(100));
                height(pixels(30));
                childLayoutHorizontal();
                text(cell(10, teamName));
                text(cell(15, unitCount));
                text(cell(15, structureCount));
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
}
