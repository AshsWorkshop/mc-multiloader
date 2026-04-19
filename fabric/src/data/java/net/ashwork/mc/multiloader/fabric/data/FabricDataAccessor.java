package net.ashwork.mc.multiloader.fabric.data;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.base.impl.AbstractModLoaderAccessor;
import net.ashwork.mc.multiloader.api.data.DataModLoaderAccessor;

/**
 * An accessor implementation for the Fabric data
 * entrypoint.
 */
public class FabricDataAccessor extends AbstractModLoaderAccessor implements DataModLoaderAccessor {

    @Override
    protected ModLoader _create(String modId) {
        return new FabricDataLoader(modId);
    }
}
