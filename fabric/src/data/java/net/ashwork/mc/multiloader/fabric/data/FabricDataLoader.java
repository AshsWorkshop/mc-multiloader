package net.ashwork.mc.multiloader.fabric.data;

import net.ashwork.mc.multiloader.fabric.base.FabricLoader;

/**
 * An implementation of the Fabric loader for the data entrypoint.
 */
public class FabricDataLoader extends FabricLoader<FabricDataExtensionsProvider> {

    /**
     * A basic constructor.
     *
     * @param id                                The base identifier of the mod.
     */
    public FabricDataLoader(String id) {
        super(id, FabricDataExtensionsProvider.class);
    }
}
