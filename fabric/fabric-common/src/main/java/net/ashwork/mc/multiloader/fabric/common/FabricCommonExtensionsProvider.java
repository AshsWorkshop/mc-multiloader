package net.ashwork.mc.multiloader.fabric.common;

import net.ashwork.mc.multiloader.fabric.base.FabricExtensionsProvider;

/**
 * A provider to register extensions for the Fabric
 * common loader. The providers will be loaded via
 * {@link java.util.ServiceLoader#load(Class)}.
 *
 * @see FabricExtensionsProvider
 */
public interface FabricCommonExtensionsProvider extends FabricExtensionsProvider {
}
