package net.ashwork.mc.multiloader.fabric.data.mixin.generator;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

/**
 * A mixin accessor for {@link FabricDataGenerator.Pack}.
 */
@Mixin(FabricDataGenerator.Pack.class)
public interface FabricDataGeneratorPackAccessor {

    /**
     * Basic constructor.
     *
     * @param shouldRun Whether the generator should run.
     * @param name The name prefix of the generator.
     * @param output The output to write the generated data to.
     * @return The constructed {@link FabricDataGenerator.Pack}.
     */
    @Invoker("<init>")
    static FabricDataGenerator.Pack newPack(boolean shouldRun, String name, FabricPackOutput output) {
        throw new AssertionError("");
    }
}
