package com.omnicrola.voxel.ui.builders;

import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.TextBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;

/**
 * Created by Eric on 3/27/2016.
 */
public abstract class AbstractGuiBuilder implements IGuiBuilder {
    protected ButtonBuilder createButton(final String token, final String text, final int width, final int height) {
        return new ButtonBuilder(token, text) {{
            alignCenter();
            valignCenter();
            height(pixels(height));
            width(pixels(width));
        }};
    }

    protected TextBuilder createText(String id, String text, final int width) {
        return new TextBuilder(id.toString()) {{
            text(text);
            font(UiConstants.DEFAULT_FONT);
            width(pixels(width));
            align(Align.Left);
            textHAlign(Align.Left);
        }};
    }

    protected TextBuilder createText(String text, final int width) {
        return new TextBuilder() {{
            text(text);
            font(UiConstants.DEFAULT_FONT);
            width(pixels(width));
            align(Align.Left);
            textHAlign(Align.Left);
        }};
    }

    protected PanelBuilder spacerH(final int percentage) {
        return new PanelBuilder() {
            {
                width(percentage(percentage));
            }
        };
    }

    protected PanelBuilder spacerV(final int percentage) {
        return new PanelBuilder() {
            {
                height(percentage(percentage));
            }
        };
    }
}
