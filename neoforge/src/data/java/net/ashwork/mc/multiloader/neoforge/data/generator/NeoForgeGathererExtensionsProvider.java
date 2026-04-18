package net.ashwork.mc.multiloader.neoforge.data.generator;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer;

/**
 * A provider to register extensions to the {@link DataProviderGatherer}
 * for the NeoForge loader. The implementing provider interface will be
 * loaded via {@link java.util.ServiceLoader#load(Class)}.
 */
public interface NeoForgeGathererExtensionsProvider {

    /**
     * Registers an extension to the calling holder.
     *
     * @param modId The identiifer of the mod.
     * @param providers The gatherer for the {@link net.minecraft.data.DataProvider}s.
     * @param extensions The registrar for registering extensions.
     */
    void registerExtensions(String modId, DataProviderGatherer providers, ExtensionRegistrar extensions);
}
