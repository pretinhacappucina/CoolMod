package pretinha;

import net.minecraft.block.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StructureSpawner {

    private static final Identifier LEAVEFOLT =
            Identifier.of("coolmod", "leavefolt");

    // 🔥 cooldown por jogador
    private static final Map<UUID, Long> COOLDOWN = new HashMap<>();

    private static final long COOLDOWN_TIME = 60 * 20 * 5; // 5 minutos (ticks)

    public static void trySpawn(ServerWorld world, BlockPos playerPos, UUID playerId) {

        if (!world.getRegistryKey().equals(ModDimensions.DIMENSIONS)) {
            return;
        }

        long now = world.getTime();

        // 🔥 cooldown check
        if (COOLDOWN.containsKey(playerId)) {
            long last = COOLDOWN.get(playerId);

            if (now - last < COOLDOWN_TIME) {
                return;
            }
        }

        COOLDOWN.put(playerId, now);

        spawnCluster(world, playerPos);
    }

    public static void spawnCluster(ServerWorld world, BlockPos playerPos) {

        StructureTemplate template =
                world.getStructureTemplateManager()
                        .getTemplate(LEAVEFOLT)
                        .orElse(null);

        if (template == null) {
            Coolmod.LOGGER.error("leavefolt.nbt não encontrado!");
            return;
        }

        Random random = Random.create();

        // 🔥 4 direções
        for (int dir = 0; dir < 4; dir++) {

            double baseAngle = dir * (Math.PI / 2);

            // 🔥 5 estruturas por direção = 20 total
            for (int i = 0; i < 5; i++) {

                double angle = baseAngle + (random.nextDouble() - 0.5) * 0.8;

                int distance = 200 + random.nextInt(301);

                int x = playerPos.getX() + (int)(Math.cos(angle) * distance);
                int z = playerPos.getZ() + (int)(Math.sin(angle) * distance);

                boolean placed = false;

                for (int y = 20; y < 120; y++) {

                    BlockPos pos = new BlockPos(x, y, z);

                    BlockPos ground = pos.down();

                    // precisa de chão sólido
                    if (!world.getBlockState(ground).isSolidBlock(world, ground)) {
                        continue;
                    }

                    // precisa de espaço
                    if (!world.isAir(pos)
                            || !world.isAir(pos.up())
                            || !world.isAir(pos.up(2))) {
                        continue;
                    }

                    StructurePlacementData placement =
                            new StructurePlacementData();

                    template.place(
                            world,
                            pos,
                            pos,
                            placement,
                            random,
                            2
                    );

                    // 🔥 base de obsidiana
                    int sx = template.getSize().getX();
                    int sz = template.getSize().getZ();

                    for (int dx = 0; dx < sx; dx++) {
                        for (int dz = 0; dz < sz; dz++) {

                            BlockPos p = pos.add(dx, -1, dz);

                            while (p.getY() > world.getBottomY()
                                    && world.isAir(p)) {

                                world.setBlockState(
                                        p,
                                        Blocks.OBSIDIAN.getDefaultState()
                                );

                                p = p.down();
                            }
                        }
                    }

                    // 🔥 beacon fix seguro
                    BeaconFix.fixBeaconsAround(world, pos, 20);

                    LeavefoltManager.add(pos);

                    placed = true;
                    break;
                }

                if (!placed) {
                    Coolmod.LOGGER.warn("Falhou spawn em cluster");
                }
            }
        }
    }
}