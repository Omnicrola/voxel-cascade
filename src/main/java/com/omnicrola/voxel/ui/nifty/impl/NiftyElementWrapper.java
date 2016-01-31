package com.omnicrola.voxel.ui.nifty.impl;

import com.omnicrola.voxel.ui.nifty.IUiElement;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ElementBuilder;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;
import de.lessvoid.nifty.screen.Screen;

/**
 * Created by Eric on 1/30/2016.
 */
public class NiftyElementWrapper implements IUiElement {
    private Nifty nifty;
    private Screen screen;
    private Element element;

    public NiftyElementWrapper(Nifty nifty, Screen screen, Element element) {
        this.nifty = nifty;
        this.screen = screen;
        this.element = element;
    }

    @Override
    public void removeAllChildren() {
        this.element.getElements().forEach(e -> e.markForRemoval());
        this.element.layoutElements();
    }

    @Override
    public void addElement(ElementBuilder elementBuilder) {
        this.element.add(elementBuilder.build(this.nifty, this.screen, this.element));
    }

    @Override
    public void setText(String text) {
        this.element.getRenderer(TextRenderer.class).setText(text);
    }
}
