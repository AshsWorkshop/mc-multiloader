package net.ashwork.mc.multiloader.api.client.event.resources;

import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.minecraft.resources.Identifier;

/**
 * Vanilla listeners available on the logical client.
 */
public interface ClientListeners {

    /**
     * The {@link net.minecraft.client.resources.model.sprite.AtlasManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> ATLAS = LoaderExtension.Key.vanilla("atlas");


    /**
     * The {@link net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> BLOCK_ENTITY_RENDER_DISPATCHER = LoaderExtension.Key.vanilla("block_entity_render_dispatcher");

    /**
     * The {@link net.minecraft.client.renderer.CloudRenderer} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> CLOUD_RENDERER = LoaderExtension.Key.vanilla("cloud_renderer");

    /**
     * The {@link net.minecraft.client.resources.DryFoliageColorReloadListener} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> DRY_FOLIAGE_COLOR = LoaderExtension.Key.vanilla("dry_foliage_color");

    /**
     * The {@link net.minecraft.client.renderer.entity.EntityRenderDispatcher} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> ENTITY_RENDER_DISPATCHER = LoaderExtension.Key.vanilla("entity_render_dispatcher");

    /**
     * The {@link net.minecraft.client.resources.model.EquipmentAssetManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> EQUIPMENT_ASSETS = LoaderExtension.Key.vanilla("equipment_assets");

    /**
     * The {@link net.minecraft.client.resources.FoliageColorReloadListener} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> FOLIAGE_COLOR = LoaderExtension.Key.vanilla("foliage_color");

    /**
     * The {@link net.minecraft.client.gui.font.FontManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> FONTS = LoaderExtension.Key.vanilla("fonts");

    /**
     * The {@link net.minecraft.client.renderer.GpuWarnlistManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> GPU_WARNLIST = LoaderExtension.Key.vanilla("gpu_warnlist");

    /**
     * The {@link net.minecraft.client.resources.GrassColorReloadListener} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> GRASS_COLOR = LoaderExtension.Key.vanilla("grass_color");

    /**
     * The {@link net.minecraft.client.resources.language.LanguageManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> LANGUAGE = LoaderExtension.Key.vanilla("language");

    /**
     * The {@link net.minecraft.client.renderer.LevelRenderer} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> LEVEL_RENDERER = LoaderExtension.Key.vanilla("level_renderer");

    /**
     * The {@link net.minecraft.client.resources.model.ModelManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> MODELS = LoaderExtension.Key.vanilla("models");

    /**
     * The {@link net.minecraft.client.particle.ParticleResources} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> PARTICLE_RESOURCES = LoaderExtension.Key.vanilla("particle_resources");

    /**
     * The {@link net.minecraft.client.PeriodicNotificationManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> REGIONAL_COMPLIANCES = LoaderExtension.Key.vanilla("regional_compliances");

    /**
     * The {@link net.minecraft.client.renderer.ShaderManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> SHADERS = LoaderExtension.Key.vanilla("shaders");

    /**
     * The {@link net.minecraft.client.sounds.SoundManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> SOUNDS = LoaderExtension.Key.vanilla("sounds");

    /**
     * The {@link net.minecraft.client.resources.SplashManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> SPLASH_TEXTS = LoaderExtension.Key.vanilla("splash_texts");

    /**
     * The {@link net.minecraft.client.resources.WaypointStyle} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> TEXTURES = LoaderExtension.Key.vanilla("textures");

    /**
     * The {@link net.minecraft.client.renderer.texture.TextureManager} identifier.
     *
     * @extension {@link AddReloadListeners}.
     */
    LoaderExtension.Key<Identifier> WAYPOINT_STYLES = LoaderExtension.Key.vanilla("waypoint_styles");
}
