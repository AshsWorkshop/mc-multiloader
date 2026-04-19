package net.ashwork.mc.multiloader.api.data.generator;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.minecraft.resources.Identifier;

/**
 * A factory that creates the packs to generate data for.
 */
public interface PackFactory {

    /**
     * Creates packs to generate the data for.
     *
     * @extension {@link net.ashwork.mc.multiloader.api.base.ModLoader}
     */
    LoaderExtension.Key<PackFactory> EXT = Multiloader.extension("pack_factory");

    /**
     * Creates a pack for the mod. All providers registered in
     * the global pack will run before any built-in packs.
     *
     * @return The {@link PackBuilder} to generate data for.
     */
    PackBuilder global();

    /**
     * Creates a built-in pack. Assumes the namespace is the
     * mod creating the pack.
     *
     * @param id The identifier of the built-in pack.
     * @return The {@link PackBuilder} to generate data for.
     */
    PackBuilder builtIn(String id);

    /**
     * Creates a built-in pack.
     *
     * @param id The identifier of the built-in pack.
     * @return The {@link PackBuilder} to generate data for.
     */
    PackBuilder builtIn(Identifier id);
}
