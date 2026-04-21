package net.ashwork.mc.multiloader.api.common.registry;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;

import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * A utility for registering items to
 * {@link net.minecraft.core.registries.BuiltInRegistries#ITEM}.
 */
public interface ItemRegistrar extends Registrar<Item> {

    /**
     * Analogous to {@link RegistrarAccessor#create(ResourceKey)}.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<ItemRegistrar> BASIC = Multiloader.extension("item_registrar_basic");
    /**
     * Analogous to {@link RegistrarAccessor#create(ResourceKey, String)}.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<Function<String, ItemRegistrar>> EXT = Multiloader.extension("item_registrar");

    /**
     * Registers a basic {@link Item} with default properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @return A {@link Holder}-wrapped, registered item.
     */
    default Holder<Item> registerBasicItem(String name) {
        return this.registerBasicItem(name, UnaryOperator.identity());
    }

    /**
     * Registers a basic {@link Item}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param properties A unary operator of the item properties.
     * @return A {@link Holder}-wrapped, registered item.
     */
    default Holder<Item> registerBasicItem(String name, UnaryOperator<Item.Properties> properties) {
        return this.registerItem(name, Item::new, properties);
    }

    /**
     * Registers an {@link Item} with default properties.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param factory A function creating the item from its properties.
     * @return A {@link Holder}-wrapped, registered item.
     * @param <IMPL> The type of the item being registered.
     */
    default <IMPL extends Item> Holder<IMPL> registerItem(String name, Function<Item.Properties, IMPL> factory) {
        return this.registerItem(name, factory, UnaryOperator.identity());
    }

    /**
     * Registers an {@link Item}.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param factory A function creating the item from its properties.
     * @param properties A unary operator of the item properties.
     * @return A {@link Holder}-wrapped, registered item.
     * @param <IMPL> The type of the item being registered.
     */
    default <IMPL extends Item> Holder<IMPL> registerItem(String name, Function<Item.Properties, IMPL> factory, UnaryOperator<Item.Properties> properties) {
        return this.register(name, key -> factory.apply(properties.apply(new Item.Properties()).setId(ResourceKey.create(Registries.ITEM, key))));
    }
}
