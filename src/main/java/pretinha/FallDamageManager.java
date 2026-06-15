package pretinha;

import java.util.HashMap;
import java.util.UUID;

public class FallDamageManager {

    private static final HashMap<UUID, Boolean> DATA = new HashMap<>();

    public static void create(UUID uuid) {
        DATA.putIfAbsent(uuid, false);
    }

    public static boolean has(UUID uuid) {
        return DATA.getOrDefault(uuid, false);
    }

    public static void enable(UUID uuid) {
        DATA.put(uuid, true);
    }

    public static void disable(UUID uuid) {
        DATA.put(uuid, false);
    }
}