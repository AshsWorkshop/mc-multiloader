package net.ashwork.mc.multiloader.api.base.impl;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.base.impl.extension.AbstractExtensionHolder;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An abstract implementation of a mod loader.
 *
 * @param <PROVIDER> The type of the extension provider.
 */
public class AbstractModLoader<PROVIDER> extends AbstractExtensionHolder<PROVIDER> implements ModLoader {

    private final String id;

    /**
     * A basic constructor.
     *
     * @param id The base identifier of the mod.
     * @param providerClass The class of the extension provider.
     * @param method        The currying method to register extensions to.
     */
    protected AbstractModLoader(String id, Class<PROVIDER> providerClass, Function<PROVIDER, Consumer<ExtensionRegistrar>> method) {
        super(providerClass, method);
        this.id = id;
    }

    @Override
    public String id() {
        return this.id;
    }
}
