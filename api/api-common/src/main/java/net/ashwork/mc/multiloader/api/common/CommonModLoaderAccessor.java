package net.ashwork.mc.multiloader.api.common;

import net.ashwork.mc.multiloader.api.base.ModLoaderAccessor;

/**
 * An acessor implementation for the common entrypoint
 * present at all times.
 *
 * @see ModLoaderAccessor
 */
public interface CommonModLoaderAccessor extends ModLoaderAccessor {

    /**
     * The accessor of the currently loaded loader.
     */
    CommonModLoaderAccessor INSTANCE = ModLoaderAccessor.initialize(CommonModLoaderAccessor.class);
}
