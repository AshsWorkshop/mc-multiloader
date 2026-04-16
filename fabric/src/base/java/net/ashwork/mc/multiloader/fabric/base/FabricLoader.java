package net.ashwork.mc.multiloader.fabric.base;

import net.ashwork.mc.multiloader.api.base.impl.AbstractModLoader;

/**
 * An implementation of the Fabric loader.
 *
 * @param <PROVIDER> The type of the extension provider.
 */
public class FabricLoader<PROVIDER extends FabricExtensionsProvider> extends AbstractModLoader<PROVIDER> {

    /**
     * A basic constructor.
     *
     * @param id     The base identifier of the mod.
     * @param providerClass The class of the extension provider.
     */
    public FabricLoader(String id, Class<PROVIDER> providerClass) {
        super(id, providerClass, provider -> extensions ->  provider.registerExtensions(id, extensions));
    }
}
