package cn.gtemc.levelerbridge.hook;

import cn.gtemc.levelerbridge.api.LevelerBridgeException;
import cn.gtemc.levelerbridge.api.LevelerProvider;
import cn.gtemc.levelerbridge.api.util.MiscUtils;
import cn.gtemc.levelerbridge.hook.provider.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public final class HookHelper {
    static Supplier<Map<String, LevelerProvider<Player>>> j21ProvidersGetter;

    static {
        if (MiscUtils.isRunningOnJava21()) {
            try {
                MethodHandles.lookup().ensureInitialized(Class.forName("cn.gtemc.levelerbridge.hook.J21HookHelper"));
            } catch (ReflectiveOperationException e) {
                throw new LevelerBridgeException("Failed to load builtin providers", e);
            }
        } else {
            j21ProvidersGetter = Map::of;
        }
    }

    private HookHelper() {}

    static boolean hasPlugin(String plugin) {
        return Bukkit.getPluginManager().getPlugin(plugin) != null;
    }

    public static Map<String, LevelerProvider<Player>> getSupportedPlugins() {
        Map<String, LevelerProvider<Player>> providers = new HashMap<>(j21ProvidersGetter.get());
        MiscUtils.addToMap(MinecraftLevelerProvider.INSTANCE, providers);
        if (hasPlugin("AuraSkills")) {
            MiscUtils.addToMap(AuraSkillsLevelerProvider.INSTANCE, providers);
        }
        if (hasPlugin("AureliumSkills")) {
            MiscUtils.addToMap(AureliumSkillsLevelerProvider.INSTANCE, providers);
        }
        if (hasPlugin("Jobs")) {
            MiscUtils.addToMap(JobsRebornLevelerProvider.INSTANCE, providers);
        }
        if (hasPlugin("McMMO")) {
            MiscUtils.addToMap(McMMOLevelerProvider.INSTANCE, providers);
        }
        if (hasPlugin("MMOCore")) {
            MiscUtils.addToMap(MMOCoreLevelerProvider.INSTANCE, providers);
        }
        return providers;
    }
}
