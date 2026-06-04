package pretinha;

import java.util.HashMap;
import java.util.UUID;

public class DimensionRunManager {

    private static final HashMap<UUID, Integer> RUN = new HashMap<>();

    public static void create(UUID uuid) {
        RUN.putIfAbsent(uuid, 0);
    }

    public static int get(UUID uuid) {
        return RUN.getOrDefault(uuid, 0);
    }

    public static void add(UUID uuid) {
        RUN.put(uuid, get(uuid) + 1);
    }

    // NOVO: opcional reset quando morrer/sair
    public static void reset(UUID uuid) {
        RUN.put(uuid, 0);
    }
}