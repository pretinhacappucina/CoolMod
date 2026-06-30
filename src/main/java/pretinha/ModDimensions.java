package pretinha;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

public class ModDimensions {

    public static final RegistryKey<World> DIMENSIONAL_BARRIERS =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "dimensional_barriers")
            );
    public static final RegistryKey<World> LOVE_DIMENSION =
            RegistryKey.of(RegistryKeys.WORLD, Identifier.of("coolmod", "love_dimension"));

    public static final RegistryKey<World> MINE_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "mine_dimension")
            );
    public static final RegistryKey<World> STRONGER_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of("coolmod", "stronger_dimension")
            );
    public static final RegistryKey<World> PARKOUR_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of("coolmod", "parkour_dimension")
            );
    public static final RegistryKey<World> SPEEDRUN_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of("coolmod", "speedrun_dimension")
            );
    public static final RegistryKey<World> SKY_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "sky_dimension")
            );
    public static final RegistryKey<World> SCULK_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "sculk_dimension")

            );
    public static final RegistryKey<World> LUSH_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "lush_dimension")
            );
    public static final RegistryKey<World> WATER_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "water_dimension")
            );
    public static final RegistryKey<World> LAVA_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "lava_dimension")
            );
    public static final RegistryKey<World> TNT_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "tnt_dimension")
            );
    public static final RegistryKey<World> DESERT_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "desert_dimension")
            );
    public static final RegistryKey<World> EXIT_DIMENSION =
            RegistryKey.of(
                    RegistryKeys.WORLD,
                    Identifier.of(Coolmod.MOD_ID, "exit_dimension")



            );
}