package net.ashwork.mc.multiloader.fabric.data;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.data.DataModLoaderAccessor;
import net.ashwork.mc.multiloader.fabric.base.FabricLoader;

/**
 * An accessor implementation for the Fabric data
 * entrypoint.
 */
public class FabricDataAccessor implements DataModLoaderAccessor {

    @Override
    public ModLoader create(String modId) {
        return new FabricLoader<>(modId, FabricDataExtensionsProvider.class);
    }
}
