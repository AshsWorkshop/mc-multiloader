package net.ashwork.mc.multiloader.fabric.common.event.resources;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.ashwork.mc.multiloader.api.common.event.resources.ServerListeners;
import net.fabricmc.fabric.api.resource.v1.reloader.ResourceReloaderKeys;

/**
 * Loader extensions for the server listeners API.
 */
public class FabricServerListenerExtensions implements FabricServerListenerExtensionsProvider {

    @Override
    public void registerIdentifiers(ExtensionRegistrar extensions) {
        extensions.provide(ServerListeners.ADVANCEMENTS, () -> ResourceReloaderKeys.Server.ADVANCEMENTS);
        extensions.provide(ServerListeners.FUNCTIONS, () -> ResourceReloaderKeys.Server.FUNCTIONS);
        extensions.provide(ServerListeners.RECIPES, () -> ResourceReloaderKeys.Server.RECIPES);
        extensions.provide(AddReloadListeners.Keys.FIRST, () -> ResourceReloaderKeys.BEFORE_VANILLA);
        extensions.provide(AddReloadListeners.Keys.LAST, () -> ResourceReloaderKeys.AFTER_VANILLA);
    }
}
