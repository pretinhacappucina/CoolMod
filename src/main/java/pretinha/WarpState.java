package pretinha;

import java.util.HashSet;
import java.util.UUID;

public class WarpState {

    private static final HashSet<UUID> ACTIVE = new HashSet<>();

    public static void start(UUID id) {
        ACTIVE.add(id);
    }

    public static void stop(UUID id) {
        ACTIVE.remove(id);
    }

    public static boolean isActive(UUID id) {
        return ACTIVE.contains(id);
    }
}