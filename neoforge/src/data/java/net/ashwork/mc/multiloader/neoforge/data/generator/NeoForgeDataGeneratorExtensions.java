package net.ashwork.mc.multiloader.neoforge.data.generator;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.data.generator.PackFactory;
import net.ashwork.mc.multiloader.neoforge.data.NeoForgeDataExtensionsProvider;
import net.neoforged.bus.api.IEventBus;

/**
 * Loader extensions for the data generation API.
 */
public class NeoForgeDataGeneratorExtensions implements NeoForgeDataExtensionsProvider {

    @Override
    public void registerExtensions(String modId, IEventBus modBus, ExtensionRegistrar extensions) {
        extensions.provide(PackFactory.EXT, () -> new NeoForgePackFactory(modId, modBus));
    }
}
