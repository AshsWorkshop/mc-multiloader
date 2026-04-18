package net.ashwork.mc.multiloader.neoforge.data;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.base.impl.AbstractModLoaderAccessor;
import net.ashwork.mc.multiloader.api.data.DataModLoaderAccessor;
import net.ashwork.mc.multiloader.neoforge.base.NeoForgeLoader;

/**
 * An accessor implementation for the NeoForge data
 * entrypoint.
 */
public class NeoForgeDataAccessor extends AbstractModLoaderAccessor implements DataModLoaderAccessor {

    @Override
    public ModLoader _create(String modId) {
        return new NeoForgeLoader<>(modId, NeoForgeDataExtensionsProvider.class);
    }
}
