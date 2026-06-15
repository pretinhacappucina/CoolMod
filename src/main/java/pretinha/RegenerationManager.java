package pretinha;

import java.util.HashMap;
import java.util.UUID;

public class RegenerationManager {

    private static final HashMap<UUID, Integer> REGEN = new HashMap<>();

    public static void create(UUID uuid) {
        REGEN.putIfAbsent(uuid, 0);
    }

    public static int get(UUID uuid) {
        return REGEN.getOrDefault(uuid, 0);
    }

    public static void add(UUID uuid) {
        REGEN.put(uuid, get(uuid) + 1);
    }
}