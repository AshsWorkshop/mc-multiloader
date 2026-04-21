package net.ashwork.mc.multiloader.api.common.registry.impl;

import net.ashwork.mc.multiloader.api.common.registry.BlockRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.ItemRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.Registrar;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.function.Function;

/**
 * A block registrar that wraps around a {@link Registrar}.
 *
 * @param delegate The registrar delegate.
 */
public record BlockRegistrarWrapper(Registrar<Block> delegate) implements BlockRegistrar {

    @Override
    public <IMPL extends Block> Holder<IMPL> register(String name, Function<Identifier, IMPL> factory) {
        return this.delegate.register(name, factory);
    }
}
