package pretinha;

import java.util.HashMap;
import java.util.UUID;

public class JumpManager {

    private static final HashMap<UUID, Integer> JUMPS = new HashMap<>();

    public static void create(UUID uuid) {
        JUMPS.putIfAbsent(uuid, 0);
    }

    public static int get(UUID uuid) {
        return JUMPS.getOrDefault(uuid, 0);
    }

    public static void add(UUID uuid) {
        JUMPS.put(uuid, get(uuid) + 1);
    }

    public static void remove(UUID uuid) {
        JUMPS.remove(uuid);
    }
}