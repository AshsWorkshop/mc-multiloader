package net.ashwork.mc.multiloader.api.common.event.resources;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

/**
 * An event called when a built-in pack should be registered.
 */
public interface RegisterBuiltInPacks {

    /**
     * The namespace representing resource packs.
     */
    String RESOURCE_PACK_ID = "resource_pack";
    /**
     * The description of a resource pack.
     */
    String RESOURCE_PACK_DESC = "desc";

    /**
     * Registers a built-in pack.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<RegisterBuiltInPacks> EVENT = Multiloader.extension("register_builtin_packs");

    /**
     * Registers a built-in pack. The display name is translated
     * from the identifier as 'resource_pack.{name_namespace}.{name_path}'.
     *
     * @param name The identifier of the built-in pack.
     * @param alwaysActive Whether the pack is always enabled.
     */
    default void add(Identifier name, boolean alwaysActive) {
        this.add(name, Component.translatable(Util.makeDescriptionId(RESOURCE_PACK_ID, name)), alwaysActive);
    }

    /**
     * Registers a built-in pack.
     *
     * @param name The identifier of the built-in pack.
     * @param display The display name.
     * @param alwaysActive Whether the pack is always enabled.
     */
    void add(Identifier name, Component display, boolean alwaysActive);
}
