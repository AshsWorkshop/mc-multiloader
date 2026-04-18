package net.ashwork.mc.multiloader.api.common.registry.impl;

import net.ashwork.mc.multiloader.api.common.registry.ItemRegistrar;
import net.ashwork.mc.multiloader.api.common.registry.Registrar;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;

import java.util.function.Function;

/**
 * An item registrar that wraps around a {@link Registrar}.
 *
 * @param delegate The registrar delegate.
 */
public record ItemRegistrarWrapper(Registrar<Item> delegate) implements ItemRegistrar {

    @Override
    public <IMPL extends Item> Holder<IMPL> register(String name, Function<Identifier, IMPL> factory) {
        return this.delegate.register(name, factory);
    }
}
