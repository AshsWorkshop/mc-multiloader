package net.ashwork.mc.multiloader.neoforge.common.event.resources;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.ashwork.mc.multiloader.api.common.event.resources.ServerListeners;
import net.neoforged.neoforge.resource.VanillaServerListeners;

/**
 * Loader extensions for the server listeners API.
 */
public class NeoForgeServerListenerExtensions implements NeoForgeServerListenerExtensionsProvider {

    @Override
    public void registerIdentifiers(ExtensionRegistrar extensions) {
        extensions.provide(ServerListeners.ADVANCEMENTS, () -> VanillaServerListeners.ADVANCEMENTS);
        extensions.provide(ServerListeners.FUNCTIONS, () -> VanillaServerListeners.FUNCTIONS);
        extensions.provide(ServerListeners.RECIPES, () -> VanillaServerListeners.RECIPES);
        extensions.provide(AddReloadListeners.Keys.FIRST, () -> VanillaServerListeners.FIRST);
        extensions.provide(AddReloadListeners.Keys.LAST, () -> VanillaServerListeners.LAST);
    }
}
