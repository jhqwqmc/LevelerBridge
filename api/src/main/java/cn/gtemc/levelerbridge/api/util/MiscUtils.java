package cn.gtemc.levelerbridge.api.util;

import cn.gtemc.levelerbridge.api.LevelerProvider;

import java.util.Map;

public final class MiscUtils {

    private MiscUtils() {}

    public static boolean isRunningOnJava21() {
        try {
            int version = Runtime.version().feature();
            return version >= 21;
        } catch (Throwable e) {
            try {
                int version = Integer.parseInt(System.getProperty("java.version"));
                return version >= 21;
            } catch (Throwable t) {
                return false;
            }
        }
    }

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
