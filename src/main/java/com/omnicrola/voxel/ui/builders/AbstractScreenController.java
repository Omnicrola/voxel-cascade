package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.nifty.IUi;
import com.omnicrola.voxel.ui.nifty.impl.NiftyUiWrapper;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * Created by Eric on 1/24/2016.
 */
public abstract class AbstractScreenController implements ScreenController {
    private NiftyUiWrapper niftyUiWrapper;
    private boolean isBound = false;

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.niftyUiWrapper = new NiftyUiWrapper(nifty, screen);
        this.isBound = true;
    }

    protected IUi ui() {
        return this.niftyUiWrapper;
    }

    @Override
    public final void onStartScreen() {
        screenOpen();
    }

    protected abstract void screenOpen();

    @Override
    public final void onEndScreen() {
        screenClose();
    }

    protected abstract void screenClose();

    public boolean isBound() {
        return this.isBound;
    }
}
