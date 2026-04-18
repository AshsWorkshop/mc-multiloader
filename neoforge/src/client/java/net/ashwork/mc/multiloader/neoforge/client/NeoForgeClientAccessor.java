package net.ashwork.mc.multiloader.neoforge.client;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.base.impl.AbstractModLoaderAccessor;
import net.ashwork.mc.multiloader.api.client.ClientModLoaderAccessor;
import net.ashwork.mc.multiloader.neoforge.base.NeoForgeLoader;

/**
 * An accessor implementation for the NeoForge client
 * entrypoint.
 */
public class NeoForgeClientAccessor extends AbstractModLoaderAccessor implements ClientModLoaderAccessor {

    @Override
    public ModLoader _create(String modId) {
        return new NeoForgeLoader<>(modId, NeoForgeClientExtensionsProvider.class);
    }
}
