package net.ashwork.mc.multiloader.api.data.generator.provider.client;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.ashwork.mc.multiloader.api.data.generator.provider.DataGatherer;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;

import java.util.function.Consumer;

/**
 * Generates model JSONs for the provided keys.
 */
public interface ModelCreator {

    /**
     * Generates the block model JSONs for the provided keys.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer}
     */
    LoaderExtension.Key<DataGatherer.One<BlockModelGenerators>> BLOCK_PROVIDER = Multiloader.extension("block_models");
    /**
     * Generates the item model JSONs for the provided keys.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer}
     */
    LoaderExtension.Key<DataGatherer.One<ItemModelGenerators>> ITEM_PROVIDER = Multiloader.extension("item_models");
    /**
     * Generates the model JSONs for the provided keys.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer}
     */
    LoaderExtension.Key<DataGatherer.Two<BlockModelGenerators, ItemModelGenerators>> FLAT_PROVIDER = Multiloader.extension("flat_models");
    /**
     * Generates the model JSONs for the provided keys.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer}
     */
    LoaderExtension.Key<Consumer<ModelCreator>> PROVIDER = Multiloader.extension("models");

    /**
     * Generates the block state models for the provided keys.
     *
     * @param generators The {@link BlockModelGenerators}.
     */
    void blockStateModels(BlockModelGenerators generators);

    /**
     * Generates the item models for the provided keys.
     *
     * @param generators The {@link ItemModelGenerators}.
     */
    void itemModels(ItemModelGenerators generators);
}
