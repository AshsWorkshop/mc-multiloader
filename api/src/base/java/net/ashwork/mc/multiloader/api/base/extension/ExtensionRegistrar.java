package net.ashwork.mc.multiloader.api.base.extension;

/**
 * A registrar for registering the extensions a loader provides.
 */
public interface ExtensionRegistrar {

    /**
     * Provides the following extension for the loader.
     *
     * @param id The identifier of the extension.
     * @param extension The extension implementation.
     * @param <API> The type of the API.
     */
    <API> void provide(LoaderExtension.Key<API> id, LoaderExtension<API> extension);
}
