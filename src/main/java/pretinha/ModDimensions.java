package pretinha;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDimensions {

    // sua dimensão original
    public static final RegistryKey<World> DIMENSIONAL_BARRIERS =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of("coolmod", "dimensional_barriers")
            );

    // nova dimensão (Craftmine system)
    public static final RegistryKey<World> DIMENSIONS =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of("coolmod", "dimensions")
            );
}