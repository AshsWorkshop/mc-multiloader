package net.ashwork.mc.multiloader.fabric.common.registry;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.ItemRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.Registrar;
import net.ashwork.mc.multiloader.api.common.registry.RegistrarAccessor;
import net.ashwork.mc.multiloader.fabric.common.FabricCommonExtensionsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Loader extensions for the registry API.
 */
public class FabricRegistryExtensions implements FabricCommonExtensionsProvider {

    @Override
    public void registerExtensions(String modId, ExtensionRegistrar extensions) {
        extensions.provide(RegistrarAccessor.EXT, () -> new RegistrarAccessor() {

            @Override
            public <REGISTRY> Registrar<REGISTRY> create(ResourceKey<? extends Registry<REGISTRY>> registry) {
                return _create(registry, modId, FabricRegistryExtensions::_wrapBasic);
            }

            @Override
            public <REGISTRY> Registrar<REGISTRY> create(ResourceKey<? extends Registry<REGISTRY>> registry, String namespace) {
                return _create(registry, namespace, FabricRegistryExtensions::_wrapBasic);
            }
        });
        extensions.provide(ItemRegistrar.EXT, () -> namespace -> _create(Registries.ITEM, namespace, FabricRegistryExtensions::_wrapItem));
        extensions.provide(ItemRegistrar.BASIC, () -> _create(Registries.ITEM, modId, FabricRegistryExtensions::_wrapItem));
    }

    private static <REGISTRY, REGISTRAR extends Registrar<REGISTRY>> REGISTRAR _create(ResourceKey<? extends Registry<REGISTRY>> key, String namespace, BiFunction<Registry<REGISTRY>, String, REGISTRAR> factory) {
        Registry<REGISTRY> registry = (Registry<REGISTRY>) BuiltInRegistries.REGISTRY.getValue(key.identifier());
        return factory.apply(registry, namespace);
    }

    private static <REGISTRY> Registrar<REGISTRY> _wrapBasic(Registry<REGISTRY> delegate, String namespace) {
        return new Registrar<REGISTRY>() {
            @Override
            public <IMPL extends REGISTRY> Holder<IMPL> register(String name, Function<Identifier, IMPL> factory) {
                var id = Identifier.fromNamespaceAndPath(namespace, name);
                return Registry.registerForHolder(delegate, id, factory.apply(id));
            }
        };
    }

    private static ItemRegistrar _wrapItem(Registry<Item> delegate, String namespace) {
        return new ItemRegistrar() {
            @Override
            public <IMPL extends Item> Holder<IMPL> register(String name, Function<Identifier, IMPL> factory) {
                var id = Identifier.fromNamespaceAndPath(namespace, name);
                return Registry.registerForHolder(delegate, id, factory.apply(id));
            }
        };
    }
}
