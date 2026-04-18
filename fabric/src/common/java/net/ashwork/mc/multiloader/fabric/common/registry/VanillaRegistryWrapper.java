package net.ashwork.mc.multiloader.fabric.common.registry;

import net.ashwork.mc.multiloader.api.common.registry.Registrar;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.function.Function;

/**
 * A registrar that wraps around a vanilla {@link Registry}.
 *
 * @param delegate The registry delegate.
 * @param namespace The namespace of the objects to register.
 * @param <REGISTRY> The type of the registry objects.
 */
public record VanillaRegistryWrapper<REGISTRY>(Registry<REGISTRY> delegate, String namespace) implements Registrar<REGISTRY> {

    /**
     * A constructor that resolves the registry from its key.
     *
     * @param key The key of the registry to register to.
     * @param namespace The namespace of the objects to register.
     */
    public VanillaRegistryWrapper(ResourceKey<? extends Registry<REGISTRY>> key, String namespace) {
        Registry<REGISTRY> registry = (Registry<REGISTRY>) BuiltInRegistries.REGISTRY.getValue(key.identifier());
        this(registry, namespace);
    }

    @Override
    public <IMPL extends REGISTRY> Holder<IMPL> register(String name, Function<Identifier, IMPL> factory) {
        var id = Identifier.fromNamespaceAndPath(namespace, name);
        return Registry.registerForHolder(this.delegate, id, factory.apply(id));
    }
}
