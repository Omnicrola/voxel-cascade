package com.omnicrola.voxel.ui.controllers;

import com.omnicrola.voxel.input.GameInputAction;
import com.omnicrola.voxel.input.listeners.OrderSelectedUnitsStopListeners;
import com.omnicrola.voxel.input.listeners.SetAttackCursorStrategyListener;
import com.omnicrola.voxel.input.listeners.SetMoveCursorStrategyListener;

/**
 * Created by Eric on 1/26/2016.
 */
public class UserActionGuiAdapter {
    private final SetMoveCursorStrategyListener moveListener;
    private final SetAttackCursorStrategyListener attackListener;
    private final OrderSelectedUnitsStopListeners stopListener;

    public UserActionGuiAdapter(SetMoveCursorStrategyListener moveListener,
                                SetAttackCursorStrategyListener attackListener,
                                OrderSelectedUnitsStopListeners stopListener) {
        this.moveListener = moveListener;
        this.attackListener = attackListener;
        this.stopListener = stopListener;
    }

    public void triggerMove() {
        this.moveListener.onAction(GameInputAction.ORDER_MOVE.trigger(), false, 0f);
    }

    public void triggerAttack() {
        this.attackListener.onAction(GameInputAction.ORDER_ATTACK.trigger(), false, 0f);
    }

    public void triggerStop() {
        this.stopListener.onAction(GameInputAction.ORDER_STOP.trigger(), false, 0f);
    }
}
