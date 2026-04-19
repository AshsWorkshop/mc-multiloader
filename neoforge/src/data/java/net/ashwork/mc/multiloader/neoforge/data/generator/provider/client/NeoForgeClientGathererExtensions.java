package net.ashwork.mc.multiloader.neoforge.data.generator.provider.client;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer;
import net.ashwork.mc.multiloader.api.data.generator.provider.client.TranslationKeyMapper;
import net.ashwork.mc.multiloader.neoforge.data.generator.NeoForgeGathererExtensionsProvider;
import net.neoforged.neoforge.common.data.LanguageProvider;

/**
 * Loader extensions for the client data generation provider API.
 */
public class NeoForgeClientGathererExtensions implements NeoForgeGathererExtensionsProvider {

    @Override
    public void registerExtensions(String modId, DataProviderGatherer providers, ExtensionRegistrar extensions) {
        extensions.provide(TranslationKeyMapper.PROVIDER, () -> locale -> translations ->
                providers.add((output) -> new LanguageProvider(output, modId, locale) {
                    @Override
                    protected void addTranslations() {
                        translations.accept(this::add);
                    }
                })
        );
        extensions.provide(TranslationKeyMapper.ENGLISH_PROVIDER, holder -> holder.access(TranslationKeyMapper.PROVIDER).apply("en_us"), TranslationKeyMapper.PROVIDER);
    }
}
