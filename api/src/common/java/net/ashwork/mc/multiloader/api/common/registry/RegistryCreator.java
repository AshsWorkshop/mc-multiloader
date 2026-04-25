package net.ashwork.mc.multiloader.api.common.registry;

import com.mojang.serialization.Codec;
import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

/**
 * An accessor for creating registries.
 */
public interface RegistryCreator {

    /**
     * Accessor for creating registries.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<RegistryCreator> EXT = Multiloader.extension("registry_creator");

    /**
     * Creates the builder to construct a static registry.
     *
     * @param id The identifier of the registry.
     * @return The registry builder.
     * @param <T> The type of the registry elements.
     */
    <T> StaticRegistryBuilder createStatic(ResourceKey<Registry<T>> id);

    /**
     * Creates the builder to construct a static registry. If an
     * accessed identifier does not exist, it will default to the
     * specified entry.
     *
     * @param id The identifier of the registry.
     * @param defaultId The identifier of the entry treated as the fallback.
     * @return The registry builder.
     * @param <T> The type of the registry elements.
     */
    <T> StaticRegistryBuilder createStaticWithDefault(ResourceKey<Registry<T>> id, Identifier defaultId);

    /**
     * Creates a datapack registry.
     *
     * @param id The identifier of the registry.
     * @param codec The codec used to serialize and deserialze the elements of the registry.
     * @param <T> The type of the registry elements.
     */
    <T> void createDatapack(ResourceKey<Registry<T>> id, Codec<T> codec);

    /**
     * Creates a datapack registry. The registry entries will be synced
     * to the client.
     *
     * @param id The identifier of the registry.
     * @param codec The codec used to serialize and deserialze the elements of the registry.
     * @param networkCodec The codec used to sync and deserialize the elements on the client.
     * @param <T> The type of the registry elements.
     */
    <T> void createDatapack(ResourceKey<Registry<T>> id, Codec<T> codec, Codec<T> networkCodec);
}
