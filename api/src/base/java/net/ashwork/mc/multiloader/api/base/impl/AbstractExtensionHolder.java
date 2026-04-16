package net.ashwork.mc.multiloader.api.base.impl;

import net.ashwork.mc.multiloader.api.base.extension.ExtensionHolder;
import net.ashwork.mc.multiloader.api.base.extension.ExtensionRegistrar;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * An abstract implementation of an extension holder. Makes use of the
 * {@link ExtensionManager} to load the desired extensions.
 *
 * @param <PROVIDER> The type of the extension provider.
 */
public class AbstractExtensionHolder<PROVIDER> implements ExtensionHolder {

    private final ExtensionHolder extensions;

    /**
     * A basic constructor.
     *
     * @param providerClass The class of the extension provider.
     * @param method The currying method to register extensions to.
     */
    protected AbstractExtensionHolder(Class<PROVIDER> providerClass, Function<PROVIDER, Consumer<ExtensionRegistrar>> method) {
        this.extensions = ExtensionManager.load(providerClass, method);
    }


    @Override
    public <API> API access(LoaderExtension.Key<API> extension) throws IllegalArgumentException {
        return this.extensions.access(extension);
    }

    @Override
    public <API> void accessIfPresent(LoaderExtension.Key<API> extension, Consumer<API> ifPresent) {
        this.extensions.accessIfPresent(extension, ifPresent);
    }
}
