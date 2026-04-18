package net.ashwork.mc.multiloader.api.common.registry;

import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

/**
 * An accessor to the registrar to register registry
 * objects.
 */
public interface RegistrarAccessor {

    /**
     * Accessor for creating registrars.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<RegistrarAccessor> EXT = new LoaderExtension.Key<>(Identifier.fromNamespaceAndPath("multiloader", "registrar_accessor"));

    /**
     * Creates a registrar to register registry objects to
     * the given registry. Assumes the namespace is the mod
     * accessing the registrar.
     *
     * @param registry The key of the registry to register to.
     * @return A {@link Registrar}.
     * @param <REGISTRY> The type of the registry objects.
     */
    <REGISTRY> Registrar<REGISTRY> create(ResourceKey<? extends Registry<REGISTRY>> registry);

    /**
     * Creates a registrar to register registry objects to
     * the given registry.
     *
     * @param registry The key of the registry to register to.
     * @param namespace The namespace of the objects to register.
     * @return A {@link Registrar}.
     * @param <REGISTRY> The type of the registry objects.
     */
    <REGISTRY> Registrar<REGISTRY> create(ResourceKey<? extends Registry<REGISTRY>> registry, String namespace);
}
