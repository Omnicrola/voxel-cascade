package com.omnicrola.voxel.ui.data;

/**
 * Created by Eric on 2/6/2016.
 */
public class TeamStatistics {
    private String name;
    private int unitsBuilt;
    private int structuresBuilt;
    private boolean winner;

    public TeamStatistics(String name, int unitsBuilt, int structuresBuilt, boolean winner) {
        this.name = name;
        this.unitsBuilt = unitsBuilt;
        this.structuresBuilt = structuresBuilt;
        this.winner = winner;
    }

    public String getName() {
        return name;
    }

    public String getUnitsBuilt() {
        return String.valueOf(unitsBuilt);
    }

    public String getStructuresBuilt() {
        return String.valueOf(structuresBuilt);
    }

    public String isWinner() {
        return winner ? "WIN" : "LOSS";
    }
}
