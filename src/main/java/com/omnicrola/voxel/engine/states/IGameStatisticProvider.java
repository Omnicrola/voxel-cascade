package com.omnicrola.voxel.engine.states;

import com.omnicrola.voxel.ui.data.TeamStatistics;

import java.util.List;

/**
 * Created by Eric on 2/6/2016.
 */
public interface IGameStatisticProvider {
    List<TeamStatistics> getTeamStatistics();
}
