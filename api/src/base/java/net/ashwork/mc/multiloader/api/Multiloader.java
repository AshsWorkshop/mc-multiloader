package net.ashwork.mc.multiloader.api;

import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.resources.Identifier;

/**
 * The main interface.
 */
public interface Multiloader {

    /**
     * The identifier of the multiloader.
     */
    String ID = "ashsmultiloader";

    /**
     * Creates a {@link LoaderExtension.Key} for the multiloader.
     *
     * @param path The path of the identifier.
     * @return A {@link LoaderExtension.Key}.
     * @param <API> The type of the API.
     */
    static <API> LoaderExtension.Key<API> extension(String path) {
        return new LoaderExtension.Key<>(Identifier.fromNamespaceAndPath(ID, path));
    }
}
