package net.ashwork.mc.multiloader.api.data.generator;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

/**
 * A builder that adds providers to generate the pack data.
 */
public interface PackBuilder {

    /**
     * Registers the provided datapack registry entries to be generated. This
     * will always run before all other added providers such that its output
     * can be used.
     *
     * @implSpec Registry entries registered to the global pack can be consumed
     * by providers in a built-in pack.
     *
     * @param registrar The registrar to register the datapack entries to.
     * @return This builder.
     */
    PackBuilder buildRegistries(Consumer<RegistrySetBuilder> registrar);

    /**
     * Registers the provided {@code bootstrap} to the datapack registry for
     * generation. This will always run before all other added providers such
     * that its output can be used.
     *
     * @implSpec Registry entries registered to the global pack can be consumed
     * by providers in a built-in pack.
     *
     * @param registry The key of the datapack registry to bootstrap.
     * @param bootstrap The registrar to register the datapack entries.
     * @param <REGISTRY> The type of the registry objects.
     * @return This builder.
     */
    default <REGISTRY> PackBuilder buildRegistry(ResourceKey<? extends Registry<REGISTRY>> registry, RegistrySetBuilder.RegistryBootstrap<REGISTRY> bootstrap) {
        return this.buildRegistries(builder -> builder.add(registry, bootstrap));
    }

    /**
     * Gathers the providers used to generate the data.
     *
     * @param gatherer The gatherer to add data providers to.
     * @return This builder.
     */
    PackBuilder gatherProviders(Consumer<DataProviderGatherer> gatherer);
}
