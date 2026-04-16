package net.ashwork.mc.multiloader.api.base.extension;

import java.util.function.Consumer;

/**
 * A holder that contains loader extensions for an
 * agnostic system to access.
 */
public interface ExtensionHolder {

    /**
     * Access the API to run operations on the loader. If not present,
     * throws an {@link IllegalArgumentException}.
     *
     * @param extension The key of the extension to access.
     * @return The API access.
     * @param <API> The type of the API.
     * @throws IllegalArgumentException If the API is not present on the loader.
     */
    <API> API access(LoaderExtension.Key<API> extension) throws IllegalArgumentException;

    /**
     * Accesses the API to run operations on the loader when present.
     * Otherwise, nothing occurs.
     *
     * @param extension The key of the extension to access.
     * @param ifPresent A consumer of the operations to perform on the API.
     * @param <API> The type of the API.
     */
    <API> void accessIfPresent(LoaderExtension.Key<API> extension, Consumer<API> ifPresent);
}
