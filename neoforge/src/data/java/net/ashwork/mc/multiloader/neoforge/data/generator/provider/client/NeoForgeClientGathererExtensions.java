package net.ashwork.mc.multiloader.neoforge.data.generator.provider.client;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer;
import net.ashwork.mc.multiloader.api.data.generator.provider.client.ModelCreator;
import net.ashwork.mc.multiloader.api.data.generator.provider.client.TranslationKeyMapper;
import net.ashwork.mc.multiloader.neoforge.data.generator.NeoForgeGathererExtensionsProvider;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.apache.commons.lang3.function.Consumers;

/**
 * Loader extensions for the client data generation provider API.
 */
public class NeoForgeClientGathererExtensions implements NeoForgeGathererExtensionsProvider {

    @Override
    public void registerExtensions(String modId, DataProviderGatherer providers, ExtensionRegistrar extensions) {
        // Translations
        extensions.provide(TranslationKeyMapper.PROVIDER, () -> locale -> translations ->
                providers.add((output) -> new LanguageProvider(output, modId, locale) {
                    @Override
                    protected void addTranslations() {
                        translations.accept(this::add);
                    }
                })
        );
        extensions.provide(TranslationKeyMapper.ENGLISH_PROVIDER, holder -> holder.access(TranslationKeyMapper.PROVIDER).apply("en_us"), TranslationKeyMapper.PROVIDER);

        // Models
        extensions.provide(ModelCreator.PROVIDER, () -> models ->
                providers.add((output) -> new ModelProvider(output, modId) {

                    @Override
                    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
                        models.blockStateModels(blockModels);
                        models.itemModels(itemModels);
                    }
                })
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
}
