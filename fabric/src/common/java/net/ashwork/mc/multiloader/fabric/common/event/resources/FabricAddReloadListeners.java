package net.ashwork.mc.multiloader.fabric.common.event.resources;

import net.ashwork.mc.multiloader.api.base.impl.extension.AbstractExtensionHolder;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.fabricmc.fabric.api.resource.v1.ResourceLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.PreparableReloadListener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The Fabric implementation of {@link AddReloadListeners}.
 *
 * @param <PROVIDER> The type of the extensions provider.
 */
public class FabricAddReloadListeners<PROVIDER extends FabricListenerExtensionsProvider> extends AbstractExtensionHolder<PROVIDER> implements AddReloadListeners {

    private final ResourceLoader loader;

    /**
     * A basic constructor.
     *
     * @param providerClass The class of the extension provider.
     * @param type The pack type to register the listeners to.
     */
    public FabricAddReloadListeners(Class<PROVIDER> providerClass, PackType type) {
        super(providerClass, provider -> extensions -> provider.registerIdentifiers(extensions));
        this.loader = ResourceLoader.get(type);
    }

    @Override
    public void register(Identifier id, PreparableReloadListener listener) {
        this.loader.registerReloadListener(id, listener);
    }

    @Override
    public void dependency(Identifier first, Identifier second) {
        this.loader.addListenerOrdering(first, second);
    }
}
