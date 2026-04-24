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
     * @param holder The holder the access is obtained from.
     * @return The API access.
     */
    API access(ExtensionHolder holder);

    /**
     * A loader extension that does not need access to the
     * backing holder.
     *
     * @param <API> The type of the API.
     */
    @FunctionalInterface
    interface WithoutHolder<API> {

        /**
         * Accesses the API to run operations on the loader.
         *
         * @return The API access.
         */
        API access();
    }

    /**
     * A unique identfier associated with a loader
     * extension.
     *
     * @param name The identifier of the extension.
     * @param <API> The type of the API.
     */
    record Key<API>(Identifier name) {

        /**
         * Constructs a loader extension in the vanilla namespace.
         *
         * @param name The path of the vanilla extension.
         * @return The vanilla extension key.
         * @param <API> The type of the API.
         */
        public static <API> Key<API> vanilla(String name) {
            return new Key<>(Identifier.withDefaultNamespace(name));
        }

        @Override
        public @NonNull String toString() {
            return this.name.toString();
        }
    }
}
