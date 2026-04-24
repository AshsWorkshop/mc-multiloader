package net.ashwork.mc.multiloader.api.common.event.resources;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.util.Util;

import java.util.Set;

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
     * Registers a built-in resource pack that is not always active.
     * The display name is translated from the identifier as
     * 'resource_pack.{name_namespace}.{name_path}'.
     *
     * @param name The identifier of the built-in pack.
     */
    default void add(Identifier name) {
        this.add(name, createDefault(name), false);
    }

    /**
     * Registers a built-in resource pack that is not always active.
     *
     * @param name The identifier of the built-in pack.
     * @param display The display name.
     */
    default void add(Identifier name, Component display) {
        this.add(name, display, false);
    }

    /**
     * Registers a built-in resource pack. The display name is translated
     * from the identifier as 'resource_pack.{name_namespace}.{name_path}'.
     *
     * @param name The identifier of the built-in pack.
     * @param alwaysActive Whether the pack is always enabled.
     */
    default void add(Identifier name, boolean alwaysActive) {
        this.add(name, createDefault(name), alwaysActive);
    }

    /**
     * Registers a built-in resource pack.
     *
     * @param name The identifier of the built-in pack.
     * @param display The display name.
     * @param alwaysActive Whether the pack is always enabled.
     */
    default void add(Identifier name, Component display, boolean alwaysActive) {
        this.add(name, display, PackTypes.ALL, alwaysActive);
    }

    /**
     * Registers a built-in pack. The display name is translated
     * from the identifier as 'resource_pack.{name_namespace}.{name_path}'.
     *
     * @param name The identifier of the built-in pack.
     * @param type The type of the pack.
     * @param alwaysActive Whether the pack is always enabled.
     */
    default void add(Identifier name, PackTypes type, boolean alwaysActive) {
        this.add(name, createDefault(name), type, alwaysActive);
    }

    /**
     * Registers a built-in pack.
     *
     * @param name The identifier of the built-in pack.
     * @param display The display name.
     * @param type The type of the pack.
     * @param alwaysActive Whether the pack is always enabled.
     */
    void add(Identifier name, Component display, PackTypes type, boolean alwaysActive);

    private static Component createDefault(Identifier name) {
        return Component.translatable(name.toLanguageKey(RESOURCE_PACK_ID));
    }

    /**
     * The types a registered pack can be.
     */
    enum PackTypes {
        /**
         * A client assets pack.
         */
        CLIENT_RESOURCES(PackType.CLIENT_RESOURCES),
        /**
         * A server data pack.
         */
        SERVER_DATA(PackType.SERVER_DATA),
        /**
         * A general resource pack.
         */
        ALL(PackType.CLIENT_RESOURCES, PackType.SERVER_DATA);

        private final Set<PackType> types;

        private PackTypes(PackType... types) {
            this.types = Set.of(types);
        }

        /**
         * Checks whether the supported types contains a given {@link PackType}.
         *
         * @param type The type to check against.
         * @return {@code true} if the type is supported, {@code false} otherwise.
         */
        public boolean isIn(PackType type) {
            return this.types.contains(type);
        }
    }
}
