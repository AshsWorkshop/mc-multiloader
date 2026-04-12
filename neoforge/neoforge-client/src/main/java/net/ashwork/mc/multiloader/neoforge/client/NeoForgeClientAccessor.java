package net.ashwork.mc.multiloader.neoforge.client;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.client.ClientModLoaderAccessor;
import net.ashwork.mc.multiloader.neoforge.base.NeoForgeLoader;

/**
 * An accessor implementation for the NeoForge client
 * entrypoint.
 */
public class NeoForgeClientAccessor implements ClientModLoaderAccessor {

    @Override
    public ModLoader create(String modId) {
        return new NeoForgeLoader<>(modId, NeoForgeClientExtensionsProvider.class);
    }
}
