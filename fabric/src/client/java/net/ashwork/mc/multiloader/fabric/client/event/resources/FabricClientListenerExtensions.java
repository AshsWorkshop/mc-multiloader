package net.ashwork.mc.multiloader.fabric.client.event.resources;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.client.event.resources.ClientListeners;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.fabricmc.fabric.api.resource.v1.reloader.ResourceReloaderKeys;
import net.minecraft.client.PeriodicNotificationManager;
import net.minecraft.client.renderer.GpuWarnlistManager;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.Identifier;

import java.util.Locale;

/**
 * Loader extensions for the client listeners API.
 */
public class FabricClientListenerExtensions implements FabricClientListenerExtensionsProvider {

    @Override
    public void registerIdentifiers(ExtensionRegistrar extensions) {
        extensions.provide(ClientListeners.ATLAS, () -> ResourceReloaderKeys.Client.ATLAS);
        extensions.provide(ClientListeners.BLOCK_ENTITY_RENDER_DISPATCHER, () -> ResourceReloaderKeys.Client.BLOCK_ENTITY_RENDER_DISPATCHER);
        extensions.provide(ClientListeners.CLOUD_RENDERER, () -> ResourceReloaderKeys.Client.CLOUD_RENDERER);
        extensions.provide(ClientListeners.DRY_FOLIAGE_COLOR, () -> ResourceReloaderKeys.Client.DRY_FOLIAGE_COLOR);
        extensions.provide(ClientListeners.ENTITY_RENDER_DISPATCHER, () -> ResourceReloaderKeys.Client.ENTITY_RENDER_DISPATCHER);
        extensions.provide(ClientListeners.EQUIPMENT_ASSETS, () -> ResourceReloaderKeys.Client.EQUIPMENT_ASSETS);
        extensions.provide(ClientListeners.FOLIAGE_COLOR, () -> ResourceReloaderKeys.Client.FOLIAGE_COLOR);
        extensions.provide(ClientListeners.FONTS, () -> ResourceReloaderKeys.Client.FONTS);
        extensions.provide(ClientListeners.GPU_WARNLIST, () -> privateListener(GpuWarnlistManager.class));
        extensions.provide(ClientListeners.GRASS_COLOR, () -> ResourceReloaderKeys.Client.GRASS_COLOR);
        extensions.provide(ClientListeners.LANGUAGE, () -> ResourceReloaderKeys.Client.LANGUAGES);
        extensions.provide(ClientListeners.LEVEL_RENDERER, () -> privateListener(LevelRenderer.class));
        extensions.provide(ClientListeners.MODELS, () -> ResourceReloaderKeys.Client.MODELS);
        extensions.provide(ClientListeners.PARTICLE_RESOURCES, () -> ResourceReloaderKeys.Client.PARTICLES);
        extensions.provide(ClientListeners.REGIONAL_COMPLIANCES, () -> privateListener(PeriodicNotificationManager.class));
        extensions.provide(ClientListeners.SHADERS, () -> ResourceReloaderKeys.Client.SHADERS);
        extensions.provide(ClientListeners.SOUNDS, () -> ResourceReloaderKeys.Client.SOUNDS);
        extensions.provide(ClientListeners.SPLASH_TEXTS, () -> ResourceReloaderKeys.Client.SPLASH_TEXTS);
        extensions.provide(ClientListeners.TEXTURES, () -> ResourceReloaderKeys.Client.TEXTURES);
        extensions.provide(ClientListeners.WAYPOINT_STYLES, () -> ResourceReloaderKeys.Client.WAYPOINT_STYLE);
        extensions.provide(AddReloadListeners.Keys.FIRST, () -> ResourceReloaderKeys.BEFORE_VANILLA);
        extensions.provide(AddReloadListeners.Keys.LAST, () -> ResourceReloaderKeys.AFTER_VANILLA);
    }

    private static Identifier privateListener(Class<?> listenerClass) {
        return Identifier.withDefaultNamespace("private/" + listenerClass.getSimpleName().toLowerCase(Locale.ROOT));
    }
}
