package net.ashwork.mc.multiloader.neoforge.common.registry;

import com.mojang.serialization.Codec;
import net.ashwork.mc.multiloader.api.common.registry.RegistryCreator;
import net.ashwork.mc.multiloader.api.common.registry.StaticRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * NeoForge implementation of the {@link RegistryCreator}.
 */
public class NeoForgeRegistryCreator implements RegistryCreator {

    private final List<Registry<?>> staticRegistries;
    private final List<Consumer<DataPackRegistryEvent.NewRegistry>> datapackRegistries;

    /**
     * A basic constructor.
     *
     * @param modBus The event bus for the mod instance.
     */
    public NeoForgeRegistryCreator(IEventBus modBus) {
        this.staticRegistries = new ArrayList<>();
        this.datapackRegistries = new ArrayList<>();

        modBus.addListener(this::registerStatic);
        modBus.addListener(this::registerDatapack);
    }

    private void registerStatic(NewRegistryEvent event) {
        this.staticRegistries.forEach(registry -> event.register(registry));
    }

    private void registerDatapack(DataPackRegistryEvent.NewRegistry event) {
        this.datapackRegistries.forEach(consumer -> consumer.accept(event));
    }

    @Override
    public <T> StaticRegistryBuilder createStatic(ResourceKey<Registry<T>> id) {
        return new NeoForgeStaticRegistryBuilder<>(new RegistryBuilder<>(id), this.staticRegistries::add);
    }

    @Override
    public <T> StaticRegistryBuilder createStaticWithDefault(ResourceKey<Registry<T>> id, Identifier defaultId) {
        return new NeoForgeStaticRegistryBuilder<>(new RegistryBuilder<>(id).defaultKey(defaultId), this.staticRegistries::add);
    }

    @Override
    public <T> void createDatapack(ResourceKey<Registry<T>> id, Codec<T> codec) {
        this.datapackRegistries.add(event -> event.dataPackRegistry(id, codec));
    }

    @Override
    public <T> void createDatapack(ResourceKey<Registry<T>> id, Codec<T> codec, Codec<T> networkCodec) {
        this.datapackRegistries.add(event -> event.dataPackRegistry(id, codec, networkCodec));
    }
}
