package pretinha;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDimensions {

    public static final RegistryKey<World> DIMENSIONAL_BARRIERS =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of("coolmod", "dimensional_barriers")
            );

    public static final RegistryKey<World> MINE_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of("coolmod", "mine_dimension")
            );
}