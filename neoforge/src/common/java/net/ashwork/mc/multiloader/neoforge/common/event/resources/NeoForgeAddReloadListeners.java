package net.ashwork.mc.multiloader.neoforge.common.event.resources;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.ashwork.mc.multiloader.api.base.impl.extension.AbstractExtensionHolder;
import net.ashwork.mc.multiloader.api.common.event.resources.AddReloadListeners;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.SortedReloadListenerEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * The NeoForge implementation of {@link AddReloadListeners}.
 *
 * @param <PROVIDER> The type of the extensions provider.
 * @param <EVENT> The type of the registry event.
 */
public class NeoForgeAddReloadListeners<PROVIDER extends NeoForgeListenerExtensionsProvider, EVENT extends SortedReloadListenerEvent> extends AbstractExtensionHolder<PROVIDER> implements AddReloadListeners {

    private final List<Consumer<EVENT>> toRun;

    /**
     * A basic constructor.
     *
     * @param providerClass The class of the extension provider.
     * @param bus The event bus to register the listener.
     * @param eventClass The class of the event.
     */
    public NeoForgeAddReloadListeners(Class<PROVIDER> providerClass, IEventBus bus, Class<EVENT> eventClass) {
        super(providerClass, provider -> extensions -> provider.registerIdentifiers(extensions));
        this.toRun = new ArrayList<>();
        bus.addListener(eventClass, this::registerListeners);
    }

    protected void registerListeners(EVENT event) {
        this.toRun.forEach(runnable -> runnable.accept(event));
    }

    @Override
    public void register(Identifier id, PreparableReloadListener listener) {
        this.toRun.add(event -> event.addListener(id, listener));
    }

    @Override
    public void dependency(Identifier first, Identifier second) {
        this.toRun.add(event -> event.addDependency(first, second));
    }
}
