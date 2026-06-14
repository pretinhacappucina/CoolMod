package pretinha;

import java.util.HashMap;
import java.util.UUID;

public class SpeedManager {

    private static final HashMap<UUID, Integer> SPEED = new HashMap<>();

    public static void create(UUID uuid) {
        SPEED.putIfAbsent(uuid, 0);
    }

    public static int get(UUID uuid) {
        return SPEED.getOrDefault(uuid, 0);
    }

    public static void add(UUID uuid) {
        SPEED.put(uuid, get(uuid) + 1);
    }

    public static void remove(UUID uuid) {
        SPEED.remove(uuid);
    }
}