package net.ashwork.mc.multiloader.api.base.impl;

import com.google.common.collect.ImmutableMap;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.MutableGraph;
import net.ashwork.mc.multiloader.api.base.extension.ExtensionHolder;
import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;

import java.util.Map;
import java.util.ServiceLoader;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * A manager for handling the extensions a loader implements.
 */
public final class ExtensionManager implements ExtensionHolder {

    private final Map<LoaderExtension.Key<?>, LoaderExtension<?>> extensions;

    /**
     * Loads and resolves the extensions from a given provider. This implementation is meant
     * to allow ambiguity towards the backing provider, allowing loaders to create registration
     * methods with additional parameters.
     *
     * @param providerClass The class of the extension provider.
     * @param method The currying method to register extensions to.
     * @return A {@link ExtensionHolder} for the provider.
     * @param <PROVIDER> The type of the extension provider.
     */
    public static <PROVIDER> ExtensionManager resolve(Class<PROVIDER> providerClass, Function<PROVIDER, Consumer<ExtensionRegistrar>> method) {
        return load(providerClass).resolve(method);
    }

    /**
     * Loads the providers that register extensions. This implementation is meant
     * to allow ambiguity towards the backing provider, allowing loaders to create registration
     * methods with additional parameters.
     *
     * @param providerClass The class of the extension provider.
     * @return A {@link ExtensionManager.Loader} containing the providers.
     * @param <PROVIDER> The type of the extension provider.
     */
    public static <PROVIDER> ExtensionManager.Loader<PROVIDER> load(Class<PROVIDER> providerClass) {
        // Load providers
        return new Loader<>(ServiceLoader.load(providerClass));
    }

    private ExtensionManager(Map<LoaderExtension.Key<?>, LoaderExtension<?>> extensions) {
        this.extensions = extensions;
    }

    @Override
    public <API> API access(LoaderExtension.Key<API> extension) throws IllegalArgumentException {
        if (this.extensions.containsKey(extension)) {
            return (API) this.extensions.get(extension).access(this);
        }

        throw new IllegalArgumentException("Extension '" + extension + "' is not implemented on the current holder");
    }

    @Override
    public <API> void accessIfPresent(LoaderExtension.Key<API> extension, Consumer<API> ifPresent) {
        if (this.extensions.containsKey(extension)){
            ifPresent.accept((API) this.extensions.get(extension).access(this));
        }
    }

    /**
     * A record that holds the loaded services for resolution.
     *
     * @param services The loaded serivces.
     * @param <PROVIDER> The type of the provider to register extensions.
     */
    public static record Loader<PROVIDER>(ServiceLoader<PROVIDER> services) {

        /**
         * Resolves the extensions for the provider. This implementation is meant to allow
         * ambiguity towards the backing provider, allowing loaders to create registration
         * methods with additional parameters.
         *
         * @param method The currying method to register extensions to.
         * @return A {@link ExtensionHolder} for the provider.
         */
        public ExtensionManager resolve(Function<PROVIDER, Consumer<ExtensionRegistrar>> method) {
            var builder = new Builder();

            // Register extensions
            this.services.forEach(provider -> method.apply(provider).accept(builder));

            return builder.build();
        }
    }

    private static final class Builder implements ExtensionRegistrar {

        private final ImmutableMap.Builder<LoaderExtension.Key<?>, LoaderExtension<?>> builder;
        private final MutableGraph<LoaderExtension.Key<?>> graph = GraphBuilder.directed().nodeOrder(ElementOrder.insertion()).build();

        private Builder() {
            this.builder = ImmutableMap.builder();
        }

        @Override
        public <API> void provide(LoaderExtension.Key<API> id, LoaderExtension<API> extension, LoaderExtension.Key<?> dependency, LoaderExtension.Key<?>... dependencies) {
            this.builder.put(id, extension);
            this.graph.addNode(id);
            this.graph.putEdge(dependency, id);
            for (var dep : dependencies) this.graph.putEdge(dep, id);
        }

        @Override
        public <API> void provide(LoaderExtension.Key<API> id, LoaderExtension.WithoutHolder<API> extension) {
            this.builder.put(id, holder -> extension.access());
        }

        private ExtensionManager build() {
            if (Graphs.hasCycle(this.graph)) {
                throw new IllegalStateException("The extension graph contains a cycle.");
            }
            return new ExtensionManager(this.builder.buildOrThrow());
        }

    }
}
