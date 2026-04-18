package net.ashwork.mc.multiloader.api.data.generator;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionHolder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;

import java.util.concurrent.CompletableFuture;

/**
 * A collector to add data providers to the generator.
 */
public interface DataProviderGatherer extends ExtensionHolder {

    /**
     * {@return the pack generator}
     */
    DataGenerator.PackGenerator pack();

    /**
     * {@return the registry snapshot}
     */
    CompletableFuture<HolderLookup.Provider> registries();

    /**
     * Constructs and adds a data provider to the pack generator.
     *
     * @param provider The factory constructing the provider.
     * @return The constructed {@link DataProvider}.
     * @param <PROVIDER> The type of the data provider.
     */
    default <PROVIDER extends DataProvider> PROVIDER add(DataProvider.Factory<PROVIDER> provider) {
        return this.pack().addProvider(provider);
    }

    /**
     * Constructs and adds a data provider to the pack generator.
     *
     * @param provider The factory constructing the provider.
     * @return The constructed {@link DataProvider}.
     * @param <PROVIDER> The type of the data provider.
     */
    default <PROVIDER extends DataProvider> PROVIDER add(FactoryWithRegistries<PROVIDER> provider) {
        return this.add((output) -> provider.create(output, this.registries()));
    }

    /**
     * A data provider factory that creates a {@link DataProvider} given the
     * output and the current registry snapshot.
     *
     * @param <PROVIDER> The type of the data provider.
     */
    interface FactoryWithRegistries<PROVIDER extends DataProvider> {

        /**
         * Creates a data provider.
         *
         * @param output The output of the generated data for the pack.
         * @param registries A snapshot of the registries.
         * @return The constructed {@link DataProvider}.
         */
        PROVIDER create(PackOutput output, CompletableFuture<HolderLookup.Provider> registries);
    }
}
