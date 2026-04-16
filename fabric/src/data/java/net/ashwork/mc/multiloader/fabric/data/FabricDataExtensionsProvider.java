package net.ashwork.mc.multiloader.fabric.data;

import net.ashwork.mc.multiloader.fabric.base.FabricExtensionsProvider;

/**
 * A provider to register extensions for the Fabric
 * client loader. The providers will be loaded via
 * {@link java.util.ServiceLoader#load(Class)}.
 *
 * @see FabricExtensionsProvider
 */
public interface FabricDataExtensionsProvider extends FabricExtensionsProvider {
}
