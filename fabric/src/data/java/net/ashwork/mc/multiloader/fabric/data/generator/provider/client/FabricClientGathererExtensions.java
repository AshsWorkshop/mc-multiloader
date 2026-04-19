package net.ashwork.mc.multiloader.fabric.data.generator.provider.client;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer;
import net.ashwork.mc.multiloader.api.data.generator.provider.client.ModelCreator;
import net.ashwork.mc.multiloader.api.data.generator.provider.client.TranslationKeyMapper;
import net.ashwork.mc.multiloader.fabric.data.generator.FabricGathererExtensionsProvider;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;
import org.apache.commons.lang3.function.Consumers;

/**
 * Loader extensions for the client data generation provider API.
 */
public class FabricClientGathererExtensions implements FabricGathererExtensionsProvider {

    @Override
    public void registerExtensions(String modId, DataProviderGatherer providers, ExtensionRegistrar extensions) {
        // Translations
        extensions.provide(TranslationKeyMapper.PROVIDER, () -> locale -> translations ->
                providers.add(_wrap((output, registries) -> new FabricLanguageProvider(output, locale, registries) {

                    @Override
                    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
                        translations.accept(translationBuilder::add);
                    }
                }))
        );
        extensions.provide(TranslationKeyMapper.ENGLISH_PROVIDER, holder -> holder.access(TranslationKeyMapper.PROVIDER).apply("en_us"), TranslationKeyMapper.PROVIDER);

        // Models
        extensions.provide(ModelCreator.PROVIDER, () -> models ->
                providers.add(_wrap(output -> new FabricModelProvider(output) {
                            @Override
                            public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
                                models.blockStateModels(blockModelGenerators);
                            }

                            @Override
                            public void generateItemModels(ItemModelGenerators itemModelGenerators) {
                                models.itemModels(itemModelGenerators);
                            }
                        }
                ))
        );
        extensions.provide(ModelCreator.FLAT_PROVIDER, holder -> (blockModels, itemModels) ->
                        holder.access(ModelCreator.PROVIDER).accept(new ModelCreator() {
                            @Override
                            public void blockStateModels(BlockModelGenerators generators) {
                                blockModels.accept(generators);
                            }

                            @Override
                            public void itemModels(ItemModelGenerators generators) {
                                itemModels.accept(generators);
                            }
                        }),
                ModelCreator.PROVIDER
        );
        extensions.provide(ModelCreator.ITEM_PROVIDER, holder -> itemModels ->
                        holder.access(ModelCreator.FLAT_PROVIDER).add(Consumers.nop(), itemModels),
                ModelCreator.FLAT_PROVIDER
        );
        extensions.provide(ModelCreator.BLOCK_PROVIDER, holder -> blockModels ->
                        holder.access(ModelCreator.FLAT_PROVIDER).add(blockModels, Consumers.nop()),
                ModelCreator.FLAT_PROVIDER
        );
    }

    private static <PROVIDER extends DataProvider> DataProvider.Factory<PROVIDER> _wrap(FabricDataGenerator.Pack.Factory<PROVIDER> fabric) {
        return (output) -> fabric.create((FabricPackOutput) output);
    }

    private static <PROVIDER extends DataProvider> DataProviderGatherer.FactoryWithRegistries<PROVIDER> _wrap(FabricDataGenerator.Pack.RegistryDependentFactory<PROVIDER> fabric) {
        return (output, registries) -> fabric.create((FabricPackOutput) output, registries);
    }
}
