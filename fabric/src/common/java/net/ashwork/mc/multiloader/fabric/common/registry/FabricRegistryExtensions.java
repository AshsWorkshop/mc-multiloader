package net.ashwork.mc.multiloader.fabric.common.registry;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionHolder;
import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.ashwork.mc.multiloader.api.common.registry.ItemRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.Registrar;
import net.ashwork.mc.multiloader.api.common.registry.RegistrarAccessor;
import net.ashwork.mc.multiloader.api.common.registry.impl.ItemRegistrarWrapper;
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
                return _create(registry, modId);
            }

            @Override
            public <REGISTRY> Registrar<REGISTRY> create(ResourceKey<? extends Registry<REGISTRY>> registry, String namespace) {
                return _create(registry, namespace);
            }

            private static <REGISTRY> Registrar<REGISTRY> _create(ResourceKey<? extends Registry<REGISTRY>> registry, String namespace) {
                return new VanillaRegistryWrapper<>(registry, namespace);
            }
        });
        registerSpecialRegistrars(extensions, ItemRegistrar.EXT, ItemRegistrar.BASIC, Registries.ITEM, modId, ItemRegistrarWrapper::new);
    }

    private static <REGISTRY, REGISTRAR extends Registrar<REGISTRY>> void registerSpecialRegistrars(
            ExtensionRegistrar extensions, LoaderExtension.Key<Function<String, REGISTRAR>> accessor, LoaderExtension.Key<REGISTRAR> simpleAccessor,
            ResourceKey<? extends Registry<REGISTRY>> registry, String defaultNamespace,
            Function<Registrar<REGISTRY>, REGISTRAR> wrapper
    ) {
        extensions.provide(accessor, holder -> namespace -> _wrap(holder, registry, namespace, wrapper), RegistrarAccessor.EXT);
        extensions.provide(simpleAccessor, holder -> _wrap(holder, registry, defaultNamespace, wrapper), RegistrarAccessor.EXT);
    }

    private static <REGISTRY, REGISTRAR extends Registrar<REGISTRY>> REGISTRAR _wrap(
            ExtensionHolder holder,
            ResourceKey<? extends Registry<REGISTRY>> registry, String namespace,
            Function<Registrar<REGISTRY>, REGISTRAR> wrapper
    ) {
        return wrapper.apply(holder.access(RegistrarAccessor.EXT).create(registry, namespace));
    }
}
