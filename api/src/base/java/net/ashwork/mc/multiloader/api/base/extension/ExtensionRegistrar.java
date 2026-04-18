package net.ashwork.mc.multiloader.api.base.extension;

/**
 * A registrar for registering the extensions a loader provides.
 */
public interface ExtensionRegistrar {

    /**
     * Provides the following extension for the loader. This method
     * should only be used if the extension makes a call to another
     * extension. Otherwise register using {@link #provide(LoaderExtension.Key, LoaderExtension.WithoutHolder)}.
     *
     * @param id The identifier of the extension.
     * @param extension The extension implementation.
     * @param dependency An extension this entry requires to run.
     * @param dependencies Additional extensions this entry requires to run.
     * @param <API> The type of the API.
     */
    <API> void provide(LoaderExtension.Key<API> id, LoaderExtension<API> extension, LoaderExtension.Key<?> dependency, LoaderExtension.Key<?>... dependencies);

    /**
     * Provides the following extension for the loader.
     *
     * @param id The identifier of the extension.
     * @param extension The extension implementation.
     * @param <API> The type of the API.
     */
    <API> void provide(LoaderExtension.Key<API> id, LoaderExtension.WithoutHolder<API> extension);
}
