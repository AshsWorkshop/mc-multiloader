package net.ashwork.mc.multiloader.neoforge.data.generator;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionHolder;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.ashwork.mc.multiloader.api.base.impl.AbstractExtensionHolder;
import net.ashwork.mc.multiloader.api.base.impl.ExtensionManager;
import net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * The NeoForge implementation of the {@link DataProviderGatherer}.
 */
public class NeoForgeDataProviderGatherer implements DataProviderGatherer {

    private final DataGenerator.PackGenerator pack;
    private final CompletableFuture<HolderLookup.Provider> registries;
    private ExtensionHolder extensions;

    /**
     * A basic constructor.
     *
     * @param pack The pack generator.
     * @param modId The identiifer of the mod.
     * @param registries The registry snapshot.
     * @param loader The loaded providers to resolve.
     */
    public NeoForgeDataProviderGatherer(DataGenerator.PackGenerator pack, String modId, CompletableFuture<HolderLookup.Provider> registries, ExtensionManager.Loader<NeoForgeGathererExtensionsProvider> loader) {
        this.pack = pack;
        this.registries = registries;
        this.extensions = loader.resolve(provider -> extensions -> provider.registerExtensions(modId, this, extensions));
    }

    @Override
    public DataGenerator.PackGenerator pack() {
        return this.pack;
    }

    @Override
    public CompletableFuture<HolderLookup.Provider> registries() {
        return this.registries;
    }

    @Override
    public <API> API access(LoaderExtension.Key<API> extension) throws IllegalArgumentException {
        return this.extensions.access(extension);
    }

    @Override
    public <API> void accessIfPresent(LoaderExtension.Key<API> extension, Consumer<API> ifPresent) {
        this.extensions.accessIfPresent(extension, ifPresent);
    }
}
