package net.ashwork.mc.multiloader.neoforge.data.generator;

import net.ashwork.mc.multiloader.api.base.impl.extension.ExtensionManager;
import net.ashwork.mc.multiloader.api.data.generator.PackBuilder;
import net.ashwork.mc.multiloader.api.data.generator.PackFactory;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.resources.Identifier;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * The NeoForge implementation of the {@link PackFactory}.
 */
public class NeoForgePackFactory implements PackFactory {

    private final String modId;
    private final ExtensionManager.Loader<NeoForgeGathererExtensionsProvider> exts;

    private NeoForgePackBuilder.@Nullable Global global;
    private final List<NeoForgePackBuilder.BuiltIn> builtIns;

    /**
     * A basic constructor.
     *
     * @param modId The identiifer of the mod.
     * @param modBus The event bus for the mod instance.
     */
    public NeoForgePackFactory(String modId, IEventBus modBus) {
        this.modId = modId;
        this.exts = ExtensionManager.load(NeoForgeGathererExtensionsProvider.class);
        this.builtIns = new ArrayList<>();

        // Low here to use other modded entries.
        modBus.addListener(EventPriority.LOW, this::gatherData);
    }

    @Override
    public String id() {
        return this.modId;
    }

    @Override
    public PackBuilder global() {
        if (this.global == null) {
            this.global = new NeoForgePackBuilder.Global();
        }
        return this.global;
    }

    @Override
    public PackBuilder builtIn(Identifier id, UnaryOperator<PackMetadataGenerator> withMetadata) {
        var builder = new NeoForgePackBuilder.BuiltIn(id, withMetadata);
        this.builtIns.add(builder);
        return builder;
    }

    private void gatherData(GatherDataEvent.Client event) {
        // Handle the global first
        if (this.global != null) {
            this.global.registerRegistries(event::createDatapackRegistryObjects);
            this.global.registerProviders(event.getGenerator(), this.modId, event.getLookupProvider(), this.exts);
        }

        // Then handle the built-ins
        this.builtIns.forEach(builtIn -> builtIn.registerProviders(event.getGenerator(), this.modId, event.getLookupProvider(), this.exts));
    }
}
