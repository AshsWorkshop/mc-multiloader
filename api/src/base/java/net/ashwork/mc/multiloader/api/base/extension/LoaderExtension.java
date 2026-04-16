package net.ashwork.mc.multiloader.api.base.extension;

import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

/**
 * An extension that supplies an API that is agnostic
 * to the current loader.
 *
 * @param <API> The type of the API.
 */
@FunctionalInterface
public interface LoaderExtension<API> {

    /**
     * Accesses the API to run operations on the loader.
     *
     * @return The API access.
     */
    API access();

    /**
     * A unique identfier associated with a loader
     * extension.
     *
     * @param name The identifier of the extension.
     * @param <API> The type of the API.
     */
    record Key<API>(Identifier name) {

        @Override
        public @NonNull String toString() {
            return this.name.toString();
        }
    }
}
