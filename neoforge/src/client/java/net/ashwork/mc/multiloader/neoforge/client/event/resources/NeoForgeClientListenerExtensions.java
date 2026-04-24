package net.ashwork.mc.multiloader.neoforge.client.event.resources;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.client.event.resources.ClientListeners;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.neoforged.neoforge.client.resources.VanillaClientListeners;

/**
 * Loader extensions for the client listeners API.
 */
public class NeoForgeClientListenerExtensions implements NeoForgeClientListenerExtensionsProvider {

    @Override
    public void registerIdentifiers(ExtensionRegistrar extensions) {
        extensions.provide(ClientListeners.ATLAS, () -> VanillaClientListeners.ATLASES);
        extensions.provide(ClientListeners.BLOCK_ENTITY_RENDER_DISPATCHER, () -> VanillaClientListeners.BLOCK_ENTITY_RENDERER);
        extensions.provide(ClientListeners.CLOUD_RENDERER, () -> VanillaClientListeners.CLOUD_RENDERER);
        extensions.provide(ClientListeners.DRY_FOLIAGE_COLOR, () -> VanillaClientListeners.DRY_FOLIAGE_COLOR);
        extensions.provide(ClientListeners.ENTITY_RENDER_DISPATCHER, () -> VanillaClientListeners.ENTITY_RENDERER);
        extensions.provide(ClientListeners.EQUIPMENT_ASSETS, () -> VanillaClientListeners.EQUIPMENT_ASSETS);
        extensions.provide(ClientListeners.FOLIAGE_COLOR, () -> VanillaClientListeners.FOLIAGE_COLOR);
        extensions.provide(ClientListeners.FONTS, () -> VanillaClientListeners.FONTS);
        extensions.provide(ClientListeners.GPU_WARNLIST, () -> VanillaClientListeners.GPU_WARNLIST);
        extensions.provide(ClientListeners.GRASS_COLOR, () -> VanillaClientListeners.GRASS_COLOR);
        extensions.provide(ClientListeners.LANGUAGE, () -> VanillaClientListeners.LANGUAGE);
        extensions.provide(ClientListeners.LEVEL_RENDERER, () -> VanillaClientListeners.LEVEL_RENDERER);
        extensions.provide(ClientListeners.MODELS, () -> VanillaClientListeners.MODELS);
        extensions.provide(ClientListeners.PARTICLE_RESOURCES, () -> VanillaClientListeners.PARTICLE_RESOURCES);
        extensions.provide(ClientListeners.REGIONAL_COMPLIANCES, () -> VanillaClientListeners.REGIONAL_COMPLIANCES);
        extensions.provide(ClientListeners.SHADERS, () -> VanillaClientListeners.SHADERS);
        extensions.provide(ClientListeners.SOUNDS, () -> VanillaClientListeners.SOUNDS);
        extensions.provide(ClientListeners.SPLASH_TEXTS, () -> VanillaClientListeners.SPLASHES);
        extensions.provide(ClientListeners.TEXTURES, () -> VanillaClientListeners.TEXTURES);
        extensions.provide(ClientListeners.WAYPOINT_STYLES, () -> VanillaClientListeners.WAYPOINT_STYLES);
        extensions.provide(AddReloadListeners.Keys.FIRST, () -> VanillaClientListeners.FIRST);
        extensions.provide(AddReloadListeners.Keys.LAST, () -> VanillaClientListeners.LAST);
    }
}
