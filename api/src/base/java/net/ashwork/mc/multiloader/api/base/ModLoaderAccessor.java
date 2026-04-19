package net.ashwork.mc.multiloader.api.base;

import java.util.ServiceLoader;

/**
 * An accessor for a mod loader entrypoint. The implementing instance
 * will be loaded via {@link ServiceLoader#load(Class)}.
 */
public interface ModLoaderAccessor {

    /**
     * Creates an entrypoint to access the mod loader.
     *
     * @param modId The identifier of the mod.
     * @return The mod loader layer.
     */
    ModLoader create(String modId);

    /**
     * Initializes the accessor to create the mod loader entrypoint.
     *
     * @param accessorClass The class of the accessor to load.
     * @return The accessor instance.
     * @param <ACCESSOR> The type of the accessor.
     */
    static <ACCESSOR extends ModLoaderAccessor> ACCESSOR initialize(Class<ACCESSOR> accessorClass) {
        return ServiceLoader.load(accessorClass, ModLoaderAccessor.class.getClassLoader()).findFirst()
                .orElseThrow(() -> new IllegalStateException("No loader acessor is available for: " + accessorClass));
    }
}
