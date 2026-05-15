package cn.gtemc.levelerbridge.hook;

import cn.gtemc.levelerbridge.api.LevelerProvider;

import java.util.Map;

final class Utils {

    private Utils() {}

    public static <P> void addToMap(LevelerProvider<P> provider, Map<String, LevelerProvider<P>> map) {
        map.put(provider.plugin(), provider);
    }

    public static boolean classExists(String className) {
        try {
            Class.forName(className.replace("{}", "."));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    public static float clamp(float value, float min, float max) {
        return value < min ? min : Math.min(value, max);
    }
}
