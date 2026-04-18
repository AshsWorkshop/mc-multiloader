package net.ashwork.mc.multiloader.neoforge.common.registry;

import net.ashwork.mc.multiloader.api.common.registry.Registrar;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Function;

/**
 * A registrar that wraps around a {@link DeferredRegister}.
 *
 * @param delegate The registrar delegate.
 * @param <REGISTRY> The type of the registry objects.
 */
public record DeferredRegisterWrapper<REGISTRY>(DeferredRegister<REGISTRY> delegate) implements Registrar<REGISTRY> {

    @Override
    public <IMPL extends REGISTRY> Holder<IMPL> register(String name, Function<Identifier, IMPL> factory) {
        return (Holder<IMPL>) this.delegate.register(name, factory);
    }
}
