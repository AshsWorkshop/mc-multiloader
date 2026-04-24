package net.ashwork.mc.multiloader.fabric.common.event;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.common.event.item.ModifyCreativeModeTabContents;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.ashwork.mc.multiloader.api.common.event.resources.RegisterBuiltInPacks;
import net.ashwork.mc.multiloader.fabric.common.FabricCommonExtensionsProvider;
import net.ashwork.mc.multiloader.fabric.common.event.resources.FabricAddReloadListeners;
import net.ashwork.mc.multiloader.fabric.common.event.resources.FabricServerListenerExtensionsProvider;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.fabricmc.fabric.api.resource.v1.pack.PackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

import java.util.Arrays;

/**
 * Loader extensions for the events API.
 */
public class FabricCommonEventExtensions implements FabricCommonExtensionsProvider {

    @Override
    public void registerExtensions(String modId, ExtensionRegistrar extensions) {
        extensions.provide(ModifyCreativeModeTabContents.EVENT, () -> key -> event -> CreativeModeTabEvents.modifyOutputEvent(key).register(
                output -> event.modifyContents(new ModifyCreativeModeTabContents.Output() {
                    @Override
                    public CreativeModeTab.ItemDisplayParameters params() {
                        return output.getContext();
                    }

                    @Override
                    public void insertFirst(CreativeModeTab.TabVisibility visibility, ItemStack... stacks) {
                        // Loop backwards to preserve ordering
                        for (var i = stacks.length - 1; i >= 0; i--) output.prepend(stacks[i], visibility);
                    }

                    @Override
                    public void insertAfter(ItemStack first, CreativeModeTab.TabVisibility visibility, ItemStack... stacks) {
                        output.insertAfter(first, Arrays.asList(stacks), visibility);
                    }

                    @Override
                    public void insertBefore(ItemStack last, CreativeModeTab.TabVisibility visibility, ItemStack... stacks) {
                        output.insertBefore(last, Arrays.asList(stacks), visibility);
                    }

                    @Override
                    public void accept(ItemStack stack, CreativeModeTab.TabVisibility visibility) {
                        output.accept(stack, visibility);
                    }
                })
        ));
        extensions.provide(RegisterBuiltInPacks.EVENT, () -> (id, displayName, type, alwaysActive) -> FabricLoader.getInstance().getModContainer(modId)
                .map(container -> ResourceLoader.registerBuiltinPack(id, container, displayName, alwaysActive ? PackActivationType.ALWAYS_ENABLED : PackActivationType.NORMAL)));
        extensions.provide(AddReloadListeners.EVENT, () -> new FabricAddReloadListeners<>(FabricServerListenerExtensionsProvider.class, PackType.SERVER_DATA));
    }
}
