package net.ashwork.mc.multiloader.api.data.generator.provider;

import java.util.function.Consumer;

/**
 * Gathers data for the provider to generate.
 */
public interface DataGatherer {

    /**
     * Gathers data from one provider to generate.
     *
     * @param <PROVIDER> The type of the provider.
     */
    interface One<PROVIDER> {

        /**
         * Adds the provider to generate data for.
         *
         * @param provider The provider.
         */
        void add(Consumer<PROVIDER> provider);
    }

    /**
     * Gathers data from two providers to generate.
     *
     * @param <PROVIDERA> The type of the first provider.
     * @param <PROVIDERB> The type of the second provider.
     */
    interface Two<PROVIDERA, PROVIDERB> {

        /**
         * Adds the providers to generate data for.
         *
         * @param providerA The first provider.
         * @param providerB The second provider.
         */
        void add(Consumer<PROVIDERA> providerA, Consumer<PROVIDERB> providerB);
    }
}
