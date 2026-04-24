package net.ashwork.mc.multiloader.fabric.client.event.resources;

import net.ashwork.mc.multiloader.fabric.common.event.resources.FabricListenerExtensionsProvider;

/**
 * A provider to register extensions to {@link net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners}
 * on the logical client for the Fabric loader. The implementing provider
 * interface will be loaded via {@link java.util.ServiceLoader#load(Class)}.
 */
public interface FabricClientListenerExtensionsProvider extends FabricListenerExtensionsProvider {
}
