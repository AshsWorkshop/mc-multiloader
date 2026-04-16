package net.ashwork.mc.multiloader.api.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A utility for registering objects to the backing registry.
 *
 * @param <REGISTRY> The type of the registry objects.
 */
public interface Registrar<REGISTRY> {

    /**
     * Registers an object.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param factory A supplier of the object.
     * @return A {@link Holder}-wrapped, registered object.
     * @param <IMPL> The type of the object being registered.
     */
    default <IMPL extends REGISTRY> Holder<IMPL> register(String name, Supplier<IMPL> factory) {
        return this.register(name, key -> factory.get());
    }

    /**
     * Registers an object.
     *
     * @param name The name of the object. Must be a valid {@link Identifier#getPath()}.
     * @param factory A function creating the object from its identifier.
     * @return A {@link Holder}-wrapped, registered object.
     * @param <IMPL> The type of the object being registered.
     */
    <IMPL extends REGISTRY> Holder<IMPL> register(String name, Function<Identifier, IMPL> factory);
}
