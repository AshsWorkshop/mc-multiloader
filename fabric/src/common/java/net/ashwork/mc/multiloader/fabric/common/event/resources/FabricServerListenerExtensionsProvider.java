package net.ashwork.mc.multiloader.fabric.common.event.resources;

/**
 * A provider to register extensions to {@link net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners}
 * on the logical server for the Fabric loader. The implementing provider
 * interface will be loaded via {@link java.util.ServiceLoader#load(Class)}.
 */
public interface FabricServerListenerExtensionsProvider extends FabricListenerExtensionsProvider {
}
