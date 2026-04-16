package net.ashwork.mc.multiloader.neoforge.common;

import net.ashwork.mc.multiloader.neoforge.base.NeoForgeExtensionsProvider;

/**
 * A provider to register extensions for the NeoForge
 * common loader. The providers will be loaded via
 * {@link java.util.ServiceLoader#load(Class)}.
 *
 * @see NeoForgeExtensionsProvider
 */
public interface NeoForgeCommonExtensionsProvider extends NeoForgeExtensionsProvider {
}
