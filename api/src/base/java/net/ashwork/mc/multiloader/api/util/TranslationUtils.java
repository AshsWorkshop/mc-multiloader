package net.ashwork.mc.multiloader.api.util;

import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;

/**
 * A utility for common translation methods.
 */
public interface TranslationUtils {

    /**
     * Creates a description identifier translation key.
     *
     * @param namespace The namespace or type of the identifier.
     * @param key The unique identifier of the constructed type.
     * @param suffix A subcategory or suffix for the specific type.
     * @return The constructed translation key.
     */
    static String makeDescriptionId(String namespace, Identifier key, String suffix) {
        return Util.makeDescriptionId(namespace, key) + "." + suffix;
    }
}
