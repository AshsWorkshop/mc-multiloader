package net.ashwork.mc.multiloader.fabric.data.generator;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.data.generator.PackFactory;
import net.ashwork.mc.multiloader.fabric.data.FabricDataExtensionsProvider;

/**
 * Loader extensions for the data generation API.
 */
public class FabricDataGeneratorExtensions implements FabricDataExtensionsProvider {

    @Override
    public void registerExtensions(String modId, ExtensionRegistrar extensions) {
        extensions.provide(PackFactory.EXT, () -> new FabricPackFactory(modId));
    }
}
