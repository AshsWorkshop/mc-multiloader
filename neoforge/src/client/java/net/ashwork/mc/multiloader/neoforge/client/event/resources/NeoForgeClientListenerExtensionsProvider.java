package net.ashwork.mc.multiloader.neoforge.client.event.resources;

import net.ashwork.mc.multiloader.neoforge.common.event.resources.NeoForgeListenerExtensionsProvider;

/**
 * A provider to register extensions to {@link net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners}
 * on the logical client for the NeoForge loader. The implementing provider
 * interface will be loaded via {@link java.util.ServiceLoader#load(Class)}.
 */
public interface NeoForgeClientListenerExtensionsProvider extends NeoForgeListenerExtensionsProvider {
}
