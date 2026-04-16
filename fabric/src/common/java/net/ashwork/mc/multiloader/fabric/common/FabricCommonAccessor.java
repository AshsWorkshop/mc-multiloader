package net.ashwork.mc.multiloader.fabric.common;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.common.CommonModLoaderAccessor;
import net.ashwork.mc.multiloader.fabric.base.FabricLoader;

/**
 * An accessor implementation for the Fabric common
 * entrypoint.
 */
public class FabricCommonAccessor implements CommonModLoaderAccessor {

    @Override
    public ModLoader create(String modId) {
        return new FabricLoader<>(modId, FabricCommonExtensionsProvider.class);
    }
}
