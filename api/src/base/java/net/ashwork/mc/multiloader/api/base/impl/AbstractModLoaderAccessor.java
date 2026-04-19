package net.ashwork.mc.multiloader.api.base.impl;

import net.ashwork.mc.multiloader.api.base.ModLoader;
import net.ashwork.mc.multiloader.api.base.ModLoaderAccessor;

import java.util.HashMap;
import java.util.Map;

/**
 * An abstract implementation of a mod loader accessor.
 */
public abstract class AbstractModLoaderAccessor implements ModLoaderAccessor {

    private final Map<String, ModLoader> loaders;

    protected AbstractModLoaderAccessor() {
        this.loaders = new HashMap<>();
    }

    @Override
    public ModLoader create(String modId) {
        return this.loaders.computeIfAbsent(modId, this::_create);
    }

    protected abstract ModLoader _create(String modId);
}
