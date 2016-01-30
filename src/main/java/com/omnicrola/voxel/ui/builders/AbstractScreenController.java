package com.omnicrola.voxel.ui.builders;

import com.omnicrola.voxel.main.VoxelException;
import com.omnicrola.voxel.ui.SubscriberLink;
import com.omnicrola.voxel.ui.UiToken;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Eric on 1/24/2016.
 */
public abstract class AbstractScreenController implements ScreenController {
    private Nifty nifty;
    private Screen screen;

    @Override
    public void bind(Nifty nifty, Screen screen) {
        this.nifty = nifty;
        this.screen = screen;
        assertEventSubscribers(this, UiToken.BUTTON_START);
    }

    protected Screen getScreen() {
        return screen;
    }

    public Nifty getNifty() {
        return nifty;
    }

    protected static void assertEventSubscribers(ScreenController controller, UiToken... tokens) {
        List<Method> allMethods = Arrays.asList(controller.getClass().getDeclaredMethods());
        List<Method> linkedMethods = allMethods
                .stream()
                .filter(m -> hasLink(m))
                .filter(m -> hasSubscriber(m))
                .collect(Collectors.toList());
        linkedMethods.stream().forEach(method -> {
            UiToken token = method.getAnnotation(SubscriberLink.class).value();
            String linkId = method.getAnnotation(NiftyEventSubscriber.class).id();
            if (!token.toString().equals(linkId)) {
                throw new VoxelException("Broken UI event subscriber. Method is listening for '" +
                        linkId + "' but enum is '" + token + "'");
            }
        });
    }

    private static boolean hasSubscriber(Method method) {
        NiftyEventSubscriber subscriberLink = method.getAnnotation(NiftyEventSubscriber.class);
        return subscriberLink != null;
    }

    private static boolean hasLink(Method method) {
        SubscriberLink subscriberLink = method.getAnnotation(SubscriberLink.class);
        return subscriberLink != null;
    }

    @Override
    public void onStartScreen() {

    }

    @Override
    public void onEndScreen() {

    }
}