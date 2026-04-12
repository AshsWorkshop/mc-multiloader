package net.ashwork.mc.multiloader.fabric.base;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;

/**
 * A provider to register extensions for the Fabric loader.
 * The implementing provider interface will be loaded via
 * {@link java.util.ServiceLoader#load(Class)}.
 */
public interface FabricExtensionsProvider {

    /**
     * Registers an extension to the calling holder.
     *
     * @param modId The identiifer of the mod.
     * @param extensions The registrar for registering extensions.
     */
    void registerExtensions(String modId, ExtensionRegistrar extensions);
}
