package net.ashwork.mc.multiloader.api.data.generator.provider.client;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.ashwork.mc.multiloader.api.data.generator.provider.DataGatherer;
import net.minecraft.core.Holder;
import net.minecraft.world.item.Item;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * A mapper that maps a translation key to its corresponding translation.
 */
public interface TranslationKeyMapper {

    /**
     * Generates the "en_us" localizations for some translation keys.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer}
     */
    LoaderExtension.Key<DataGatherer<TranslationKeyMapper>> ENGLISH_PROVIDER = Multiloader.extension("english_localization");
    /**
     * Generates the localizations for some translation keys for the provided locale.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer}
     */
    LoaderExtension.Key<Function<String, DataGatherer<TranslationKeyMapper>>> PROVIDER = Multiloader.extension("localization");

    /**
     * Adds a translation.
     *
     * @param key The translation key.
     * @param value The localized translation.
     */
    void translate(String key, String value);

    /**
     * Adds a translation for a {@link Holder}-wrapped object.
     *
     * @param holder The {@link Holder}-wrapped object.
     * @param value The localized translation.
     * @param translate A function that maps the object to its translation.
     * @param <T> The type of the wrapped object.
     */
    default <T> void holder(Holder<T> holder, String value, BiConsumer<T, String> translate) {
        translate.accept(holder.value(), value);
    }

    /**
     * Adds a translation for an {@link Item}.
     *
     * @param item The {@link Item} to get the translation key from.
     * @param value The localized translation.
     */
    default void item(Item item, String value) {
        this.translate(item.getDescriptionId(), value);
    }

    /**
     * Adds a translation for an {@link Item}.
     *
     * @param item The {@link Item} to get the translation key from.
     * @param value The localized translation.
     */
    default void item(Holder<Item> item, String value) {
        this.holder(item, value, this::item);
    }
}
