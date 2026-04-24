package net.ashwork.mc.multiloader.neoforge.common.event;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.common.event.item.ModifyCreativeModeTabContents;
import net.ashwork.mc.multiloader.api.common.event.resources.RegisterBuiltInPacks;
import net.ashwork.mc.multiloader.neoforge.common.NeoForgeCommonExtensionsProvider;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.KnownPack;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.resource.JarContentsPackResources;
import net.neoforged.neoforgespi.language.IModInfo;

import java.util.Optional;

/**
 * Loader extensions for the events API.
 */
public class NeoForgeCommonEventExtensions implements NeoForgeCommonExtensionsProvider {

    private static final PackSource DEFAULT = PackSource.create(PackSource.NO_DECORATION, false);

    @Override
    public void registerExtensions(String modId, IEventBus modBus, ExtensionRegistrar extensions) {
        extensions.provide(ModifyCreativeModeTabContents.EVENT, () -> key -> event -> modBus.addListener((BuildCreativeModeTabContentsEvent neoEvent) -> {
            if (neoEvent.getTabKey() == key) {
                event.modifyContents(new ModifyCreativeModeTabContents.Output() {
                    @Override
                    public CreativeModeTab.ItemDisplayParameters params() {
                        return neoEvent.getParameters();
                    }

                    @Override
                    public void insertFirst(CreativeModeTab.TabVisibility visibility, ItemStack... stacks) {
                        // Loop backwards to preserve ordering
                        for (var i = stacks.length - 1; i >= 0; i--) neoEvent.insertFirst(stacks[i], visibility);
                    }

                    @Override
                    public void insertAfter(ItemStack first, CreativeModeTab.TabVisibility visibility, ItemStack... stacks) {
                        // Loop backwards to preserve ordering
                        for (var i = stacks.length - 1; i >= 0; i--) neoEvent.insertAfter(first, stacks[i], visibility);
                    }

                    @Override
                    public void insertBefore(ItemStack last, CreativeModeTab.TabVisibility visibility, ItemStack... stacks) {
                        for (var i = 0; i < stacks.length; i++) neoEvent.insertBefore(last, stacks[i], visibility);
                    }

                    @Override
                    public void accept(ItemStack stack, CreativeModeTab.TabVisibility visibility) {
                        neoEvent.accept(stack, visibility);
                    }
                });
            }
        }));
        extensions.provide(RegisterBuiltInPacks.EVENT, () -> (id, displayName, type, alwaysActive) -> modBus.addListener(((AddPackFindersEvent neoEvent) -> {
            // Copy from AddPackFindersEvent#addPackFinders
            if (type.isIn(neoEvent.getPackType())) {
                IModInfo modInfo = ModList.get().getModContainerById(id.getNamespace()).orElseThrow(() -> new IllegalArgumentException("Mod not found: " + id.getNamespace())).getModInfo();

                var version = modInfo.getVersion();

                // Keep id the same but add prefix
                String prefix = id.withPrefix("resourcepacks/" + (id.getNamespace() == modId ? "" : (id.getNamespace() + "/"))).getPath();

                var pack = Pack.readMetaAndCreate(
                        new PackLocationInfo("mod/" + id, displayName, DEFAULT, Optional.of(new KnownPack("neoforge", "mod/" + id, version.toString()))),
                        new JarContentsPackResources.JarContentsResourcesSupplier(modInfo.getOwningFile().getFile().getContents(), prefix),
                        neoEvent.getPackType(),
                        new PackSelectionConfig(alwaysActive, Pack.Position.BOTTOM, false)
                );

                neoEvent.addRepositorySource((packConsumer) -> packConsumer.accept(pack));
            }
        })));
    }
}
