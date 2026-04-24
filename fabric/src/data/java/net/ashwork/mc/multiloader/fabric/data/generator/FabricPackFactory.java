package net.ashwork.mc.multiloader.fabric.data.generator;

import net.ashwork.mc.multiloader.api.base.impl.extension.ExtensionManager;
import net.ashwork.mc.multiloader.api.data.generator.PackBuilder;
import net.ashwork.mc.multiloader.api.data.generator.PackFactory;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.DataProvider;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * The NeoForge implementation of the {@link PackFactory}.
 */
public class FabricPackFactory implements PackFactory {

    private final String modId;
    private final ExtensionManager.Loader<FabricGathererExtensionsProvider> exts;

    private final List<FabricPackBuilder> packs;

    /**
     * A basic constructor.
     *
     * @param modId The identiifer of the mod.
     */
    public FabricPackFactory(String modId) {
        this.modId = modId;
        this.exts = ExtensionManager.load(FabricGathererExtensionsProvider.class);
        this.packs = new ArrayList<>();

        // Low here to use other modded entries.
    }

    @Override
    public String id() {
        return this.modId;
    }

    @Override
    public PackBuilder global() {
        return this.pack(new FabricPackBuilder.Global());
    }

    @Override
    public PackBuilder builtIn(Identifier id, UnaryOperator<PackMetadataGenerator> withMetadata) {
        return this.pack(new FabricPackBuilder.BuiltIn(id, withMetadata));
    }

    private PackBuilder pack(FabricPackBuilder builder) {
        this.packs.add(builder);
        return builder;
    }

    /**
     * Resolves the datapack registrars and registers them to a registry.
     *
     * @param registry The registry set to register to.
     */
    public void registerRegistries(RegistrySetBuilder registry) {
        this.packs.forEach(builder -> builder.registerRegistries(registry));
    }

    /**
     * Resolves and registers the stored {@link DataProvider}s.
     *
     * @param generator The data generator..
     */
    public void registerProviders(FabricDataGenerator generator) {
        this.packs.forEach(builder -> builder.registerProviders(generator, this.modId, this.exts));
    }
}
