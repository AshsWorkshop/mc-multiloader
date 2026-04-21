package net.ashwork.mc.multiloader.api.common.registry;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * A utility for registering blocks to {@link net.minecraft.core.registries.BuiltInRegistries#BLOCK}
 * and its associated block item to {@link net.minecraft.core.registries.BuiltInRegistries#ITEM}.
 */
public interface BlockItemRegistrar {

    /**
     * Analogous to {@link RegistrarAccessor#create(ResourceKey)}.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<BlockItemRegistrar> BASIC = Multiloader.extension("block_item_registrar_basic");
    /**
     * Analogous to {@link RegistrarAccessor#create(ResourceKey, String)}.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<Function<String, BlockItemRegistrar>> EXT = Multiloader.extension("block_item_registrar");

    /**
     * {@return the backing block registrar}
     */
    BlockRegistrar blocks();

    /**
     * {@return the backing item registrar}
     */
    ItemRegistrar items();

    /**
     * Registers a basic {@link Block} with default properties and its associated basic
     * {@link BlockItem} with default properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @return A {@link Holder}-wrapped, registered block.
     */
    default Holder<Block> registerBasicBlockWithBasicItem(String name) {
        return this.registerBasicBlockWithBasicItem(name, UnaryOperator.identity());
    }

    /**
     * Registers a basic {@link Block} with new properties and its associated basic
     * {@link BlockItem} with default properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockProperties A unary operator of the block properties.
     * @return A {@link Holder}-wrapped, registered block.
     */
    default Holder<Block> registerBasicBlockWithBasicItem(String name, UnaryOperator<BlockBehaviour.Properties> blockProperties) {
        return this.registerBasicBlockWithBasicItem(name, blockProperties, UnaryOperator.identity());
    }

    /**
     * Registers a basic {@link Block} and its associated basic {@link BlockItem} with
     * default properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockProperties The supplied block properties.
     * @return A {@link Holder}-wrapped, registered block.
     */
    default Holder<Block> registerBasicBlockWithBasicItem(String name, Supplier<BlockBehaviour.Properties> blockProperties) {
        return this.registerBasicBlockWithBasicItem(name, blockProperties, UnaryOperator.identity());
    }

    /**
     * Registers a basic {@link Block} with new properties and its associated basic {@link BlockItem}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockProperties A unary operator of the block properties.
     * @param itemProperties A unary operator of the item properties.
     * @return A {@link Holder}-wrapped, registered block.
     */
    default Holder<Block> registerBasicBlockWithBasicItem(String name, UnaryOperator<BlockBehaviour.Properties> blockProperties, UnaryOperator<Item.Properties> itemProperties) {
        return this.registerBlockWithBasicItem(name, Block::new, blockProperties, itemProperties);
    }

    /**
     * Registers a basic {@link Block} and its associated basic {@link BlockItem}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockProperties The supplied block properties.
     * @param itemProperties A unary operator of the item properties.
     * @return A {@link Holder}-wrapped, registered block.
     */
    default Holder<Block> registerBasicBlockWithBasicItem(String name, Supplier<BlockBehaviour.Properties> blockProperties, UnaryOperator<Item.Properties> itemProperties) {
        return this.registerBlockWithBasicItem(name, Block::new, blockProperties, itemProperties);
    }

    /**
     * Registers a {@link Block} with new properties and its associated basic {@link BlockItem}
     * with default properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockFactory A function creating the block from its properties.
     * @param blockProperties A unary operator of the block properties.
     * @return A {@link Holder}-wrapped, registered block.
     * @param <BLOCK> The type of the block being registered.
     */
    default <BLOCK extends Block> Holder<BLOCK> registerBlockWithBasicItem(String name, Function<BlockBehaviour.Properties, BLOCK> blockFactory, UnaryOperator<BlockBehaviour.Properties> blockProperties) {
        return this.registerBlockWithBasicItem(name, blockFactory, blockProperties, UnaryOperator.identity());
    }

    /**
     * Registers a {@link Block} and its associated basic {@link BlockItem} with default properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockFactory A function creating the block from its properties.
     * @param blockProperties The supplied block properties.
     * @return A {@link Holder}-wrapped, registered block.
     * @param <BLOCK> The type of the block being registered.
     */
    default <BLOCK extends Block> Holder<BLOCK> registerBlockWithBasicItem(String name, Function<BlockBehaviour.Properties, BLOCK> blockFactory, Supplier<BlockBehaviour.Properties> blockProperties) {
        return this.registerBlockWithBasicItem(name, blockFactory, blockProperties, UnaryOperator.identity());
    }

    /**
     * Registers a {@link Block} with new properties and its associated basic {@link BlockItem}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockFactory A function creating the block from its properties.
     * @param blockProperties A unary operator of the block properties.
     * @param itemProperties A unary operator of the item properties.
     * @return A {@link Holder}-wrapped, registered block.
     * @param <BLOCK> The type of the block being registered.
     */
    default <BLOCK extends Block> Holder<BLOCK> registerBlockWithBasicItem(String name, Function<BlockBehaviour.Properties, BLOCK> blockFactory, UnaryOperator<BlockBehaviour.Properties> blockProperties, UnaryOperator<Item.Properties> itemProperties) {
        return this.registerBlockWithItem(name, blockFactory, blockProperties, BlockItem::new, itemProperties);
    }

    /**
     * Registers a {@link Block} and its associated basic {@link BlockItem}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockFactory A function creating the block from its properties.
     * @param blockProperties The supplied block properties.
     * @param itemProperties A unary operator of the item properties.
     * @return A {@link Holder}-wrapped, registered block.
     * @param <BLOCK> The type of the block being registered.
     */
    default <BLOCK extends Block> Holder<BLOCK> registerBlockWithBasicItem(String name, Function<BlockBehaviour.Properties, BLOCK> blockFactory, Supplier<BlockBehaviour.Properties> blockProperties, UnaryOperator<Item.Properties> itemProperties) {
        return this.registerBlockWithItem(name, blockFactory, blockProperties, BlockItem::new, itemProperties);
    }

    /**
     * Registers a {@link Block} with new properties and its associated {@link BlockItem}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockFactory A function creating the block from its properties.
     * @param blockProperties A unary operator of the block properties.
     * @param itemFactory A function creating the item from its properties.
     * @param itemProperties A unary operator of the item properties.
     * @return A {@link Holder}-wrapped, registered block.
     * @param <BLOCK> The type of the block being registered.
     * @param <ITEM> The type of the item being registered.
     */
    default <BLOCK extends Block, ITEM extends BlockItem> Holder<BLOCK> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, BLOCK> blockFactory, UnaryOperator<BlockBehaviour.Properties> blockProperties, BiFunction<Block, Item.Properties, ITEM> itemFactory, UnaryOperator<Item.Properties> itemProperties) {
        return this.registerBlockWithItem(name, blockFactory, () -> blockProperties.apply(BlockBehaviour.Properties.of()), itemFactory, itemProperties);
    }

    /**
     * Registers a {@link Block} and its associated {@link BlockItem}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param blockFactory A function creating the block from its properties.
     * @param blockProperties The supplied block properties.
     * @param itemFactory A function creating the item from its properties.
     * @param itemProperties A unary operator of the item properties.
     * @return A {@link Holder}-wrapped, registered block.
     * @param <BLOCK> The type of the block being registered.
     * @param <ITEM> The type of the item being registered.
     */
    default <BLOCK extends Block, ITEM extends BlockItem> Holder<BLOCK> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, BLOCK> blockFactory, Supplier<BlockBehaviour.Properties> blockProperties, BiFunction<Block, Item.Properties, ITEM> itemFactory, UnaryOperator<Item.Properties> itemProperties) {
        var block = this.blocks().registerBlock(name, blockFactory, blockProperties);
        this.items().registerItem(name, props -> itemFactory.apply(block.value(), props), props -> itemProperties.apply(props.useBlockDescriptionPrefix().requiredFeatures(block.value().requiredFeatures())));
        return block;
    }
}
