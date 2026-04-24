package net.ashwork.mc.multiloader.api.data.generator;

import net.ashwork.mc.multiloader.api.Multiloader;
import net.ashwork.mc.multiloader.api.base.IdHelper;
import net.ashwork.mc.multiloader.api.base.extension.LoaderExtension;
import net.ashwork.mc.multiloader.api.common.event.resources.RegisterBuiltInPacks;
import net.minecraft.DetectedVersion;
import net.minecraft.data.metadata.PackMetadataGenerator;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.metadata.pack.PackFormat;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.util.InclusiveRange;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.function.Consumers;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * A factory that creates the packs to generate data for.
 */
public interface PackFactory extends IdHelper {

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
     * Creates a built-in pack. Assumes the namespace is the mod
     * creating the pack. Sets the default pack metadata section
     * to encompass both client and server.
     *
     * @param id The identifier of the built-in pack.
     * @return The {@link PackBuilder} to generate data for.
     */
    default PackBuilder builtIn(String id) {
        return this.builtIn(this.withId(id));
    }

    /**
     * Creates a built-in pack. Sets the default pack metadata section
     * to encompass both client and server.
     *
     * @param id The identifier of the built-in pack.
     * @return The {@link PackBuilder} to generate data for.
     */
    default PackBuilder builtIn(Identifier id) {
        return this.builtIn(id, generator -> {
            // Compute range that encompasses both server and client
            var server = DetectedVersion.BUILT_IN.packVersion(PackType.SERVER_DATA).minorRange();
            var client = DetectedVersion.BUILT_IN.packVersion(PackType.CLIENT_RESOURCES).minorRange();
            InclusiveRange<PackFormat> full = new InclusiveRange<>(
                    ObjectUtils.min(client.minInclusive(), server.minInclusive()),
                    ObjectUtils.max(client.maxInclusive(), server.maxInclusive())
            );

            // Use one of the available section types since otherwise the pack is marked as incompatible
            return generator.add(PackMetadataSection.SERVER_TYPE, new PackMetadataSection(
                    Component.translatable(id.toLanguageKey(RegisterBuiltInPacks.RESOURCE_PACK_ID, RegisterBuiltInPacks.RESOURCE_PACK_DESC)), full
            ));
        });
    }

    /**
     * Creates a built-in pack. Assumes the namespace is the
     * mod creating the pack.
     *
     * @param id The identifier of the built-in pack.
     * @param withMetadata The metadata of the built-in pack.
     * @return The {@link PackBuilder} to generate data for.
     */
    default PackBuilder builtIn(String id, UnaryOperator<PackMetadataGenerator> withMetadata) {
        return this.builtIn(this.withId(id), withMetadata);
    }

    /**
     * Creates a built-in pack.
     *
     * @param id The identifier of the built-in pack.
     * @param withMetadata The metadata of the built-in pack.
     * @return The {@link PackBuilder} to generate data for.
     */
    PackBuilder builtIn(Identifier id, UnaryOperator<PackMetadataGenerator> withMetadata);
}
