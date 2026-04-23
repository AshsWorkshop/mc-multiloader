package net.ashwork.mc.multiloader.api.data.generator.provider.client;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.ashwork.mc.multiloader.api.data.generator.provider.DataGatherer;
import net.ashwork.mc.multiloader.api.util.TranslationUtils;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Maps a translation key to its corresponding translation.
 */
public interface TranslationKeyMapper {

    /**
     * Generates the "en_us" localizations for some translation keys.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer}
     */
    LoaderExtension.Key<DataGatherer.One<TranslationKeyMapper>> ENGLISH_PROVIDER = Multiloader.extension("english_localization");
    /**
     * Generates the localizations for some translation keys for the provided locale.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.data.generator.DataProviderGatherer}
     */
    LoaderExtension.Key<Function<String, DataGatherer.One<TranslationKeyMapper>>> PROVIDER = Multiloader.extension("localization");

    /**
     * Adds a translation.
     *
     * @param key The translation key.
     * @param value The localized translation.
     */
    void translate(String key, String value);

    /**
     * Adds a translation for a description id.
     *
     * @param namespace The namespace of the description identifier.
     * @param key The identifier.
     * @param value The localized translation.
     */
    default void descriptionId(String namespace, Identifier key, String value) {
        this.translate(Util.makeDescriptionId(namespace, key), value);
    }

    /**
     * Adds a translation for a description id.
     *
     * @param namespace The namespace of the description identifier.
     * @param suffix The suffix to append to the description identifier.
     * @param key The identifier.
     * @param value The localized translation.
     */
    default void descriptionId(String namespace, String suffix, Identifier key, String value) {
        this.translate(TranslationUtils.makeDescriptionId(namespace, key, suffix), value);
    }

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

    /**
     * Adds a translation for a {@link Block}.
     *
     * @param block The {@link Block} to get the translation key from.
     * @param value The localized translation.
     */
    default void block(Block block, String value) {
        this.translate(block.getDescriptionId(), value);
    }

    /**
     * Adds a translation for a {@link Block}.
     *
     * @param block The {@link Block} to get the translation key from.
     * @param value The localized translation.
     */
    default void block(Holder<Block> block, String value) {
        this.holder(block, value, this::block);
    }
}
