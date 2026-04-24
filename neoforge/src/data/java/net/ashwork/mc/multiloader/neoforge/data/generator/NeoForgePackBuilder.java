package net.ashwork.mc.multiloader.neoforge.data.generator;

import net.ashwork.mc.multiloader.api.base.impl.extension.ExtensionManager;
import net.ashwork.mc.multiloader.api.common.event.resources.RegisterBuiltInPacks;
import net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer;
import net.ashwork.mc.multiloader.api.data.generator.PackBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * The NeoForge implementation of the {@link PackBuilder}.
 */
public abstract sealed class NeoForgePackBuilder implements PackBuilder permits NeoForgePackBuilder.BuiltIn, NeoForgePackBuilder.Global {

    private final List<Consumer<RegistrySetBuilder>> registrars;
    private final List<Consumer<DataProviderGatherer>> gatherers;

    /**
     * A basic constructor.
     */
    protected NeoForgePackBuilder() {
        this.registrars = new ArrayList<>();
        this.gatherers = new ArrayList<>();
    }

    @Override
    public NeoForgePackBuilder buildRegistries(Consumer<RegistrySetBuilder> registrar) {
        this.registrars.add(registrar);
        return this;
    }

    @Override
    public NeoForgePackBuilder gatherProviders(Consumer<DataProviderGatherer> gatherer) {
        this.gatherers.add(gatherer);
        return this;
    }

    /**
     * Resolves the datapack registrars and registers them to a registry.
     *
     * @param callback A consumer operating on the resolved registry set.
     */
    public void registerRegistries(Consumer<RegistrySetBuilder> callback) {
        var registry = new RegistrySetBuilder();
        this.registrars.forEach(registrar -> registrar.accept(registry));
        callback.accept(registry);
    }

    /**
     * Resolves and registers the stored {@link DataProvider}s.
     *
     * @param generator The data generator.
     * @param modId The identiifer of the mod.
     * @param registries The registry snapshot.
     * @param loader The loaded providers to resolve.
     */
    public abstract void registerProviders(DataGenerator generator, String modId, CompletableFuture<HolderLookup.Provider> registries, ExtensionManager.Loader<NeoForgeGathererExtensionsProvider> loader);

    protected void registerProviders(DataGenerator.PackGenerator pack, String modId, CompletableFuture<HolderLookup.Provider> registries, ExtensionManager.Loader<NeoForgeGathererExtensionsProvider> loader) {
        var gatherer = new NeoForgeDataProviderGatherer(pack, modId, registries, loader);
        this.gatherers.forEach(consumer -> consumer.accept(gatherer));
    }

    /**
     * The NeoForge implementation of the global {@link PackBuilder}.
     */
    public static final class Global extends NeoForgePackBuilder {

        @Override
        public void registerProviders(DataGenerator generator, String modId, CompletableFuture<HolderLookup.Provider> registries, ExtensionManager.Loader<NeoForgeGathererExtensionsProvider> loader) {
            this.registerProviders(generator.getPackGenerator(true, modId,""), modId, registries, loader);
        }
    }

    /**
     * The NeoForge implementation of the built-in {@link PackBuilder}s.
     */
    public static final class BuiltIn extends NeoForgePackBuilder {

        private final Identifier id;

        /**
         * A basic constructor.
         *
         * @param id The unique identifier of the built-in pack.
         * @param withMetadata The metadata of the built-in pack.
         */
        public BuiltIn(Identifier id, UnaryOperator<PackMetadataGenerator> withMetadata) {
            super();
            this.id = id;
            this.gatherProviders(gatherer -> gatherer.add(output ->
                    withMetadata.apply(new PackMetadataGenerator(output))
            ));
        }

        @Override
        public void registerProviders(DataGenerator generator, String modId, CompletableFuture<HolderLookup.Provider> registries, ExtensionManager.Loader<NeoForgeGathererExtensionsProvider> loader) {
            // Register registries for pack first
            var pack = generator.getPackGenerator(true, this.id.toString(), "resourcepacks/" + (this.id.getNamespace() == modId ? "" : (this.id.getNamespace() + "/")) + this.id.getPath());
            AtomicReference<CompletableFuture<HolderLookup.Provider>> registriesWrapper = new AtomicReference<>(registries);
            this.registerRegistries(registry -> {
                var provider = pack.addProvider(
                        output -> new DatapackBuiltinEntriesProvider(output, registriesWrapper.get(), registry, Set.of(modId))
                );
                registriesWrapper.set(provider.getRegistryProvider());
            });

            // Then register providers
            this.registerProviders(pack, modId, registriesWrapper.get(), loader);
        }
    }
}
