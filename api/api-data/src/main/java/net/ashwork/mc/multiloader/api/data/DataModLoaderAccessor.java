package net.ashwork.mc.multiloader.api.data;

import net.ashwork.mc.multiloader.api.base.ModLoaderAccessor;

/**
 * An acessor implementation for the data entrypoint.
 *
 * @see ModLoaderAccessor
 */
public interface DataModLoaderAccessor extends ModLoaderAccessor {

    /**
     * The accessor of the currently loaded loader.
     */
    DataModLoaderAccessor INSTANCE = ModLoaderAccessor.initialize(DataModLoaderAccessor.class);
}
