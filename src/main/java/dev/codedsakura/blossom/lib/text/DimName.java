package dev.codedsakura.blossom.lib.text;

import dev.codedsakura.blossom.lib.BlossomLib;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.StringJoiner;

public class DimName {
    public static String get(ServerWorld world) {
        return get(world.getRegistryKey().getValue());
    }

    public static String get(Identifier dimensionIdentifier) {
        if (BlossomLib.CONFIG.dimNameOverrides != null) {
            String key = dimensionIdentifier.toString();
            if (BlossomLib.CONFIG.dimNameOverrides.containsKey(key)) {
                return BlossomLib.CONFIG.dimNameOverrides.get(key);
            }
        }

        StringJoiner joiner = new StringJoiner(" ");

        Arrays.stream(dimensionIdentifier.getPath().split("_"))
                .map(StringUtils::capitalize)
                .forEachOrdered(joiner::add);

        return joiner.toString();
    }

    public static String get(String dimensionIdentifier) {
        return get(new Identifier(dimensionIdentifier));
    }
}
