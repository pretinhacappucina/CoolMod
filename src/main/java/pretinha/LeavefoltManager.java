package pretinha;

import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class LeavefoltManager {

    public static final List<BlockPos> LEAVEFOLTS = new ArrayList<>();

    public static void add(BlockPos pos) {
        LEAVEFOLTS.add(pos.toImmutable());
    }
}