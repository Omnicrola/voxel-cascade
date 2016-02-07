package com.omnicrola.voxel.ui.data;

/**
 * Created by Eric on 2/6/2016.
 */
public class TeamStatistics {
    private String name;
    private int unitsBuilt;
    private int structuresBuilt;
    private boolean winner;
    private int unitsLost;
    private int structuresLost;

    public TeamStatistics(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getUnitsBuilt() {
        return String.valueOf(unitsBuilt);
    }

    public String getUnitsLost() {
        return String.valueOf(unitsLost);
    }

    public String getStructuresBuilt() {
        return String.valueOf(structuresBuilt);
    }

    public String getStructuresLost() {
        return String.valueOf(structuresLost);
    }

    public String isWinner() {
        return winner ? "WIN" : "LOSS";
    }

    public void incrementUnitsBuilt() {
        this.unitsBuilt++;
    }

    public void increaseStructuresBuilt() {
        this.structuresBuilt++;
    }

    public void increaseUnitsLost() {
        this.unitsLost++;
    }

    public void increaseStructuresLost() {
        this.structuresLost++;
    }
}
