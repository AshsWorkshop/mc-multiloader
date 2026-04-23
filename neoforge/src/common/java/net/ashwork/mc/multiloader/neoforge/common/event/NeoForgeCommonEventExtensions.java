package net.ashwork.mc.multiloader.neoforge.common.event;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.common.event.item.ModifyCreativeModeTabContents;
import net.ashwork.mc.multiloader.api.common.event.resources.RegisterBuiltInPacks;
import net.ashwork.mc.multiloader.neoforge.common.NeoForgeCommonExtensionsProvider;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

/**
 * Loader extensions for the events API.
 */
public class NeoForgeCommonEventExtensions implements NeoForgeCommonExtensionsProvider {

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
        extensions.provide(RegisterBuiltInPacks.EVENT, () -> (id, displayName, alwaysActive) -> modBus.addListener(((AddPackFindersEvent neoEvent) ->
            neoEvent.addPackFinders(
                    id.withPrefix("data/" + modId + "/datapacks/"),
                    PackType.SERVER_DATA,
                    displayName,
                    PackSource.BUILT_IN,
                    alwaysActive,
                    Pack.Position.TOP
            )
        )));
    }
}
