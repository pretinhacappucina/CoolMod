package pretinha;

import java.util.HashSet;
import java.util.Set;

public class RunStructureManager {

    private static final Set<Integer> GENERATED =
            new HashSet<>();

    public static boolean generated(int run) {
        return GENERATED.contains(run);
    }

    public static void markGenerated(int run) {
        GENERATED.add(run);
    }
}