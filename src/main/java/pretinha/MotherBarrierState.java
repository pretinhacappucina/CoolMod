package pretinha;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.datafixer.DataFixTypes;

/**
 * Guarda, de forma persistente (salvo em disco junto do save do mundo),
 * se a Motherbarrier já foi gerada.
 *
 * Isso evita duplicação caso o servidor seja reiniciado: uma variável
 * "static boolean" comum NÃO sobrevive a um restart e faria a estrutura
 * ser colocada novamente por cima da anterior.
 */
public class MotherBarrierState extends PersistentState {

    private static final String KEY_GENERATED = "generated";

    public static final PersistentState.Type<MotherBarrierState> TYPE =
            new PersistentState.Type<>(
                    MotherBarrierState::new,           // construtor (estado novo, "generated = false")
                    MotherBarrierState::createFromNbt,  // deserializer (lendo do disco): (NbtCompound, RegistryWrapper.WrapperLookup) -> T
                    DataFixTypes.LEVEL                  // tipo de data fixer (genérico, serve para este caso)
            );

    private boolean generated = false;

    public MotherBarrierState() {
    }

    public static MotherBarrierState createFromNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        MotherBarrierState state = new MotherBarrierState();
        state.generated = nbt.getByte(KEY_GENERATED) != 0;
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registries) {
        nbt.putBoolean(KEY_GENERATED, generated);
        return nbt;
    }

    public boolean isGenerated() {
        return generated;
    }

    public void setGenerated(boolean value) {
        this.generated = value;
        this.markDirty();
    }

    public static MotherBarrierState get(ServerWorld world) {
        return world.getPersistentStateManager().getOrCreate(TYPE, "coolmod_motherbarrier");
    }
}