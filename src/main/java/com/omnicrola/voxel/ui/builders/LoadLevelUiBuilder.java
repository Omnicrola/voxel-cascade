package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.ui.UiAdapter;
import com.omnicrola.voxel.ui.UiScreen;
import com.omnicrola.voxel.ui.UiToken;
import com.omnicrola.voxel.ui.controllers.LoadLevelScreenController;
import de.lessvoid.nifty.builder.*;
import de.lessvoid.nifty.tools.Color;

/**
 * Created by Eric on 4/10/2016.
 */
public class LoadLevelUiBuilder extends AbstractGuiBuilder {
    @Override
    public void build(UiAdapter uiAdapter) {

        String screenName = UiScreen.LOAD_LEVEL.toString();
        LoadLevelScreenController controller = new LoadLevelScreenController(uiAdapter);

        uiAdapter.addScreen(screenName, new ScreenBuilder(screenName) {{
            controller(controller);
            layer(new LayerBuilder("background") {
                {
                    childLayoutCenter();
                    backgroundColor("#000f");
                    image(new ImageBuilder() {{
                        filename("Interface/main-menu-bg.png");
                    }});
                }
            });
            layer(new LayerBuilder("background") {
                {
                    childLayoutVertical();
                    panel(spacerH(33));
                    panel(new PanelBuilder() {{
                        childLayoutVertical();
                        backgroundColor(Color.BLACK);
                        onActiveEffect(new EffectBuilder("border") {{
                            effectParameter("color", "#0008");
                        }});

                        panel(spacerV(33));
                        panel(addLoadingStatusPanel());
                        panel(spacerV(33));
                    }});
                    panel(spacerH(33));
                }
            });

        }});
    }

    private PanelBuilder addLoadingStatusPanel() {
        return new PanelBuilder() {{
            childLayoutHorizontal();
            text(createText(UiToken.Loading.STATUS_LABEL, "Status", 300));
            text(createText(UiToken.Loading.PERCENTAGE, "0%", 50));
        }};
    }
}
