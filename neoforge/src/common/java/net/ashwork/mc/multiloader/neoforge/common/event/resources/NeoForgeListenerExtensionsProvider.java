package net.ashwork.mc.multiloader.neoforge.common.event.resources;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;

/**
 * A provider to register extensions to {@link net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners}
 * for the NeoForge loader. The implementing provider interface will
 * be loaded via {@link java.util.ServiceLoader#load(Class)}.
 */
public interface NeoForgeListenerExtensionsProvider {

    /**
     * Registers an extension to the calling holder.
     *
     * @param extensions The registrar for registering extensions.
     */
    void registerIdentifiers(ExtensionRegistrar extensions);
}
