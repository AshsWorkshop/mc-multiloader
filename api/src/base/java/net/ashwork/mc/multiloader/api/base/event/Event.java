package net.ashwork.mc.multiloader.api.base.event;

import java.util.function.Consumer;

/**
 * A callback that's triggered during a certain operation, captured
 * for additional invokation.
 *
 * @param <EVENT> The type of the event.
 */
public interface Event<EVENT> {

    /**
     * Registers a listener to the event, acting upon when the
     * event is triggered.
     *
     * @param listener The listener that's triggered.
     */
    void on(EVENT listener);
}
