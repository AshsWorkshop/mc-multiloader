package net.ashwork.mc.multiloader.neoforge.client.event;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.ashwork.mc.multiloader.neoforge.client.NeoForgeClientExtensionsProvider;
import net.ashwork.mc.multiloader.neoforge.client.event.resources.NeoForgeClientListenerExtensionsProvider;
import net.ashwork.mc.multiloader.neoforge.common.event.resources.NeoForgeAddReloadListeners;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.AddClientReloadListenersEvent;

/**
 * Loader extensions for the client events API.
 */
public class NeoForgeClientEventExtensions implements NeoForgeClientExtensionsProvider {

    @Override
    public void registerExtensions(String modId, IEventBus modBus, ExtensionRegistrar extensions) {
        extensions.provide(AddReloadListeners.EVENT, () -> new NeoForgeAddReloadListeners<>(NeoForgeClientListenerExtensionsProvider.class, modBus, AddClientReloadListenersEvent.class));
    }
}
