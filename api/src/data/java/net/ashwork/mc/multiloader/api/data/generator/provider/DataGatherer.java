package net.ashwork.mc.multiloader.api.data.generator.provider;

import java.util.function.Consumer;

/**
 * Gathers data for the provider to generate.
 *
 * @param <PROVIDER> The type of the data provider.
 */
public interface DataGatherer<PROVIDER> {

    /**
     * Adds the provider to generate data for.
     *
     * @param provider The provider.
     */
    void add(Consumer<PROVIDER> provider);
}
