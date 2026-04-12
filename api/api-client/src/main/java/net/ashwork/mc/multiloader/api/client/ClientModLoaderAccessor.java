package net.ashwork.mc.multiloader.api.client;

import net.ashwork.mc.multiloader.api.base.ModLoaderAccessor;

/**
 * An acessor implementation for the client entrypoint.
 *
 * @see ModLoaderAccessor
 */
public interface ClientModLoaderAccessor extends ModLoaderAccessor {

    /**
     * The accessor of the currently loaded loader.
     */
    ClientModLoaderAccessor INSTANCE = ModLoaderAccessor.initialize(ClientModLoaderAccessor.class);
}
