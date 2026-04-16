package net.ashwork.mc.multiloader.api.base;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

/**
 * A utility for creating common representations of the
 * identifier.
 */
public interface IdHelper {

    /**
     * {@return the base identifier}
     */
    String id();

    /**
     * Creates an {@link Identifier} using the {@link #id()}
     * as the namespace.
     *
     * @param path The path of the identifier.
     * @return An {@link Identifier}.
     */
    default Identifier withId(String path) {
        return Identifier.fromNamespaceAndPath(this.id(), path);
    }

    /**
     * Creates a {@link ResourceKey} using the {@link #id()}
     * as the namespace for the registry object.
     *
     * @param registry The key of the registry.
     * @param path The path of the identifier.
     * @return A {@link ResourceKey}.
     * @param <T> The type of the registry object.
     */
    default <T> ResourceKey<T> withId(ResourceKey<? extends Registry<T>> registry, String path) {
        return ResourceKey.create(registry, this.withId(path));
    }
}
