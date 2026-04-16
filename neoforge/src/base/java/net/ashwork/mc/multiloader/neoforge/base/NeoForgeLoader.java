package net.ashwork.mc.multiloader.neoforge.base;

import net.ashwork.mc.multiloader.api.base.impl.AbstractModLoader;
import net.neoforged.fml.ModList;

/**
 * An implementation of the NeoForge loader.
 *
 * @param <PROVIDER> The type of the extension provider.
 */
public class NeoForgeLoader<PROVIDER extends NeoForgeExtensionsProvider> extends AbstractModLoader<PROVIDER> {

    /**
     * A basic constructor.
     *
     * @param id     The base identifier of the mod.
     * @param providerClass The class of the extension provider.
     */
    public NeoForgeLoader(String id, Class<PROVIDER> providerClass) {
        var modBus = ModList.get().getModContainerById(id).get().getEventBus();
        super(id, providerClass, provider -> extensions ->  provider.registerExtensions(id, modBus, extensions));
    }
}
