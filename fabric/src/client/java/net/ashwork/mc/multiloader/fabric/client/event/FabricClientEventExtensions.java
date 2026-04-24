package net.ashwork.mc.multiloader.fabric.client.event;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.ashwork.mc.multiloader.fabric.client.FabricClientExtensionsProvider;
import net.ashwork.mc.multiloader.fabric.client.event.resources.FabricClientListenerExtensionsProvider;
import net.ashwork.mc.multiloader.fabric.common.event.resources.FabricAddReloadListeners;
import net.minecraft.server.packs.PackType;

/**
 * Loader extensions for the client events API.
 */
public class FabricClientEventExtensions implements FabricClientExtensionsProvider {

    @Override
    public void registerExtensions(String modId, ExtensionRegistrar extensions) {
        extensions.provide(AddReloadListeners.EVENT, () -> new FabricAddReloadListeners<>(FabricClientListenerExtensionsProvider.class, PackType.CLIENT_RESOURCES));
    }
}
