package net.ashwork.mc.multiloader.api;

import net.minecraft.resources.Identifier;

/**
 * The main interface.
 */
public interface Multiloader {

    /**
     * The identifier of the multiloader.
     */
    String ID = "ashsmultiloader";

    /**
     * Creates an {@link Identifier} for the multiloader.
     *
     * @param path The path of the identifier.
     * @return An {@link Identifier}.
     */
    static Identifier withId(String path) {
        return Identifier.fromNamespaceAndPath(ID, path);
    }
}
