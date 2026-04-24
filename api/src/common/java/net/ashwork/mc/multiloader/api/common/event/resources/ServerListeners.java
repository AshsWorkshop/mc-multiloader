package net.ashwork.mc.multiloader.api.common.event.resources;

import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.resources.Identifier;

/**
 * Vanilla listeners available on the logical server.
 */
public interface ServerListeners {

    /**
     * The {@link net.minecraft.server.ServerAdvancementManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> ADVANCEMENTS = LoaderExtension.Key.vanilla("advancements");

    /**
     * The {@link net.minecraft.server.ServerFunctionLibrary} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> FUNCTIONS = LoaderExtension.Key.vanilla("functions");

    /**
     * The {@link net.minecraft.world.item.crafting.RecipeManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> RECIPES = LoaderExtension.Key.vanilla("recipes");
}
