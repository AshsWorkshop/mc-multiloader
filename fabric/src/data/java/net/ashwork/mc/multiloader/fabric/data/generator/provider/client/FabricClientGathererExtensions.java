package net.ashwork.mc.multiloader.fabric.data.generator.provider.client;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer;
import net.ashwork.mc.multiloader.api.data.generator.provider.client.TranslationKeyMapper;
import net.ashwork.mc.multiloader.fabric.data.generator.FabricGathererExtensionsProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataProvider;

/**
 * Loader extensions for the client data generation provider API.
 */
public class FabricClientGathererExtensions implements FabricGathererExtensionsProvider {

    @Override
    public void registerExtensions(String modId, DataProviderGatherer providers, ExtensionRegistrar extensions) {
        extensions.provide(TranslationKeyMapper.PROVIDER, () -> locale -> translations ->
                providers.add(_wrap((output, registries) -> new FabricLanguageProvider(output, locale, registries) {

                    @Override
                    public void generateTranslations(HolderLookup.Provider registryLookup, TranslationBuilder translationBuilder) {
                        translations.accept(translationBuilder::add);
                    }
                }))
        );
        extensions.provide(TranslationKeyMapper.ENGLISH_PROVIDER, holder -> holder.access(TranslationKeyMapper.PROVIDER).apply("en_us"), TranslationKeyMapper.PROVIDER);
    }

    private static <PROVIDER extends DataProvider> DataProvider.Factory<PROVIDER> _wrap(FabricDataGenerator.Pack.Factory<PROVIDER> fabric) {
        return (output) -> fabric.create((FabricPackOutput) output);
    }

    private static <PROVIDER extends DataProvider> DataProviderGatherer.FactoryWithRegistries<PROVIDER> _wrap(FabricDataGenerator.Pack.RegistryDependentFactory<PROVIDER> fabric) {
        return (output, registries) -> fabric.create((FabricPackOutput) output, registries);
    }
}
