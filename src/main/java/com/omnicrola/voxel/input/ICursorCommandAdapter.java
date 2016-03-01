package com.omnicrola.voxel.input;

import com.omnicrola.voxel.input.actions.SelectUnitsCursorStrategy;

/**
 * Created by Eric on 2/29/2016.
 */
public interface ICursorCommandAdapter {
    SelectUnitsCursorStrategy setSelectStrategy();

    void setAttackStrategy();

    void setMoveStrategy();

    void setBuildStrategy(SelectionGroup selectionGroup);

    void setBuildUnitStrategy(int unitId, float buildRadius, SelectionGroup selectionGroup);

    void setBuildVoxelStrategy(byte type);

    void setHarvestStrategy();
}
