package net.ashwork.mc.multiloader.neoforge.common;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.common.CommonModLoaderAccessor;
import net.ashwork.mc.multiloader.neoforge.base.NeoForgeLoader;

/**
 * An accessor implementation for the NeoForge common
 * entrypoint.
 */
public class NeoForgeCommonAccessor implements CommonModLoaderAccessor {

    @Override
    public ModLoader create(String modId) {
        return new NeoForgeLoader<>(modId, NeoForgeCommonExtensionsProvider.class);
    }
}
