package pretinha;



import net.minecraft.server.world.ServerWorld;

import net.minecraft.structure.StructureTemplate;

import net.minecraft.structure.StructureTemplateManager;

import net.minecraft.structure.StructurePlacementData;

import net.minecraft.util.BlockMirror;

import net.minecraft.util.BlockRotation;

import net.minecraft.util.Identifier;

import net.minecraft.util.math.BlockPos;

import net.minecraft.util.math.ChunkPos;



public class MotherBarrierSpawner {



    private static final BlockPos SPAWN_POS = new BlockPos(0, 90, 0);



    // Faz a mensagem aparecer apenas uma vez.

    private static boolean alreadyLogged = false;



    public static void trySpawn(ServerWorld world) {



        // server.getWorld(...) pode retornar null se essa dimensão ainda

        // não foi carregada (ex: servidor recém-iniciado e nenhum jogador

        // entrou nela ainda). Sem essa checagem, o tick quebraria com NPE.

        if (world == null) {

            return;

        }



        if (!world.getRegistryKey().equals(ModDimensions.DIMENSIONAL_BARRIERS)) {

            return;

        }



        // Estado persistente: sobrevive a restart do servidor.

        MotherBarrierState state = MotherBarrierState.get(world);

        if (state.isGenerated()) {

            // Mostra essa mensagem apenas uma vez para não lotar o console.

            if (!alreadyLogged) {

                alreadyLogged = true;

                Coolmod.LOGGER.info("MOTHERBARRIER: já marcada como gerada (state.isGenerated() = true), pulando.");

            }

            return;

        }



        Coolmod.LOGGER.info("MOTHERBARRIER: tentando gerar...");



        StructureTemplateManager manager = world.getStructureTemplateManager();



        var optional = manager.getTemplate(Identifier.of("coolmod", "motherbarrier"));

        if (optional.isEmpty()) {

            Coolmod.LOGGER.info("MOTHERBARRIER NÃO ENCONTRADA");

            return;

        }



        StructureTemplate template = optional.get();



        // Garante que o chunk de destino esteja carregado antes de colocar a estrutura.

        ChunkPos chunkPos = new ChunkPos(SPAWN_POS);

        world.getChunk(chunkPos.x, chunkPos.z);



        StructurePlacementData placement = new StructurePlacementData()

                .setRotation(BlockRotation.NONE)

                .setMirror(BlockMirror.NONE)

                .setIgnoreEntities(false);



        boolean success = template.place(

                world,

                SPAWN_POS,

                SPAWN_POS,

                placement,

                world.getRandom(),

                2

        );



        if (!success) {

            Coolmod.LOGGER.info("MOTHERBARRIER: place() retornou false, tentando novamente na próxima chamada");

            return; // não marca como gerado; vai tentar de novo na próxima vez que trySpawn for chamado

        }



        state.setGenerated(true);



        Coolmod.LOGGER.info("MOTHERBARRIER GERADA em " + SPAWN_POS);

    }

}