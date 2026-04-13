package net.ashwork.mc.multiloader.neoforge.common.registry;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.ItemRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.Registrar;
import net.ashwork.mc.multiloader.api.common.registry.RegistrarAccessor;
import net.ashwork.mc.multiloader.neoforge.common.NeoForgeCommonExtensionsProvider;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

/**
 * Loader extensions for the registry API.
 */
public class NeoForgeRegistryExtensions implements NeoForgeCommonExtensionsProvider {

    @Override
    public void registerExtensions(String modId, IEventBus modBus, ExtensionRegistrar extensions) {
        extensions.provide(RegistrarAccessor.EXT, () -> new RegistrarAccessor() {

            @Override
            public <REGISTRY> Registrar<REGISTRY> create(ResourceKey<? extends Registry<REGISTRY>> registry) {
                return _create(registry, modId, modBus, NeoForgeRegistryExtensions::_wrapBasic);
            }

            @Override
            public <REGISTRY> Registrar<REGISTRY> create(ResourceKey<? extends Registry<REGISTRY>> registry, String namespace) {
                return _create(registry, namespace, modBus, NeoForgeRegistryExtensions::_wrapBasic);
            }
        });
        extensions.provide(ItemRegistrar.EXT, () -> namespace -> _create(Registries.ITEM, namespace, modBus, NeoForgeRegistryExtensions::_wrapItem));
        extensions.provide(ItemRegistrar.BASIC, () -> _create(Registries.ITEM, modId, modBus, NeoForgeRegistryExtensions::_wrapItem));
    }

    private static <REGISTRY, REGISTRAR extends Registrar<REGISTRY>> REGISTRAR _create(ResourceKey<? extends Registry<REGISTRY>> registry, String namespace, IEventBus modBus, Function<DeferredRegister<REGISTRY>, REGISTRAR> factory) {
        DeferredRegister<REGISTRY> registrar = DeferredRegister.create(registry, namespace);
        registrar.register(modBus);
        return factory.apply(registrar);
    }

    private static <REGISTRY> Registrar<REGISTRY> _wrapBasic(DeferredRegister<REGISTRY> delegate) {
        return new Registrar<REGISTRY>() {
            @Override
            public <IMPL extends REGISTRY> Holder<IMPL> register(String name, Function<Identifier, IMPL> factory) {
                return (Holder<IMPL>) delegate.register(name, factory);
            }
        };
    }

    private static ItemRegistrar _wrapItem(DeferredRegister<Item> delegate) {
        return new ItemRegistrar() {
            @Override
            public <IMPL extends Item> Holder<IMPL> register(String name, Function<Identifier, IMPL> factory) {
                return (Holder<IMPL>) delegate.register(name, factory);
            }
        };
    }
}
