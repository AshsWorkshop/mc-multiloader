package net.ashwork.mc.multiloader.neoforge.base;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.neoforged.bus.api.IEventBus;

/**
 * A provider to register extensions for the NeoForge loader.
 * The implementing provider interface will be loaded via
 * {@link java.util.ServiceLoader#load(Class)}.
 */
public interface NeoForgeExtensionsProvider {

    /**
     * Registers an extension to the calling holder.
     *
     * @param modId The identiifer of the mod.
     * @param modBus The event bus for the mod instance.
     * @param extensions The registrar for registering extensions.
     */
    void registerExtensions(String modId, IEventBus modBus, ExtensionRegistrar extensions);
}
