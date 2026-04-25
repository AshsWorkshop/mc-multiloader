package net.ashwork.mc.multiloader.neoforge.common.registry;

import net.ashwork.mc.multiloader.api.common.registry.StaticRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.function.Consumer;

/**
 * NeoForge implementation of the {@link StaticRegistryBuilder}.
 *
 * @param <T> The type of the registry elements.
 */
public class NeoForgeStaticRegistryBuilder<T> implements StaticRegistryBuilder<T> {

    private final RegistryBuilder<T> builder;
    private final Consumer<Registry<T>> callback;

    /**
     * A basic constructor.
     *
     * @param builder The registry builder delegate.
     * @param callback A callback for registering the constructed registry
     */
    public NeoForgeStaticRegistryBuilder(RegistryBuilder<T> builder, Consumer<Registry<T>> callback) {
        this.builder = builder;
        this.callback = callback;
    }

    @Override
    public StaticRegistryBuilder<T> sync() {
        this.builder.sync(true);
        return this;
    }

    @Override
    public StaticRegistryBuilder<T> onEntryAdded(EntryAddedCallback<T> callback) {
        this.builder.onAdd((registry, rawId, id, value) -> callback.onEntryAdded(rawId, id.identifier(), value));
        return this;
    }

    @Override
    public Registry<T> create() {
        var registry = this.builder.create();
        this.callback.accept(registry);
        return registry;
    }
}
