package net.ashwork.mc.multiloader.fabric.data.generator;

import net.ashwork.mc.multiloader.api.base.impl.extension.ExtensionManager;
import net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer;
import net.ashwork.mc.multiloader.api.data.generator.PackBuilder;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.RegistryDataLoader;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * The Fabric implementation of the {@link PackBuilder}.
 */
public abstract sealed class FabricPackBuilder implements PackBuilder permits FabricPackBuilder.BuiltIn, FabricPackBuilder.Global {

    private final List<Consumer<RegistrySetBuilder>> registrars;
    private final List<Consumer<DataProviderGatherer>> gatherers;

    /**
     * A basic constructor.
     */
    protected FabricPackBuilder() {
        this.registrars = new ArrayList<>();
        this.gatherers = new ArrayList<>();
    }

    @Override
    public FabricPackBuilder buildRegistries(Consumer<RegistrySetBuilder> registrar) {
        this.registrars.add(registrar);
        return this;
    }

    @Override
    public FabricPackBuilder gatherProviders(Consumer<DataProviderGatherer> gatherer) {
        this.gatherers.add(gatherer);
        return this;
    }

    /**
     * Resolves the datapack registrars and registers them to a registry.
     *
     * @param registry The registry set to register to.
     */
    public void registerRegistries(RegistrySetBuilder registry) {
        this.registrars.forEach(registrar -> registrar.accept(registry));
    }

    /**
     * Resolves and registers the stored {@link DataProvider}s.
     *
     * @param generator The data generator.
     * @param modId The identiifer of the mod.
     * @param registries The registry snapshot.
     * @param loader The loaded providers to resolve.
     */
    public abstract void registerProviders(FabricDataGenerator generator, String modId, CompletableFuture<HolderLookup.Provider> registries, ExtensionManager.Loader<FabricGathererExtensionsProvider> loader);

    protected void registerProviders(FabricDataGenerator.Pack pack, String modId, CompletableFuture<HolderLookup.Provider> registries, ExtensionManager.Loader<FabricGathererExtensionsProvider> loader) {
        var gatherer = new FabricDataProviderGatherer(pack, modId, registries, loader);
        // Add fabric registries
        pack.addProvider((output, lookup) -> new FabricDynamicRegistryProvider(output, lookup) {

            @Override
            protected void configure(HolderLookup.Provider registries, Entries entries) {
                Set<ResourceKey<?>> seen = new HashSet<>();

                List.of(
                        RegistryDataLoader.DIMENSION_REGISTRIES,
                        RegistryDataLoader.WORLDGEN_REGISTRIES,
                        RegistryDataLoader.SYNCHRONIZED_REGISTRIES
                ).stream().flatMap(list -> list.stream()).forEach(data -> {
                    if (seen.add(data.key())) {
                        entries.addAll(registries.lookupOrThrow(data.key()));
                    }
                });
            }

            @Override
            public String getName() {
                return modId + " registries";
            }
        });
        this.gatherers.forEach(consumer -> consumer.accept(gatherer));
    }

    /**
     * The NeoForge implementation of the global {@link PackBuilder}.
     */
    public static final class Global extends FabricPackBuilder {

        @Override
        public void registerProviders(FabricDataGenerator generator, String modId, CompletableFuture<HolderLookup.Provider> registries, ExtensionManager.Loader<FabricGathererExtensionsProvider> loader) {
            this.registerProviders(generator.createPack(), modId, registries, loader);
        }
    }

    /**
     * The NeoForge implementation of the built-in {@link PackBuilder}s.
     */
    public static final class BuiltIn extends FabricPackBuilder {

        private final Identifier id;

        /**
         * A basic constructor.
         *
         * @param id The unique identifier of the built-in pack.
         */
        public BuiltIn(Identifier id) {
            super();
            this.id = id;
        }

        @Override
        public void registerProviders(FabricDataGenerator generator, String modId, CompletableFuture<HolderLookup.Provider> registries, ExtensionManager.Loader<FabricGathererExtensionsProvider> loader) {
            this.registerProviders(generator.createBuiltinResourcePack(this.id), modId, registries, loader);
        }
    }
}
