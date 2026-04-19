package net.ashwork.mc.multiloader.fabric.data.generator;

import net.ashwork.mc.multiloader.api.base.impl.extension.ExtensionManager;
import net.ashwork.mc.multiloader.api.data.generator.PackBuilder;
import net.ashwork.mc.multiloader.api.data.generator.PackFactory;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

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
    public PackBuilder global() {
        return this.pack(new FabricPackBuilder.Global());
    }

    @Override
    public PackBuilder builtIn(String id) {
        return this.builtIn(Identifier.fromNamespaceAndPath(this.modId, id));
    }

    @Override
    public PackBuilder builtIn(Identifier id) {
        return this.pack(new FabricPackBuilder.BuiltIn(id));
    }

    private PackBuilder pack(FabricPackBuilder builder) {
        this.packs.add(builder);
        return builder;
    }

    /**
     * Performs the given action for each element until all elements
     * have been processed or the action throws an exception. Actions
     * are performed in the order of iteration, if that order is specified.
     * Exceptions thrown by the action are relayed to the caller.
     *
     * @param action The action to be performed for each element.
     */
    public void forEach(Consumer<FabricPackBuilder> action) {
        this.packs.forEach(action);
    }
}
