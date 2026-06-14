package pretinha;

import java.util.HashMap;
import java.util.UUID;

public class StrengthManager {

    private static final HashMap<UUID, Integer> STRENGTH = new HashMap<>();

    public static void create(UUID uuid) {
        STRENGTH.putIfAbsent(uuid, 0);
    }

    public static int get(UUID uuid) {
        return STRENGTH.getOrDefault(uuid, 0);
    }

    public static void add(UUID uuid) {
        STRENGTH.put(uuid, get(uuid) + 1);
    }
}