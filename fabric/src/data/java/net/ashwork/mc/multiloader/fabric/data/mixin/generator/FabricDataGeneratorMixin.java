package net.ashwork.mc.multiloader.fabric.data.mixin.generator;

import net.ashwork.mc.multiloader.fabric.data.generator.FabricDataGeneratorExtension;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.nio.file.Path;

/**
 * A mixin for {@link FabricDataGenerator}.
 */
@Mixin(FabricDataGenerator.class)
public abstract class FabricDataGeneratorMixin implements FabricDataGeneratorExtension {

    @Shadow
    @Final
    protected PackOutput vanillaPackOutput;
    @Shadow
    @Final
    private ModContainer modContainer;
    @Shadow
    @Final
    private boolean strictValidation;

    @Override
    public FabricDataGenerator.Pack createBuiltInDataPack(Identifier id) {
        Path path = this.vanillaPackOutput.getOutputFolder(PackOutput.Target.DATA_PACK).resolve(id.getNamespace()).resolve("datapacks").resolve(id.getPath());
        return FabricDataGeneratorPackAccessor.newPack(true, id.toString(), new FabricPackOutput(this.modContainer, path, this.strictValidation));
    }
}
