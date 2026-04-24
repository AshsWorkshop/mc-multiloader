package net.ashwork.mc.multiloader.api.common.event.resources;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.ExtensionHolder;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;

/**
 * Registers reload listeners and any dependencies to the logical side.
 */
public interface AddReloadListeners extends ExtensionHolder {

    /**
     * Registers reload listeners.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<AddReloadListeners> EVENT = Multiloader.extension("add_reload_listeners");

    /**
     * Adds a new {@link PreparableReloadListener} to the resource manager.
     *
     * @param id The identifier of the listener.
     * @param listener The listener to add.
     */
    void register(Identifier id, PreparableReloadListener listener);

    /**
     * Adds a dependency on listener ordering, such that {@code first}
     * runs before {@code second}, if the listener key is present.
     *
     * @param first The listener that should run before {@code second}.
     * @param second The listener that should run after {@code first}, if present.
     */
    default void dependencyIfPresent(Identifier first, LoaderExtension.Key<Identifier> second) {
        this.accessIfPresent(second, s -> this.dependency(first, s));
    }

    /**
     * Adds a dependency on listener ordering, such that {@code first}
     * runs before {@code second}, if the listener key is present.
     *
     * @param first The listener that should run before {@code second}, if present.
     * @param second The listener that should run after {@code first}.
     */
    default void dependencyIfPresent(LoaderExtension.Key<Identifier> first, Identifier second) {
        this.accessIfPresent(first, f -> this.dependency(f, second));
    }

    /**
     * Adds a dependency on listener ordering, such that {@code first}
     * runs before {@code second}.
     *
     * @param first The listener that should run before {@code second}.
     * @param second The listener that should run after {@code first}.
     */
    default void dependency(Identifier first, LoaderExtension.Key<Identifier> second) {
        this.dependency(first, this.access(second));
    }

    /**
     * Adds a dependency on listener ordering, such that {@code first}
     * runs before {@code second}.
     *
     * @param first The listener that should run before {@code second}.
     * @param second The listener that should run after {@code first}.
     */
    default void dependency(LoaderExtension.Key<Identifier> first, Identifier second) {
        this.dependency(this.access(first), second);
    }

    /**
     * Adds a dependency on listener ordering, such that {@code first}
     * runs before {@code second}.
     *
     * @param first The listener that should run before {@code second}.
     * @param second The listener that should run after {@code first}.
     */
    void dependency(Identifier first, Identifier second);

    /**
     * Common listener identifiers.
     */
    interface Keys {
        /**
         * The first listener or phase, used for adding listeners before all others.
         *
         * @extension {@link AddReloadListeners}.
         */
        LoaderExtension.Key<Identifier> FIRST = LoaderExtension.Key.vanilla("first");

        /**
         * The first listener or phase, used for adding listeners after all others.
         *
         * @extension {@link AddReloadListeners}.
         */
        LoaderExtension.Key<Identifier> LAST = LoaderExtension.Key.vanilla("last");
    }
}
