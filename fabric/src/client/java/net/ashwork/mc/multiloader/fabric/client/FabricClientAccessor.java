package net.ashwork.mc.multiloader.fabric.client;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.base.impl.AbstractModLoaderAccessor;
import net.ashwork.mc.multiloader.api.client.ClientModLoaderAccessor;
import net.ashwork.mc.multiloader.fabric.base.FabricLoader;

/**
 * An accessor implementation for the Fabric client
 * entrypoint.
 */
public class FabricClientAccessor extends AbstractModLoaderAccessor implements ClientModLoaderAccessor {

    @Override
    public ModLoader _create(String modId) {
        return new FabricLoader<>(modId, FabricClientExtensionsProvider.class);
    }
}
