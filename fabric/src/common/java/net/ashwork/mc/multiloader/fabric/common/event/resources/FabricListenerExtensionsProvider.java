package net.ashwork.mc.multiloader.fabric.common.event.resources;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;

/**
 * A provider to register extensions to {@link net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners}
 * for the Fabric loader. The implementing provider interface will
 * be loaded via {@link java.util.ServiceLoader#load(Class)}.
 */
public interface FabricListenerExtensionsProvider {

    /**
     * Registers an extension to the calling holder.
     *
     * @param extensions The registrar for registering extensions.
     */
    void registerIdentifiers(ExtensionRegistrar extensions);
}
