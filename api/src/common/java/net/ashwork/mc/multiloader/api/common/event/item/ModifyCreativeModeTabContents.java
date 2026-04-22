package net.ashwork.mc.multiloader.api.common.event.item;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.event.Event;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

import java.util.Arrays;
import java.util.function.Function;

/**
 * An event called when the contents of a {@link CreativeModeTab}
 * are being built.
 */
@FunctionalInterface
public interface ModifyCreativeModeTabContents {

    /**
     * Modifies the contents of a given {@link CreativeModeTab}.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}.
     */
    LoaderExtension.Key<Function<ResourceKey<CreativeModeTab>, Event<ModifyCreativeModeTabContents>>> EVENT = Multiloader.extension("modify_tab_contents_event");

    /**
     * Modifies the contents of a {@link CreativeModeTab}.
     *
     * @param output The output that accepts the modifications to apply.
     */
    void modifyContents(ModifyCreativeModeTabContents.Output output);

    /**
     * An extension of {@link CreativeModeTab.Output} to support additional
     * loader features.
     */
    public interface Output extends CreativeModeTab.Output {

        /**
         * {@return the display parameters for items in the tab}
         */
        CreativeModeTab.ItemDisplayParameters params();

        /**
         * Inserts the given item holders at the end of the tab in the order
         * provided. Uses the maximum visibility.
         *
         * @param items The item holders to append.
         */
        default void accept(Holder<? extends Item>... items) {
            this.accept(CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, items);
        }

        /**
         * Inserts the given item holders at the end of the tab in the order
         * provided.
         *
         * @param visibility How the entries will be shown in the tabs menu.
         * @param items The item holders to append.
         */
        default void accept(CreativeModeTab.TabVisibility visibility, Holder<? extends Item>... items) {
            for (var item : items) this.accept(item.value(), visibility);
        }

        /**
         * Inserts the given item holders at the start of the tab in the order
         * provided. Uses the maximum visibility.
         *
         * @param items The item holders to prepend.
         */
        default void insertFirst(Holder<? extends Item>... items) {
            this.insertFirst(CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, items);
        }

        /**
         * Inserts the given item holders at the start of the tab in the order
         * provided.
         *
         * @param visibility How the entries will be shown in the tabs menu.
         * @param items The item holders to prepend.
         */
        default void insertFirst(CreativeModeTab.TabVisibility visibility, Holder<? extends Item>... items) {
            this.insertFirst(visibility, mapArray(holder -> new ItemStack((Holder<Item>) holder), items));
        }

        /**
         * Inserts the given items at the start of the tab in the order provided.
         * Uses the maximum visibility.
         *
         * @param items The items to prepend.
         */
        default void insertFirst(ItemLike... items) {
            this.insertFirst(CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, items);
        }

        /**
         * Inserts the given items at the start of the tab in the order provided.
         *
         * @param visibility How the entries will be shown in the tabs menu.
         * @param items The items to prepend.
         */
        default void insertFirst(CreativeModeTab.TabVisibility visibility, ItemLike... items) {
            this.insertFirst(visibility, mapArray(ItemStack::new, items));
        }

        /**
         * Inserts the given stacks at the start of the tab in the order provided.
         * Uses the maximum visibility.
         *
         * @param stacks The stacks to prepend.
         */
        default void insertFirst(ItemStack... stacks) {
            this.insertFirst(CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, stacks);
        }

        /**
         * Inserts the given stacks at the start of the tab in the order provided.
         *
         * @param visibility How the entries will be shown in the tabs menu.
         * @param stacks The stacks to prepend.
         */
        void insertFirst(CreativeModeTab.TabVisibility visibility, ItemStack... stacks);

        /**
         * Inserts the given item holders after the specified 'first' stack in the
         * order provided. Uses the maximum visibility.
         *
         * @param first The stack to insert after.
         * @param items The item holders to append.
         */
        default void insertAfter(ItemStack first, Holder<? extends Item>... items) {
            this.insertAfter(first, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, items);
        }

        /**
         * Inserts the given item holders after the specified 'first' stack in the
         * order provided.
         *
         * @param first The stack to insert after.
         * @param visibility How the entries will be shown in the tabs menu.
         * @param items The item holders to append.
         */
        default void insertAfter(ItemStack first, CreativeModeTab.TabVisibility visibility, Holder<? extends Item>... items) {
            this.insertAfter(first, visibility, mapArray(holder -> new ItemStack((Holder<Item>) holder), items));
        }

        /**
         * Inserts the given items after the specified 'first' stack in the order
         * provided. Uses the maximum visibility.
         *
         * @param first The stack to insert after.
         * @param items The items to append.
         */
        default void insertAfter(ItemStack first, ItemLike... items) {
            this.insertAfter(first, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, items);
        }

        /**
         * Inserts the given items after the specified 'first' stack in the order
         * provided.
         *
         * @param first The stack to insert after.
         * @param visibility How the entries will be shown in the tabs menu.
         * @param items The items to append.
         */
        default void insertAfter(ItemStack first, CreativeModeTab.TabVisibility visibility, ItemLike... items) {
            this.insertAfter(first, visibility, mapArray(ItemStack::new, items));
        }

        /**
         * Inserts the given stacks after the specified 'first' stack in the order
         * provided. Uses the maximum visibility.
         *
         * @param first The stack to insert after.
         * @param stacks The stacks to append.
         */
        default void insertAfter(ItemStack first, ItemStack... stacks) {
            this.insertAfter(first, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, stacks);
        }

        /**
         * Inserts the given stacks after the specified 'first' stack in the order
         * provided.
         *
         * @param first The stack to insert after.
         * @param visibility How the entries will be shown in the tabs menu.
         * @param stacks The stacks to append.
         */
        void insertAfter(ItemStack first, CreativeModeTab.TabVisibility visibility, ItemStack... stacks);

        /**
         * Inserts the given item holders before the specified 'last' stack in the
         * order provided. Uses the maximum visibility.
         *
         * @param last The stack to insert before.
         * @param items The item holders to prepend.
         */
        default void insertBefore(ItemStack last, Holder<? extends Item>... items) {
            this.insertBefore(last, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, items);
        }

        /**
         * Inserts the given item holders before the specified 'last' stack in the
         * order provided.
         *
         * @param last The stack to insert before.
         * @param visibility How the entries will be shown in the tabs menu.
         * @param items The item holders to prepend.
         */
        default void insertBefore(ItemStack last, CreativeModeTab.TabVisibility visibility, Holder<? extends Item>... items) {
            this.insertBefore(last, visibility, mapArray(holder -> new ItemStack((Holder<Item>) holder), items));
        }

        /**
         * Inserts the given items before the specified 'last' stack in the order
         * provided. Uses the maximum visibility.
         *
         * @param last The stack to insert before.
         * @param items The items to prepend.
         */
        default void insertBefore(ItemStack last, ItemLike... items) {
            this.insertBefore(last, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, items);
        }

        /**
         * Inserts the given items before the specified 'last' stack in the order
         * provided.
         *
         * @param last The stack to insert before.
         * @param visibility How the entries will be shown in the tabs menu.
         * @param items The items to prepend.
         */
        default void insertBefore(ItemStack last, CreativeModeTab.TabVisibility visibility, ItemLike... items) {
            this.insertBefore(last, visibility, mapArray(ItemStack::new, items));
        }

        /**
         * Inserts the given stacks before the specified 'last' stack in the order
         * provided. Uses the maximum visibility.
         *
         * @param last The stack to insert before.
         * @param stacks The stacks to prepend.
         */
        default void insertBefore(ItemStack last, ItemStack... stacks) {
            this.insertBefore(last, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, stacks);
        }

        /**
         * Inserts the given stacks before the specified 'last' stack in the order
         * provided.
         *
         * @param last The stack to insert before.
         * @param visibility How the entries will be shown in the tabs menu.
         * @param stacks The stacks to prepend.
         */
        void insertBefore(ItemStack last, CreativeModeTab.TabVisibility visibility, ItemStack... stacks);

        private static <T> ItemStack[] mapArray(Function<T, ItemStack> mapper, T... items) {
            return Arrays.stream(items).map(mapper).toArray(ItemStack[]::new);
        }
    }
}
