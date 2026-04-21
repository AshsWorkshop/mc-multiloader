package net.ashwork.mc.multiloader.api.common.registry;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * A utility for registering items to
 * {@link net.minecraft.core.registries.BuiltInRegistries#BLOCK}.
 */
public interface BlockRegistrar extends Registrar<Block> {

    /**
     * Analogous to {@link RegistrarAccessor#create(ResourceKey)}.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<BlockRegistrar> BASIC = Multiloader.extension("block_registrar_basic");
    /**
     * Analogous to {@link RegistrarAccessor#create(ResourceKey, String)}.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<Function<String, BlockRegistrar>> EXT = Multiloader.extension("block_registrar");

    /**
     * Registers a basic {@link Block} with default properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @return A {@link Holder}-wrapped, registered block.
     */
    default Holder<Block> registerBasicBlock(String name) {
        return this.registerBlock(name, Block::new);
    }

    /**
     * Registers a basic {@link Block} with new properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param properties A unary operator of the block properties.
     * @return A {@link Holder}-wrapped, registered block.
     */
    default Holder<Block> registerBasicBlock(String name, UnaryOperator<BlockBehaviour.Properties> properties) {
        return this.registerBlock(name, Block::new, properties);
    }

    /**
     * Registers a basic {@link Block}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param properties The supplied block properties.
     * @return A {@link Holder}-wrapped, registered block.
     */
    default Holder<Block> registerBasicBlock(String name, Supplier<BlockBehaviour.Properties> properties) {
        return this.registerBlock(name, Block::new, properties);
    }

    /**
     * Registers a {@link Block} with default properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param factory A function creating the block from its properties.
     * @return A {@link Holder}-wrapped, registered block.
     * @param <IMPL> The type of the block being registered.
     */
    default <IMPL extends Block> Holder<IMPL> registerBlock(String name, Function<BlockBehaviour.Properties, IMPL> factory) {
        return this.registerBlock(name, factory, UnaryOperator.identity());
    }

    /**
     * Registers a {@link Block} with new properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param factory A function creating the block from its properties.
     * @param properties A unary operator of the block properties.
     * @return A {@link Holder}-wrapped, registered block.
     * @param <IMPL> The type of the block being registered.
     */
    default <IMPL extends Block> Holder<IMPL> registerBlock(String name, Function<BlockBehaviour.Properties, IMPL> factory, UnaryOperator<BlockBehaviour.Properties> properties) {
        return this.registerBlock(name, factory, () -> properties.apply(BlockBehaviour.Properties.of()));
    }

    /**
     * Registers a {@link Block}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param factory A function creating the block from its properties.
     * @param properties The supplied block properties.
     * @return A {@link Holder}-wrapped, registered block.
     * @param <IMPL> The type of the block being registered.
     */
    default <IMPL extends Block> Holder<IMPL> registerBlock(String name, Function<BlockBehaviour.Properties, IMPL> factory, Supplier<BlockBehaviour.Properties> properties) {
        return this.register(name, key -> factory.apply(properties.get().setId(ResourceKey.create(Registries.BLOCK, key))));
    }
}
