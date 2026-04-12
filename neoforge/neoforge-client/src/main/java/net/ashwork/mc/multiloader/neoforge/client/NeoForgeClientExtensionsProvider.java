package net.ashwork.mc.multiloader.neoforge.client;

import net.ashwork.mc.multiloader.neoforge.base.NeoForgeExtensionsProvider;

/**
 * A provider to register extensions for the NeoForge
 * client loader. The providers will be loaded via
 * {@link java.util.ServiceLoader#load(Class)}.
 *
 * @see NeoForgeExtensionsProvider
 */
public interface NeoForgeClientExtensionsProvider extends NeoForgeExtensionsProvider {
}
