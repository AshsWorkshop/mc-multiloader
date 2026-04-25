package net.ashwork.mc.multiloader.fabric.common.registry;

import com.mojang.serialization.Codec;
import net.ashwork.mc.multiloader.api.common.registry.RegistryCreator;
import net.ashwork.mc.multiloader.api.common.registry.StaticRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Fabric implementation of the {@link StaticRegistryBuilder}.
 *
 * @param <T> The type of the registry elements.
 */
public class FabricStaticRegistryBuilder<T> implements StaticRegistryBuilder<T> {

    private final FabricRegistryBuilder<T, ? extends Registry<T>> builder;
    private final List<RegistryEntryAddedCallback<T>> entryAddedCallbacks;

    /**
     * A basic constructor.
     *
     * @param builder The registry builder delegate.
     */
    public FabricStaticRegistryBuilder(FabricRegistryBuilder<T, ? extends Registry<T>> builder) {
        this.builder = builder;
        this.entryAddedCallbacks = new ArrayList<>();
    }

    @Override
    public StaticRegistryBuilder<T> sync() {
        this.builder.attribute(RegistryAttribute.SYNCED);
        return this;
    }

    @Override
    public StaticRegistryBuilder<T> onEntryAdded(EntryAddedCallback<T> callback) {
        this.entryAddedCallbacks.add(callback::onEntryAdded);
        return this;
    }

    @Override
    public Registry<T> create() {
        var registry = this.builder.buildAndRegister();
        if (!this.entryAddedCallbacks.isEmpty()) {
            var event = RegistryEntryAddedCallback.event(registry);
            this.entryAddedCallbacks.forEach(callback -> event.register(callback));
        }
        return registry;
    }
}
