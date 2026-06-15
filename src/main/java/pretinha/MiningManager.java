package pretinha;

import java.util.HashMap;
import java.util.UUID;

public class MiningManager {

    private static final HashMap<UUID, Integer> MINING = new HashMap<>();

    public static void create(UUID uuid) {
        MINING.putIfAbsent(uuid, 0);
    }

    public static int get(UUID uuid) {
        return MINING.getOrDefault(uuid, 0);
    }

    public static void add(UUID uuid) {
        MINING.put(uuid, get(uuid) + 1);
    }
}