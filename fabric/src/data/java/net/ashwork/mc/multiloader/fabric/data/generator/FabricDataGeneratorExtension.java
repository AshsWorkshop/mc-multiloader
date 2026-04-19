package net.ashwork.mc.multiloader.fabric.data.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.Identifier;

/**
 * An extension interface for the {@link FabricDataGenerator}.
 */
public interface FabricDataGeneratorExtension {

    /**
     * Creates a built-in pack located in 'data/{@code id_namespace}/datapacks/{@code id_path}'.
     *
     * @param id The identifier of the built-in pack.
     * @return The {@link FabricDataGenerator.Pack} to generate data for.
     */
    FabricDataGenerator.Pack createBuiltInDataPack(Identifier id);
}
