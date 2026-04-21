package net.ashwork.mc.multiloader.api.common.registry.impl;

import net.ashwork.mc.multiloader.api.common.registry.BlockItemRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.BlockRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.ItemRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.Registrar;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

/**
 * A registrar that wraps around a {@link BlockRegistrar}
 * and {@link ItemRegistrar}.
 *
 * @param blocks The block registrar delegate.
 * @param items The item registrar delegate.
 */
public record BlockItemRegistrarWrapper(BlockRegistrar blocks, ItemRegistrar items) implements BlockItemRegistrar {}
