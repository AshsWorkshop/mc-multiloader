package net.ashwork.mc.multiloader.api.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;

/**
 * A builder for creating a static registry.
 *
 * @param <T> The type of the registry elements.
 */
public interface StaticRegistryBuilder<T> {

    /**
     * Marks that the registry identifiers will be synced to the client.
     *
     * @return This builder.
     */
    StaticRegistryBuilder<T> sync();

    /**
     * Adds a callback when an entry is registered to this registry.
     *
     * @param callback When an entry is registered to this registry.
     * @return This builder.
     */
    StaticRegistryBuilder<T> onEntryAdded(EntryAddedCallback<T> callback);

    /**
     * Creates the registry from the builder.
     *
     * @return The newly constructed registry.
     */
    Registry<T> create();

    /**
     * A callback that's fired when an entry is registered to the attached
     * registry.
     *
     * @param <T> The type of the registry elements.
     */
    interface EntryAddedCallback<T> {

        /**
         * When an entry is registered to this registry.
         *
         * @param rawId The raw integer identifier assigned to the entry.
         * @param id The text identifier of the entry.
         * @param value The entry value.
         */
        void onEntryAdded(int rawId, Identifier id, T value);
    }
}
